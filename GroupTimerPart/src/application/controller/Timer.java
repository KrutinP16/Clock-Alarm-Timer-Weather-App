package application.controller;
//IMPORT THE JAVA APPLICATIONS
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
//DECLARE MY CLASS
public class Timer {
	//DECLARE VARIABLES
	boolean play = false;
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;
	
	// CODE FOR THE FXML PARTS FOR THE TIMER
	@FXML
	private Label timeLabel;

	@FXML
	private VBox timer;
	
	// FXML BUTTON FOR RESET AND CONFIGURE THE CODE TO RESET TO 00:00:00
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
					//CALCULATE THE SEC MIN AND HOURS TO PLAY THE TIMER
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
	//START THE BUTTON FOR THE TIMER
	@FXML
	void start(ActionEvent event) {
		play = true;
	}
	//STOP BUTTON TO PAUSE THE TIMER
	@FXML
	void stop(ActionEvent event) {
		play = false;
	}
}
