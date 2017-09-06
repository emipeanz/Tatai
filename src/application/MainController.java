package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	private Button hardButton;
	
	@FXML
	private Button easyButton;
	
	public void easyButtonEvent(ActionEvent e) {
		System.out.print("Easy Button Pressed");
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		Scene easyScene = null;
		try {
			easyScene = new Scene(FXMLLoader.load(getClass().getResource("Level.fxml")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		stageEventBelongsTo.setScene(easyScene);
		
	}
	
	public void hardButtonEvent(ActionEvent e) {
		System.out.print("Hard Button Pressed");
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		
		Scene easyScene = null;
		try {
			easyScene = new Scene(FXMLLoader.load(getClass().getResource("Level.fxml")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		stageEventBelongsTo.setScene(easyScene);
		
	}

}
