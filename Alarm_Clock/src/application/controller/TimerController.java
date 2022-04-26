package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.ResourceBundle;

import application.model.AlarmModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimerController implements Initializable {
	boolean play = false;
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;

	int alarmN = 0;

	final DateFormat formatTime = DateFormat.getInstance();

	@FXML
	private Pane alarmOn;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private Label timeLabel;

	@FXML
	private Label currentTime;

	@FXML
	private VBox timer;

	@FXML
	void reset(ActionEvent event) {
		play = false;
		elapsedTime = 0;
		timeLabel.setText("00:00:00");
	}

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
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}

				if (play) {
					elapsedTime = elapsedTime + 1000;
					hours = (elapsedTime / 3600000);
					minutes = (elapsedTime / 60000) % 60;
					seconds = (elapsedTime / 1000) % 60;

					timeLabel.setText(String.format(String.format("%02d", hours) + ":" + String.format("%02d", minutes)
							+ ":" + String.format("%02d", seconds)));
				}
			}
		}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	@FXML
	void start(ActionEvent event) {
		play = true;
	}

	@FXML
	void stop(ActionEvent event) {
		play = false;
	}

	@FXML
	void alarmOff(ActionEvent event) throws IOException {
		alarmOn.setVisible(false);

		AlarmModel remove = new AlarmModel();
		remove.removeAlarm(alarmN - 1, "alarms.txt");
	}

	@FXML
	void handleAlarm(ActionEvent event) throws IOException {
		URL url = new File("Alarm.fxml").toURI().toURL();
		Parent mainPane = FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bindToTime();
	}
}