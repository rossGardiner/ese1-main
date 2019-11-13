package uk.ac.gla.dcs.tp3_2019_ese1.test;

import java.util.Scanner;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.DaqDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard.USB_1608FS;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.ADCRange;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.EventType;

public class Test {

    private static final int ACCELEROMETER_IN = 1;
    private static final int MAGNET_OUT = 0;

    public static void main(String[] args) throws LibcbwException {
        System.setProperty("jna.library.path", "D:/Measurement Computing/DAQ/");

        try(Scanner scanner = new Scanner(System.in)) {
            DaqDeviceDescriptor dev;
            if(!LibcbwJNA.USE_INSTACAL) {
                DaqDeviceDescriptor[] devs = DaqDeviceDescriptor.getDaqDeviceInventory(DaqDeviceDescriptor.USB_IFC, 16);
                for(int i = 0; i < devs.length; i++) {
                    System.out.println("" + i + " : " + devs[i]);
                }
                System.out.println();
                System.out.print("Enter device index : ");

                dev = devs[scanner.nextInt()];
            }
            try(USB_1608FS board = LibcbwJNA.USE_INSTACAL ? new USB_1608FS(0)
                    : dev.createDaqDevice(USB_1608FS.class/* ::new */)) {
                board.digitalOut(MAGNET_OUT, true);
                System.out.println("Holding...");
                scanner.next();
                board.enableEvent(EventType.ON_END_OF_INPUT_SCAN, (b, t, d) -> {
                    System.out.println("Samples : " + d);
                    int[] acc = board.analogueInStopAsync()[0];
                    for(int a : acc) System.out.println("" + a);
                });
                System.out.println("Reading...");
                board.analogueInStartAsync(ACCELEROMETER_IN, 1, ADCRange.BIP5VOLTS, 16384, 9600, 0);
                System.out.println("Dropping...");
                board.digitalOut(MAGNET_OUT, false);
                try {
                    Thread.sleep(5000);
                } catch(InterruptedException ignore) {}
            }
        }
    }

}
