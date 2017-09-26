package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class is the controller for the main class and handles button events
 *
 */
public class MainController{

	@FXML private LevelController levelController;
	@FXML public Button hardButton = new Button();
	@FXML public Label hardText = new Label();
	@FXML private Button easyButton;

	private Difficulty _difficulty;
	private boolean unlocked = false;

	/**
	 * 
	 */
	public void initialize() {
		hardText.setVisible(false);

		try {
			//checks the current highscore
			String highScoreString = Files.readAllLines(Paths.get(".results.txt")).get(1);
			int highScore = Integer.parseInt(highScoreString);

			//hard level will only be accessible when user has got 8 questions in a round
			if (highScore >= 8) {
				unlocked = true;
			} 

		} catch(IOException e) {

		}

	}

	/**
	 * This method directs the user to the easy test screen.  A custom controller is made with
	 * the difficulty parameter and attached to the Level scene
	 * @param e ActionEvent when easyButton is clicked
	 */
	public void easyButtonEvent(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		AnchorPane easyScene = null;
		try {
			System.out.println("easy set");
			_difficulty = Difficulty.EASY;
			LevelController controller = new LevelController(_difficulty);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
			loader.setController(controller);
			easyScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(easyScene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageEventBelongsTo.setScene(scene);

	}

	/**
	 * This method directs the user to the hard test screen.  A custom controller is made with
	 * the difficulty parameter and attached to the Level scene
	 * @param e ActionEvent when hardButton is clicked
	 */
	public void hardButtonEvent(ActionEvent e) {
		if (unlocked) {
			// Get the main stage to display the scene in
			Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

			AnchorPane hardScene = null;
			try {
				System.out.println("hard set");
				_difficulty = Difficulty.HARD;
				LevelController controller = new LevelController(_difficulty);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
				loader.setController(controller);
				hardScene = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Scene scene = new Scene(hardScene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stageEventBelongsTo.setScene(scene);
		}
	}

	public void enterStatsView(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		AnchorPane statsScene = null;
		try {
			System.out.println("Enterings stats view");
			StatsController controller = new StatsController();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StatsView.fxml"));
			loader.setController(controller);
			statsScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(statsScene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageEventBelongsTo.setScene(scene);
	}

	public void showHardText() {
		if (!unlocked) { 
			hardText.setVisible(true);
		}
	}

	public void hideHardText() {
		if (!unlocked) {
			hardText.setVisible(false);
		}
	}

}
