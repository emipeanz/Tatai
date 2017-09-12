package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is the controller for a level ie. a test environment.
 * @author Maddie Beagley and Emilie Pearce
 *
 */

public class LevelController {

	
	@FXML
	private Button readyButton;
	
	@FXML
	private Label numberToTest;
	
	@FXML
	private Label numberWord;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Label progressLabel;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button recordButton;
	
	@FXML
	private Button checkButton;
	
	@FXML
	private Button listenButton;
	
	@FXML
	private JFXDialog dialog;
	
	private int progress = 0;
	//will store all the data associated with the current level
	private Result _currentLevelResult;
	//will store all data associated with entire test
	private Test _test;
	
	private File _recording;
	private String _recordingFilepath = "RecordingDir/recording.wav";
	private Difficulty _difficulty;
	
	private int chances = 2;
	
	/**
	 * Method is custom constructor for LevelController so parameters can be passed into it.
	 * the difficulty is set and a new test is made
	 * @param diff Difficulty of the test user wants to run (enum)
	 */
	public LevelController(Difficulty diff) {
		_difficulty = diff;
		_test = new Test(_difficulty);
		
		//generates a new directory
		File recordingDir = new File("RecordingDir/");
		if(!recordingDir.exists()) {
			recordingDir.mkdir();
		}
	
	}

	/**
	 * For now just having a play around - this method is called when the make random number
	 * button is clicked and will show the number and the word of that number in maori.
	 * Learning how to use events.
	 * @param event
	 */
	public void updateLabels(ActionEvent event) {
		_currentLevelResult = new Result(_test.getdifficulty());	
		//sets labels that show a number and the maori word corresponding to it
		numberToTest.setText(Integer.toString(_currentLevelResult._numberInt));
		numberWord.setText(_currentLevelResult._numberWord);
	}

	public void takeRecording(ActionEvent e) {
		//part 2 of cbf using bash as do not want to work on VM
		System.out.println("In method for taking a recording");
		
		_recordingFilepath = "RecordingDir/foo.wav";
	
		String cmd = "ffmpeg -y -f alsa -i \"default\" -t 6 -acodec pcm_s16le -ar 22050 -ac 1 " + _recordingFilepath;
		System.out.print(cmd);
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);

		//generates a new thread to execute the recording functionality
		Thread record = new Thread(() -> {
			try {
				if (listenButton.isDisabled()) {
					listenButton.setDisable(true);					
				}
				if (checkButton.isDisabled()) {
					checkButton.setDisable(true);					
				}
				recordButton.setDisable(true);
				
				System.out.println("about to run process to take recording");
				pb.start().waitFor();
				//when recording has completed, run the onRecordComplete with the input 
				//being the recording file that has just been generated.
				System.out.println("recording ready to update");
				_recording = new File(_recordingFilepath);
				
				listenButton.setDisable(false);
				checkButton.setDisable(false);
				recordButton.setDisable(false);

			} catch (InterruptedException ignored) { // if process is prematurely terminated
			} catch (IOException ioEvent) { //if process is incorrect (likely programmer error)
				throw new RuntimeException("Programmer messed up command...");
			}
		});
		record.start();
		
	}

	public void playRecording() {
		//if recording has been set for a level...
		if (_recording == null) {
			System.out.println("recording has not been properly initialised");
			//play recording (cbf using bash as do not want to work on VM)
		} else {
			recordButton.setDisable(true);
			checkButton.setDisable(true);
			System.out.println("In method for hearing a recording (recording not null)");
			/**
			 * add functionality to play recording
			 */
			recordButton.setDisable(false);
			checkButton.setDisable(false);
		}
	}
	
	class Reproductor extends Application {
	   @Override
	   public void start(Stage stage) throws Exception {
	       Media media = new Media("file:///Movies/test.mp3"); //replace /Movies/test.mp3 with your file
	       MediaPlayer player = new MediaPlayer(media); 
	       player.play();
	   }  
	 }

	/**
	 * Updates the state of the progress bar. Tracks how many rounds of the
	 * level have been made.
	 */
	private void updateProgressBar() {
		progress = _test.getNumberofRound();
		System.out.println("update bar progress = " + progress);
		progressLabel.setText("Round " + progress + "/10");
		progressBar.setProgress((double) progress / 10);
	}
	
	/**
	 * Called only when the user is advancing to another question
	 */
	public void nextLevel(ActionEvent event) {
		System.out.println("entered next question");
		//stores result of previous test in test model
		_test.addTestResult(_currentLevelResult);
		//instantiates a new result for the next level of the test
		_currentLevelResult = new Result(_test.getdifficulty());
		System.out.println("next level progress = " + progress);
		progress += 0.1;
		
		if(progress == 10) {
			showResults(event);
		}
		if(progress>10) {
			throw new RuntimeException("Too many tests have been logged");
		}
		
		listenButton.setDisable(true);
		_recording = null;
	}
	
	/**
	 * Method takes user to a screen which displays their results, invoked
	 * when 10 rounds of the test have been completed or if user quits prematurely.
	 * @param event
	 */
	public void showResults(ActionEvent event) {
		System.out.println("in method that sets the scene to results");
		
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();

		AnchorPane resultsScene = null;
		ResultsController controller = null;
		try {
			controller = new ResultsController(_test);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Results.fxml"));
			loader.setController(controller);
			resultsScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(resultsScene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		controller.setUpResultsTable();
		stageEventBelongsTo.setScene(scene);
	}

	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
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
		mainMenuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageEventBelongsTo.setScene(mainMenuScene);
	}
	
	/**
	 * Method take an event on the ready button to start the test.  It disabled the 'ready' button
	 * and enabled all the other control buttons so the user can submit/record/listen to recordings
	 * before the actual test has started
	 * @param event
	 */
	public void readyButtonAction(ActionEvent event){
		readyButton.setDisable(true);
		readyButton.setVisible(false);
		updateLabels(event);
		checkButton.setDisable(true);
		recordButton.setDisable(false);
		listenButton.setDisable(true);
	}
	
	/**
	 * Method checks if the recording the user wants tested if the correct pronunciation
	 * for the current number and displays the respective instructions and feedback.
	 * ****ISSUE WITH PROGRESS BAR
	 * I know there HEAPS of code duplication, but i was trailing this out to see if the idea
	 * worked - if we wanna use it then ill come back to it and fix it
	 * Checking audio part of method needs doing
	 * @param e
	 */
	public void checkRecording(ActionEvent e) {
		System.out.println("Checking recording check button");
		checkRecordingForWord();
		Boolean correct = true;
		
	}
	
	/**
	 * Method checks if the recording that is currently in the recording directory is the
	 * word that is the current test that it is on.  Uses back commands to run the wav file
	 * through HTK and reads from the recout.lmf file to see if all parts of the word have
	 * been picked up in the analysis
	 * @return boolean if the recording is the correct number of now=t
	 */
	private boolean checkRecordingForWord() {
		ArrayList<String> output = new ArrayList<String>();
		System.out.println("Checking recording HTK bash");
		_recordingFilepath = "RecordingDir/foo.wav";
		String cmd = "HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  "
				+ "-w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  "
				+ "user/dictionaryD user/tiedList " + _recordingFilepath;
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			System.out.println("Starting process");
			Process process = processBuilder.start();
			process.waitFor();
			FileReader in = new FileReader("recout.mlf");
			BufferedReader br = new BufferedReader(in);
			String line = null;
			while((line = br.readLine()) != null) {
				if((!(line.contains("#!MLF!#"))) && (!(line.contains("\"*/foo.rec\""))) && (!(line.contains(".")))) {
					System.out.println(line);
					output.add(line);
				}
			}
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		List<String> numberWord = _currentLevelResult.numberInSplitformat();
		for(String s:numberWord) {
			if(!(output.contains(s))) {
				System.out.println("word not there, exiting FALSE");
				return false;
			}
		}
		System.out.println("word there, exiting TRUE");
		return true;
	}
	
}
