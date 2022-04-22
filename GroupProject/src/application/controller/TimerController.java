package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimerController implements Initializable {
	boolean play = false;
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;

	final DateFormat formatTime = DateFormat.getInstance();

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
	void handleAlarm(ActionEvent event) throws IOException {
		URL url = new File("Alarm.fxml").toURI().toURL();
		Parent mainPane = FXMLLoader.load(url);
		Scene scene = new Scene(mainPane);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@FXML
	void handleClock(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bindToTime();
	}
}