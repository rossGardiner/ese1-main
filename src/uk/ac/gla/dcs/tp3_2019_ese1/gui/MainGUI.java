/**
 * @author Agnes Ola
 * 
 * All code below auto generated so far - with WindowBuilder in Eclipse
 * TO DO refactoring
 * 
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
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainGUI {
	private JFrame frame;
	private final JPanel panel_5 = new JPanel();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_4;
	private JTextField textField_5;

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
		frame.setBounds(100, 100, 1109, 802);
		
		JTabbedPane settingsPane = new JTabbedPane(JTabbedPane.TOP);
		settingsPane.setToolTipText("SESTTING\r\n");
		frame.getContentPane().add(settingsPane, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		settingsPane.addTab("Results", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		settingsPane.addTab("Setup", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		settingsPane.addTab("Calibrate", null, panel_1, null);
		
		JPanel panel_4 = new JPanel();
		settingsPane.addTab("LVDT", null, panel_3, null);
		
		JPanel panel = new JPanel();
		settingsPane.addTab("Results", null, panel, null);
		
		JPanel launchControlPanel = new JPanel();
		frame.getContentPane().add(launchControlPanel, BorderLayout.WEST);
		launchControlPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panel_6 = new JPanel();
		launchControlPanel.add(panel_6);
		panel_6.setBorder(new TitledBorder(null, "Select test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JButton btnAaa = new JButton("AAA");
		panel_6.add(btnAaa, "cell 0 0,growx");
		
		JButton btnForceRed = new JButton("Force Red");
		panel_6.add(btnForceRed, "cell 0 1,growx");
		
		JButton btnDisplacement = new JButton("Displacement");
		panel_6.add(btnDisplacement, "cell 0 2,growx");
		
		JPanel panel_7 = new JPanel();
		launchControlPanel.add(panel_7);
		panel_7.setBorder(new TitledBorder(null, "Test control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setLayout(new MigLayout("", "[grow][]", "[][]"));
		
		JButton btnMagnetStatus_1 = new JButton("Magnet status");
		panel_7.add(btnMagnetStatus_1, "cell 0 0,growx");
		
		JButton btnRunTest_1 = new JButton("Run Test");
		panel_7.add(btnRunTest_1, "cell 0 1,grow");
		
		JPanel panel_9 = new JPanel();
		panel_7.add(panel_9, "cell 1 1");
		panel_9.setLayout(new MigLayout("", "[]", "[]"));
		panel_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		launchControlPanel.add(panel_5);
		panel_5.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Timer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.add(panel_8, "cell 0 0,grow");
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
		
		JCheckBox chckbxSaveFile = new JCheckBox("Save file");
		panel_5.add(chckbxSaveFile, "cell 0 1");
		
		JButton btnSaveFile = new JButton("Save file");
		panel_5.add(btnSaveFile, "cell 0 2");
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenuItem mntmFile = new JMenuItem("File");
		mntmFile.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mntmFile);
		
		JMenuItem mntmEdit = new JMenuItem("Edit");
		menuBar.add(mntmEdit);
		
		JMenuItem mntmSetup = new JMenuItem("Setup");
		menuBar.add(mntmSetup);
		
		JMenuItem mntmTest = new JMenuItem("Test");
		menuBar.add(mntmTest);
		
		JMenuItem mntmCalibrate = new JMenuItem("Calibrate");
		menuBar.add(mntmCalibrate);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		menuBar.add(mntmHelp);
		
		JPanel dataViewPanel = new JPanel();
		frame.getContentPane().add(dataViewPanel, BorderLayout.CENTER);
		GridBagLayout gbl_dataViewPanel = new GridBagLayout();
		gbl_dataViewPanel.columnWidths = new int[]{159, 157, 149, 75, 64, 98, 68, 0};
		gbl_dataViewPanel.rowHeights = new int[]{225, 20, 224, 230, 0};
		gbl_dataViewPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_dataViewPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		dataViewPanel.setLayout(gbl_dataViewPanel);
		
		JTextPane txtpnAccelerationVsTime1 = new JTextPane();
		txtpnAccelerationVsTime1.setText("Acceleration vs time graph for test 1");
		GridBagConstraints gbc_txtpnAccelerationVsTime1 = new GridBagConstraints();
		gbc_txtpnAccelerationVsTime1.fill = GridBagConstraints.BOTH;
		gbc_txtpnAccelerationVsTime1.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnAccelerationVsTime1.gridx = 0;
		gbc_txtpnAccelerationVsTime1.gridy = 0;
		dataViewPanel.add(txtpnAccelerationVsTime1, gbc_txtpnAccelerationVsTime1);
		
		JTextArea txtrSecondGraphTest = new JTextArea();
		txtrSecondGraphTest.setText("Second graph test 1");
		GridBagConstraints gbc_txtrSecondGraphTest = new GridBagConstraints();
		gbc_txtrSecondGraphTest.fill = GridBagConstraints.BOTH;
		gbc_txtrSecondGraphTest.insets = new Insets(0, 0, 5, 5);
		gbc_txtrSecondGraphTest.gridx = 1;
		gbc_txtrSecondGraphTest.gridy = 0;
		dataViewPanel.add(txtrSecondGraphTest, gbc_txtrSecondGraphTest);
		
		JTextArea txtrThirdGraphTest = new JTextArea();
		txtrThirdGraphTest.setText("Third graph test 1");
		GridBagConstraints gbc_txtrThirdGraphTest = new GridBagConstraints();
		gbc_txtrThirdGraphTest.fill = GridBagConstraints.BOTH;
		gbc_txtrThirdGraphTest.insets = new Insets(0, 0, 5, 5);
		gbc_txtrThirdGraphTest.gridx = 2;
		gbc_txtrThirdGraphTest.gridy = 0;
		dataViewPanel.add(txtrThirdGraphTest, gbc_txtrThirdGraphTest);
		
		JLabel lblFmax = new JLabel("Fmax");
		GridBagConstraints gbc_lblFmax = new GridBagConstraints();
		gbc_lblFmax.anchor = GridBagConstraints.EAST;
		gbc_lblFmax.insets = new Insets(0, 0, 5, 5);
		gbc_lblFmax.gridx = 3;
		gbc_lblFmax.gridy = 0;
		dataViewPanel.add(lblFmax, gbc_lblFmax);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 4;
		gbc_textField_1.gridy = 0;
		dataViewPanel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblAverageFmax = new JLabel("Average Fmax");
		GridBagConstraints gbc_lblAverageFmax = new GridBagConstraints();
		gbc_lblAverageFmax.anchor = GridBagConstraints.EAST;
		gbc_lblAverageFmax.insets = new Insets(0, 0, 5, 5);
		gbc_lblAverageFmax.gridx = 5;
		gbc_lblAverageFmax.gridy = 0;
		dataViewPanel.add(lblAverageFmax, gbc_lblAverageFmax);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.gridx = 6;
		gbc_textField_4.gridy = 0;
		dataViewPanel.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblForceReduction = new JLabel("Force reduction");
		GridBagConstraints gbc_lblForceReduction = new GridBagConstraints();
		gbc_lblForceReduction.anchor = GridBagConstraints.WEST;
		gbc_lblForceReduction.insets = new Insets(0, 0, 5, 5);
		gbc_lblForceReduction.gridx = 3;
		gbc_lblForceReduction.gridy = 1;
		dataViewPanel.add(lblForceReduction, gbc_lblForceReduction);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 1;
		dataViewPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblAverageFReduction = new JLabel("Average F reduction");
		GridBagConstraints gbc_lblAverageFReduction = new GridBagConstraints();
		gbc_lblAverageFReduction.anchor = GridBagConstraints.WEST;
		gbc_lblAverageFReduction.insets = new Insets(0, 0, 5, 5);
		gbc_lblAverageFReduction.gridx = 5;
		gbc_lblAverageFReduction.gridy = 1;
		dataViewPanel.add(lblAverageFReduction, gbc_lblAverageFReduction);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.anchor = GridBagConstraints.NORTH;
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.gridx = 6;
		gbc_textField_5.gridy = 1;
		dataViewPanel.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JTextPane txtpnAccelerationVsTime = new JTextPane();
		txtpnAccelerationVsTime.setText("Acceleration vs time graph for test 2");
		GridBagConstraints gbc_txtpnAccelerationVsTime = new GridBagConstraints();
		gbc_txtpnAccelerationVsTime.fill = GridBagConstraints.BOTH;
		gbc_txtpnAccelerationVsTime.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnAccelerationVsTime.gridx = 0;
		gbc_txtpnAccelerationVsTime.gridy = 2;
		dataViewPanel.add(txtpnAccelerationVsTime, gbc_txtpnAccelerationVsTime);
		
		JTextPane txtpnAcclerationVsTime = new JTextPane();
		txtpnAcclerationVsTime.setText("Accleration vs time graph for test 3");
		GridBagConstraints gbc_txtpnAcclerationVsTime = new GridBagConstraints();
		gbc_txtpnAcclerationVsTime.fill = GridBagConstraints.BOTH;
		gbc_txtpnAcclerationVsTime.insets = new Insets(0, 0, 0, 5);
		gbc_txtpnAcclerationVsTime.gridx = 0;
		gbc_txtpnAcclerationVsTime.gridy = 3;
		dataViewPanel.add(txtpnAcclerationVsTime, gbc_txtpnAcclerationVsTime);
	}

}
