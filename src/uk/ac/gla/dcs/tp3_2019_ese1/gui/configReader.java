package uk.ac.gla.dcs.tp3_2019_ese1.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class configReader {

	public static Map<String, double[]> parseCSV() throws IOException {
		Map<String, double[]> rigs = new HashMap<String, double[]>();
		String line;

		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("calibration.csv"));
			/*
			 * First readline discards file headers
			 */
			line = csvReader.readLine();
			while ((line = csvReader.readLine()) != null) {
				String[] data = line.strip().split(",");
				/*
				 * Parse numbers
				 */
				double[] values = new double[7];
				for (int i = 1; i <= 7; i++) {
					values[i - 1] = Double.valueOf(data[i]);
				}

				rigs.put(data[0], values);
			}
			csvReader.close();
		} catch (Exception FileNotFoundException) {
			System.out.println("calibration.csv file missing from program directory.");
		}

		return rigs;
	}
}
