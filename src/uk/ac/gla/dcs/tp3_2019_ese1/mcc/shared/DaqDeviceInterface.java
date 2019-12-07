package uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared;

public final class DaqDeviceInterface {
    private DaqDeviceInterface(){}

    public static final int USB_IFC = (1 << 0);
    public static final int BLUETOOTH_IFC = (1 << 1);
    public static final int ETHERNET_IFC = (1 << 2);
    public static final int ANY_IFC = (USB_IFC | BLUETOOTH_IFC | ETHERNET_IFC);
}
