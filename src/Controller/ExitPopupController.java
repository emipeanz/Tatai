package Controller;

/**
 * This class handles everything to do with an exit popup. It is generic and can be used to chack if the 
 * user want to exit, knowing all their progress in some scene will not be saved
 */
import java.io.IOException;

import View.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExitPopupController extends BaseController {
	
	@FXML private Button stayButton;
	@FXML private Button exitButton;
	@FXML private Stage rootScene;
	
	private String FXMLMainSceneLocation;
	
	/**
	 * Creates a new exitPopupController with the stage it is being popped up over and a string
	 * name as the location.
	 * @param root Place where popup is meant to go over
	 * @param location Name of scene that the popup is meant to exit to, should the user decide to exit
	 */
	public ExitPopupController(Stage root, String location) {
		this.rootScene = root;
		this.FXMLMainSceneLocation = location;
	}
	
	/**
	 * Method handles if the user doesnt want to exit and wants to go back to the scene they are currently on.
	 * The stage simply closes itself and returns to the main scene
	 * @param e ActionEvent when the user clicks the return button
	 */
	public void returnToScene(ActionEvent e) {
		Stage stage = (Stage) stayButton.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Method handles returning to a specified scene once the user decides they want to exit where they are currently.
	 * @param e ActionEvent when the used choses to quit
	 */
	public void returnToMainMenu(ActionEvent e) {
		Stage stage = (Stage) stayButton.getScene().getWindow();
		stage.close();
		
		Scene scene = new Loader(FXMLMainSceneLocation, null).load();
		rootScene.setScene(scene);
	}

}
