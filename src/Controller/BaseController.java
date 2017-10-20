package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This abstract class is used for all controllers. It comes with the base method of returning to
 * main menu - should any scene require a button returning to the main menu then this one can be 
 * used. It also classifies all controllers of type BaseController to the load class in the View 
 * package can take in any BaseController and attach it to any FXML file
 * @author se206
 *
 */
public abstract class BaseController {
	

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
		mainMenuScene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stageEventBelongsTo.setScene(mainMenuScene);
	}

}
