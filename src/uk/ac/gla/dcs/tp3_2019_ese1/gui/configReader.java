package uk.ac.gla.dcs.tp3_2019_ese1.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class configReader {

	public static Map<String, Double[]> parseCSV() throws IOException {
		Map<String, Double[]> rigs = new HashMap<String, Double[]>();
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
				Double[] values = new Double[7];
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
