package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class is the controller for the main class and handles button events
 * @author emipe
 *
 */
public class MainController {
	
	@FXML
	private Button hardButton;
	
	@FXML
	private Button easyButton;
	
	/**
	 * This method directs the user to the easy test screen
	 * @param e ActionEvent when easyButton is clicked
	 */
	public void easyButtonEvent(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		Scene easyScene = null;
		try {
			easyScene = new Scene(FXMLLoader.load(getClass().getResource("Level.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(easyScene);	
	}
	
	/**
	 * This method directs the user to the hard test screen
	 * @param e ActionEvent when hardButton is clicked
	 */
	public void hardButtonEvent(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		Scene easyScene = null;
		try {
			easyScene = new Scene(FXMLLoader.load(getClass().getResource("Level.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(easyScene);
	}

}
