package Controller;

import java.io.File;
/**
 * This class is the main controller for the main menu. After the first scene has shown the titles and 
 * transitioned, this controller handles going to a hard, easy or stats page.
 * @author Maddie Beagley and Emilie Pearce
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import Model.*;
import View.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class is the controller for the main class and handles button events
 *
 */
public class MainController extends BaseController {

	@FXML private Button practiceButton;
	@FXML private Button playButton;

	private TestType testType;

	//makes sure files exist for results to be stored in for easy hard and medium
	public void initialize() {
		String[] difficulties = {"easy", "medium", "hard"};		
		File tmpfile;
		String filename;

		//cycles through to ensure that there is a file ready to store results
		for (String difficulty : difficulties) {
			filename = "." + difficulty + "Results.txt";
			tmpfile = new File(filename);
			//checks if file exists
			if (!tmpfile.exists()) {
				try {
					//if file doesn't exist write all zeroes as initial results
					Files.write(Paths.get(filename), Arrays.asList("0", "0", "0", "0"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		File tmpdir = new File(".CustomEquations");
		tmpdir.mkdir();

		if (!tmpdir.exists()) {
			File customEquations = new File(".CustomEquations");
			customEquations.mkdir();
		}
	}

	/**
	 * This method directs the user to the easy test screen.  A custom controller is made with
	 * the difficulty parameter and attached to the Level scene
	 * @param e ActionEvent when easyButton is clicked
	 */
	public void enterDifficultyOption(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("Difficulty.fxml",  new DifficultyController()).load();
		stageEventBelongsTo.setScene(scene);

	}

	public void enterPracticeMode(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("Level.fxml", new LevelController(TestType.EASY, false)).load();
		stageEventBelongsTo.setScene(scene);
	}

	public void enterStatsView(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("StatsView.fxml", new StatsController()).load();
		stageEventBelongsTo.setScene(scene);
	}

}
