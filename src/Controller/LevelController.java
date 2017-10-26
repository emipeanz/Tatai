package Controller;

import Model.*;
import View.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * This class is the controller for a level ie. a test environment. The controller handles recording, playing 
 * recording back to the user, checking if their pronunciation was correct and then moving on to the next
 * question in the test.
 * @author Maddie Beagley and Emilie Pearce
 *
 */

public class LevelController extends BaseController {

	@FXML private Label numberToTest;
	@FXML private Button backButton;
	@FXML private Button recordButton;
	@FXML private Button checkButton;
	@FXML private Button listenButton;
	@FXML private Label firstChance = new Label();
	@FXML private Label secondChance = new Label();
	@FXML private Label feedbackMessage;
	@FXML private ProgressBar recordingProgress;
	@FXML private Circle circle1, circle2, circle3, circle4, 
	circle5, circle6, circle7, circle8, circle9, circle10;
	@FXML private Button skipButton;

	private Test _test;
	private TestType _testType;
	private Round _currentRound;
	private int questionNumber = 1;
	private Color RED = Color.web("ef473a");
	private Color GREEN = Color.web("56ab2f");
	private Color ORANGE = Color.web("ff9e0c");
	private String BLUEPROGRESSBAR = "-fx-accent: #0fb7ff;";
	private String ORANGEPROGRESSBAR = "-fx-accent: orange;";
	private List<Circle> progressCircles;
	private boolean goToResultsOnceFinished;
	private String[] tryAgainMessages = {"Oops, try again!", "Not quite, have another go!", "Keep trying!"};
	private String[] wrongMessages = {"Bad luck, the answer was: ", "Better luck next time, the answer was: "};
	private String[] rightMessages = {"Ka Pai!", "Kei reira!", "Kia kaha!"};

	/**
	 * Method is custom constructor for LevelController so parameters can be passed into it.
	 * the difficulty is set and a new test is made
	 * @param diff Difficulty of the test user wants to run (enum)
	 */
	public LevelController(TestType testType, boolean b) {
		_testType = testType;
		goToResultsOnceFinished = b;
		_test = new Test(testType);
		makeRecordingDir();
		_currentRound = _test.getTestRound(questionNumber - 1);
	}

	/**
	 * Method is custom constructor for LevelController so parameters can be passed into it.
	 * the difficulty is set and a new test is made
	 * @param diff Difficulty of the test user wants to run (enum)
	 */
	public LevelController(Operator operator, boolean b) {
		_testType = TestType.PRACTICE;
		goToResultsOnceFinished = b;
		_test = new Test(operator);
		makeRecordingDir();
		_currentRound = _test.getTestRound(questionNumber - 1);
	}

	/**
	 * Used only when a custom list is being used
	 * @param customListName
	 */
	public LevelController(String customListName, boolean b) {
		_testType = TestType.CUSTOM;
		_test = new Test(customListName);
		goToResultsOnceFinished = b;
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
		skipButton.setVisible(false);
		checkButton.setDisable(true);
		recordButton.setDisable(false);
		listenButton.setDisable(true);
		feedbackMessage.setVisible(false);
		recordingProgress.setVisible(false);
		numberToTest.setText(_currentRound.getQuestion().getDisplayString());

		progressCircles = new ArrayList<Circle>(Arrays.asList(circle1, circle2,
				circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10));

		if (_testType.equals(TestType.PRACTICE)){
			showFeedbackMessage("User can finish practicing at any time by clicking the arrow in the top left corner", 
					"-fx-background-color: linear-gradient(to right, #4AC29A, #BDFFF3);", 34, 6);
			for (Circle circle : progressCircles) {
				circle.setVisible(false);
			}
		}
	}

	/**
	 * Uses a bash command to take a new recording. This functionality will be run in a 
	 * backgroud thread. Buttons (except return to main menu) will be disabled during the
	 * recording process and reenabled after. A new media player storing the current 
	 * recording will be instantiated once this recording has been taken.
	 * @param e
	 */
	public void takeRecording(ActionEvent event) {
		setDisableButtons(true, true, true, true);
		recordingProgressBar(BLUEPROGRESSBAR);

		Task task = new Task() {
			@Override
			protected Object call() throws Exception {
				_currentRound.takeRecording();
				return null;
			}
		};
		task.setOnSucceeded(e -> {
			setDisableButtons(false, false, false, false);
		});
		new Thread(task).start();
	}

	/**
	 * Uses the media player to play the current recording as long as that player has been
	 * correctly set. Nothing will play if the media player is set to null (or has not been set).
	 */
	public void playRecording() {	
		recordingProgressBar(ORANGEPROGRESSBAR);
		setDisableButtons(true, true, true, true);
		//plays media
		_currentRound.getRecording().newMediaPlayer(recordButton, checkButton, listenButton, skipButton); 
		_currentRound.getRecording().getMediaPlayer().play();
		//invokes a runnable that resets the mediaplayer and updates buttons
		_currentRound.getRecording().getMediaPlayer().onEndOfMediaProperty();
	}

	public void setDisableButtons(boolean listenDisable, boolean checkDisable, boolean recordDisable, boolean skipDisable) {
		listenButton.setDisable(listenDisable);
		checkButton.setDisable(checkDisable);
		recordButton.setDisable(recordDisable);
		skipButton.setDisable(skipDisable);
	}

	/**
	 * Updates the state of the progress bar. Tracks how many rounds of the
	 * level have been made. This feature is disabled in a practice test as
	 * there is not a set limit of 10 questions
	 */
	private void updateProgressBar(Color color) {
		if (!_testType.equals(TestType.PRACTICE)) {
			Circle circle =	progressCircles.get(questionNumber - 1);
			circle.setFill(color);
			circle.setStroke(color);
		}
	}

	/**
	 * Called only when the user is advancing to another question
	 */
	public void nextQuestion(ActionEvent event) {
		questionNumber++;
		skipButton.setVisible(false);

		if (!_testType.equals(TestType.PRACTICE)) {
			if((questionNumber == 11) && (goToResultsOnceFinished)) {
				showResults(event);
				return;
			}
			if((questionNumber == 11) && (!goToResultsOnceFinished)) {
				clearAndStartAgain(event);
				return;
			}
			if(questionNumber - 1 > 10) {
				throw new RuntimeException("Too many tests have been logged");
			}
			_currentRound = _test.getTestRound(questionNumber - 1);
		} else {
			_test.addRound();
			_currentRound = _test.getTestRound(questionNumber -1);
		}
		numberToTest.setText(_currentRound.getQuestion().getDisplayString());
		
		if ((_test.getTestQuestions().size() % 6 == 0) && _testType.equals(TestType.PRACTICE)) {
			String displayText = "You have practiced " + _test.getTestQuestions().size() + " questions! Click back arrow to leave at any time.";
			showFeedbackMessage(displayText, "-fx-background-color: linear-gradient(to right, #4AC29A, #BDFFF3);", 34, 4);
		}
	}

	private void clearAndStartAgain(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		Scene scene = new Loader("Level.fxml", new LevelController(TestType.EASY, false)).load();
		stageEventBelongsTo.setScene(scene);
	}

	/**
	 * Method takes user to a screen which displays their results, invoked
	 * when 10 rounds of the test have been completed or if user quits prematurely.
	 * @param event
	 */
	public void showResults(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Loader("Results.fxml", new ResultsController(_test, _testType)).load();
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
		
		Stage stage = new Loader("ExitPopup.fxml",new ExitPopupController(stageEventBelongsTo, "/View/MainMenu.fxml")).loadPopup();
		stage.initOwner(eventButton.getScene().getWindow());
		stage.initStyle(StageStyle.UNDECORATED);
		stage.showAndWait();
	}

	/**
	 * Method checks if the recording the user wants tested if the correct pronunciation
	 * for the current number and displays the respective instructions and feedback.
	 * @param e
	 */
	public void checkRecording(ActionEvent e) {
		setDisableButtons(true, true, true, true);
		String numberString = _currentRound.getQuestion().getAnswerString();
		boolean correct = _currentRound.getRecording().checkRecordingForWord(numberString);

		if(correct) {		
			_currentRound.setPass(true);
			
			showFeedbackMessage(selectMessage(rightMessages), "-fx-background-color: linear-gradient(to right, #56ab2f, #a8e063);", 64, 3);
			updateProgressBar(GREEN);
			
			PauseTransition delay = new PauseTransition(Duration.seconds(3));
			delay.setOnFinished( event -> this.nextQuestion(e) );
			delay.play();
		} else {
			_currentRound.decreaseChances();
			if(_currentRound.getChances() == 0) { // If they have no more chances left
				_currentRound.setPass(false);
				
				updateProgressBar(RED);
				String warningMessage = selectMessage(wrongMessages) + + _currentRound.getQuestion().getAnswerInt();
				showFeedbackMessage(warningMessage, "-fx-background-color: linear-gradient(to right, #cb2d3e, #ef473a);", 46, 3);
				
				PauseTransition delay = new PauseTransition(Duration.seconds(3));
				delay.setOnFinished( event -> this.nextQuestion(e) );
				delay.play();
			
			} else { // If they have one more chance left
				int messageDisplayTime = 3;
				showFeedbackMessage(selectMessage(tryAgainMessages), "-fx-background-color: linear-gradient(to right, #ff8008, #ffc837);", 54, messageDisplayTime);
				
				skipButton.setDisable(false);
				
				PauseTransition delay = new PauseTransition(Duration.seconds(messageDisplayTime));
				delay.setOnFinished( event -> skipButton.setVisible(true));
				delay.play();
			}
		}
	}
	
	/**
	 * Displays a message input on screen with colour defined by input css information, with a defined font
	 * size and time for message to be displayed.
	 * @param message: message to be displayed onscreen
	 * @param cssInfo: css information for colouring, ect of enclosing message box
	 * @param fontSize: size of font of displayed message
	 * @param displayTime: time to display the message on screen
	 */
	private void showFeedbackMessage(String message, String cssInfo, int fontSize, int displayTime) {
		feedbackMessage.setText(message);
		feedbackMessage.setFont(new Font("System", fontSize));
		feedbackMessage.setStyle(cssInfo);
		
		feedbackMessage.setVisible(true);
		setDisableButtons(true, true, true, true);
		
		PauseTransition delay = new PauseTransition(Duration.seconds(displayTime));
		
		delay.setOnFinished( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				feedbackMessage.setVisible(false);
				setDisableButtons(true, true, false, false);
				
			}
		});
		delay.play();
	}

	/**
	 * Selects a string from a randomly generated index position from 
	 * an input array of strings. Used for showing user feedback on the popup dialog.

	 * @param messages
	 * @return
	 */
	private String selectMessage(String[] messages) {
		int index = (int)(Math.random() * messages.length);
		return messages[index];
	}

	/**
	 * Increments a progress bar at the top of the game scene that fills with the 
	 * input colour gradually over duration of recording. Used when playing and
	 * taking a recording.
	 * @param progressStyle: CSS string representing colour of bar
	 */
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


	/**
	 * Method handled a skip button event; which includes setting the skip option in the 
	 * round to true and progressing to next question
	 * @param e
	 */
	public void skipQuestion(ActionEvent e) {
		skipButton.setVisible(false);
		_currentRound.setPass(false);
		_currentRound.setSkip();
		updateProgressBar(ORANGE);
		showFeedbackMessage("Question skipped!", "-fx-background-color: linear-gradient(to right, #ece9e6, #ffffff);", 50, 3);
		
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished( event -> this.nextQuestion(e) );
		delay.play();
	}
}
