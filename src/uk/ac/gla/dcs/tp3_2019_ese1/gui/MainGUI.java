/**
 * @author Agnes Ola
 * 
 * All code below auto generated so far - with WindowBuilder in Eclipse
 * TO DO: refactoring
 * TO DO: replace auto generated variable names
 */

package uk.ac.gla.dcs.tp3_2019_ese1.gui;
 

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import net.miginfocom.swing.MigLayout;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.DaqDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.Region;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class MainGUI {
	private JFrame frame;
	private final JPanel timerPanel = new JPanel();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
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
	private JTextField textField_45;
	private JTextField txtPeak;
	private JTextField textField_46;
	private JTextField textField_47;
	private JTextField textField_48;
	private JTextField textField_49;
	private JTextField textField_50;
	private final Action action = new SwingAction();
	private LibcbwBoard.USB_1608FS board;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
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
	private JTextField textField_51;
	private JTextField textField_52;
	private JTextField textField_53;
	private JTextField textField_54;
	private JTextField textField_55;
	private JTextField textField_56;
	private JTextField textField_57;
	private JTextField textField_58;
	private JTextField textField_59;
	private JTextField textField_60;
	private JTextField textField_61;
	private JTextField textField_62;
	private JTextField textField_63;
	private JTextField textField_64;
	private JTextField textField_65;
	private JTextField textField_66;

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
		frame.getContentPane().setLayout(new MigLayout("", "[652px][444px][]", "[23px][825px]"));
		JTabbedPane settingsPane = new JTabbedPane(JTabbedPane.TOP);
		settingsPane.setToolTipText("SESTTING\r\n");
		frame.getContentPane().add(settingsPane, "cell 3 1,alignx right,aligny top");
		
		JPanel calibrateTab = new JPanel();
		settingsPane.addTab("Results", null, calibrateTab, null);
		
		JPanel setupTab = new JPanel();
		settingsPane.addTab("Setup", null, setupTab, null);
		setupTab.setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[][][][][][][][]"));
		
		JLabel lblStartChan = new JLabel("Start chan");
		setupTab.add(lblStartChan, "cell 0 0");
		
		JLabel lblStopChan = new JLabel("Stop chan");
		setupTab.add(lblStopChan, "cell 1 0");
		
		JLabel lblFrequency = new JLabel("Frequency");
		setupTab.add(lblFrequency, "cell 2 0");
		
		textField_32 = new JTextField();
		setupTab.add(textField_32, "cell 0 1,growx");
		textField_32.setColumns(10);
		
		textField_33 = new JTextField();
		setupTab.add(textField_33, "cell 1 1,growx");
		textField_33.setColumns(10);
		
		textField_34 = new JTextField();
		setupTab.add(textField_34, "cell 2 1,growx");
		textField_34.setColumns(10);
		
		JPanel panel = new JPanel();
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
		
		JToggleButton tglbtnYes = new JToggleButton("Yes");
		setupTab.add(tglbtnYes, "cell 1 4");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Sampling", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setupTab.add(panel_1, "cell 2 4,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][][][][][][]"));
		
		JLabel lblMethod = new JLabel("Method");
		panel_1.add(lblMethod, "cell 0 0,alignx trailing");
		
		textField_45 = new JTextField();
		panel_1.add(textField_45, "cell 0 1,growx");
		textField_45.setColumns(10);
		
		JCheckBox chckbxBackground = new JCheckBox("Background");
		panel_1.add(chckbxBackground, "cell 0 2");
		
		JCheckBox chckbxContinous = new JCheckBox("Continous");
		panel_1.add(chckbxContinous, "cell 0 3");
		JCheckBox chckbxNoCalibration = new JCheckBox("No calibration");
		panel_1.add(chckbxNoCalibration, "cell 0 4");
		
		JCheckBox chckbxExtTrigger = new JCheckBox("Ext Trigger");
		panel_1.add(chckbxExtTrigger, "cell 0 5");
		
		JCheckBox chckbxExtClock = new JCheckBox("Ext Clock");
		panel_1.add(chckbxExtClock, "cell 0 6");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Graph setup", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setupTab.add(panel_2, "cell 0 5 2 1,grow");
		panel_2.setLayout(new MigLayout("", "[grow][grow]", "[][]"));
		
		JLabel lblMethod_1 = new JLabel("Method");
		panel_2.add(lblMethod_1, "cell 0 0,alignx trailing");
		
		txtPeak = new JTextField();
		panel_2.add(txtPeak, "cell 1 0,growx");
		txtPeak.setText("Peak");
		txtPeak.setColumns(10);
		
		JLabel lblCount = new JLabel("Count");
		panel_2.add(lblCount, "cell 0 1,alignx trailing");
		
		textField_46 = new JTextField();
		panel_2.add(textField_46, "cell 1 1,growx");
		textField_46.setText("12");
		textField_46.setColumns(10);
		
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
		
		JButton btnRestoreDefaults = new JButton("Restore Defaults");
		setupTab.add(btnRestoreDefaults, "cell 0 7");
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
		
		JPanel panel_4 = new JPanel();
		
		JPanel launchControlPanel = new JPanel();
		launchControlPanel.setBorder(null);
		frame.getContentPane().add(launchControlPanel, "cell 0 1,alignx left,aligny top");
		launchControlPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel testLaunchPanel = new JPanel();
		launchControlPanel.add(testLaunchPanel);
		testLaunchPanel.setBorder(new TitledBorder(null, "Test control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		testLaunchPanel.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JButton btnMagnetStatus_1 = new JButton("Magnet status");
		btnMagnetStatus_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					board.digitalOut(0, true);
				}
				catch (LibcbwException ex) {
					ex.printStackTrace();
				}
			}
		});
		testLaunchPanel.add(btnMagnetStatus_1, "cell 0 0,aligny top");
		
		JButton btnRunTest_1 = new JButton("Run Test");
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
		
		JPanel resultsPane = new JPanel();
		launchControlPanel.add(resultsPane);
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
		
		textField_10 = new JTextField();
		resultsPane.add(textField_10, "cell 1 1,growx");
		textField_10.setText("10");
		textField_10.setColumns(10);
		
		textField_11 = new JTextField();
		resultsPane.add(textField_11, "cell 2 1,growx");
		textField_11.setText("11");
		textField_11.setColumns(10);
		
		textField_14 = new JTextField();
		resultsPane.add(textField_14, "cell 3 1,growx");
		textField_14.setText("14");
		textField_14.setColumns(10);
		
		textField_15 = new JTextField();
		resultsPane.add(textField_15, "cell 4 1,growx");
		textField_15.setText("15");
		textField_15.setColumns(10);
		
		JLabel lblFmax_3 = new JLabel("Fmax");
		resultsPane.add(lblFmax_3, "cell 0 2,alignx trailing");
		
		textField_55 = new JTextField();
		resultsPane.add(textField_55, "cell 1 2,growx");
		textField_55.setText("55");
		textField_55.setColumns(10);
		
		textField_56 = new JTextField();
		resultsPane.add(textField_56, "cell 2 2,growx");
		textField_56.setText("56");
		textField_56.setColumns(10);
		
		textField_57 = new JTextField();
		resultsPane.add(textField_57, "cell 3 2,growx");
		textField_57.setText("57");
		textField_57.setColumns(10);
		
		textField_58 = new JTextField();
		resultsPane.add(textField_58, "cell 4 2,growx");
		textField_58.setText("58");
		textField_58.setColumns(10);
		
		JLabel lblVelocitymmsec = new JLabel("Velocity 1 (mm/sec)");
		resultsPane.add(lblVelocitymmsec, "cell 0 3,alignx trailing");
		
		textField_59 = new JTextField();
		resultsPane.add(textField_59, "cell 1 3,growx");
		textField_59.setText("59");
		textField_59.setColumns(10);
		
		textField_60 = new JTextField();
		resultsPane.add(textField_60, "cell 2 3,growx");
		textField_60.setText("60");
		textField_60.setColumns(10);
		
		textField_61 = new JTextField();
		resultsPane.add(textField_61, "cell 3 3,growx");
		textField_61.setText("61");
		textField_61.setColumns(10);
		
		textField_62 = new JTextField("62");
		resultsPane.add(textField_62, "cell 4 3,growx");
		textField_62.setColumns(10);
		
		JLabel lblVelocitymmsec_1 = new JLabel("Velocity2 (mm/sec)");
		resultsPane.add(lblVelocitymmsec_1, "cell 0 4,alignx trailing");
		
		textField_63 = new JTextField("63");
		resultsPane.add(textField_63, "cell 1 4,growx");
		textField_63.setColumns(10);
		
		textField_64 = new JTextField("64");
		resultsPane.add(textField_64, "cell 2 4,growx");
		textField_64.setColumns(10);
		
		textField_65 = new JTextField("65");
		resultsPane.add(textField_65, "cell 3 4,growx");
		textField_65.setColumns(10);
		
		textField_66 = new JTextField("66");
		resultsPane.add(textField_66, "cell 4 4,growx");
		textField_66.setColumns(10);
		
		JLabel lblDropHt = new JLabel("Drop HT");
		resultsPane.add(lblDropHt, "cell 0 5,alignx trailing");
		
		textField_16 = new JTextField();
		resultsPane.add(textField_16, "cell 1 5,growx");
		textField_16.setText("16");
		textField_16.setColumns(10);
		
		textField_17 = new JTextField();
		resultsPane.add(textField_17, "cell 2 5,growx");
		textField_17.setText("17");
		textField_17.setColumns(10);
		
		textField_18 = new JTextField();
		resultsPane.add(textField_18, "cell 3 5,growx");
		textField_18.setText("18");
		textField_18.setColumns(10);
		
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
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, "cell 0 0 4 1,growx,aligny top");
		
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
		frame.getContentPane().add(dataViewPanel, "cell 1 1 2 1,grow");
		GridBagLayout gbl_dataViewPanel = new GridBagLayout();
		gbl_dataViewPanel.columnWidths = new int[]{159, 157, 149, 75, 0};
		gbl_dataViewPanel.rowHeights = new int[]{225, 0, 224, 0, 230, 0, 0};
		gbl_dataViewPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_dataViewPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		dataViewPanel.setLayout(gbl_dataViewPanel);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "Current test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridwidth = 4;
		gbc_panel_10.insets = new Insets(0, 0, 5, 0);
		gbc_panel_10.gridx = 0;
		gbc_panel_10.gridy = 0;
		dataViewPanel.add(panel_10, gbc_panel_10);
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[]{159, 157, 149, 75, 0};
		gbl_panel_10.rowHeights = new int[]{225, 0};
		gbl_panel_10.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_10.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_10.setLayout(gbl_panel_10);
		
		JTextArea txtrSecondGraphTest = new JTextArea();
		GridBagConstraints gbc_txtrSecondGraphTest = new GridBagConstraints();
		gbc_txtrSecondGraphTest.fill = GridBagConstraints.BOTH;
		gbc_txtrSecondGraphTest.insets = new Insets(0, 0, 0, 5);
		gbc_txtrSecondGraphTest.gridx = 1;
		gbc_txtrSecondGraphTest.gridy = 0;
		panel_10.add(txtrSecondGraphTest, gbc_txtrSecondGraphTest);
		txtrSecondGraphTest.setText("Second graph test 1");
		
		JTextArea txtrThirdGraphTest = new JTextArea();
		GridBagConstraints gbc_txtrThirdGraphTest = new GridBagConstraints();
		gbc_txtrThirdGraphTest.fill = GridBagConstraints.BOTH;
		gbc_txtrThirdGraphTest.insets = new Insets(0, 0, 0, 5);
		gbc_txtrThirdGraphTest.gridx = 2;
		gbc_txtrThirdGraphTest.gridy = 0;
		panel_10.add(txtrThirdGraphTest, gbc_txtrThirdGraphTest);
		txtrThirdGraphTest.setText("Third graph test 1");
		XYSeries series = new XYSeries("Series1");
		for(int i = 0; i < 1000; i++) {
			series.add(new XYDataItem(i, i*i));
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Acceleration Vs Time","Time","Acceleration", dataset);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		dataViewPanel.add(tabbedPane, gbc_tabbedPane);
		
		JPanel panel_9 = new JPanel();
		tabbedPane.addTab("Acceleration Vs. Time", null, panel_9, null);
		ChartPanel chartPanelAcceleration = new ChartPanel(chart);
		chartPanelAcceleration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					//new ChartViewer(chart).setVisible(true);
					new ChartViewerDialog(chart).setVisible(true);
				}
			}
		});
		chartPanelAcceleration.setDomainZoomable(true);
		panel_9.setLayout(new BorderLayout());
		panel_9.add(chartPanelAcceleration, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		ChartPanel chartPanelGraph2 = new ChartPanel(chart);
		chartPanelGraph2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ChartViewerDialog(chart).setVisible(true);
			}
		});
		chartPanelGraph2.setDomainZoomable(true);
		panel_12.setLayout(new BorderLayout());
		panel_12.add(chartPanelGraph2, BorderLayout.CENTER);
		tabbedPane.addTab("Graph 2", null, panel_12, null);
		
		JPanel panel_13 = new JPanel();
		ChartPanel chartPanelGraph3 = new ChartPanel(chart);
		chartPanelGraph3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ChartViewerDialog(chart).setVisible(true);
			}
		});
		chartPanelGraph3.setDomainZoomable(true);
		panel_13.setLayout(new BorderLayout());
		panel_13.add(chartPanelGraph3, BorderLayout.CENTER);
		tabbedPane.addTab("Graph 3", null, panel_13, null);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "");
			putValue(SHORT_DESCRIPTION, "");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}

	
}
