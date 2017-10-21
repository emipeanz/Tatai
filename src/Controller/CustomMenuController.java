package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Model.TestType;
import View.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomMenuController {

	@FXML private DialogPane dialogChooseTest;
	@FXML private ComboBox comboBox;
	@FXML private Button PlayCustomTestButton;
	
	public void openCreateCustom(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("CustomEquations.fxml", null).load();
		stageEventBelongsTo.setScene(scene);		
	}

	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void returnMainMenu(ActionEvent event) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Scene mainMenuScene = null;

		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml")));
			mainMenuScene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);
	}

	public void selectCustomTest(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Button eventButton = (Button)event.getSource();
		
		try {
			Stage stage = new Stage(); 
			AnchorPane root;
			ChoseCustomListController c = new ChoseCustomListController(stageEventBelongsTo);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ChoseCustomList.fxml"));
			loader.setController(c);
			root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(eventButton.getScene().getWindow());
			stage.showAndWait();
		} catch (IOException e1) {
			System.out.println("exception thrown");
			e1.printStackTrace();
		}
		
	}		

	
}
