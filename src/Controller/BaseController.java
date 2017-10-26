package Controller;

import java.io.IOException;

import View.Loader;
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
	public void returnPreviousScene(ActionEvent event, String fxmlLocation) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();

		Scene scene = new Loader(fxmlLocation, null).load();
		
		stageEventBelongsTo.setScene(scene);
	}
	
	/**
	 * Method to return to the main menu from whatever scene the user is currently at
	 * @param e
	 */
	public void returnMainMenu(ActionEvent e) {
		this.returnPreviousScene(e, "MainMenu.fxml");
	}

}
