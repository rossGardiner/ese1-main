package uk.ac.gla.dcs.tp3_2019_ese1.test;



import uk.ac.gla.dcs.tp3_2019_ese1.gui.MainGUI;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;

public class Test {

    private static final int ACCELEROMETER_IN = 1;
    private static final int MAGNET_OUT = 0;

    public static void main(String[] args) throws LibcbwException {
        testGUI();
    }
    private static void testGUI() {
		// TODO Auto-generated method stub
		//boolean assertionBool = true;
    	MainGUI gui = new MainGUI();
    	boolean guiPassed = gui.getInitSucc();
    	if(guiPassed) {
    	//assertEquals(assertionBool,guiBOOl);
    		System.out.print("GUI initialization test passed\n");
    	}
    	else {
    		System.out.print("GUI initialization test failed\n");

    	}
		
	}

}
