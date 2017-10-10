package Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Model.Difficulty;
import Model.TestType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DifficultyController {

	@FXML private Button easyButton;
	@FXML private Button hardButton;
	@FXML private Button customButton;
	@FXML private Label hardText;
	@FXML private Label hardLockSymbol = new Label();

	private TestType testType;
	private Difficulty difficulty;
	private boolean unlocked = false;

	public DifficultyController(TestType type) {
		testType = type;
	}

	/**
	 * Takes note of current high score, and sets boolean "unlocked" to true
	 * if score is above 8. Hard level will only be accessible when unlocked has 
	 * been set to true.
	 */
	public void initialize() {
		if (testType == testType.PRACTICE) {
			customButton.setVisible(false);
		}

		hardText.setVisible(false);

		try {
			//checks the current highscore
			String highScoreString = Files.readAllLines(Paths.get(".results.txt")).get(1);
			int highScore = Integer.parseInt(highScoreString);

			//hard level will only be accessible when user has got 8 questions in a round
			if (highScore >= 8) {
				unlocked = true;
				hardLockSymbol.setText("\uf09c");
			} 
		} catch(IOException e) {
		}
	}


	/**
	 * This method directs the user to the easy test screen.  A custom controller is made with
	 * the difficulty parameter and attached to the Level scene
	 * @param e ActionEvent when easyButton is clicked
	 */
	public void enterTest(ActionEvent e) {
		if ((e.getSource().equals(hardButton) && unlocked) || (e.getSource().equals(easyButton))) {

			// Get the main stage to display the scene in
			Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
			AnchorPane levelScene = null;

			if (e.getSource().equals(easyButton)) {
				difficulty = Difficulty.EASY;
			} else if (e.getSource().equals(hardButton)) {
				difficulty = Difficulty.HARD;
			}

			System.out.println(testType);
			System.out.println(difficulty);

			try {
				System.out.println("easy set");
				LevelController controller = new LevelController(difficulty, testType);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Level.fxml"));
				loader.setController(controller);
				levelScene = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Scene scene = new Scene(levelScene);
			stageEventBelongsTo.setScene(scene);
		}

	}


	public void enterCustom(ActionEvent e) {
		System.out.println("Enter Custom view");
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/CustomEquations.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);		
	}

	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void returnMainMenu(ActionEvent event) {
		System.out.println("Event triggering return to main menu");

		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Scene mainMenuScene = null;

		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);
	}

	/**
	 * Displays the text telling user that they need 8+ in a round to progress to hard level.
	 */
	public void showHardText() {
		if (!unlocked) { 
			hardText.setVisible(true);
		}
	}

	/**
	 * Hides text telling user that they need 8+ in a round to progress to hard level.
	 */
	public void hideHardText() {
		if (!unlocked) {
			hardText.setVisible(false);
		}
	}



}
