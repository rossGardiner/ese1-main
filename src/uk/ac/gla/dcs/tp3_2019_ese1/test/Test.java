package uk.ac.gla.dcs.tp3_2019_ese1.test;

import java.util.Scanner;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.DaqDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard.USB_1608FS;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.ADCRange;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.DaqDeviceInterface;

public class Test {
	
	private static final int ACCELEROMETER_IN = 1;

	public static void main(String[] args) throws LibcbwException {
        System.setProperty("jna.library.path", "D:/Measurement Computing/DAQ/");
        
		try (Scanner scanner = new Scanner(System.in)) {
			/*DaqDeviceDescriptor[] devs = DaqDeviceDescriptor.getDaqDeviceInventory(DaqDeviceInterface.USB_IFC, 16);
			for (int i = 0; i < devs.length; i++) {
				System.out.println("" + i + " : " + devs[i]);
			}
			System.out.println();
			System.out.print("Enter device index : ");

			try(USB_1608FS board = devs[scanner.nextInt()].createDaqDevice(USB_1608FS::new)) {*/
			try(USB_1608FS board = new USB_1608FS(0)) {
				for(int i = 0; i < 120; i++) {
					System.out.println("" + board.analogueInRaw(ACCELEROMETER_IN, ADCRange.BIP5VOLTS));
					try {
						Thread.sleep(500);
					} catch (InterruptedException ignore) {}
				}
			}
		}
	}

}
