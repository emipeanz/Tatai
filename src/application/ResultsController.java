package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ResultsController {

	@FXML
	//will return user to the main menu
	private Button returnButton;
	@FXML
	//will take user to the beginning of a new test
	private Button tryAgainButton;

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
	 * Takes the user back to the start of a new round. Need to check that a new
	 * LevelController is instantiated so that there is a new Test/set of results.
	 * @param event
	 */
	public void restartTest(ActionEvent event) {
		System.out.println("Event triggering that user wishes to do another test");
		//functionality should be added that returns the user to the level they want
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("Level.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);		
	}




}
