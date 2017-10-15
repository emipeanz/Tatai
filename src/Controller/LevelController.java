package Controller;

import java.io.BufferedReader;
import Model.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.nio.file.Paths;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is the controller for a level ie. a test environment. The controller handles recording, playing 
 * recording back to the user, checking if their pronunciation was correct and then moving on to the next
 * question in the test.
 * @author Maddie Beagley and Emilie Pearce
 *
 */

public class LevelController {

	@FXML private Label numberToTest;
	@FXML private Label progressLabel;
	@FXML private Button backButton;
	@FXML private Button recordButton;
	@FXML private Button checkButton;
	@FXML private Button listenButton;
	@FXML private Label firstChance = new Label();
	@FXML private Label secondChance = new Label();
	@FXML private Label feedbackMessage;
	@FXML private AnchorPane helpWindow;
	@FXML private ProgressBar recordingProgress;
	@FXML private Circle circle1, circle2, circle3, circle4, 
	circle5, circle6, circle7, circle8, circle9, circle10;

	private Test _test;
	private TestType _testType;
	private Round _currentRound;
	private int questionNumber = 1;
	private Color red = Color.web("ef473a");
	private Color green = Color.web("56ab2f");
	private String blueProgressBar = "-fx-accent: blue;";
	private String orangeProgressBar = "-fx-accent: orange;";
	private List<Circle> progressCircles;
	private boolean equationPlay;

	/**
	 * Method is custom constructor for LevelController so parameters can be passed into it.
	 * the difficulty is set and a new test is made
	 * @param diff Difficulty of the test user wants to run (enum)
	 */
	public LevelController(TestType testType, boolean b) {
		_testType = testType;
		equationPlay = b;
		_test = new Test(testType);
		makeRecordingDir();
		_currentRound = _test.getTestRound(questionNumber - 1);
	}

	/**
	 * Used only when a custom list is being used
	 * @param customListName
	 */
	public LevelController(String customListName) {
		_testType = TestType.CUSTOM;
		_test = new Test(customListName);
		makeRecordingDir();
		_currentRound = _test.getTestRound(questionNumber - 1);
	}
	
	/**
	 * Generates a directory to store recordings in
	 */
	public void makeRecordingDir() {
		File recordingDir = new File("RecordingDir/");
		if(!recordingDir.exists()) {
			recordingDir.mkdir();
		}
	}


	/**
	 * Makes the various parts of the level scene invisible until they are needed further on
	 * in the code.
	 */
	public void initialize() {
		checkButton.setDisable(true);
		recordButton.setDisable(false);
		listenButton.setDisable(true);
		feedbackMessage.setVisible(false);
		recordingProgress.setVisible(false);
		numberToTest.setText(_currentRound.getQuestion().getDisplayString());

		progressCircles = new ArrayList<Circle>(Arrays.asList(circle1, circle2,
				circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10));
	}

	/**
	 * Uses a bash command to take a new recording. This functionality will be run in a 
	 * backgroud thread. Buttons (except return to main menu) will be disabled during the
	 * recording process and reenabled after. A new media player storing the current 
	 * recording will be instantiated once this recording has been taken.
	 * @param e
	 */
	public void takeRecording(ActionEvent e) {
		recordingProgressBar(blueProgressBar);
		setDisableButtons(true, true, true);

		Thread record = new Thread(() -> {
			_currentRound.takeRecording();
		});

		record.start();
		setDisableButtons(false, false, false);
	}

	/**
	 * Uses the media player to play the current recording as long as that player has been
	 * correctly set. Nothing will play if the media player is set to null (or has not been set).
	 */
	public void playRecording() {	
		recordingProgressBar(orangeProgressBar);

			setDisableButtons(true, true, true);
			//plays media
			_currentRound.getRecording().newMediaPlayer(recordButton, checkButton, listenButton); 
			_currentRound.getRecording().getMediaPlayer().play();
			//invokes a runnable that resets the mediaplayer and updates buttons
			_currentRound.getRecording().getMediaPlayer().onEndOfMediaProperty();

	}
	
	public void setDisableButtons(boolean listenDisable, boolean checkDisable, boolean recordDisable) {
		listenButton.setDisable(listenDisable);
		checkButton.setDisable(checkDisable);
		recordButton.setDisable(recordDisable);
	}

	/**
	 * Updates the state of the progress bar. Tracks how many rounds of the
	 * level have been made.
	 */
	private void updateProgressBar(Color color) {
		Circle circle =	progressCircles.get(questionNumber - 1);
		circle.setFill(color);
		circle.setStroke(color);
	}

	/**
	 * Called only when the user is advancing to another question
	 */
	public void nextQuestion(ActionEvent event) {
		questionNumber++;

		if((questionNumber == 11) && (equationPlay)) {
			System.out.println("Results showing");
			showResults(event);
			return;
		}
		if((questionNumber == 11) && (!equationPlay)) {
			System.out.println("Restarting");
			clearAndStartAgain(event);
			return;
		}
		if(questionNumber - 1 > 10) {
			throw new RuntimeException("Too many tests have been logged");
		}
		_currentRound = _test.getTestRound(questionNumber - 1);
		numberToTest.setText(_currentRound.getQuestion().getDisplayString());
		progressLabel.setText("A tawhio noa " + questionNumber + "/10");
		listenButton.setDisable(true);
	}

	private void clearAndStartAgain(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		AnchorPane statsScene = null;
		try {
			System.out.println("Entering practice mode");
			LevelController controller = new LevelController(TestType.EASY, false);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Level.fxml"));
			loader.setController(controller);
			statsScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(statsScene);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stageEventBelongsTo.setScene(scene);

	}

	/**
	 * Method takes user to a screen which displays their results, invoked
	 * when 10 rounds of the test have been completed or if user quits prematurely.
	 * @param event
	 */
	public void showResults(ActionEvent event) {
		System.out.println("Going to the results page");
		
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		AnchorPane resultsScene = null;
		ResultsController controller = null;
		try {
			controller = new ResultsController(_test, _testType);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Results.fxml"));
			loader.setController(controller);
			resultsScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(resultsScene);
		resultsScene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stageEventBelongsTo.setScene(scene);
		 
	}

	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void backButtonEvent(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Button eventButton = (Button)event.getSource();

		try {
			Stage stage = new Stage(); 
			AnchorPane root;
			ExitPopupController popupController = new ExitPopupController(stageEventBelongsTo, "/View/MainMenu.fxml");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ExitPopup.fxml"));
			loader.setController(popupController);
			root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(eventButton.getScene().getWindow());
			stage.showAndWait();
		} catch (IOException e1) {
			System.out.println("exception thrown");
			e1.printStackTrace();
		}

	}

	/**
	 * Method checks if the recording the user wants tested if the correct pronunciation
	 * for the current number and displays the respective instructions and feedback.
	 * @param e
	 */
	public void checkRecording(ActionEvent e) {
		String numberString = _currentRound.getQuestion().getAnswerString();
		boolean correct = _currentRound.getRecording().checkRecordingForWord(numberString);

		if(correct) {		
			_currentRound.setPass(true);
			feedbackMessage(true);
			updateProgressBar(green);
			PauseTransition delay = new PauseTransition(Duration.seconds(3));
			delay.setOnFinished( event -> this.nextQuestion(e) );
			delay.play();
			//setDisableButtons(true, true, false);
		} else {
			_currentRound.decreaseChances();
			if(_currentRound.getChances() == 0) { // If they have no more chances left
				_currentRound.setPass(false);
				updateProgressBar(red);
				feedbackMessage(false);
				PauseTransition delay = new PauseTransition(Duration.seconds(3));
				delay.setOnFinished( event -> this.nextQuestion(e) );
				delay.play();
				} else { // If they have one more chance left
				feedbackMessage(false);
			}
			//setDisableButtons(true, true, true);

		}

	}

	/**
	 * This method handles showing the user their feedback based on what they pronounced
	 * @param b
	 */
	private void feedbackMessage(boolean b) {
		int chances = _currentRound.getChances();
		if(b) {
			feedbackMessage.setText("I tika koe i te whakautu!");
			feedbackMessage.setStyle("-fx-background-color: linear-gradient(to right, #56ab2f, #a8e063);");
		}
		else if((!b) && (chances == 1)) {
			feedbackMessage.setText("Kati ... tamata ano");
			feedbackMessage.setStyle("-fx-background-color: linear-gradient(to right, #ff8008, #ffc837);");
		}
		else {
			feedbackMessage.setText("Kaore, he he. \nKo te whakautu he " + _currentRound.getQuestion().getAnswerInt());
			feedbackMessage.setStyle("-fx-background-color: linear-gradient(to right, #cb2d3e, #ef473a);");
		}
		feedbackMessage.setVisible(true);
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished( event -> feedbackMessage.setVisible(false) );
		delay.play();
	}

	public void recordingProgressBar(String progressStyle) {
		recordingProgress.setStyle(progressStyle);
		recordingProgress.setVisible(true);

		Task<Void> task = new Task<Void>(){
			@Override
			public Void call(){
				for (int i = 1; i <= 100; i++)    {
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					updateProgress(i , 100);
				}
				recordingProgress.setVisible(false);
				return null;                
			}
		};
		recordingProgress.progressProperty().bind(task.progressProperty());
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	public void showInstructions() {
		helpWindow.setVisible(true);
	}

	public void hideInstructions() {
		helpWindow.setVisible(false);
	}



}
