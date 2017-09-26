package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatsController {

	double _averageMark;
	int _numberOfTests;
	int _highScore;

	@FXML public Label averageScore;
	@FXML public Label highScore;
	@FXML public Label testsTaken;

	/**
	 * Sets the values of the labels that display the user's results with their 
	 * average high score, highest score and number of tests they have taken.
	 */
	public void initialize() {
		try {
			//finds previous average score and converts it to an integer
			String averageScoreString = Files.readAllLines(Paths.get(".results.txt")).get(0);
			averageScore.setText(averageScoreString);

			//finds previous high score and converts it to an integer
			String highScoreString = Files.readAllLines(Paths.get(".results.txt")).get(1);
			highScore.setText(highScoreString);

			//finds previous number of tests run and converts to an integer
			String numOfTestsString = Files.readAllLines(Paths.get(".results.txt")).get(2);
			testsTaken.setText(numOfTestsString);
			
		} catch (Exception e) {

		}
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




}
