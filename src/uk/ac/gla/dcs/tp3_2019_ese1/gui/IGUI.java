package uk.ac.gla.dcs.tp3_2019_ese1.gui;

public interface IGUI {

    void makeGraphs(double[] acceleration, double[] velocity, double[] disp, int drop_touch2, int testNr);
    void outputResults(double peakG, double fmax, double fred, double v1, double v2, double energy, double drop_dist,
            double spring, double material, int testNr);
    void displayErrorMessage(String msg);
    
}
