package Controller;

import java.io.File;
import java.util.ArrayList;

import View.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ChoseCustomListController extends BaseController{

	@FXML private ComboBox<String> customListOptions;
	@FXML private Button playCustomTestButton;
	@FXML private Label noListsText;
	private ObservableList<String> equationLists;
	private Stage root;
	@FXML private Button backButton;

	public ChoseCustomListController(Stage root) {
		this.root = root;
	}

	public void initialize() {
		noListsText.setVisible(false);
		populateComboBox();
	}

	public void playCustomTest(ActionEvent e) {
		if (!(customListOptions.getValue() == null)) {
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
		if(equationListNames.isEmpty()) {
			customListOptions.setDisable(true);
			noListsText.setVisible(true);
		}
		else {
			equationLists = FXCollections.observableArrayList(equationListNames);
			customListOptions.getItems().addAll(equationLists);
		}
	}

	public void backButtonEvent(ActionEvent e) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}


}
