package uk.ac.gla.dcs.tp3_2019_ese1.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.*;

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
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import net.miginfocom.swing.MigLayout;
import uk.ac.gla.dcs.tp3_2019_ese1.aaadata.AAARunner;

import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.DaqDeviceDescriptor;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwBoard;
import uk.ac.gla.dcs.tp3_2019_ese1.libcbw.LibcbwException;
import com.jtattoo.plaf.acryl.*;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class MainGUI implements IGUI {

	/* Set colour scheme */
	{
		try {
			AcrylLookAndFeel.setTheme("Green-Giant-Font", "", "");
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JFrame frame;

	private final JPanel timerPanel = new JPanel();
	private JTextField textField_secs;
	private JTextField textField_mins;
	private JTextField cellTest1_PeakG;
	private JTextField cellTest2_PeakG;
	private LibcbwBoard.USB_1608FS board;
	private AAARunner _runner;
	private boolean _magnetStatus;
	private JTextField cellTest3_PeakG;
	private JTextField cellAvg_PeakG;
	private JTextField cellTest1_DropHT;
	private JTextField cellTest2_DropHT;
	private JTextField cellTest3_DropHT;
	private JTextField cellTest2_SpngDef;
	private JTextField cellTest1_SpngDef;
	private JTextField cellTest3_SpngDef;
	private JTextField cellTestAvg_SpngDef;
	private JTextField cellTest1_fred;
	private JTextField cellTest2_fred;
	private JTextField cellTest3_fred;
	private JTextField cellTestAvg_fred;
	private JTextField cellTest1_vdef;
	private JTextField cellTest2_vdef;
	private static Timer timer;
	private boolean _timerIsStarted = false;
	private String _minString = "0";
	private String _secString = "30";

	private JTextField cellTest3_vdef;
	private JTextField cellTestAvg_vdef;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
	private JTextField textField_45;
	private JTextField txtPeak;
	private JTextField textField_46;
	private JPanel panel_displacement;
	private static int delay;

	private JPanel panel_graph3;
	private JTextField cellTest1_ergRest;
	private JTextField cellTest2_ergRest;
	private JTextField cellTest3_ergRest;
	private JTextField cellTestAvg_ergRest;
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

	private JFreeChart _accelerationChart;
	private JFreeChart _velocityChart;
	private JFreeChart _displacementChart;

	private XYSeriesCollection _accelerationData = new XYSeriesCollection();
	private XYSeriesCollection _velocityData = new XYSeriesCollection();
	private XYSeriesCollection _displacementData = new XYSeriesCollection();

	private ArrayList<Double> _test2Values = new ArrayList<Double>();
	private ArrayList<Double> _test3Values = new ArrayList<Double>();
	private ArrayList<Double> _avgValues = new ArrayList<Double>();

	private int _n = 0;

	private boolean _initSucc = false;
	private JTextField cellTestAvg_DropHT;
	private JTextField textField;

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
	 * 
	 * @throws IOException
	 */
	public MainGUI() throws IOException {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		Map<String, double[]> RigMap = configReader.parseCSV();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		board = null;
		try {
			DaqDeviceDescriptor[] daqArray = DaqDeviceDescriptor.getDaqDeviceInventory(DaqDeviceDescriptor.USB_IFC, 5);
			// routine to test devices for board
			for (int i = 0; i < daqArray.length; i++) {
				if (daqArray[i].ProductID == 125 || daqArray[i].ProductID == 234)
					board = daqArray[i].createDaqDevice(LibcbwBoard.USB_1608FS::new);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		_runner = new AAARunner(board, this);

		frame.getContentPane().setLayout(new MigLayout("", "[652px][444px][][]", "[23px][][825px]"));
		JTabbedPane settingsPane = new JTabbedPane(JTabbedPane.TOP);
		settingsPane.setToolTipText("SESTTING\r\n");
		frame.getContentPane().add(settingsPane, "cell 3 2,alignx right,growy");

		JPanel resultsPane = new JPanel();
		settingsPane.addTab("Results", null, resultsPane, null);
		resultsPane.setBorder(new TitledBorder(null, "Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		resultsPane.setLayout(new MigLayout("", "[77px][grow][grow][grow][grow]", "[][][14px][][][][][][][][][]"));

		JLabel lblTest = new JLabel("Test 1");
		resultsPane.add(lblTest, "cell 1 0");

		JLabel lblTest_1 = new JLabel("Test 2");
		resultsPane.add(lblTest_1, "cell 2 0");

		JLabel lblTest_2 = new JLabel("Test 3");
		resultsPane.add(lblTest_2, "cell 3 0");

		JLabel lblAvgTests = new JLabel("Avg Tests 2&3");
		resultsPane.add(lblAvgTests, "cell 4 0");

		JLabel lblPeakG = new JLabel("Peak G");
		resultsPane.add(lblPeakG, "cell 0 1,alignx trailing");

		cellTest1_PeakG = new JTextField();
		resultsPane.add(cellTest1_PeakG, "cell 1 1,growx");
		cellTest1_PeakG.setColumns(10);

		cellTest2_PeakG = new JTextField();
		resultsPane.add(cellTest2_PeakG, "cell 2 1,growx");
		cellTest2_PeakG.setColumns(10);

		cellTest3_PeakG = new JTextField();
		resultsPane.add(cellTest3_PeakG, "cell 3 1,growx");
		cellTest3_PeakG.setColumns(10);

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

		cellTestAvg_DropHT = new JTextField();
		cellTestAvg_DropHT.setColumns(10);
		resultsPane.add(cellTestAvg_DropHT, "cell 4 5,growx");

		// textField_19 = new JTextField();
		// resultsPane.add(textField_19, "cell 4 5,growx");
		// textField_19.setColumns(10);

		JLabel lblSpringDeformation = new JLabel("Spring deformation");
		resultsPane.add(lblSpringDeformation, "cell 0 6,alignx trailing");

		cellTest1_SpngDef = new JTextField();
		resultsPane.add(cellTest1_SpngDef, "cell 1 6,growx");
		cellTest1_SpngDef.setColumns(10);

		cellTest2_SpngDef = new JTextField();
		resultsPane.add(cellTest2_SpngDef, "cell 2 6,growx");
		cellTest2_SpngDef.setColumns(10);

		cellTest3_SpngDef = new JTextField();
		resultsPane.add(cellTest3_SpngDef, "cell 3 6,growx");
		cellTest3_SpngDef.setColumns(10);

		cellTestAvg_SpngDef = new JTextField();
		resultsPane.add(cellTestAvg_SpngDef, "cell 4 6,growx");
		cellTestAvg_SpngDef.setColumns(10);

		JLabel lblForceReduction_3 = new JLabel("Force reduction");
		resultsPane.add(lblForceReduction_3, "cell 0 7,alignx trailing");
		lblForceReduction_3.setFont(new Font("SansSerif", Font.BOLD, 12));

		cellTest1_fred = new JTextField();
		resultsPane.add(cellTest1_fred, "cell 1 7,growx");
		cellTest1_fred.setColumns(10);

		cellTest2_fred = new JTextField();
		resultsPane.add(cellTest2_fred, "cell 2 7,growx");
		cellTest2_fred.setColumns(10);

		cellTest3_fred = new JTextField();
		resultsPane.add(cellTest3_fred, "cell 3 7,growx");
		cellTest3_fred.setColumns(10);

		cellTestAvg_fred = new JTextField();
		resultsPane.add(cellTestAvg_fred, "cell 4 7,growx");
		cellTestAvg_fred.setColumns(10);

		JLabel lblVerticalDeformation = new JLabel("Vertical deformation");
		resultsPane.add(lblVerticalDeformation, "cell 0 8,alignx trailing");
		lblVerticalDeformation.setFont(new Font("SansSerif", Font.BOLD, 12));

		cellTest1_vdef = new JTextField();
		resultsPane.add(cellTest1_vdef, "cell 1 8,growx");
		cellTest1_vdef.setColumns(10);

		cellTest2_vdef = new JTextField();
		resultsPane.add(cellTest2_vdef, "cell 2 8,growx");
		cellTest2_vdef.setColumns(10);

		cellTest3_vdef = new JTextField();
		resultsPane.add(cellTest3_vdef, "cell 3 8,growx");
		cellTest3_vdef.setColumns(10);

		cellTestAvg_vdef = new JTextField();
		resultsPane.add(cellTestAvg_vdef, "cell 4 8,growx");
		cellTestAvg_vdef.setColumns(10);

		JLabel lblEnergyRestitution = new JLabel("Energy restitution");
		resultsPane.add(lblEnergyRestitution, "cell 0 9,alignx trailing");
		lblEnergyRestitution.setFont(new Font("SansSerif", Font.BOLD, 12));

		cellTest1_ergRest = new JTextField();
		resultsPane.add(cellTest1_ergRest, "cell 1 9,growx");
		cellTest1_ergRest.setColumns(10);

		cellTest2_ergRest = new JTextField();
		resultsPane.add(cellTest2_ergRest, "cell 2 9,growx");
		cellTest2_ergRest.setColumns(10);

		cellTest3_ergRest = new JTextField();
		resultsPane.add(cellTest3_ergRest, "cell 3 9,growx");
		cellTest3_ergRest.setColumns(10);

		cellTestAvg_ergRest = new JTextField();
		resultsPane.add(cellTestAvg_ergRest, "cell 4 9,growx");
		cellTestAvg_ergRest.setColumns(10);
								
										JButton btnSaveFile = new JButton("Save file");
										resultsPane.add(btnSaveFile, "cell 0 11 5 1,alignx right");
				
						btnSaveFile.addActionListener((evt) -> {
							int n = 0;
						    JFileChooser saveFile = new JFileChooser();
						    saveFile.setDialogTitle("Choose where to save the file, the file will be saved into a csv format");
						    int userSelection = saveFile.showSaveDialog(frame);
						    if (userSelection == JFileChooser.APPROVE_OPTION) {
						        File file = saveFile.getSelectedFile();
						        //Save file into csv format
						        file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".csv");
						        ArrayList<String> csv = new ArrayList<>();
						        csv.add(String.format("%s, %s,  %s, %s", "TIME", "ACCELERATION", "VELOCITY", "DISPLACEMENT"));
						        if(_accelerationData != null) {
						        	int itemCount = _accelerationData.getItemCount(0);
						        	//ASSUMING THE ITEM COUNT IS THE SAME FOR EACH SERIES (IT IS)
						        	if(_velocityData != null) {
						        		if(_displacementData != null) {
						        			for(int i = 0; i < itemCount; i++) {
						        				Number timeNr = _accelerationData.getX(0, i);
						        				Number accNr = _accelerationData.getY(0, i);
						        				Number velNr = _velocityData.getY(0, i);
						        				Number disNr = _displacementData.getY(0, i);
						        				csv.add(String.format("%s, %s, %s, %s", timeNr, accNr, velNr, disNr));
						        			}
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
		;

		JPanel launchControlPanel = new JPanel();
		launchControlPanel.setBorder(null);
		frame.getContentPane().add(launchControlPanel, "cell 0 2,grow");
		launchControlPanel.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel testLaunchPanel = new JPanel();
		launchControlPanel.add(testLaunchPanel);
		// testLaunchPanel.setPreferredSize(new Dimension(480, 64));
		testLaunchPanel
				.setBorder(new TitledBorder(null, "Test control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		testLaunchPanel.setLayout(new MigLayout("", "[]", "[][][][]"));

		JButton btnMagnetStatus_1 = new JButton("Magnet toggle");
		_magnetStatus = false;

		btnMagnetStatus_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					if (_magnetStatus == false) {
						_magnetStatus = !_magnetStatus;
						board.digitalOut(0, true);
						btnMagnetStatus_1.setLabel("Magnet is ON");
						btnMagnetStatus_1.setBackground(new Color(0, 71, 137)); // SportsLabs Colors

					} else {
						_magnetStatus = !_magnetStatus;
						board.digitalOut(0, false);
						btnMagnetStatus_1.setLabel("Magnet is OFF");
						btnMagnetStatus_1.setBackground(new Color(255, 255, 255)); // SportsLabs Colors
					}

				} catch (LibcbwException ex) {
					ex.printStackTrace();
				}
			}
		});
		testLaunchPanel.add(btnMagnetStatus_1, "cell 0 0,growx,aligny top");

		JButton btnRunTest_1 = new JButton("Run Test");
		btnRunTest_1.addActionListener(_runner::runTest);

		testLaunchPanel.add(btnRunTest_1, "cell 0 1,growy");
		timerPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		launchControlPanel.add(timerPanel);
		timerPanel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][][]"));

		/*
		 * btnRunTest_1 = new JButton("Run Test"); btnRunTest_1.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) { Random r =
		 * new Random(); double[] array = new double[1000]; for(int i = 0; i < 1000;
		 * i++) { array[i] = (double)i * r.nextFloat(); } makeGraphs(array, array,
		 * array, 0, _n); _n++; }
		 * 
		 * });
		 */

		testLaunchPanel.add(btnRunTest_1, "cell 0 1");

		JPanel panel_8 = new JPanel();
		testLaunchPanel.add(panel_8, "cell 0 3");
		panel_8.setBorder(new TitledBorder(null, "Timer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.setLayout(new MigLayout("", "[left][]", "[][][][]"));

		JLabel lblMin = new JLabel("Min");
		panel_8.add(lblMin, "cell 0 0,alignx left");

		JLabel lblSec = new JLabel("Sec");
		panel_8.add(lblSec, "cell 1 0");

		textField_mins = new JTextField();
		textField_mins.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(textField_mins, "cell 0 1");
		textField_mins.setText("00");
		textField_mins.setColumns(10);
		
		textField_secs = new JTextField();
		textField_secs.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(textField_secs, "cell 1 1,growx");
		textField_secs.setText("30");
		textField_secs.setColumns(10);
		JButton button = btnRunTest_1;

		JButton btnStart_1 = new JButton("Start");
		panel_8.add(btnStart_1, "cell 0 3");

		JButton btnReset_1 = new JButton("Reset");
		btnReset_1.addActionListener((ae) -> {
			timer.stop();
			btnRunTest_1.setEnabled(true);
			textField_mins.setText("0");
			textField_secs.setText("30.00");
			btnStart_1.setText("Start");
			_timerIsStarted = false;
		});
		panel_8.add(btnReset_1, "cell 1 3");

		_timerIsStarted = false;

		btnStart_1.addActionListener((ae) -> {

				_timerIsStarted = !_timerIsStarted;
				if(_timerIsStarted) btnStart_1.setText("Stop");
				if(!_timerIsStarted) btnStart_1.setText("Start");		
				//disable the button
				
				button.setEnabled(false);
				//remove all non integers from input field
				String minString = textField_mins.getText();
				minString = minString.replaceAll("[^\\d.]", "");
				String secString = textField_secs.getText();
				secString = secString.replaceAll("[^\\d.]", "");
				//parse input data
				float minutes = Float.parseFloat(minString);
				float seconds = Float.parseFloat(secString);
				//create duration (in milliseconds)
				minutes = minutes * 60000;
				seconds = seconds * 1000;
				long duration = (int)minutes + (int)seconds;
				long startTime = System.currentTimeMillis();
				final Long startTimeInner = new Long(startTime);
				timer = new Timer(0, new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                    long now = System.currentTimeMillis();
		                    long clockTime = now - startTimeInner;
		                    //if time is up, stop
		                    if (clockTime >= duration) {
		                        clockTime = duration;
		                        ((Timer)e.getSource()).stop();
		                        button.setEnabled(true);
		                        btnStart_1.setText("Start");
		                    }
		                    //if stop button is pressed, stop
		                    if(!_timerIsStarted) {
		                    	((Timer)e.getSource()).stop();
		                    	button.setEnabled(true);
		                    }
		                    //format timer output
		                    long mins = (duration - clockTime)/60000;
		                    float secs = (duration - clockTime)%60000;
		                    secs = secs / 1000;
		                    String secStr = String.format("%.2f", secs);
		                    textField_secs.setText(secStr);
		                    textField_mins.setText(String.valueOf(mins));
		            }
		        });;
				timer.start();	
				

		});
		timerPanel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		launchControlPanel.add(timerPanel);
		timerPanel.setLayout(new MigLayout("", "[grow]", "[][][]"));

		/*
		 * Begin calibration dropdown and display section
		 */
		JComboBox selectRig = new JComboBox();
		timerPanel.add(selectRig, "cell 0 0");

		selectRig.removeAllItems();

		// Add rig titles as dropdown options
		for (String key : RigMap.keySet()) {
			selectRig.addItem(key);
		}

		/*
		 * Listen for new selection
		 */

		JLabel lbldisplayMass = new JLabel("Mass");
		timerPanel.add(lbldisplayMass, "cell 0 2,");

		JTextField txtMassField = new JTextField();
		txtMassField.setText(" ");
		txtMassField.setEditable(false);
		timerPanel.add(txtMassField, "cell 1 2,growx");

		JLabel lbldisplaySpring = new JLabel("Spring deformation");
		timerPanel.add(lbldisplaySpring, "cell 0 3");

		JTextField txtSpringField = new JTextField();
		txtSpringField.setText(" ");
		txtSpringField.setEditable(false);
		timerPanel.add(txtSpringField, "cell 1 3,growx");

		JLabel lblDisplayGain = new JLabel("Gain");
		timerPanel.add(lblDisplayGain, "cell 0 4");

		JTextField txtGainField = new JTextField();
		txtGainField.setText(" ");
		txtGainField.setEditable(false);
		timerPanel.add(txtGainField, "cell 1 4,growx");

		JLabel lblDisplayFreq = new JLabel("Frequency");
		timerPanel.add(lblDisplayFreq, "cell 0 5");

		JTextField txtFreqField = new JTextField();
		txtFreqField.setText(" ");
		txtFreqField.setEditable(false);
		timerPanel.add(txtFreqField, "cell 1 5,growx");

		JLabel lblDisplayCnt = new JLabel("Cnt");
		timerPanel.add(lblDisplayCnt, "cell 0 6");

		JTextField txtCntField = new JTextField();
		txtCntField.setText(" ");
		txtCntField.setEditable(false);
		timerPanel.add(txtCntField, "cell 1 6,growx");

		JLabel lblDisplayPre = new JLabel("Pre");
		timerPanel.add(lblDisplayPre, "cell 0 7");

		JTextField txtPreField = new JTextField();
		txtPreField.setText(" ");
		txtPreField.setEditable(false);
		timerPanel.add(txtPreField, "cell 1 7,growx");

		JLabel lblDisplaySafe = new JLabel("Safe");
		timerPanel.add(lblDisplaySafe, "cell 0 8");

		JTextField txtSafeField = new JTextField();
		txtSafeField.setText(" ");
		txtSafeField.setEditable(false);
		timerPanel.add(txtSafeField, "cell 1 8,growx");

		ActionListener cbActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String selectedRig = (String) selectRig.getSelectedItem();

				double[] calibrationValues = RigMap.get(selectedRig);

				_runner.MASS = calibrationValues[0];
				_runner.SPRINGCAL = calibrationValues[1];
				_runner.GAIN_CALI = calibrationValues[2];
				_runner.SAMPLE_RATE = (int) calibrationValues[3];
				_runner.SAMPLE_COUNT = (int) calibrationValues[4];
				_runner.PRE_DROP_CNT = (int) calibrationValues[5];
				_runner.SAFETY_MARGIN = (int) calibrationValues[6];

				txtMassField.setText(Double.toString(calibrationValues[0]));
				txtSpringField.setText(Double.toString(calibrationValues[1]));
				txtGainField.setText(Double.toString(calibrationValues[2]));
				txtFreqField.setText(Double.toString(calibrationValues[3]));
				txtCntField.setText(Double.toString(calibrationValues[4]));
				txtPreField.setText(Double.toString(calibrationValues[5]));
				txtSafeField.setText(Double.toString(calibrationValues[6]));
			}
		};

		/*
		 * This listener updated calibration values after dropdown is selected.
		 */
		selectRig.addActionListener(cbActionListener);

		/*
		 * Pausing the Button For given seconds to avoid repeating too many tests
		 */

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

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		JPanel dataViewPanel = new JPanel();
		frame.getContentPane().add(dataViewPanel, "cell 1 2 2 1,alignx left,growy");
		GridBagLayout gbl_dataViewPanel = new GridBagLayout();
		gbl_dataViewPanel.columnWidths = new int[] { 159, 157, 149, 75, 0 };
		gbl_dataViewPanel.rowHeights = new int[] { 225, 0, 224, 0, 230, 0, 0 };
		gbl_dataViewPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_dataViewPanel.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		dataViewPanel.setLayout(gbl_dataViewPanel);
		XYSeries series = new XYSeries("Test 1");
		XYSeries series2 = new XYSeries("Test 2");
		XYSeries series3 = new XYSeries("Test 3");


		//for(int i = 0; i < 1000; i++) {
		//	series.add(new XYDataItem(i, 0));
		//	series2.add(new XYDataItem(i, 0));
		//	series3.add(new XYDataItem(i, 0));
		//}

		_accelerationData.addSeries(series);
		_accelerationData.addSeries(series2);
		_accelerationData.addSeries(series3);
		_accelerationChart = ChartFactory.createXYLineChart("Acceleration Vs Time", "Time", "Acceleration",
				_accelerationData);
		XYItemRenderer theRenderer = _accelerationChart.getXYPlot().getRenderer();
		theRenderer.setSeriesStroke(0, new BasicStroke(2.5f));
		theRenderer.setSeriesStroke(1, new BasicStroke(2.5f));
		theRenderer.setSeriesStroke(2, new BasicStroke(2.5f));
		_accelerationChart.getXYPlot().setRenderer(theRenderer);
		_velocityData.addSeries(series);
		_velocityData.addSeries(series2);
		_velocityData.addSeries(series3);
		_velocityChart = ChartFactory.createXYLineChart("Velocity Vs Time", "Time", "Velocity", _velocityData);
		_velocityChart.getXYPlot().setRenderer(theRenderer);
		_displacementData.addSeries(series);
		_displacementData.addSeries(series2);
		_displacementData.addSeries(series3);
		_displacementChart = ChartFactory.createXYLineChart("Displacement Vs Time", "Time", "Displacement",
				_displacementData);
		_displacementChart.getXYPlot().setRenderer(theRenderer);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 5;
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		dataViewPanel.add(tabbedPane, gbc_tabbedPane);


		JPanel panel_acceleration = new JPanel();
		tabbedPane.addTab("Acceleration Vs. Time", null, panel_acceleration, null);
		ChartPanel chartPanelAcceleration = new ChartPanel(_accelerationChart);
		chartPanelAcceleration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// new ChartViewer(chart).setVisible(true);
					new ChartViewerDialog(_accelerationChart).setVisible(true);
				}
			}
		});
		chartPanelAcceleration.setDomainZoomable(true);
		panel_acceleration.setLayout(new BorderLayout());
		panel_acceleration.add(chartPanelAcceleration, BorderLayout.CENTER);

		JPanel panel_velocity = new JPanel();
		ChartPanel chartPanelVelocity = new ChartPanel(_velocityChart);
		chartPanelVelocity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
				    new ChartViewerDialog(_velocityChart).setVisible(true);
				}
			}
		});
		chartPanelVelocity.setDomainZoomable(true);
		panel_velocity.setLayout(new BorderLayout());
		panel_velocity.add(chartPanelVelocity, BorderLayout.CENTER);
		tabbedPane.addTab("Velocity Vs. Time", null, panel_velocity, null);

		panel_displacement = new JPanel();

		ChartPanel chartPanelDisplacement = new ChartPanel(_displacementChart);

		chartPanelDisplacement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
				    new ChartViewerDialog(_displacementChart).setVisible(true);
				}
			}
		});
		chartPanelDisplacement.setDomainZoomable(true);

		panel_displacement.setLayout(new BorderLayout());
		panel_displacement.add(chartPanelDisplacement, BorderLayout.CENTER);
		tabbedPane.addTab("Displacement Vs. Time", null, panel_displacement, null);

		JPanel averageResultsPanel = new JPanel();
		frame.getContentPane().add(averageResultsPanel, "cell 0 0 4 1,grow");
		averageResultsPanel.setLayout(new MigLayout("", "[77px]", "[14px]"));


		_initSucc = true;

	}

	@Override
	public void makeGraphs(double[] acceleration, double[] velocity, double[] disp, int drop_touch2, int testNr) {
		// first, work out the test index (0-2 inclusive)
		int testIdx = testNr % 3;

		// make graphical datasets for:
		// ACCELERATION
		XYSeries accelerationSeries = new XYSeries("Test " + (testNr + 1));
		int x = 0;
		for (double accVal : acceleration) {
			accelerationSeries.add(new XYDataItem(x, accVal));
			x++;
		}
		// VELOCITY
		XYSeries velocitySeries = new XYSeries("Test " + (testNr + 1));
		x = 0;
		for (double velVal : velocity) {
			velocitySeries.add(new XYDataItem(x, velVal));
			x++;
		}
		// DISPLACEMENT
		XYSeries displacementSeries = new XYSeries("Test " + (testNr + 1));
		x = 0;
		for (double dispVal : disp) {
			displacementSeries.add(new XYDataItem(x, dispVal));
			x++;
		}

		// update chart datasets for:
		// ACCELERATION
		List<Object> accData = Arrays.asList(_accelerationData.getSeries().toArray());
accData.set(testIdx,accelerationSeries);_accelerationData.removeAllSeries();for(

	Object series:accData)
	{
		_accelerationData.addSeries((XYSeries) series);
	}

	// VELOCITY
	List<Object> velData = Arrays.asList(_velocityData.getSeries()
			.toArray());velData.set(testIdx,velocitySeries);_velocityData.removeAllSeries();for(
	Object series:velData)
	{
		_velocityData.addSeries((XYSeries) series);
	}
	// DISPACEMENT
	List<Object> dispData = Arrays.asList(_displacementData.getSeries()
			.toArray());dispData.set(testIdx,displacementSeries);_displacementData.removeAllSeries();for(
	Object series:dispData)
	{
		_displacementData.addSeries((XYSeries) series);
	}

	}

	@Override
    public void outputResults(double peakG, double fmax, double fred, double v1, double v2, double energy,
            double drop_dist, double spring, double material, int testNr) {
    	
    	//first work out test index (0-2 inclusive)
    	int testIdx = testNr%3;
    	BigDecimal db;
    	if(testIdx == 0) {
    	  db = new BigDecimal(peakG);
    	  db = db.round(new MathContext(4));
   	 	  cellTest1_PeakG.setText(db.toString());
   	 	  db = new BigDecimal(fmax);
  	      db = db.round(new MathContext(4));
   	 	  cellTest1_Fmax.setText(db.toString());
   	 	  db = new BigDecimal(v1);
	      db = db.round(new MathContext(4));
   	 	  cellTest1_Velocity1.setText(db.toString());
   	 	  db = new BigDecimal(v2);
	      db = db.round(new MathContext(4));
   	 	  cellTest1_Velocity2.setText(db.toString());
   	 	  db = new BigDecimal(drop_dist);
	      db = db.round(new MathContext(4));
   	 	  cellTest1_DropHT.setText(db.toString());
   	 	  db = new BigDecimal(spring);
	      db = db.round(new MathContext(4));
   	 	  cellTest1_SpngDef.setText(db.toString());
   	 	  db = new BigDecimal(fred);
	      db = db.round(new MathContext(4));
   	 	  cellTest1_fred.setText(db.toString());
   	 	  db = new BigDecimal(material);
 	 	  db = db.round(new MathContext(4));
 	 	  cellTest1_vdef.setText(db.toString());
   	 	  db = new BigDecimal(energy);
	      db = db.round(new MathContext(4));
   	 	  cellTest1_ergRest.setText(db.toString());
    	}
    	if(testIdx == 1) {
    		_test2Values.add(peakG);
    		db = new BigDecimal(peakG);
      	  	db = db.round(new MathContext(4));
     	 	cellTest2_PeakG.setText(db.toString());
     	 	_test2Values.add(fmax);
     	 	db = new BigDecimal(fmax);
    	    db = db.round(new MathContext(4));
     	 	cellTest2_Fmax.setText(db.toString());
     	 	_test2Values.add(v1);
     	 	db = new BigDecimal(v1);
  	        db = db.round(new MathContext(4));
     	 	cellTest2_Velocity1.setText(db.toString());
     	 	_test2Values.add(v2);
     	 	db = new BigDecimal(v2);
  	        db = db.round(new MathContext(4));
     	 	cellTest2_Velocity2.setText(db.toString());
     	 	_test2Values.add(drop_dist);
     	 	db = new BigDecimal(drop_dist);
  	        db = db.round(new MathContext(4));
     	 	cellTest2_DropHT.setText(db.toString());
     	 	_test2Values.add(spring);
     	 	db = new BigDecimal(spring);
  	        db = db.round(new MathContext(4));
  	        cellTest2_SpngDef.setText(db.toString());
  	      _test2Values.add(fred);
     	    db = new BigDecimal(fred);
  	        db = db.round(new MathContext(4));
     	 	cellTest2_fred.setText(db.toString());
     	 	_test2Values.add(material);
     	 	db = new BigDecimal(material);
     	 	db = db.round(new MathContext(4));
     	 	cellTest2_vdef.setText(db.toString());
     	 	_test2Values.add(energy);
     	 	db = new BigDecimal(energy);
  	        db = db.round(new MathContext(4));
     	 	cellTest2_ergRest.setText(db.toString());
    	}
    	if(testIdx == 2) {
    		//System.out.println(_test2Values.toString());
    		_test3Values.add(peakG);
    		db = new BigDecimal(peakG);
      	  	db = db.round(new MathContext(4));
     	 	cellTest3_PeakG.setText(db.toString());
     	 	_test3Values.add(fmax);
     	 	db = new BigDecimal(fmax);
    	    db = db.round(new MathContext(4));
     	 	cellTest3_Fmax.setText(db.toString());
     	 	_test3Values.add(v1);
     	 	db = new BigDecimal(v1);
  	        db = db.round(new MathContext(4));
     	 	cellTest3_Velocity1.setText(db.toString());
     	 	_test3Values.add(v2);
     	 	db = new BigDecimal(v2);
  	        db = db.round(new MathContext(4));
     	 	cellTest3_Velocity2.setText(db.toString());
     	 	_test3Values.add(drop_dist);
     	 	db = new BigDecimal(drop_dist);
  	        db = db.round(new MathContext(4));
     	 	cellTest3_DropHT.setText(db.toString());
     	 	_test3Values.add(spring);
     	 	db = new BigDecimal(spring);
  	        db = db.round(new MathContext(4));
     	    cellTest3_SpngDef.setText(db.toString());
     	   _test3Values.add(fred);
     	    db = new BigDecimal(fred);
  	        db = db.round(new MathContext(4));
     	 	cellTest3_fred.setText(db.toString());
     	 	_test3Values.add(material);
     	 	db = new BigDecimal(material);
     	 	db = db.round(new MathContext(4));
     	 	cellTest3_vdef.setText(db.toString());
     	 	_test3Values.add(energy);
     	 	db = new BigDecimal(energy);
  	        db = db.round(new MathContext(4));
     	 	cellTest3_ergRest.setText(db.toString());
        	// Get averages into _avgValues array
        	for(int i = 0; i < _test3Values.size(); i++){
        		_avgValues.add(((_test3Values.get(i)+_test2Values.get(i))/2));
        	}
        	// Put them in the text fields and round
      	  	db = new BigDecimal(_avgValues.get(0));
      	  	db = db.round(new MathContext(4));
     	 	cellAvg_PeakG.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(1));
    	    db = db.round(new MathContext(4));
     	 	cellAvg_Fmax.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(2));
    	    db = db.round(new MathContext(4));
     	 	cellAvg_Velocity1.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(3));
    	    db = db.round(new MathContext(4));
     	 	cellAvg_Velocity2.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(4));
    	    db = db.round(new MathContext(4));
     	 	cellTestAvg_DropHT.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(5));
    	    db = db.round(new MathContext(4));
     	 	cellTestAvg_SpngDef.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(6));
    	    db = db.round(new MathContext(4));
     	 	cellTestAvg_fred.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(7));
    	    db = db.round(new MathContext(4));
     	 	cellTestAvg_vdef.setText(db.toString());
     	 	db = new BigDecimal(_avgValues.get(8));
    	    db = db.round(new MathContext(4));
     	 	cellTestAvg_ergRest.setText(db.toString());
    	}


    		
    }

	@Override
    public void displayErrorMessage(String msg) {
        // TODO Auto-generated method stub
        
    }


	public boolean is_magnetStatus() {
		return _magnetStatus;
	}

	public JFreeChart get_accelerationChart() {
		return _accelerationChart;
	}

	public XYSeriesCollection get_accelerationData() {
		return _accelerationData;
	}

	public XYSeriesCollection get_displacementData() {
		return _displacementData;
	}

	public ArrayList<Double> get_avgValues() {
		return _avgValues;
	}

}
