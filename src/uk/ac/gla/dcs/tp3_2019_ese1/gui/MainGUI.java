/**
 * @author Agnes Ola
 * 
 * All code below auto generated so far - with WindowBuilder in Eclipse
 * TO DO: refactoring
 * TO DO: replace auto generated variable names
 */

package uk.ac.gla.dcs.tp3_2019_ese1.gui;
 

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import net.miginfocom.swing.MigLayout;
//import sun.security.provider.CtrDrbg;
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
import javax.swing.JFileChooser;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.Region;
import javax.swing.Timer;

import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.Dataset;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainGUI {
	private JFrame frame;
	private final JPanel timerPanel = new JPanel();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_12;
	private JTextField textField_13;
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
	private static int delay;

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
		JTabbedPane settingsPane = new JTabbedPane(JTabbedPane.TOP);
		settingsPane.setToolTipText("SESTTING\r\n");
		frame.getContentPane().add(settingsPane, BorderLayout.EAST);
		
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
		
		JPanel lvdtTab = new JPanel();
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
		settingsPane.addTab("LVDT", null, lvdtTab, null);
		lvdtTab.setLayout(new MigLayout("", "[grow][grow][grow][grow][][grow]", "[][][][][][][][][][][][][][]"));
		
		JLabel lblLvdtCalibration = new JLabel("LVDT Calibration");
		lvdtTab.add(lblLvdtCalibration, "cell 0 0");
		
		JLabel lblLvdt = new JLabel("LVDT1");
		lvdtTab.add(lblLvdt, "cell 0 1");
		
		JLabel lblLvdt_1 = new JLabel("LVDT2");
		lvdtTab.add(lblLvdt_1, "cell 2 1");
		
		JRadioButton radioButton = new JRadioButton("0.0");
		lvdtTab.add(radioButton, "cell 0 2");
		
		textField_15 = new JTextField();
		lvdtTab.add(textField_15, "cell 1 2,growx");
		textField_15.setColumns(10);
		
		JRadioButton radioButton_8 = new JRadioButton("0.0");
		lvdtTab.add(radioButton_8, "cell 2 2");
		
		textField_14 = new JTextField();
		lvdtTab.add(textField_14, "cell 3 2,growx");
		textField_14.setColumns(10);
		
		JRadioButton radioButton_1 = new JRadioButton("3.1");
		lvdtTab.add(radioButton_1, "cell 0 3");
		
		textField_16 = new JTextField();
		lvdtTab.add(textField_16, "cell 1 3,growx");
		textField_16.setColumns(10);
		
		JRadioButton radioButton_9 = new JRadioButton("3.1");
		lvdtTab.add(radioButton_9, "cell 2 3");
		
		textField_23 = new JTextField();
		lvdtTab.add(textField_23, "cell 3 3,growx");
		textField_23.setColumns(10);
		
		JRadioButton radioButton_2 = new JRadioButton("6.5");
		lvdtTab.add(radioButton_2, "cell 0 4");
		
		textField_17 = new JTextField();
		lvdtTab.add(textField_17, "cell 1 4,growx");
		textField_17.setColumns(10);
		
		JRadioButton radioButton_10 = new JRadioButton("6.5");
		lvdtTab.add(radioButton_10, "cell 2 4");
		
		textField_24 = new JTextField();
		lvdtTab.add(textField_24, "cell 3 4,growx");
		textField_24.setColumns(10);
		
		JRadioButton radioButton_3 = new JRadioButton("9.7");
		lvdtTab.add(radioButton_3, "cell 0 5");
		
		textField_18 = new JTextField();
		lvdtTab.add(textField_18, "cell 1 5,growx");
		textField_18.setColumns(10);
		
		JRadioButton radioButton_11 = new JRadioButton("9.7");
		lvdtTab.add(radioButton_11, "cell 2 5");
		
		textField_25 = new JTextField();
		lvdtTab.add(textField_25, "cell 3 5,growx");
		textField_25.setColumns(10);
		
		JRadioButton radioButton_4 = new JRadioButton("15.8");
		lvdtTab.add(radioButton_4, "cell 0 6");
		
		textField_19 = new JTextField();
		lvdtTab.add(textField_19, "cell 1 6,growx");
		textField_19.setColumns(10);
		
		JRadioButton radioButton_12 = new JRadioButton("15.8");
		lvdtTab.add(radioButton_12, "cell 2 6");
		
		textField_26 = new JTextField();
		lvdtTab.add(textField_26, "cell 3 6,growx");
		textField_26.setColumns(10);
		
		JRadioButton radioButton_5 = new JRadioButton("19.0");
		lvdtTab.add(radioButton_5, "cell 0 7");
		
		textField_20 = new JTextField();
		lvdtTab.add(textField_20, "cell 1 7,growx");
		textField_20.setColumns(10);
		
		JRadioButton radioButton_13 = new JRadioButton("19.0");
		lvdtTab.add(radioButton_13, "cell 2 7");
		
		textField_27 = new JTextField();
		lvdtTab.add(textField_27, "cell 3 7,growx");
		textField_27.setColumns(10);
		
		JRadioButton radioButton_6 = new JRadioButton("21.9");
		lvdtTab.add(radioButton_6, "cell 0 8");
		
		textField_22 = new JTextField();
		lvdtTab.add(textField_22, "cell 1 8,growx");
		textField_22.setColumns(10);
		
		JRadioButton radioButton_14 = new JRadioButton("21.9");
		lvdtTab.add(radioButton_14, "cell 2 8");
		
		textField_28 = new JTextField();
		lvdtTab.add(textField_28, "cell 3 8,growx");
		textField_28.setColumns(10);
		
		JRadioButton radioButton_7 = new JRadioButton("25.0");
		lvdtTab.add(radioButton_7, "cell 0 9");
		
		textField_21 = new JTextField();
		lvdtTab.add(textField_21, "cell 1 9,growx");
		textField_21.setColumns(10);
		
		JRadioButton radioButton_15 = new JRadioButton("25.0");
		lvdtTab.add(radioButton_15, "cell 2 9");
		
		textField_29 = new JTextField();
		lvdtTab.add(textField_29, "cell 3 9,growx");
		textField_29.setColumns(10);
		
		JButton btnRunCalibrate = new JButton("Run Calibrate");
		lvdtTab.add(btnRunCalibrate, "cell 0 10");
		
		JButton btnValidate = new JButton("Validate");
		lvdtTab.add(btnValidate, "cell 2 10");
		
		textField_30 = new JTextField();
		lvdtTab.add(textField_30, "cell 0 11,growx");
		textField_30.setColumns(10);
		
		textField_31 = new JTextField();
		lvdtTab.add(textField_31, "cell 2 11,growx");
		textField_31.setColumns(10);
		
		JButton btnSaveSettings_1 = new JButton("Save settings");
		lvdtTab.add(btnSaveSettings_1, "cell 2 13");
		
		JPanel launchControlPanel = new JPanel();
		frame.getContentPane().add(launchControlPanel, BorderLayout.WEST);
		launchControlPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel selectTestPanel = new JPanel();
		launchControlPanel.add(selectTestPanel);
		selectTestPanel.setBorder(new TitledBorder(null, "Select test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		selectTestPanel.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JButton btnAaa = new JButton("AAA");
		selectTestPanel.add(btnAaa, "cell 0 0,growx");
		
		JButton btnForceRed = new JButton("Force Red");
		selectTestPanel.add(btnForceRed, "cell 0 1,growx");
		
		JButton btnDisplacement = new JButton("Displacement");
		selectTestPanel.add(btnDisplacement, "cell 0 2,growx");
		
		JPanel testLaunchPanel = new JPanel();
		launchControlPanel.add(testLaunchPanel);
		testLaunchPanel.setBorder(new TitledBorder(null, "Test control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		testLaunchPanel.setLayout(new MigLayout("", "[grow][]", "[][]"));
		
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
		testLaunchPanel.add(btnMagnetStatus_1, "cell 0 0,growx");
		
		JButton btnRunTest_1 = new JButton("Run Test");
		

		testLaunchPanel.add(btnRunTest_1, "cell 0 1,grow");
		timerPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		launchControlPanel.add(timerPanel);
		timerPanel.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Timer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		timerPanel.add(panel_8, "cell 0 0,grow");
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
		/*
		 *  Pausing the Button For given seconds to avoid 
		 *  repeating too many tests
		 */
		
		Timer timer = new Timer(delay, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    	btnStart_1.setEnabled(true);
		    }
		});
		timer.setRepeats(false);
		
		btnStart_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton)(ae.getSource());
				button.setEnabled(false);
				System.out.printf("Timer running");
				int getTime = getTime();
				timer.start();
				}

			private int getTime() {
				int minutes = Integer.parseInt(textField_2.getText());
				int seconds = Integer.parseInt(textField_3.getText());
				minutes = minutes * 10000;
				seconds = seconds * 1000;
				delay = minutes + seconds;
				return delay;
			}
		});
		
		
		JButton btnReset_1 = new JButton("Reset");
		panel_8.add(btnReset_1, "cell 1 3");
		
		JCheckBox chckbxSaveFile = new JCheckBox("Save file");
		timerPanel.add(chckbxSaveFile, "cell 0 1");
		
		JButton btnSaveFile = new JButton("Save file");
		
		timerPanel.add(btnSaveFile, "cell 0 2");
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
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
		
		JMenuItem mntmPrintScreen = new JMenuItem("Print screen");
		mnFile.add(mntmPrintScreen);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnSetup = new JMenu("Setup");
		menuBar.add(mnSetup);
		
		JMenu mnSettings = new JMenu("Settings");
		mnSetup.add(mnSettings);
		
		JMenuItem mntmApplication = new JMenuItem("Application");
		mnSettings.add(mntmApplication);
		
		JMenuItem mntmTests = new JMenuItem("Tests");
		mnSettings.add(mntmTests);
		
		JMenuItem mntmRegistration = new JMenuItem("Registration");
		mnSetup.add(mntmRegistration);
		
		JMenu mnTest = new JMenu("Test");
		menuBar.add(mnTest);
		
		JMenuItem mntmTripleA = new JMenuItem("Triple A");
		mnTest.add(mntmTripleA);
		
		JMenuItem mntmForceReduction = new JMenuItem("Force reduction");
		mnTest.add(mntmForceReduction);
		
		JMenuItem mntmForceDispl = new JMenuItem("Force displ");
		mnTest.add(mntmForceDispl);
		
		JMenuItem mntmBallBounce = new JMenuItem("Ball bounce");
		mnTest.add(mntmBallBounce);
		
		JMenuItem mntmBallSpeed = new JMenuItem("Ball speed");
		mnTest.add(mntmBallSpeed);
		
		JMenuItem mntmStudSlide = new JMenuItem("Stud slide");
		mnTest.add(mntmStudSlide);
		
		JMenuItem mntmHeadInjury = new JMenuItem("Head injury");
		mnTest.add(mntmHeadInjury);
		
		JMenuItem mntmGenericTest = new JMenuItem("Generic test");
		mnTest.add(mntmGenericTest);
		
		JMenu mnCalibrate = new JMenu("Calibrate");
		menuBar.add(mnCalibrate);
		
		JMenu mnLvdts = new JMenu("LVDTs");
		mnCalibrate.add(mnLvdts);
		
		JMenuItem mntmCalibrate = new JMenuItem("Calibrate");
		mnLvdts.add(mntmCalibrate);
		
		JMenuItem mntmValidate = new JMenuItem("Validate");
		mnLvdts.add(mntmValidate);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmLicenceRegistration = new JMenuItem("Licence registration");
		mnHelp.add(mntmLicenceRegistration);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JPanel dataViewPanel = new JPanel();
		frame.getContentPane().add(dataViewPanel, BorderLayout.CENTER);
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
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 3;
		gbc_panel_5.gridy = 0;
		panel_10.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{75, 64, 0};
		gbl_panel_5.rowHeights = new int[]{225, 20, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblFmax = new JLabel("Fmax");
		GridBagConstraints gbc_lblFmax = new GridBagConstraints();
		gbc_lblFmax.anchor = GridBagConstraints.EAST;
		gbc_lblFmax.insets = new Insets(0, 0, 5, 5);
		gbc_lblFmax.gridx = 0;
		gbc_lblFmax.gridy = 0;
		panel_5.add(lblFmax, gbc_lblFmax);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_5.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblForceReduction = new JLabel("Force reduction");
		GridBagConstraints gbc_lblForceReduction = new GridBagConstraints();
		gbc_lblForceReduction.anchor = GridBagConstraints.WEST;
		gbc_lblForceReduction.insets = new Insets(0, 0, 0, 5);
		gbc_lblForceReduction.gridx = 0;
		gbc_lblForceReduction.gridy = 1;
		panel_5.add(lblForceReduction, gbc_lblForceReduction);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panel_5.add(textField, gbc_textField);
		textField.setColumns(10);
		XYSeries series = new XYSeries("Series1");
		for(int i = 0; i < 1000; i++) {
			series.add(new XYDataItem(i, i*i));
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Acceleration Vs Time","Time","Acceleration", dataset);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		dataViewPanel.add(tabbedPane, gbc_tabbedPane);
		
		/*
		 * GENERATING XML REPORT
		 */
		/*
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveFile = new JFileChooser();
				saveFile.setDialogTitle("Choose where to save the file");
				int userSelection = saveFile.showSaveDialog(frame);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File file = saveFile.getSelectedFile();
					//Save file into xml format
					file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".xml");
					ArrayList<String> csv = new ArrayList<>();
				    if (chart.getPlot() instanceof XYPlot) {
				    	Dataset dataset = chart.getXYPlot().getDataset();
				        XYDataset xyDataset = (XYDataset) dataset;
				        int seriesCount = xyDataset.getSeriesCount();
				        for (int i = 0; i < seriesCount; i++) {
				        	int itemCount = xyDataset.getItemCount(i);
				            for (int j = 0; j < itemCount; j++) {
				               Comparable key = xyDataset.getSeriesKey(i);
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
				
			}
		});
		*/
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
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Results", null, panel_6, null);
		panel_6.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{75, 64, 0};
		gbl_panel_6.rowHeights = new int[]{224, 0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		
		JLabel lblFmax_1 = new JLabel("Fmax");
		GridBagConstraints gbc_lblFmax_1 = new GridBagConstraints();
		gbc_lblFmax_1.anchor = GridBagConstraints.EAST;
		gbc_lblFmax_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblFmax_1.gridx = 0;
		gbc_lblFmax_1.gridy = 0;
		panel_6.add(lblFmax_1, gbc_lblFmax_1);
		
		textField_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 0;
		panel_6.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);
		
		JLabel lblForceReduction_1 = new JLabel("Force reduction");
		GridBagConstraints gbc_lblForceReduction_1 = new GridBagConstraints();
		gbc_lblForceReduction_1.anchor = GridBagConstraints.EAST;
		gbc_lblForceReduction_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblForceReduction_1.gridx = 0;
		gbc_lblForceReduction_1.gridy = 1;
		panel_6.add(lblForceReduction_1, gbc_lblForceReduction_1);
		
		textField_8 = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 1;
		panel_6.add(textField_8, gbc_textField_8);
		textField_8.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridheight = 2;
		gbc_panel_7.gridx = 3;
		gbc_panel_7.gridy = 3;
		dataViewPanel.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[]{75, 64, 0};
		gbl_panel_7.rowHeights = new int[]{0, 230, 0, 0};
		gbl_panel_7.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JLabel lblFmax_2 = new JLabel("Fmax");
		GridBagConstraints gbc_lblFmax_2 = new GridBagConstraints();
		gbc_lblFmax_2.anchor = GridBagConstraints.EAST;
		gbc_lblFmax_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblFmax_2.gridx = 0;
		gbc_lblFmax_2.gridy = 0;
		panel_7.add(lblFmax_2, gbc_lblFmax_2);
		
		textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 0);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 1;
		gbc_textField_7.gridy = 0;
		panel_7.add(textField_7, gbc_textField_7);
		textField_7.setColumns(10);
		
		JLabel lblForceReduction_2 = new JLabel("Force reduction");
		GridBagConstraints gbc_lblForceReduction_2 = new GridBagConstraints();
		gbc_lblForceReduction_2.anchor = GridBagConstraints.EAST;
		gbc_lblForceReduction_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblForceReduction_2.gridx = 0;
		gbc_lblForceReduction_2.gridy = 1;
		panel_7.add(lblForceReduction_2, gbc_lblForceReduction_2);
		
		textField_9 = new JTextField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 0);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 1;
		gbc_textField_9.gridy = 1;
		panel_7.add(textField_9, gbc_textField_9);
		textField_9.setColumns(10);
		
		JTextPane txtpnAcclerationVsTime = new JTextPane();
		txtpnAcclerationVsTime.setText("Accleration vs time graph for test 3");
		GridBagConstraints gbc_txtpnAcclerationVsTime = new GridBagConstraints();
		gbc_txtpnAcclerationVsTime.fill = GridBagConstraints.BOTH;
		gbc_txtpnAcclerationVsTime.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnAcclerationVsTime.gridx = 0;
		gbc_txtpnAcclerationVsTime.gridy = 4;
		dataViewPanel.add(txtpnAcclerationVsTime, gbc_txtpnAcclerationVsTime);
		
		JTextArea txtrTestGraph_1 = new JTextArea();
		txtrTestGraph_1.setText("Test 3 graph 2");
		GridBagConstraints gbc_txtrTestGraph_1 = new GridBagConstraints();
		gbc_txtrTestGraph_1.insets = new Insets(0, 0, 5, 5);
		gbc_txtrTestGraph_1.fill = GridBagConstraints.BOTH;
		gbc_txtrTestGraph_1.gridx = 1;
		gbc_txtrTestGraph_1.gridy = 4;
		dataViewPanel.add(txtrTestGraph_1, gbc_txtrTestGraph_1);
		
		JTextArea txtrTestGraph_2 = new JTextArea();
		txtrTestGraph_2.setText("Test 3 graph 3");
		GridBagConstraints gbc_txtrTestGraph_2 = new GridBagConstraints();
		gbc_txtrTestGraph_2.insets = new Insets(0, 0, 5, 5);
		gbc_txtrTestGraph_2.fill = GridBagConstraints.BOTH;
		gbc_txtrTestGraph_2.gridx = 2;
		gbc_txtrTestGraph_2.gridy = 4;
		dataViewPanel.add(txtrTestGraph_2, gbc_txtrTestGraph_2);
		
		JPanel averageResultsPanel = new JPanel();
		frame.getContentPane().add(averageResultsPanel, BorderLayout.SOUTH);
		averageResultsPanel.setLayout(new MigLayout("", "[77px]", "[14px]"));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "Results (3 test average)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		averageResultsPanel.add(panel_11, "cell 0 0,grow");
		panel_11.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblFmaxaverage = new JLabel("Fmax (average)");
		panel_11.add(lblFmaxaverage);
		
		textField_10 = new JTextField();
		panel_11.add(textField_10);
		textField_10.setColumns(10);
		
		JLabel label = new JLabel("");
		panel_11.add(label);
		
		JLabel lblForceReductionaverage = new JLabel("Force reduction (average)");
		panel_11.add(lblForceReductionaverage);
		
		textField_11 = new JTextField();
		panel_11.add(textField_11);
		textField_11.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		panel_11.add(label_1);
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
