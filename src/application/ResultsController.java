package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ResultsController {

	//takes user back to main menu
	@FXML private Button returnButton;
	//takes user back to the start of a new test
	@FXML private Button tryAgainButton;
	//displays the user's result and a congrats message
	@FXML private Label resultLabel;
	//Lists the results from the test
	@FXML private ListView resultsList;

	private Test _test;

	/**
	 * Populates the list view with the results and sets the message
	 * to be displayed with the result.
	 */
	@FXML 
	private void initialize() {
		String listItem;
		for (Result result : _test.getTestResults()) {
			listItem = result._numberInt + "\t" + result._numberWord;
			System.out.println(listItem);
			System.out.println(listItem);
			//resultsList.getItems().add(arg0)
		}

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
