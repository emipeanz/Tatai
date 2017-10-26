package Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import View.*;
import Model.Operator;
import Model.TestType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class handles everything to do with choosing the difficulty for a test.
 * Directs user to respective level or to custom screen should they want to 
 * create their own lists
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public class DifficultyController extends BaseController {

	@FXML private Button easyButton;
	@FXML private Button hardButton;
	@FXML private Button mediumButton;
	@FXML private Button customButton;
	@FXML private Button plusButton;
	@FXML private Button minusButton;
	@FXML private Button timesButton;
	@FXML private Button divideButton;
	@FXML private Label hardText;
	@FXML private Label mediumText;
	@FXML private Label hardLockSymbol = new Label();
	@FXML private Label medLockSymbol = new Label();
	@FXML private DialogPane customDialog;

	private TestType testType;
	private Operator operator;
	private boolean hardUnlocked = false;
	private boolean medUnlocked = false;

	/**
	 * Takes note of current high score, and sets boolean "unlocked" to true
	 * if score is above 8. Hard level will only be accessible when unlocked has 
	 * been set to true.
	 */
	public void initialize() {
		hardText.setVisible(false);
		mediumText.setVisible(false);

		try {
			//checks the current highscore
			String highScoreEasyString = Files.readAllLines(Paths.get(".easyResults.txt")).get(1);
			int highScoreEasy = Integer.parseInt(highScoreEasyString);

			//hard level will only be accessible when user has got 8 questions in a round
			if (highScoreEasy >= 8) {
				medUnlocked = true;
				medLockSymbol.setText("");
				System.out.println("Medium unlocked");
			}

			//checks the current highscore
			String highScoreMedString = Files.readAllLines(Paths.get(".mediumResults.txt")).get(1);
			int highScoreMed = Integer.parseInt(highScoreMedString);

			//hard level will only be accessible when user has got 8 questions in a round
			if (highScoreMed >= 8) {
				hardUnlocked = true;
				hardLockSymbol.setText("");
				System.out.println("Hard unlocked");
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
		if ((e.getSource().equals(hardButton) && hardUnlocked) ||
				(e.getSource().equals(mediumButton) && medUnlocked) || 
				(e.getSource().equals(easyButton))) {

			// Get the main stage to display the scene in
			Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
			AnchorPane levelScene = null;

			if (e.getSource().equals(easyButton)) {
				testType = TestType.EASY;
			} else if (e.getSource().equals(hardButton)) {
				testType = TestType.HARD;
			} else if (e.getSource().equals(mediumButton)) {
				testType = TestType.MEDIUM;
			}

			Scene scene = new Loader("Level.fxml", new LevelController(testType, true)).load();
			stageEventBelongsTo.setScene(scene);
		}

	}

	/**
	 * Method only used if scene is choosing which operator to be tested on, and creates
	 * a new level and level controller with that respective operator
	 * @param e
	 */
	public void enterPracticeTest(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		AnchorPane levelScene = null;
		
		if (e.getSource().equals(minusButton)) {
			System.out.println("minus");
			operator = Operator.SUBTRACT;
		} else if (e.getSource().equals(timesButton)) {
			System.out.println("times");
			operator = Operator.MULTIPLY;
		} else if (e.getSource().equals(divideButton)) {
			operator = Operator.DIVIDE;
		} else if (e.getSource().equals(plusButton)) {
			operator = Operator.ADD;
		}

		Scene scene = new Loader("Level.fxml", new LevelController(operator, false)).load();
		stageEventBelongsTo.setScene(scene);
	}

	/**
	 * Directs user to the custom options screen if they want to make their own
	 * custom equation lists.
	 * @param e
	 */
	public void customButtonEvent(ActionEvent e) {
		System.out.println("Enter Custom view");
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		Scene scene = new Loader("CustomMenu.fxml", null).load();
		stageEventBelongsTo.setScene(scene);
	}


	/**
	 * Displays the text telling user that they need 8+ in a round to progress to hard level.
	 */
	public void showHardText() {
		if (!hardUnlocked) {
			hardText.setVisible(true);
		}
	}

	/**
	 * Displays the text telling user that they need 8+ in a round to progress to hard level.
	 */
	public void showMedText() {
		if (!medUnlocked) {
			mediumText.setVisible(true);
		}
	}

	/**
	 * Hides text telling user that they need 8+ in a round to progress to hard level.
	 */
	public void hideText() {
		hardText.setVisible(false);

		mediumText.setVisible(false);

	}



}
