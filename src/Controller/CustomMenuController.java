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

/**
 * This class handles everything to do with custom equations menu. That involves directing
 * user to the scene to make a list, or the popup to choose a list to play
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public class CustomMenuController extends BaseController{

	@FXML private DialogPane dialogChooseTest;
	@FXML private ComboBox comboBox;
	@FXML private Button PlayCustomTestButton;

	/**
	 * Method directs user to scene where they can create their own custom 
	 * equation lists.
	 * @param e
	 */
	public void openCreateCustom(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("CustomEquations.fxml", null).load();
		stageEventBelongsTo.setScene(scene);		
	}

	/**
	 * Goes back to the difficulty page
	 * @param e
	 */
	public void returnDifficulty(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("Difficulty.fxml",  new DifficultyController()).load();
		stageEventBelongsTo.setScene(scene);
	}

	/**
	 * Method opens popup to chose a custom list to play
	 * @param event
	 */
	public void selectCustomTest(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Button eventButton = (Button)event.getSource();

		Stage stage = new Loader("ChoseCustomList.fxml", new ChoseCustomListController(stageEventBelongsTo)).loadPopup();

		stage.initOwner(eventButton.getScene().getWindow());
		stage.showAndWait();


	}		


}
