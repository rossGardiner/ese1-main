package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * A device descriptor that can be used to retrieve a {@link LibcbwBoard} object.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class DaqDeviceDescriptor extends Structure {
	public static class ByReference extends DaqDeviceDescriptor implements Structure.ByReference {}
	public static class ByValue extends DaqDeviceDescriptor implements Structure.ByValue {}

    public static final List<String> FIELDS = createFieldsOrder("ProductName", "ProductID",
    		"InterfaceType", "DevString", "UniqueID", "NUID", "Reserved");
    
	public byte[] ProductName = new byte[64];
	public int ProductID;
	public int InterfaceType;
	public byte[] DevString = new byte[64];
	public byte[] UniqueID = new byte[64];
	public long NUID;
	public byte[] Reserved = new byte[512];
	
	private static int _next_id = 10;

	/**
	 * Functional interface to allow arbitrary subclasses of {@link LibcbwBoard} to be instantiated
	 * by {@link DaqDeviceDescriptor#createDaqDevice(BoardConstructor)}
	 * 
	 * @author Duncan Lowther (2402789L)
	 */
	@FunctionalInterface
	public static interface BoardConstructor {
		public LibcbwBoard apply(int boardnum) throws LibcbwException;
	}
	
	@Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }
	
	/**
	 * Uses this descriptor to create a {@link LibcbwBoard} object that can then be used to
	 * perform various operations.
	 * 
	 * @param constructor - a method reference to the constructor for the desired subclass of
	 *                      {@link LibcbwBoard}
	 * @return the new {@link LibcbwBoard} object.
	 * @throws LibcbwException if an error occurs.
	 */
	public LibcbwBoard createDaqDevice(BoardConstructor constructor) throws LibcbwException {
		int bnum = LibcbwJNA.cbGetBoardNumber((ByValue) this);
		if(bnum > -1) return constructor.apply(bnum);
		int err = LibcbwJNA.cbCreateDaqDevice(_next_id, (ByValue) this);
		if(err != LibcbwJNA.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
		return constructor.apply(_next_id++);
	}
	
	/**
	 * Identify all valid devices currently connected.
	 * 
	 * @param ifaceType - a constant from {@link LibcbwJNA.DaqDeviceInterface} indicating which type(s)
	 *                    of devices should be returned
	 * @param maxDev - the maximum number of devices to return
	 * @return an array containing descriptors of all valid devices currently connected.
	 * @throws LibcbwException if an error occurs.
	 */
	public static DaqDeviceDescriptor[] getDaqDeviceInventory(int ifaceType, int maxDev) throws LibcbwException {
		DaqDeviceDescriptor[] buf = new DaqDeviceDescriptor[maxDev];
		IntByReference nDev = new IntByReference(maxDev);
		
		int err = LibcbwJNA.cbGetDaqDeviceInventory(ifaceType, buf, nDev);
		if(err != LibcbwJNA.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
		
		DaqDeviceDescriptor[] ret = new DaqDeviceDescriptor[nDev.getValue()];
		System.arraycopy(buf, 0, ret, 0, nDev.getValue());
		return ret;
	}
	
	/**
	 * Identify a network-connected device by its hostname or IP address.
	 * 
	 * @param host - the hostname or IP address of the device.
	 * @param port - the port on which to connect.
	 * @param timeout - how long to wait for the device to respond.
	 * @return a descriptor of the device at the specified address.
	 * @throws LibcbwException if an error occurs.
	 */
	public static DaqDeviceDescriptor getNetDeviceDescriptor(String host, int port, int timeout) throws LibcbwException {
		DaqDeviceDescriptor ret = new DaqDeviceDescriptor();
		
		int err = LibcbwJNA.cbGetNetDeviceDescriptor(host, port, ret, timeout);
		if(err != LibcbwJNA.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
		
		return ret;
	}
}
