package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class LevelController {
	
	@FXML
	private Label numberToTest;
	@FXML
	private Label numberWord;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label progressLabel;
	
	private double progress;
	//will store all the data associated with the current level
	private Result _currentLevelResult;
	//will store all data associated with entire test
	private Test _test;
	
	
	
	/**
	 * For now just having a play around - this method is called when the make random number
	 * button is clicked and will show the number and the word of that number in maori.
	 * Learning how to use events.
	 * @param event
	 */
	public void updateLabels(ActionEvent event) {
		//need to figure out a way to instantiate this in a constructor cause do not 
		//want this reinstantiated each time button is pressed.
		_test = new Test(Difficulty.HARD);
		
		_currentLevelResult = new Result(_test._difficulty);
	
		//sets labels that show a number and the maori word corresponding to it
		numberToTest.setText(Integer.toString(_currentLevelResult._numberInt));
		numberWord.setText(_currentLevelResult._numberWord);
	}
	
	public void takeRecording() {
		//part 2 of cbf using bash as do not want to work on VM
		System.out.println("In method for taking a recording");
		
		//to be uncommented when there is a valid file for a recording.
		
		/*
		File recording = new File("recording");
		_currentLevelResult.setRecording(recording);
		*/
	}
	
	public void hearRecording() {
		//if recording has been set for a level...
		if (_currentLevelResult._recording != null) {
			//play recording (cbf using bash as do not want to work on VM)
			System.out.println("In method for hearing a recording (recording not null)");
		} else {
			System.out.println("recording has not been properly initialised");
		}
	}

	/**
	 * Called only when the user is advancing to the next level, updates
	 * progress bar.
	 */
	public void nextLevel() {
		_currentLevelResult = new Result(_test._difficulty);
		progress += 0.1;
		progressLabel.setText("Round " + Math.round(progress * 10) + "/10");
		progressBar.setProgress(progress);
	}
}
