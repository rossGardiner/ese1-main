package uk.ac.gla.dcs.tp3_2019_ese1.test;

import java.util.Scanner;

import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libcbw.LibcbwBoard;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libcbw.LibcbwDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libcbw.LibcbwJNA;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq.DaqDeviceHandle;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.libuldaq.LibuldaqDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.DaqDeviceInterface;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.EnumADCRange;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.EnumEventType;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.EnumScanFlags;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.IUSB_1608FS;
import uk.ac.gla.dcs.tp3_2019_ese1.mcc.shared.MCCException;

public class Test {

    private static final int ACCELEROMETER_IN = 1;
    private static final int MAGNET_OUT = 0;

    public static void main(String[] args) throws MCCException {
        try(Scanner scanner = new Scanner(System.in); IUSB_1608FS board = getBoard(scanner)) {
            board.digitalOut(MAGNET_OUT, true);
            System.out.println("Holding...");
            scanner.next();
            board.enableEvent(EnumEventType.ON_END_OF_INPUT_SCAN, (b, t, d) -> {
                System.out.println("Samples : " + d);
                double[] acc = board.analogueInStopAsync()[0];
                for(double a : acc) System.out.println("" + a);
            });
            System.out.println("Reading...");
            board.analogueInStartAsync(ACCELEROMETER_IN, 1, EnumADCRange.BIP5VOLTS, 16384, 9600, EnumScanFlags.DEFAULTIO);
            System.out.println("Dropping...");
            board.digitalOut(MAGNET_OUT, false);
            try {
                Thread.sleep(5000);
            } catch(InterruptedException ignore) {}
        }
    }

    private static IUSB_1608FS getBoard(Scanner scanner) throws MCCException {
        if(System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.setProperty("jna.library.path", "D:/Measurement Computing/DAQ/");

            if(LibcbwJNA.USE_INSTACAL) return new LibcbwBoard.USB_1608FS(0);

            LibcbwDeviceDescriptor[] devs = LibcbwDeviceDescriptor.getDaqDeviceInventory(DaqDeviceInterface.USB_IFC,
                    16);
            for(int i = 0; i < devs.length; i++) {
                System.out.println("" + i + " : " + devs[i]);
            }
            System.out.println();
            System.out.print("Enter device index : ");
            return devs[scanner.nextInt()].createDaqDevice(LibcbwBoard.USB_1608FS::new);
        } else {
            LibuldaqDeviceDescriptor[] devs = LibuldaqDeviceDescriptor.getDaqDeviceInventory(DaqDeviceInterface.USB_IFC,
                    16);
            for(int i = 0; i < devs.length; i++) {
                System.out.println("" + i + " : " + devs[i]);
            }
            System.out.println();
            System.out.print("Enter device index : ");
            return devs[scanner.nextInt()].createDaqDevice(DaqDeviceHandle.USB_1608FS::new);
        }
    }

}
