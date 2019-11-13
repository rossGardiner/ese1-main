package uk.ac.gla.dcs.tp3_2019_ese1.libcbw;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * A device descriptor that can be used to retrieve a {@link LibcbwBoard}
 * object.
 * 
 * @author Duncan Lowther (2402789L)
 */
public class DaqDeviceDescriptor extends Structure {
    public static class ByReference extends DaqDeviceDescriptor implements Structure.ByReference {
    }

    public static class ByValue extends DaqDeviceDescriptor implements Structure.ByValue {
    }

    public static final List<String> FIELDS = createFieldsOrder("ProductName", "ProductID", "InterfaceType",
            "DevString", "UniqueID", "NUID", "Reserved");

    public byte[] ProductName = new byte[64];
    public int ProductID;
    public int InterfaceType;
    public byte[] DevString = new byte[64];
    public byte[] UniqueID = new byte[64];
    public long NUID;
    public byte[] Reserved = new byte[512];

    private static int _next_id = 10;

    /**
     * Functional interface to allow arbitrary subclasses of {@link LibcbwBoard} to
     * be instantiated by
     * {@link DaqDeviceDescriptor#createDaqDevice(BoardConstructor)}
     * 
     * @author Duncan Lowther (2402789L)
     */
    @FunctionalInterface
    public static interface BoardConstructor<T extends LibcbwBoard> {
        public T apply(int boardnum) throws LibcbwException;
    }

    public static final int USB_IFC = (1 << 0);
    public static final int BLUETOOTH_IFC = (1 << 1);
    public static final int ETHERNET_IFC = (1 << 2);
    public static final int ANY_IFC = (USB_IFC | BLUETOOTH_IFC | ETHERNET_IFC);


    @Override
    protected List<String> getFieldOrder() {
        return FIELDS;
    }

    /**
     * Uses this descriptor to create a {@link LibcbwBoard} object that can then be
     * used to perform various operations.
     * 
     * @param constructor - a method reference to the constructor for the desired
     *                    subclass of {@link LibcbwBoard}
     * @return the new {@link LibcbwBoard} object.
     * @throws LibcbwException if an error occurs.
     */
    public <T extends LibcbwBoard> T createDaqDevice(BoardConstructor<T> constructor) throws LibcbwException {
        ByValue dis = (this instanceof ByValue) ? (ByValue) this
                : Structure.newInstance(ByValue.class, this.getPointer());
        int bnum = LibcbwJNA.cbGetBoardNumber(dis);
        if(bnum > -1) return constructor.apply(bnum);
        int err = LibcbwJNA.cbCreateDaqDevice(_next_id, dis);
        if(err != LibcbwException.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
        return constructor.apply(_next_id++);
    }

    /**
     * Uses this descriptor to create a {@link LibcbwBoard} object that can then be
     * used to perform various operations.
     * 
     * @param clazz - the desired subclass of {@link LibcbwBoard}
     * @return the new {@link LibcbwBoard} object.
     * @throws LibcbwException if an error occurs.
     */
    public <T extends LibcbwBoard> T createDaqDevice(Class<T> clazz) throws LibcbwException {
        try {
            ByValue dis = (this instanceof ByValue) ? (ByValue) this
                    : Structure.newInstance(ByValue.class, this.getPointer());
            int bnum = LibcbwJNA.cbGetBoardNumber(dis);
            if(bnum > -1) return clazz.getDeclaredConstructor(int.class).newInstance(bnum);
            int err = LibcbwJNA.cbCreateDaqDevice(_next_id, dis);
            if(err != LibcbwException.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);
            return clazz.getDeclaredConstructor(int.class).newInstance(_next_id++);
        } catch(InvocationTargetException ex) {
            Throwable ex1 = ex.getCause();
            if(ex1 instanceof LibcbwException) throw (LibcbwException) ex1;
            else if(ex1 instanceof RuntimeException) throw (RuntimeException) ex1;
            else if(ex1 instanceof Error) throw (Error) ex1;
            else {
                /*
                 * TODO Something bad happened -- Board constructors should not throw checked
                 * exceptions other than LibcbwException
                 */
                throw new RuntimeException(ex);
            }
        } catch(IllegalAccessException | InstantiationException | NoSuchMethodException ex) {
            /*
             * TODO Something bad happened -- Board constructors should be instantiable,
             * with a valid constructor accepting an int.
             */
            throw new RuntimeException(ex);
        }
    }

    /**
     * Identify all valid devices currently connected.
     * 
     * @param ifaceType - which type(s) of devices should be returned (one or more of {@link #USB_IFC},
     *                    {@link #ETHERNET_IFC}, {@link #BLUETOOTH_IFC}, and {@link #ANY_IFC})
     * @param maxDev    - the maximum number of devices to return
     * @return an array containing descriptors of all valid devices currently
     *         connected.
     * @throws LibcbwException if an error occurs.
     */
    public static DaqDeviceDescriptor[] getDaqDeviceInventory(int ifaceType, int maxDev) throws LibcbwException {
        IntByReference nDev = new IntByReference(maxDev);
        DaqDeviceDescriptor[] buf = (DaqDeviceDescriptor[]) (new DaqDeviceDescriptor()).toArray(maxDev);

        int err = LibcbwJNA.cbGetDaqDeviceInventory(ifaceType, buf[0], nDev);
        if(err != LibcbwException.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        DaqDeviceDescriptor[] ret = new DaqDeviceDescriptor[nDev.getValue()];
        System.arraycopy(buf, 0, ret, 0, nDev.getValue());
        return ret;
    }

    /**
     * Identify a network-connected device by its hostname or IP address.
     * 
     * @param host    - the hostname or IP address of the device.
     * @param port    - the port on which to connect.
     * @param timeout - how long to wait for the device to respond.
     * @return a descriptor of the device at the specified address.
     * @throws LibcbwException if an error occurs.
     */
    public static DaqDeviceDescriptor getNetDeviceDescriptor(String host, int port, int timeout)
            throws LibcbwException {
        DaqDeviceDescriptor ret = new DaqDeviceDescriptor();

        int err = LibcbwJNA.cbGetNetDeviceDescriptor(host, port, ret, timeout);
        if(err != LibcbwException.ErrorCode.NOERRORS) throw LibcbwException.fromErrorCode(err);

        return ret;
    }

    @Override
    public String toString() {
        return "[" + String
                .join(",", new String(ProductName).trim(), new String(DevString).trim(), new String(UniqueID)).trim()
                + "]";
    }
}
