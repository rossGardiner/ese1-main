/**
 * @author Agnes Ola
 * 
 * All code below auto generated so far - with WindowBuilder in Eclipse
 * TO DO: refactoring
 * TO DO: replace auto generated variable names
 */

package uk.ac.gla.dcs.tp3_2019_ese1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager; 

import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import net.miginfocom.swing.MigLayout;
import uk.ac.gla.dcs.tp3_2019_ese1.aaadata.AAARunner;
//import sun.security.provider.CtrDrbg;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.DaqDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;
import com.jtattoo.plaf.acryl.*;
 

public class MainGUI implements IGUI {


	{ 
	try {
		AcrylLookAndFeel.setTheme("Green-Giant-Font","" ,"");
		UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		
	} catch (Exception e) {
		e.printStackTrace();
	}}
	
	private JFrame frame;
	

	private final JPanel timerPanel = new JPanel();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField cellTest1_PeakG;
	private JTextField cellTest2_PeakG;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_35;
	private JTextField textField_36;
	private JTextField textField_37;
	private JTextField textField_38;
	private JTextField textField_39;
	private JTextField textField_40;
	private JTextField textField_41;
	private JTextField textField_42;
	private JTextField textField_43;
	private JTextField textField_44;
	private LibcbwBoard.USB_1608FS board;
	private AAARunner _runner;
	private boolean _magnetStatus;
	private JTextField textField_14;
	private JTextField cellAvg_PeakG;
	private JTextField cellTest1_DropHT;
	private JTextField cellTest2_DropHT;
	private JTextField cellTest3_DropHT;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField textField_29;
	private JTextField textField_30;
	private JTextField textField_31;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
	private JTextField textField_45;
	private JTextField txtPeak;
	private JTextField textField_46;
	private JPanel panel_displacement;
	private JTextField textField_47;
	private JTextField textField_48;
	private JTextField textField_49;
	private JTextField textField_50;
	private static int delay;

	private JPanel panel_graph3;
	private JTextField textField_51;
	private JTextField textField_52;
	private JTextField textField_53;
	private JTextField textField_54;
	private JTextField cellTest1_Fmax;
	private JTextField cellTest2_Fmax;
	private JTextField cellTest3_Fmax;
	private JTextField cellAvg_Fmax;
	private JTextField cellTest1_Velocity1;
	private JTextField cellTest2_Velocity1;
	private JTextField cellTest3_Velocity1;
	private JTextField cellAvg_Velocity1;
	private JTextField cellTest1_Velocity2;
	private JTextField cellTest2_Velocity2;
	private JTextField cellTest3_Velocity2;
	private JTextField cellAvg_Velocity2;
	
	private XYSeriesCollection _accelerationData = new XYSeriesCollection();
	private XYSeriesCollection _velocityData = new XYSeriesCollection();
	private XYSeriesCollection _displacementData = new XYSeriesCollection();

	private int _n = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

		board = null;
		try {
			DaqDeviceDescriptor[] daqArray = DaqDeviceDescriptor.getDaqDeviceInventory(DaqDeviceDescriptor.USB_IFC, 5);
			//routine to test devices for board
			for(int i = 0; i< daqArray.length; i++) {
				if(daqArray[i].ProductID == 125 || daqArray[i].ProductID == 234)  board = daqArray[i].createDaqDevice(LibcbwBoard.USB_1608FS::new);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
        _runner = new AAARunner(board, this);
		
		frame.getContentPane().setLayout(new MigLayout("", "[652px][444px][][]", "[23px][][825px]"));
		JTabbedPane settingsPane = new JTabbedPane(JTabbedPane.TOP);
		settingsPane.setToolTipText("SESTTING\r\n");
		frame.getContentPane().add(settingsPane, "cell 3 2,alignx right,growy");
		
		JPanel calibrateTab = new JPanel();
		settingsPane.addTab("Results", null, calibrateTab, null);
		
		JPanel resultsPane = new JPanel();
		settingsPane.addTab("Results", null, resultsPane, null);
		resultsPane.setBorder(new TitledBorder(null, "Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		resultsPane.setLayout(new MigLayout("", "[77px][grow][grow][grow][grow]", "[][][14px][][][][][][][]"));
		
		JLabel lblTest = new JLabel("Test 1");
		resultsPane.add(lblTest, "cell 1 0");
		
		JLabel lblTest_1 = new JLabel("Test 2");
		resultsPane.add(lblTest_1, "cell 2 0");
		
		JLabel lblTest_2 = new JLabel("Test 3");
		resultsPane.add(lblTest_2, "cell 3 0");
		
		JLabel lblAvgTests = new JLabel("Avg tests 2&3");
		resultsPane.add(lblAvgTests, "cell 4 0");
		
		JLabel lblPeakG = new JLabel("Peak G");
		resultsPane.add(lblPeakG, "cell 0 1,alignx trailing");
		
		cellTest1_PeakG = new JTextField();
		resultsPane.add(cellTest1_PeakG, "cell 1 1,growx");
		cellTest1_PeakG.setColumns(10);
		
		cellTest2_PeakG = new JTextField();
		resultsPane.add(cellTest2_PeakG, "cell 2 1,growx");
		cellTest2_PeakG.setColumns(10);
		
		textField_14 = new JTextField();
		resultsPane.add(textField_14, "cell 3 1,growx");
		textField_14.setColumns(10);
		
		cellAvg_PeakG = new JTextField();
		resultsPane.add(cellAvg_PeakG, "cell 4 1,growx");
		cellAvg_PeakG.setColumns(10);
		
		JLabel lblFmax_3 = new JLabel("Fmax");
		resultsPane.add(lblFmax_3, "cell 0 2,alignx trailing");
		
		cellTest1_Fmax = new JTextField();
		resultsPane.add(cellTest1_Fmax, "cell 1 2,growx");
		cellTest1_Fmax.setColumns(10);
		
		cellTest2_Fmax = new JTextField();
		resultsPane.add(cellTest2_Fmax, "cell 2 2,growx");
		cellTest2_Fmax.setColumns(10);
		
		cellTest3_Fmax = new JTextField();
		resultsPane.add(cellTest3_Fmax, "cell 3 2,growx");
		cellTest3_Fmax.setColumns(10);
		
		cellAvg_Fmax = new JTextField();
		resultsPane.add(cellAvg_Fmax, "cell 4 2,growx");
		cellAvg_Fmax.setColumns(10);
		
		JLabel lblVelocitymmsec = new JLabel("Velocity 1 (mm/sec)");
		resultsPane.add(lblVelocitymmsec, "cell 0 3,alignx trailing");
		
		cellTest1_Velocity1 = new JTextField();
		resultsPane.add(cellTest1_Velocity1, "cell 1 3,growx");
		cellTest1_Velocity1.setColumns(10);
		
		cellTest2_Velocity1 = new JTextField();
		resultsPane.add(cellTest2_Velocity1, "cell 2 3,growx");
		cellTest2_Velocity1.setColumns(10);
		
		cellTest3_Velocity1 = new JTextField();
		resultsPane.add(cellTest3_Velocity1, "cell 3 3,growx");
		cellTest3_Velocity1.setColumns(10);
		
		cellAvg_Velocity1 = new JTextField("");
		resultsPane.add(cellAvg_Velocity1, "cell 4 3,growx");
		cellAvg_Velocity1.setColumns(10);
		
		JLabel lblVelocitymmsec_1 = new JLabel("Velocity 2 (mm/sec)");
		resultsPane.add(lblVelocitymmsec_1, "cell 0 4,alignx trailing");
		
		cellTest1_Velocity2 = new JTextField("");
		resultsPane.add(cellTest1_Velocity2, "cell 1 4,growx");
		cellTest1_Velocity2.setColumns(10);
		
		cellTest2_Velocity2 = new JTextField("");
		resultsPane.add(cellTest2_Velocity2, "cell 2 4,growx");
		cellTest2_Velocity2.setColumns(10);
		
		cellTest3_Velocity2 = new JTextField("");
		resultsPane.add(cellTest3_Velocity2, "cell 3 4,growx");
		cellTest3_Velocity2.setColumns(10);
		
		cellAvg_Velocity2 = new JTextField("");
		resultsPane.add(cellAvg_Velocity2, "cell 4 4,growx");
		cellAvg_Velocity2.setColumns(10);
		
		JLabel lblDropHt = new JLabel("Drop HT");
		resultsPane.add(lblDropHt, "cell 0 5,alignx trailing");
		
		cellTest1_DropHT = new JTextField();
		resultsPane.add(cellTest1_DropHT, "cell 1 5,growx");
		cellTest1_DropHT.setColumns(10);
		
		cellTest2_DropHT = new JTextField();
		resultsPane.add(cellTest2_DropHT, "cell 2 5,growx");
		cellTest2_DropHT.setColumns(10);
		
		cellTest3_DropHT = new JTextField();
		resultsPane.add(cellTest3_DropHT, "cell 3 5,growx");
		cellTest3_DropHT.setColumns(10);
		
		textField_19 = new JTextField();
		resultsPane.add(textField_19, "cell 4 5,growx");
		textField_19.setColumns(10);
		
		JLabel lblSpringDeformation = new JLabel("Spring deformation");
		resultsPane.add(lblSpringDeformation, "cell 0 6,alignx trailing");
		
		textField_20 = new JTextField();
		resultsPane.add(textField_20, "cell 1 6,growx");
		textField_20.setColumns(10);
		
		textField_21 = new JTextField();
		resultsPane.add(textField_21, "cell 2 6,growx");
		textField_21.setColumns(10);
		
		textField_22 = new JTextField();
		resultsPane.add(textField_22, "cell 3 6,growx");
		textField_22.setColumns(10);
		
		textField_23 = new JTextField();
		resultsPane.add(textField_23, "cell 4 6,growx");
		textField_23.setColumns(10);
		
				JLabel lblForceReduction_3 = new JLabel("Force reduction");
				resultsPane.add(lblForceReduction_3, "cell 0 7,alignx trailing");
				lblForceReduction_3.setFont(new Font("SansSerif", Font.BOLD, 12));
				
						
						textField_24 = new JTextField();
						resultsPane.add(textField_24, "cell 1 7,growx");
						textField_24.setColumns(10);
						
						textField_25 = new JTextField();
						resultsPane.add(textField_25, "cell 2 7,growx");
						textField_25.setColumns(10);
						
						textField_26 = new JTextField();
						resultsPane.add(textField_26, "cell 3 7,growx");
						textField_26.setColumns(10);
						
						textField_27 = new JTextField();
						resultsPane.add(textField_27, "cell 4 7,growx");
						textField_27.setColumns(10);
						
						JLabel lblVerticalDeformation = new JLabel("Vertical deformation");
						resultsPane.add(lblVerticalDeformation, "cell 0 8,alignx trailing");
						lblVerticalDeformation.setFont(new Font("SansSerif", Font.BOLD, 12));
						
						
						textField_28 = new JTextField();
						resultsPane.add(textField_28, "cell 1 8,growx");
						textField_28.setColumns(10);
						
								
								textField_29 = new JTextField();
								resultsPane.add(textField_29, "cell 2 8,growx");
								textField_29.setColumns(10);
								
								textField_30 = new JTextField();
								resultsPane.add(textField_30, "cell 3 8,growx");
								textField_30.setColumns(10);
								
										textField_31 = new JTextField();
										resultsPane.add(textField_31, "cell 4 8,growx");
										textField_31.setColumns(10);
										
										JLabel lblEnergyRestitution = new JLabel("Energy restitution");
										resultsPane.add(lblEnergyRestitution, "cell 0 9,alignx trailing");
										lblEnergyRestitution.setFont(new Font("SansSerif", Font.BOLD, 12));
										
										textField_51 = new JTextField();
										resultsPane.add(textField_51, "cell 1 9,growx");
										textField_51.setColumns(10);
										
										textField_52 = new JTextField();
										resultsPane.add(textField_52, "cell 2 9,growx");
										textField_52.setColumns(10);
										
										textField_53 = new JTextField();
										resultsPane.add(textField_53, "cell 3 9,growx");
										textField_53.setColumns(10);
										
										textField_54 = new JTextField();
										resultsPane.add(textField_54, "cell 4 9,growx");
										textField_54.setColumns(10);
		
		JPanel setupTab = new JPanel();
		settingsPane.addTab("Setup", null, setupTab, null);
		setupTab.setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[][][][][][][][]"));
		
		JPanel panel = new JPanel();;
		panel.setBorder(new TitledBorder(null, "Set gain", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setupTab.add(panel, "cell 0 2 2 2,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][][][]"));
		
		JLabel lblChanNo = new JLabel("Chan no");
		panel.add(lblChanNo, "cell 0 0");
		
		JLabel lblGain = new JLabel("Gain");
		panel.add(lblGain, "cell 1 0");
		
		JLabel label_2 = new JLabel("1");
		panel.add(label_2, "cell 0 1,alignx trailing");
		
		textField_37 = new JTextField();
		panel.add(textField_37, "cell 1 1,growx");
		textField_37.setColumns(10);
		
		JLabel label_3 = new JLabel("2");
		panel.add(label_3, "cell 0 2,alignx trailing");
		
		textField_38 = new JTextField();
		panel.add(textField_38, "cell 1 2,growx");
		textField_38.setColumns(10);
		
		JLabel label_4 = new JLabel("3");
		panel.add(label_4, "cell 0 3,alignx trailing");
		
		textField_39 = new JTextField();
		panel.add(textField_39, "cell 1 3,growx");
		textField_39.setColumns(10);
		
		JLabel label_5 = new JLabel("4");
		panel.add(label_5, "cell 0 4,alignx trailing");
		
		textField_40 = new JTextField();
		panel.add(textField_40, "cell 1 4,growx");
		textField_40.setColumns(10);
		
		JLabel label_6 = new JLabel("5");
		panel.add(label_6, "cell 0 5,alignx trailing");
		
		textField_41 = new JTextField();
		panel.add(textField_41, "cell 1 5,growx");
		textField_41.setColumns(10);
		
		JLabel label_7 = new JLabel("6");
		panel.add(label_7, "cell 0 6,alignx trailing");
		
		textField_42 = new JTextField();
		panel.add(textField_42, "cell 1 6,growx");
		textField_42.setColumns(10);
		
		JLabel label_8 = new JLabel("7");
		panel.add(label_8, "cell 0 7,alignx trailing");
		
		textField_43 = new JTextField();
		panel.add(textField_43, "cell 1 7,growx");
		textField_43.setColumns(10);
		
		JLabel label_9 = new JLabel("8");
		panel.add(label_9, "cell 0 8,alignx trailing");
		
		textField_44 = new JTextField();
		panel.add(textField_44, "cell 1 8,growx");
		textField_44.setColumns(10);
		
		JLabel lblSamples = new JLabel("Samples");
		setupTab.add(lblSamples, "cell 2 2");
		
		JLabel lblRange = new JLabel("Range");
		setupTab.add(lblRange, "cell 3 2");
		
		textField_36 = new JTextField();
		setupTab.add(textField_36, "cell 2 3,growx");
		textField_36.setColumns(10);
		
		textField_35 = new JTextField();
		setupTab.add(textField_35, "cell 3 3,growx");
		textField_35.setColumns(10);
		
		JLabel lblFilter = new JLabel("Filter");
		setupTab.add(lblFilter, "cell 0 4");
		
		JToggleButton tglbtnYesno = new JToggleButton("Yes/No");
		setupTab.add(tglbtnYesno, "cell 1 4");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Trigger", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setupTab.add(panel_3, "cell 2 5 2 1,grow");
		panel_3.setLayout(new MigLayout("", "[grow][grow]", "[][][][]"));
		
		JLabel lblType = new JLabel("Type");
		panel_3.add(lblType, "cell 0 0");
		
		JLabel lblValue = new JLabel("Value");
		panel_3.add(lblValue, "cell 1 0");
		
		textField_47 = new JTextField();
		panel_3.add(textField_47, "cell 0 1,growx");
		textField_47.setColumns(10);
		
		textField_48 = new JTextField();
		panel_3.add(textField_48, "cell 1 1,growx");
		textField_48.setColumns(10);
		
		JLabel lblPretrig = new JLabel("Pretrig");
		panel_3.add(lblPretrig, "cell 0 2");
		
		JLabel lblDelay = new JLabel("Delay");
		panel_3.add(lblDelay, "cell 1 2");
		
		textField_49 = new JTextField();
		panel_3.add(textField_49, "cell 0 3,growx");
		textField_49.setColumns(10);
		
		textField_50 = new JTextField();
		panel_3.add(textField_50, "cell 1 3,growx");
		textField_50.setColumns(10);
		
		JButton btnSaveSetup = new JButton("Save Setup");
		setupTab.add(btnSaveSetup, "cell 1 6");
		settingsPane.addTab("Calibrate", null, calibrateTab, null);
		calibrateTab.setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		
		JLabel lblCalibration = new JLabel("Calibration");
		calibrateTab.add(lblCalibration, "cell 0 0,alignx trailing");
		
		textField_4 = new JTextField();
		calibrateTab.add(textField_4, "cell 1 0,growx");
		textField_4.setColumns(10);
		
		JLabel lblAmpGain = new JLabel("Amp gain");
		calibrateTab.add(lblAmpGain, "cell 0 1,alignx trailing");
		
		textField_5 = new JTextField();
		calibrateTab.add(textField_5, "cell 1 1,growx");
		textField_5.setColumns(10);
		
		JLabel lblConcrete = new JLabel("Concrete");
		calibrateTab.add(lblConcrete, "cell 0 2,alignx trailing");
		
		textField_12 = new JTextField();
		calibrateTab.add(textField_12, "cell 1 2,growx");
		textField_12.setColumns(10);
		
		JLabel lblVoltage = new JLabel("Voltage");
		calibrateTab.add(lblVoltage, "cell 0 3,alignx trailing");
		
		textField_13 = new JTextField();
		calibrateTab.add(textField_13, "cell 1 3,growx");
		textField_13.setColumns(10);
		
		JButton btnSaveSettings = new JButton("Save settings");
		calibrateTab.add(btnSaveSettings, "cell 0 5");
		
		JLabel lblSaved = new JLabel("Saved!");
		calibrateTab.add(lblSaved, "cell 1 5");
		
		JPanel launchControlPanel = new JPanel();
		launchControlPanel.setBorder(null);
		frame.getContentPane().add(launchControlPanel, "cell 0 2,alignx left,growy");
		launchControlPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel testLaunchPanel = new JPanel();
		launchControlPanel.add(testLaunchPanel);
		testLaunchPanel.setPreferredSize(new Dimension(480, 64));
		testLaunchPanel.setBorder(new TitledBorder(null, "Test control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		testLaunchPanel.setLayout(new MigLayout("", "[]", "[][]"));
		
		JButton btnMagnetStatus_1 = new JButton("Magnet status");
		_magnetStatus = false;
		btnMagnetStatus_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					_magnetStatus = !_magnetStatus;
					board.digitalOut(0, _magnetStatus);
					if(_magnetStatus){
						btnMagnetStatus_1.setBackground(new Color(0,71,137)); //SportsLabs Colors
					}else{
						btnMagnetStatus_1.setBackground(null);
					}
				}
				catch (LibcbwException ex) {
					ex.printStackTrace();
				}
			}
		});
		testLaunchPanel.add(btnMagnetStatus_1, "cell 0 0,aligny top");
		
		JButton btnRunTest_1 = new JButton("Run Test");
        btnRunTest_1.addActionListener(_runner::runTest);
        
		testLaunchPanel.add(btnRunTest_1, "cell 0 1,growy");
		timerPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		launchControlPanel.add(timerPanel);
		timerPanel.setLayout(new MigLayout("", "[grow]", "[][][][]"));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Timer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		timerPanel.add(panel_8, "cell 0 1,grow");
		panel_8.setLayout(new MigLayout("", "[left][]", "[][][][]"));
		
		JLabel lblMin = new JLabel("Min");
		panel_8.add(lblMin, "cell 0 0,alignx left");
		
		JLabel lblSec = new JLabel("Sec");
		panel_8.add(lblSec, "cell 1 0");
		
		textField_3 = new JTextField();
		panel_8.add(textField_3, "cell 0 1");
		textField_3.setText("00");
		textField_3.setColumns(10);
		
		textField_2 = new JTextField();
		panel_8.add(textField_2, "cell 1 1,growx");
		textField_2.setText("1");
		textField_2.setColumns(10);
		
		JButton btnStart_1 = new JButton("Start");
		panel_8.add(btnStart_1, "cell 0 3");
		
		JButton btnReset_1 = new JButton("Reset");
		panel_8.add(btnReset_1, "cell 1 3");
		
		JButton btnSaveFile = new JButton("Save file");
		timerPanel.add(btnSaveFile, "cell 0 3");
		
		/*
		 * btnRunTest_1 = new JButton("Run Test");
		btnRunTest_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Random r = new Random();
				double[] array = new double[1000];
				for(int i = 0; i < 1000; i++) {
					array[i] = (double)i * r.nextFloat();
				}
				makeGraphs(array, array, array, 0, _n);
				_n++;
			}
			
		});
		 */

		testLaunchPanel.add(btnRunTest_1, "cell 0 1");
		timerPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		launchControlPanel.add(timerPanel);
		timerPanel.setLayout(new MigLayout("", "[grow]", "[][][]"));
	
		/*
		 *  Pausing the Button For given seconds to avoid 
		 *  repeating too many tests
		 */
		btnStart_1.addActionListener((ae) -> {
				JButton button = btnRunTest_1;
				button.setEnabled(false);
				System.out.printf("Timer running");
  
				int minutes = Integer.parseInt(textField_3.getText());
				int seconds = Integer.parseInt(textField_2.getText());
				minutes = minutes * 60000;
				seconds = seconds * 1000;
				delay = minutes + seconds;
				System.out.println(delay);
				
				Timer timer = new Timer(delay, (evt) -> btnRunTest_1.setEnabled(true));
				
				timer.setRepeats(false);
				timer.start();
			
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, "cell 0 0 4 1,alignx center,growy");
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSaveData = new JMenuItem("Save data");
		mnFile.add(mntmSaveData);
		
		JMenuItem mntmLoadData = new JMenuItem("Load data");
		mntmLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mnFile.add(mntmLoadData);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		
		JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JPanel dataViewPanel = new JPanel();
		frame.getContentPane().add(dataViewPanel, "cell 1 2 2 1,grow");
		GridBagLayout gbl_dataViewPanel = new GridBagLayout();
		gbl_dataViewPanel.columnWidths = new int[]{159, 157, 149, 75, 0};
		gbl_dataViewPanel.rowHeights = new int[]{225, 0, 224, 0, 230, 0, 0};
		gbl_dataViewPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_dataViewPanel.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		dataViewPanel.setLayout(gbl_dataViewPanel);
		XYSeries series = new XYSeries("Test 1");
		XYSeries series2  = new XYSeries("Test 2");
		XYSeries series3 = new XYSeries("Test 3");

		for(int i = 0; i < 1000; i++) {
			series.add(new XYDataItem(i, 0));
			series2.add(new XYDataItem(i, 0));
			series3.add(new XYDataItem(i, 0));
		}

		_accelerationData.addSeries(series);
		_accelerationData.addSeries(series2);
		_accelerationData.addSeries(series3);
		JFreeChart accelerationChart = ChartFactory.createXYLineChart("Acceleration Vs Time","Time","Acceleration", _accelerationData);
		
		_velocityData.addSeries(series);
		_velocityData.addSeries(series2);
		_velocityData.addSeries(series3);
		JFreeChart velocityChart = ChartFactory.createXYLineChart("Velocity Vs Time","Time","Velocity", _velocityData);
		
		_displacementData.addSeries(series);
		_displacementData.addSeries(series2);
		_displacementData.addSeries(series3);
		JFreeChart displacementChart = ChartFactory.createXYLineChart("Displacement Vs Time","Time","Displacement", _displacementData);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 5;
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		dataViewPanel.add(tabbedPane, gbc_tabbedPane);
		
		
		btnSaveFile.addActionListener((evt) -> {
		    JFileChooser saveFile = new JFileChooser();
		    saveFile.setDialogTitle("Choose where to save the file, the file will be saved into an xml format");
		    int userSelection = saveFile.showSaveDialog(frame);
		    if (userSelection == JFileChooser.APPROVE_OPTION) {
		        File file = saveFile.getSelectedFile();
		        //Save file into xml format
		        file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".csv");
		        ArrayList<String> csv = new ArrayList<>();
		        if (accelerationChart.getPlot() instanceof XYPlot) {
		            XYDataset xyDataset = accelerationChart.getXYPlot().getDataset();
		            int seriesCount = xyDataset.getSeriesCount();
		            for (int i = 0; i < seriesCount; i++) {
		                int itemCount = xyDataset.getItemCount(i);
		                for (int j = 0; j < itemCount; j++) {
		                    Comparable<?> key = xyDataset.getSeriesKey(i);
		                    Number x = xyDataset.getX(i, j);
		                    Number y = xyDataset.getY(i, j);
		                    csv.add(String.format("%s, %s, %s", key, x, y));
		                }
		            }
		        }
		        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file));)
		        {
		            for (String line : csv) {
		                writer.append(line);
		                writer.newLine();
		            }
		        } catch (IOException e) {
		            throw new IllegalStateException("Cannot write dataset",e);
		        }
		    }		
		});

		JPanel panel_acceleration = new JPanel();
		tabbedPane.addTab("Acceleration Vs. Time", null, panel_acceleration, null);
		ChartPanel chartPanelAcceleration = new ChartPanel(accelerationChart);
		chartPanelAcceleration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					//new ChartViewer(chart).setVisible(true);
					new ChartViewerDialog(accelerationChart).setVisible(true);
				}
			}
		});
		chartPanelAcceleration.setDomainZoomable(true);
		panel_acceleration.setLayout(new BorderLayout());
		panel_acceleration.add(chartPanelAcceleration, BorderLayout.CENTER);
		
		JPanel panel_velocity = new JPanel();
		ChartPanel chartPanelVelocity = new ChartPanel(velocityChart);
		chartPanelVelocity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ChartViewerDialog(velocityChart).setVisible(true);
			}
		});
		chartPanelVelocity.setDomainZoomable(true);
		panel_velocity.setLayout(new BorderLayout());
		panel_velocity.add(chartPanelVelocity, BorderLayout.CENTER);
		tabbedPane.addTab("Velocity Vs. Time", null, panel_velocity, null);
		
		panel_displacement = new JPanel();
		
		ChartPanel chartPanelDisplacement = new ChartPanel(displacementChart);

		chartPanelDisplacement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ChartViewerDialog(displacementChart).setVisible(true);
			}
		});
		chartPanelDisplacement.setDomainZoomable(true);

		panel_displacement.setLayout(new BorderLayout());
		panel_displacement.add(chartPanelDisplacement, BorderLayout.CENTER);
		tabbedPane.addTab("Displacement Vs. Time", null, panel_displacement, null);
		
		JPanel averageResultsPanel = new JPanel();
        frame.getContentPane().add(averageResultsPanel, "cell 0 0 4 1,grow");
		averageResultsPanel.setLayout(new MigLayout("", "[77px]", "[14px]"));
		//panel_13.setLayout(new BorderLayout());
		//panel_13.add(chartPanelGraph3, BorderLayout.CENTER);
		//tabbedPane.addTab("Graph 3", null, panel_13, null);
	}

    @Override
    public void makeGraphs(double[] acceleration, double[] velocity, double[] disp, int drop_touch2, int testNr) {
    	//first, work out the test index (0-2 inclusive)
    	int testIdx = testNr%3;
    	
    	//make graphical datasets for:
    	//ACCELERATION
    	XYSeries accelerationSeries = new XYSeries("Test " + (testNr + 1));
    	int x = 0;
    	for (double accVal : acceleration) {
			accelerationSeries.add(new XYDataItem(x, accVal));
			x++;
		}
    	//VELOCITY
    	XYSeries velocitySeries = new XYSeries("Test " + (testNr + 1));
    	x = 0;
    	for (double velVal : velocity) {
			velocitySeries.add(new XYDataItem(x, velVal));
			x++;
		}
    	//DISPLACEMENT
    	XYSeries displacementSeries = new XYSeries("Test " + (testNr + 1));
    	x = 0;
    	for (double dispVal : disp) {
			displacementSeries.add(new XYDataItem(x, dispVal));
			x++;
		}
    	
    	//update chart datasets for:
    	//ACCELERATION
		List<Object> accData = Arrays.asList(_accelerationData.getSeries().toArray());
    	accData.set(testIdx, accelerationSeries);
    	_accelerationData.removeAllSeries();
    	for(Object series : accData) {
    		_accelerationData.addSeries((XYSeries)series);
    	}
    	//VELOCITY
    	List<Object> velData = Arrays.asList(_velocityData.getSeries().toArray());
    	velData.set(testIdx, velocitySeries);
    	_velocityData.removeAllSeries();
    	for(Object series : velData) {
    		_velocityData.addSeries((XYSeries)series);
    	}
    	//DISPACEMENT
    	List<Object> dispData = Arrays.asList(_displacementData.getSeries().toArray());
    	dispData.set(testIdx, velocitySeries);
    	_displacementData.removeAllSeries();
    	for(Object series : dispData) {
    		_displacementData.addSeries((XYSeries)series);
    	}
    		
    }

    @Override
    public void outputResults(double peakG, double fmax, double fred, double v1, double v2, double energy,
            double drop_dist, double spring, double material, int testNr) {
    	//first work out test index (0-2 inclusive)
    	int testIdx = testNr%3;
   	 	cellTest1_PeakG.setText(Double.toString(peakG));
   	 	cellTest1_Fmax.setText(Double.toString(fmax));
   	 	cellTest1_Velocity1.setText(Double.toString(v1));
   	 	cellTest1_Velocity2.setText(Double.toString(v2));
   	 	cellTest1_DropHT.setText(Double.toString(drop_dist));
   	 	textField_20.setText(Double.toString(spring));
   	 	textField_24.setText(Double.toString(fred));
   	 	textField_51.setText(Double.toString(energy));
    	
        
    }

    @Override
    public void displayErrorMessage(String msg) {
        // TODO Auto-generated method stub
        
    }

}

