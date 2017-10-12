package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExitPopupController {
	
	@FXML private Button stayButton;
	@FXML private Button exitButton;
	@FXML private Stage rootScene;
	
	private String FXMLMainSceneLocation;
	
	public ExitPopupController(Stage root, String location) {
		this.rootScene = root;
		this.FXMLMainSceneLocation = location;
	}
	
	public void returnToScene(ActionEvent e) {
		Stage stage = (Stage) stayButton.getScene().getWindow();
		stage.close();
	}
	
	public void returnToMainMenu(ActionEvent e) {
		Stage stage = (Stage) stayButton.getScene().getWindow();
		stage.close();
		
		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource(FXMLMainSceneLocation)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		rootScene.setScene(mainMenuScene);
	}

}
