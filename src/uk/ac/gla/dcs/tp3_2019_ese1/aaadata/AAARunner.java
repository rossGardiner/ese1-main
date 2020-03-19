package uk.ac.gla.dcs.tp3_2019_ese1.aaadata;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import uk.ac.gla.dcs.tp3_2019_ese1.gui.IGUI;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard.USB_1608FS;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.ADCRange;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwJNA.EventType;

public class AAARunner {

    private static final int ACCELEROMETER_IN = 1;
    public static final int MAGNET_OUT = 0;

    private static final double SCALING_5V = (5.0 / 65536.0);
    private static final double GVALUE = 9.8;
    private static final double CONCRETE = 6760.0;
    
    private final USB_1608FS _board;
    private final IGUI _gui;
    private int _testNr = 0; //added 19/01/2020 by RG
    
    /*
     * the following 7 values are updated from the CSV file
     */
    public double MASS = 20;
    public double SPRINGCAL = 2043.36; /* XXX not this */
    public double GAIN_CALI = 0.2299428; //modified 16/03/2020 - (calibration * gain) / 2
    public int SAMPLE_RATE = 50000; /* Hz */
    public int SAMPLE_COUNT = 32768;
    public int PRE_DROP_CNT = 256;
    public int SAFETY_MARGIN = 300;
    
    public AAARunner(USB_1608FS board, IGUI gui) {
        _board = board;
        _gui = gui;
    }
    
    /**
     * Run a single AAA test.
     * 
     * @param evt - unused within the function, but makes a reference to this
     *            function a valid ActionListener
     */
    public void runTest(ActionEvent evt) {
    	try {
            _board.enableEvent(EventType.ON_END_OF_INPUT_SCAN, (b, t, d) -> {
                double volts_per_g = GAIN_CALI / 10 * 5; /* V/G ? */

                int[] raw = _board.analogueInStopAsync()[0];
                int offset = Arrays.stream(raw).limit(PRE_DROP_CNT).sum() / PRE_DROP_CNT;
                double[] acceleration = Arrays.stream(raw).mapToDouble(r -> SCALING_5V * (r - offset) / volts_per_g).toArray();
                //analyseResults(acceleration);
                analyseResults(applyLegacyFilter(acceleration));
                _testNr++;
                try {
                    _board.disableEvent(EventType.ON_END_OF_INPUT_SCAN);
                    _board.digitalOut(MAGNET_OUT, true);
                } catch(LibcbwException ex) {
                    ex.printStackTrace();
                }
            });
            System.out.println("Reading...");
            _board.analogueInStartAsync(ACCELEROMETER_IN, 1, ADCRange.BIP5VOLTS, SAMPLE_COUNT, SAMPLE_RATE, 0);
            System.out.println("Dropping...");
            _board.digitalOut(MAGNET_OUT, false);
            } catch(LibcbwException ex) {
            	ex.printStackTrace();
            }
        }
    
    /**
     *  Hack -- in-place digital filter functionally identical to the legacy code but
     *  still this is black magic.
     *  @param acc - the dataset to filter
     *  @return <code>acc</code>, after filtering
     */
    private static double[] applyLegacyFilter(double[] acc) {
        double x = 0.0, prevx = 0.0, y = 0.0, prevy = 0.0;
        for(int i = 0; i < acc.length - 1; i++) {
            x = acc[i];
            acc[i] = (acc[i+1] + 2*x + prevx) / 1.777837895e+04F
                    + 1.9786749573F*y - 0.9788999497F*prevy;
            prevx = x;
            prevy = y;
            y = acc[i];
        }
        return acc;
    }
    
    /**
     * Callback for analysis of AAA test results.
     * 
     * @param acceleration - the array of acceleration samples (in g's), after filtering and conversion.
     */
    private void analyseResults(double[] acceleration) {
        int drop_start = SAFETY_MARGIN;
        while(drop_start < acceleration.length && acceleration[drop_start] >= 0.0) drop_start++;

        int drop_final = drop_start;
        while(drop_final < (acceleration.length - SAFETY_MARGIN) && acceleration[drop_final] < 0.5) drop_final++;
        while(drop_final < (acceleration.length - SAFETY_MARGIN) && acceleration[drop_final] > 0.1) drop_final++;
        
        drop_start -= SAFETY_MARGIN;
        drop_final += SAFETY_MARGIN;
        
        if(drop_start == acceleration.length) {
            _gui.displayErrorMessage("ERROR : Nonsensical test data : No samples show downward acceleration");
            return;
        } else if(drop_final == acceleration.length) {
            _gui.displayErrorMessage("Warning : Small or nonexistant safety margine at end of test");
        }
        
        acceleration = Arrays.copyOfRange(acceleration, drop_start, drop_final);

        double peakG = Arrays.stream(acceleration).max().orElse(0.0);

        double fmax = (peakG + 1.0) * MASS * GVALUE;
        double fred = 1 - fmax / CONCRETE;

        double[] velocity = new double[acceleration.length];
        double[] disp = new double[velocity.length];

        velocity[0] = acceleration[0] * GVALUE / SAMPLE_RATE;
        disp[0] = velocity[0] * 1000.0 / SAMPLE_RATE;

        double v1 = velocity[0], v2 = velocity[0];
        int drop_touch = 0, drop_touch2 = 0;

        for(int i = 1; i < velocity.length; i++) {
            velocity[i] = velocity[i - 1] + acceleration[i] * GVALUE / SAMPLE_RATE;
            disp[i] = disp[i - 1] + velocity[i] * 1000.0 / SAMPLE_RATE;

            if(velocity[i] < v1) {
                drop_touch = i;
                v1 = velocity[i];
            } else if(velocity[i] > v2) {
                drop_touch2 = i;
                v2 = velocity[i];
            }
        }

        _gui.makeGraphs(acceleration, velocity, disp,drop_touch2, _testNr);

        double drop_dist = disp[drop_touch];
        double drop_total = Arrays.stream(disp).limit(drop_touch2).skip(drop_touch).max().orElse(0.0);

        if(v1 == 0.0) v1 = 99.9; /* XXX -- to avoid divide-by-zero I think? */

        double energy = v2 * v2 / (v1 * v1) * 100.0D;
        double spring = -fmax / SPRINGCAL;
        double material = -(drop_total - drop_dist - spring);

        _gui.outputResults(peakG, fmax, fred, v1, v2, energy, drop_dist, spring, material, _testNr);
    }
}
