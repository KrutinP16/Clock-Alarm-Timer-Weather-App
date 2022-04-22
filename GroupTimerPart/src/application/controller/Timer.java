package application.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Timer {
	boolean play = false;
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;

	@FXML
	private Label timeLabel;

	@FXML
	private VBox timer;

	@FXML
	void reset(ActionEvent event) {
		play = false;
		elapsedTime = 0;
		timeLabel.setText("00:00:00");
	}

	public Timer() {
		bindToTime();
	}

	private void bindToTime() {

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {

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
}