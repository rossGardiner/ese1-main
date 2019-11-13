
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
class gui{
    public static void main (String args[]) {
    	//Define main window
        JFrame frame = new JFrame("SportsLabs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
    	
        //Creating the MenuBar and adding components
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu setupMenu = new JMenu("Setup");
        JMenu testMenu = new JMenu("Test");
        JMenu calibrateMenu = new JMenu("Calibrate");
        JMenu helpMenu = new JMenu("Help");
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(setupMenu);
        menuBar.add(testMenu);
        menuBar.add(calibrateMenu);
        menuBar.add(helpMenu);
    	
    	//Buttons for left panel
        JButton aaa = new JButton("AAA");
        JButton forceRed = new JButton("Force Red");
        JButton displacement = new JButton("Displacement");
        
        JButton runTest = new JButton("Run test");
        
        JButton saveFile = new JButton("Save file");
    	
        //Left panel
    	JPanel dashBoard = new JPanel();
    	dashBoard.setBackground(Color.red);
    	dashBoard.setLayout(new BoxLayout(dashBoard, BoxLayout.Y_AXIS));
    	dashBoard.add(aaa);
    	dashBoard.add(forceRed);
    	dashBoard.add(displacement);
    	dashBoard.add(runTest).setBackground(Color.green);
    	dashBoard.add(saveFile);
    	
    	//Middle panel
    	JPanel graphView = new JPanel();
    	graphView.setBackground(Color.yellow);
    	
    	//Tabs for right panel
    	JTabbedPane tabbedPane = new JTabbedPane();
   // 	tabbedPane.setLayout(null);

    	JTextArea p1_txt = new JTextArea(15,20);
    	JPanel p1 = new JPanel();
    	p1.add(p1_txt);
    	tabbedPane.add("one",p1);
    	
    	JTextArea p2_txt = new JTextArea(15,20);
    	JPanel p2 = new JPanel();
    	p2.add(p2_txt);
    	tabbedPane.add("two",p2);
    	
    	JTextArea p3_txt = new JTextArea(15,20);
    	JPanel p3 = new JPanel();
    	p3.add(p3_txt);
    	tabbedPane.add("three",p3);
    	
    	JTextArea p4_txt = new JTextArea(10,7);
    	JPanel p4 = new JPanel();
    	p4.add(p4_txt);
    	tabbedPane.add("four",p4);
    	

    	
    	//Right panel
    	JPanel settingsView = new JPanel();
    	settingsView.setBackground(Color.blue);
    	settingsView.add(tabbedPane);
    	
    	
    	
    	JPanel container = new JPanel();
    	container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
    	container.add(dashBoard);
    	container.add(graphView);
    	container.add(settingsView);
    	
       //Adding Components to the frame.
       frame.getContentPane().add(BorderLayout.NORTH, menuBar);
       frame.add(container);
       
       frame.setVisible(true);
    }
}
