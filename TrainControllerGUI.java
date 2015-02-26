import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToggleButton;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JSplitPane;

import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.GridLayout;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.text.DecimalFormat;

public class TrainControllerGUI {

	private JFrame frame;
	private JPanel panel_1;
	private JPanel panel;
	private JSlider slider;
	private JLabel lblCurrentSpeed;
	private JLabel lblCurrentSpeedLimit;
	private JLabel lblCurrentAuthority;
	private JLabel lblErrors;
	private JLabel lblBeaconMessage;
	private JLabel lblTrainControllerV;
	private JLabel lblNextStation;
	private JToggleButton toggle_service;
	private JLabel lblArrivingAtStation;
	private JLabel lblLightsOnoff;
	private JLabel lblDoorsOpen;
	private JLabel lblAnnouncementInProgress;
	private JLabel lblThermostat;
	private JSlider slider_thermo;
	private JLabel label_7;
	private JLabel lblSpeed_1;
	private JLabel lblAcutal;
	private JLabel lblCommandedSpeed;
	private JSlider slider_2;
	
	private TrainController tc;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private DecimalFormat db = new DecimalFormat("0.00");
	private JLabel lblInManualMode;
	private JToggleButton tglbtnAutopilot;
	private JToggleButton toggle_emergency;
	private JRadioButton radioButton_announcement;
	private JRadioButton radioButton_doors;
	private JRadioButton radioButton_lights;
	private JRadioButton radioButton_station;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainControllerGUI window = new TrainControllerGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 */
	public TrainControllerGUI() throws InterruptedException {
		tc = new TrainController();
		initialize();
		initializeValues();
		frame.setVisible(true);
		beginPowerHandler();
		beginAutoPilotHandler();
		PowerThread pt = new PowerThread();
		Thread t1 = new Thread(pt);
		t1.start();
	}
	private class PowerThread implements Runnable {
		  private boolean isAlive = true;
		  public void run() {
		      while (isAlive) {
		    	  TrainControllerGUI.this.updatePower(); 
		    	  try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		   }
		  public void kill(){
				isAlive = false;
			}
		}
	private synchronized void updatePower(){
		setCommandedPowerBox(tc.getCommandedPower());
	}
	private void initializeValues(){
		setDesiredVelocitySlider(tc.getCommandedSpeed());
		setActualVelocitySlider(tc.getCurrentSpeed());
		setCommandedPowerBox(tc.getCommandedPower());
		setCurrentSpeedBox(tc.getCurrentSpeed());
		setCommandedSpeedBox(tc.getCommandedSpeed());
		setSpeedLimitBox(tc.getSpeedLimit());
		setAuthorityBox(tc.getAuthority());
	}
	private void setDesiredVelocitySlider( double value){
		slider.setValue((int) value);
	}
	private void setActualVelocitySlider(double value){
		slider_2.setValue((int) value);
	}
	private void setCommandedPowerBox(double value){
		textField.setText("" + db.format(value) + " kW");
	}
	private void setCurrentSpeedBox(double value){
		textField_1.setText("" + db.format(value) + " km/h");
	}
	private void setCommandedSpeedBox(double value){
		textField_2.setText("" + db.format(value) + " km/h");
	}
	private void setSpeedLimitBox(double value){
		textField_3.setText("" + db.format(value) + " km/h");
	}
	private void setAuthorityBox(double value){
		textField_4.setText("" + db.format(value) + " km");
	}
	private void setBeaconBox(String value){
		
	}
	private void setErrorBox(String value){
		
	}
	private void setNextStationBox(String value){
		
	}
	private double getCommandedSpeedSlider(){
		return (double) slider.getValue();
	}
	
	private void beginPowerHandler(){
		/*btnSetSpeed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("pushed");
            	tc.setCommandedSpeed(getCommandedSpeedSlider());
            	setCommandedSpeedBox(tc.getCommandedSpeed());
            	//setCommandedPowerBox(tc.getCommandedPower());
            }
        });*/
		
		slider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	tc.setCommandedSpeed(getCommandedSpeedSlider());
            	setCommandedSpeedBox(tc.getCommandedSpeed());
	        }
	    });
	}
	
	private void beginAutoPilotHandler(){
		tglbtnAutopilot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tglbtnAutopilot.isSelected()){
                	lblInManualMode.setText("Automatic Mode is ON");
                	lblInManualMode.setForeground(Color.RED);
                	slider.setEnabled(false);
                	toggle_service.setEnabled(false);
                	toggle_emergency.setEnabled(false);
                	radioButton_station.setEnabled(false);
                	radioButton_doors.setEnabled(false);
                	radioButton_lights.setEnabled(false);
                	radioButton_announcement.setEnabled(false);
                	//btnSetSpeed.setEnabled(false);
                	
                	tc.setCommandedSpeed(tc.getSpeedLimit());
                	setCommandedSpeedBox(tc.getCommandedSpeed());
                	setDesiredVelocitySlider(tc.getCommandedSpeed());
                	
                }
                else{
                	lblInManualMode.setText("Manual Mode is ON");
                	lblInManualMode.setForeground(Color.BLACK);
                	slider.setEnabled(true);
                	toggle_service.setEnabled(true);
                	toggle_emergency.setEnabled(true);
                	radioButton_station.setEnabled(true);
                	radioButton_doors.setEnabled(true);
                	radioButton_lights.setEnabled(true);
                	radioButton_announcement.setEnabled(true);
                	//btnSetSpeed.setEnabled(true);
                }
            }
        });
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 792, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel_1 = new JPanel();
		
		panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_1.setLayout(null);
		
		lblArrivingAtStation = new JLabel("Arriving At Station");
		lblArrivingAtStation.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblArrivingAtStation.setBounds(24, 23, 129, 16);
		panel_1.add(lblArrivingAtStation);
		
		lblLightsOnoff = new JLabel("Lights On");
		lblLightsOnoff.setHorizontalAlignment(SwingConstants.CENTER);
		lblLightsOnoff.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblLightsOnoff.setBounds(165, 23, 129, 16);
		panel_1.add(lblLightsOnoff);
		
		lblDoorsOpen = new JLabel("Doors Open");
		lblDoorsOpen.setHorizontalAlignment(SwingConstants.CENTER);
		lblDoorsOpen.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblDoorsOpen.setBounds(306, 23, 107, 16);
		panel_1.add(lblDoorsOpen);
		
		lblAnnouncementInProgress = new JLabel("Announcement in Progress");
		lblAnnouncementInProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnnouncementInProgress.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblAnnouncementInProgress.setBounds(441, 23, 180, 16);
		panel_1.add(lblAnnouncementInProgress);
		
		lblThermostat = new JLabel("Thermostat");
		lblThermostat.setHorizontalAlignment(SwingConstants.CENTER);
		lblThermostat.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblThermostat.setBounds(648, 23, 107, 16);
		panel_1.add(lblThermostat);
		
		slider_thermo = new JSlider();
		slider_thermo.setMaximum(80);
		slider_thermo.setMajorTickSpacing(15);
		slider_thermo.setSnapToTicks(true);
		slider_thermo.setPaintTicks(true);
		slider_thermo.setPaintLabels(true);
		slider_thermo.setBounds(630, 40, 144, 49);
		panel_1.add(slider_thermo);
		
		radioButton_announcement = new JRadioButton("");
		radioButton_announcement.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_announcement.setBounds(464, 51, 141, 23);
		panel_1.add(radioButton_announcement);
		
		radioButton_doors = new JRadioButton("");
		radioButton_doors.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_doors.setBounds(293, 51, 141, 23);
		panel_1.add(radioButton_doors);
		
		radioButton_lights = new JRadioButton("");
		radioButton_lights.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_lights.setBounds(165, 51, 141, 23);
		panel_1.add(radioButton_lights);
		
		radioButton_station = new JRadioButton("");
		radioButton_station.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_station.setBounds(12, 51, 141, 23);
		panel_1.add(radioButton_station);
		panel.setLayout(null);
		
		slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setMaximum(70);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setBounds(16, 106, 81, 211);
		panel.add(slider);
		
		tglbtnAutopilot = new JToggleButton("Toggle AutoPilot");
		tglbtnAutopilot.setForeground(Color.BLUE);
		tglbtnAutopilot.setBounds(318, 31, 437, 29);
		panel.add(tglbtnAutopilot);
		
		JLabel lblThrottle = new JLabel("Desired");
		lblThrottle.setHorizontalAlignment(SwingConstants.CENTER);
		lblThrottle.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		lblThrottle.setBounds(16, 58, 81, 29);
		panel.add(lblThrottle);
		
		lblCurrentSpeed = new JLabel("Current Speed");
		lblCurrentSpeed.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblCurrentSpeed.setBounds(217, 103, 88, 16);
		panel.add(lblCurrentSpeed);
		
		lblCurrentSpeedLimit = new JLabel("Current Speed Limit");
		lblCurrentSpeedLimit.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblCurrentSpeedLimit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentSpeedLimit.setBounds(164, 178, 142, 16);
		panel.add(lblCurrentSpeedLimit);
		
		lblCurrentAuthority = new JLabel("Current Authority");
		lblCurrentAuthority.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblCurrentAuthority.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrentAuthority.setBounds(174, 213, 133, 16);
		panel.add(lblCurrentAuthority);
		
		lblErrors = new JLabel("ERRORS");
		lblErrors.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblErrors.setHorizontalAlignment(SwingConstants.RIGHT);
		lblErrors.setBounds(174, 283, 132, 16);
		panel.add(lblErrors);
		
		lblBeaconMessage = new JLabel("Beacon Message");
		lblBeaconMessage.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblBeaconMessage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBeaconMessage.setBounds(164, 248, 132, 16);
		panel.add(lblBeaconMessage);
		
		lblTrainControllerV = new JLabel("Train Controller V1");
		lblTrainControllerV.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		lblTrainControllerV.setHorizontalAlignment(SwingConstants.LEFT);
		lblTrainControllerV.setBounds(16, 10, 275, 24);
		panel.add(lblTrainControllerV);
		
		lblNextStation = new JLabel("NEXT STATION");
		lblNextStation.setHorizontalAlignment(SwingConstants.CENTER);
		lblNextStation.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblNextStation.setBounds(536, 84, 185, 16);
		panel.add(lblNextStation);
		
		toggle_service = new JToggleButton("SERVICE BRAKE");
		toggle_service.setForeground(Color.GREEN);
		toggle_service.setBackground(Color.BLACK);
		toggle_service.setBounds(536, 142, 219, 80);
		panel.add(toggle_service);
		
		label_7 = new JLabel("16");
		label_7.setBackground(Color.BLACK);
		label_7.setBounds(584, -14, 25, 16);
		panel.add(label_7);
		
		toggle_emergency = new JToggleButton("EMERGENCY BRAKE");
		toggle_emergency.setForeground(Color.RED);
		toggle_emergency.setBackground(Color.BLACK);
		toggle_emergency.setBounds(536, 225, 219, 80);
		panel.add(toggle_emergency);
		
		slider_2 = new JSlider();
		slider_2.setEnabled(false);
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setOrientation(SwingConstants.VERTICAL);
		slider_2.setMaximum(70);
		slider_2.setMajorTickSpacing(10);
		slider_2.setMinorTickSpacing(1);
		slider_2.setBounds(101, 106, 81, 211);
		panel.add(slider_2);
		
		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeed.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		lblSpeed.setBounds(99, 81, 71, 35);
		panel.add(lblSpeed);
		
		JLabel lblCommandedPower = new JLabel("Commanded Power");
		lblCommandedPower.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblCommandedPower.setBounds(184, 70, 121, 16);
		panel.add(lblCommandedPower);
		
		lblSpeed_1 = new JLabel("Speed");
		lblSpeed_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeed_1.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		lblSpeed_1.setBounds(16, 81, 81, 29);
		panel.add(lblSpeed_1);
		
		lblAcutal = new JLabel("Acutal");
		lblAcutal.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcutal.setFont(new Font("Modern No. 20", Font.BOLD, 20));
		lblAcutal.setBounds(101, 58, 65, 29);
		panel.add(lblAcutal);
		
		lblCommandedSpeed = new JLabel("Commanded Speed");
		lblCommandedSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCommandedSpeed.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblCommandedSpeed.setBounds(163, 142, 142, 16);
		panel.add(lblCommandedSpeed);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(318, 63, 185, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(318, 96, 185, 28);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(318, 135, 185, 28);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(318, 171, 185, 28);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(318, 207, 185, 28);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(318, 241, 185, 28);
		panel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBounds(318, 276, 185, 28);
		panel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		textField_7.setBounds(536, 106, 219, 28);
		panel.add(textField_7);
		
		lblInManualMode = new JLabel("Manual Mode in ON");
		lblInManualMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblInManualMode.setFont(new Font("Modern No. 20", Font.PLAIN, 15));
		lblInManualMode.setBounds(455, 18, 162, 16);
		panel.add(lblInManualMode);
		frame.getContentPane().setLayout(groupLayout);
	}
}
