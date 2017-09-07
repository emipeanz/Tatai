package application;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class LevelController {

	@FXML
	private Label numberToTest;
	@FXML
	private Label numberWord;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label progressLabel;

	private int progress = 0;
	//will store all the data associated with the current level
	private Result _currentLevelResult;
	//will store all data associated with entire test
	private Test _test;


	public LevelController() {
		//need to figure out how NOT to hard code this...
		_test = new Test(Difficulty.HARD);
	}

	/**
	 * For now just having a play around - this method is called when the make random number
	 * button is clicked and will show the number and the word of that number in maori.
	 * Learning how to use events.
	 * @param event
	 */
	public void updateLabels(ActionEvent event) {
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

	public void playRecording() {
		/*
		//if recording has been set for a level...
		if (_currentLevelResult._recording == null) {
			System.out.println("recording has not been properly initialised");
			//play recording (cbf using bash as do not want to work on VM)
		} else {
			System.out.println("In method for hearing a recording (recording not null)");
		}
		*/
		
		System.out.println("In method for playing a recording");
	}

	/**
	 * Updates the state of the progress bar. Tracks how many rounds of the
	 * level have been made.
	 */
	private void updateProgressBar() {
		progress = _test.getNumberofRound();
		progressLabel.setText("Round " + progress + "/10");
		progressBar.setProgress((double) progress / 10);
	}
	/**
	 * Called only when the user is advancing to the next level, updates
	 * progress bar.
	 */
	public void nextLevel(ActionEvent event) {
		//stores result of previous test in test model
		_test.addTestResult(_currentLevelResult);
		//instantiates a new result for the next level of the test
		_currentLevelResult = new Result(_test._difficulty);
		
		if (_test.getNumberofRound() == 10) {
			showResults(event);
		} else {
			updateProgressBar();
		}
	}
	
	/**
	 * Method takes user to a screen which displays their results, invoked
	 * when 10 rounds of the test have been completed or if user quits prematurely.
	 * @param event
	 */
	public void showResults(ActionEvent event) {
		System.out.println("in method that sets the scene to results");
		
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		Scene easyScene = null;
		try {
			easyScene = new Scene(FXMLLoader.load(getClass().getResource("Results.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		stageEventBelongsTo.setScene(easyScene);
	}

	public void returnMainMenu(ActionEvent event) {
		System.out.println("Event triggering return to main menu");
		//main scene should be reloaded.

		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();

		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);
	}
}
