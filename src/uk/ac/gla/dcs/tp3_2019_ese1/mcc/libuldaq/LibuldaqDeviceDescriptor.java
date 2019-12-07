package uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq;

import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * A device descriptor that can be used to retrieve a {@link DaqDeviceHandle}
 * object.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class LibuldaqDeviceDescriptor extends Structure {
    public static final List<String> FIELDS = createFieldsOrder("productName", "productId", "devInterface",
            "devString", "uniqueId", "reserved");

    public byte[] productName = new byte[64];
    public int productId;
    public int devInterface;
    public byte[] devString = new byte[64];
    public byte[] uniqueId = new byte[64];
    public byte[] reserved = new byte[512];


    /**
     * Functional interface to allow arbitrary subclasses of {@link DaqDeviceHandle} to
     * be instantiated by
     * {@link LibuldaqDeviceDescriptor#createDaqDevice(BoardConstructor)}
     * 
     * @author Duncan Lowther (2402789L)
     */
    @FunctionalInterface
    public static interface BoardConstructor<T extends DaqDeviceHandle> {
        public T apply(long handle) throws LibuldaqException;
    }

    /**
     * Uses this descriptor to create a {@link DaqDeviceHandle} object that can then be
     * used to perform various operations.
     * 
     * @param constructor - a method reference to the constructor for the desired
     *                    subclass of {@link DaqDeviceHandle}
     * @return the new {@link DaqDeviceHandle} object.
     * @throws LibuldaqException if an error occurs.
     */
    public <T extends DaqDeviceHandle> T createDaqDevice(BoardConstructor<T> constructor) throws LibuldaqException {
        return constructor.apply(LibuldaqJNA.ulCreateDaqDevicePtr(this));
    }

    /**
     * Identify all valid devices currently connected.
     * 
     * @param ifaceType - which type(s) of devices should be returned (one or more of {@link #USB_IFC},
     *                    {@link #ETHERNET_IFC}, {@link #BLUETOOTH_IFC}, and {@link #ANY_IFC})
     * @param maxDev    - the maximum number of devices to return
     * @return an array containing descriptors of all valid devices currently
     *         connected.
     * @throws LibuldaqException if an error occurs.
     */
    public static LibuldaqDeviceDescriptor[] getDaqDeviceInventory(int ifaceType, int maxDev) throws LibuldaqException {
        IntByReference nDev = new IntByReference(maxDev);
        LibuldaqDeviceDescriptor[] buf = (LibuldaqDeviceDescriptor[]) (new LibuldaqDeviceDescriptor()).toArray(maxDev);

        int err = LibuldaqJNA.ulGetDaqDeviceInventory(ifaceType, buf[0], nDev);
        if(err != LibuldaqException.NO_ERROR) throw LibuldaqException.fromErrorCode(err);

        LibuldaqDeviceDescriptor[] ret = new LibuldaqDeviceDescriptor[nDev.getValue()];
        System.arraycopy(buf, 0, ret, 0, nDev.getValue());
        return ret;
    }
    
    
    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }

    
    @Override
    public String toString() {
        return "[" + String
                .join(",", new String(productName).trim(), new String(devString).trim(), new String(uniqueId)).trim()
                + "]";
    }
}
