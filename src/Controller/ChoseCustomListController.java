package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChoseCustomListController extends BaseController{

	@FXML private ComboBox customListOptions;
	@FXML private Button playCustomTestButton;
	private ObservableList<String> equationLists;
	private Stage root;

	public ChoseCustomListController(Stage root) {
		this.root = root;
	}

	public void initialize() {
		populateComboBox();
	}

	public void playCustomTest(ActionEvent e) {
		if (!(customListOptions.getValue() == null)) {
			// Get the main stage to display the scene in
			AnchorPane levelScene = null;
			String listName = customListOptions.getValue().toString();

			LevelController controller = new LevelController(listName, true);

			Scene scene = new Loader("Level.fxml", controller).load();	
			Stage stage = (Stage) playCustomTestButton.getScene().getWindow();
			stage.close();
			root.setScene(scene);
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
		customListOptions.getItems().addAll(equationLists);
	}


}
