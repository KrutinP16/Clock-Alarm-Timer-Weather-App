package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import application.model.AlarmModel;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class AlarmController implements Initializable {

	final DateFormat formatTime = DateFormat.getInstance();

	boolean alarmOnOff = true;
	int alarmN = 0;

	@FXML
	private Pane alarmOn;

	@FXML
	private Label currentTime;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private Label nextAlarm;

	@FXML
	private ChoiceBox<Integer> hour;

	@FXML
	private Pane timeSelect;

	@FXML
	private ChoiceBox<String> when;

	@FXML
	private ChoiceBox<Integer> minute;

	@FXML
	private ListView<String> alarms1;

	@FXML
	private ListView<String> alarms2;

	@FXML
	private ChoiceBox<Integer> alarmNumber;

	@FXML
	private Pane alarmRemove;

	// Opens box that allows user to select fields for a new alarm
	@FXML
	void addAlarm(ActionEvent event) throws Exception {
		timeSelect.setVisible(true);
	}

	// Allows the user to select an hour, minute and the meridian for their new
	// alarm
	@FXML
	void add(ActionEvent event) throws IOException, ParseException {
		timeSelect.setVisible(false);
		AlarmModel add = new AlarmModel();
		add.addAlarm(hour.getValue() + ":" + String.format("%02d", minute.getValue()) + " " + when.getValue(),
				"alarms.txt");

		updateAlarmLists();
	}

	// Opens box that allows the user to remove a given alarm
	@FXML
	void removeAlarm(ActionEvent event) {
		alarmRemove.setVisible(true);
	}

	// Allows the user to remove an alarm based on it's number, or do nothing
	@FXML
	void remove(ActionEvent event) throws IOException, ParseException {
		alarmRemove.setVisible(false);

		if (alarmNumber.getValue() == null) {
			System.out.println("No alarms removed.");
			return;
		}

		AlarmModel remove = new AlarmModel();
		remove.removeAlarm(alarmNumber.getValue() - 1, "alarms.txt");

		updateAlarmLists();
	}

	// Turns off alarm
	@FXML
	void alarmOff(ActionEvent event) throws IOException, ParseException {
		alarmOn.setVisible(false);

		AlarmModel remove = new AlarmModel();
		remove.removeAlarm(alarmN - 1, "alarms.txt");

		updateAlarmLists();
	}

	// Takes user to Timer scene
	@FXML
	void handleTimer(ActionEvent event) throws IOException {
		URL url = new File("Timer.fxml").toURI().toURL();
		mainPane = FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	// Method created to update alarms1 and alarms2 ListViews
	public void updateAlarmLists() throws ParseException {
		alarms1.getItems().clear();
		alarms2.getItems().clear();
		alarmNumber.getItems().clear();

		AlarmModel load = new AlarmModel();
		int count = 1;
		ArrayList<String> alarms1List = new ArrayList<String>();
		ArrayList<String> alarms2List = new ArrayList<String>();
		try {
			alarms1List = load.loadAlarms1("alarms.txt");
			alarms2List = load.loadAlarms2("alarms.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < alarms1List.size(); i++) {
			alarms1.getItems().add("Alarm " + count + ": " + alarms1List.get(i));
			count++;
		}

		for (int i = 0; i < alarms2List.size(); i++) {
			alarms2.getItems().add("Alarm " + count + ": " + alarms2List.get(i));
			count++;
		}

		for (int i = 1; i < count; i++) {
			alarmNumber.getItems().add(i);
		}
	}

	// Method that constantly runs and updates currentTime label to display the time
	// at the moment. Also constantly looks for the next alarm and updates the
	// nextAlarm label and checks when it is time for an alarm to go off
	private void bindToTime() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Calendar time = Calendar.getInstance();
				currentTime.setText(formatTime.format(time.getTime()));

				AlarmModel load = new AlarmModel();

				try {
					if (load.alarmOn("alarms.txt") != 0) {
						alarmN = load.alarmOn("alarms.txt");
						alarmOn.setVisible(true);
					}
					if (alarms1.getItems().isEmpty()) {
						nextAlarm.setText("No Alarms Set");
					} else {
						nextAlarm.setText("Next Alarm: " + load.updateNextAlarm("alarms.txt"));
					}
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}
		}), new KeyFrame(Duration.seconds(1)));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/*
	 * Method that runs after the scene is loaded. Runs bindToTime() which
	 * constantly runs to update labels. Creates alarm.txt file. Updates Scene's
	 * ChoiceBoxes
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bindToTime();
		AlarmModel load = new AlarmModel();

		try {
			load.createFile("alarms.txt");
			updateAlarmLists();
		} catch (ParseException | IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 1; i < 60; i++) {
			if (i <= 12) {
				hour.getItems().add(i);
			}
			minute.getItems().add(i);
		}

		when.getItems().addAll("AM", "PM");

		hour.setValue(6);
		minute.setValue(30);
		when.setValue("AM");
	}
}
