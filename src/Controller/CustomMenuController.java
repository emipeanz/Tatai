package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Model.TestType;
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
import javafx.stage.Stage;

public class CustomMenuController {

	@FXML private DialogPane dialogChooseTest;
	@FXML private ComboBox comboBox;
	@FXML private Button PlayCustomTestButton;

	private ObservableList<String> equationLists;

	public void initialize() {
		populateComboBox();
	}

	public void openCreateCustom(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/CustomEquations.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);		
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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);
	}

	public void selectCustomTest() {
		dialogChooseTest.setVisible(true);
	}		

	public void playCustomTest(ActionEvent e) {
		if (!(comboBox.getValue() == null)) {
			// Get the main stage to display the scene in
			Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
			AnchorPane levelScene = null;
			String listName = comboBox.getValue().toString();
			try {
				LevelController controller = new LevelController(listName);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Level.fxml"));
				loader.setController(controller);
				levelScene = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Scene scene = new Scene(levelScene);
			stageEventBelongsTo.setScene(scene);
		}
	}


	public void populateComboBox() {
		ArrayList<String> equationListNames = new ArrayList<String>();

		File dir = new File(".CustomEquations");
		File[] lists = dir.listFiles();

		for (File equationList : lists) {
			equationListNames.add(equationList.getName());
		}
		equationLists = FXCollections.observableArrayList(equationListNames);
		comboBox.getItems().addAll(equationLists);
	}
}
