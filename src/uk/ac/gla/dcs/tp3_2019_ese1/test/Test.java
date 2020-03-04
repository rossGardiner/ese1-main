package uk.ac.gla.dcs.tp3_2019_ese1.test;

import org.mockito.Mock;
import org.mockito.Mockito;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;

import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.text.View;

import java.awt.event.*;
import uk.ac.gla.dcs.tp3_2019_ese1.aaadata.AAARunner;
import uk.ac.gla.dcs.tp3_2019_ese1.gui.MainGUI;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;


public class Test {
	
    private static final int ACCELEROMETER_IN = 1;
    private static final int MAGNET_OUT = 0;
    public static void main(String[] args) throws LibcbwException {

        testGUI();
        testMagnet();

    }
    private static void testMagnet() {
    	
	}
	
	private static void testGUI() {
		// TODO Auto-generated method stub
		//boolean assertionBool = true;
    	//MainGUI gui = new MainGUI();
    	try {
    	   AAARunner runner = new AAARunner(null, null);
    	   boolean guiPassed = true;
    	   System.out.print("AAARunner initialization test passed\n");
    	}
    	catch(Exception ex){
    		System.out.print("AAARunner initialization test failed\n");
    	}
		
	}
	

    
}
