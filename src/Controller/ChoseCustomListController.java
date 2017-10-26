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

/**
 * Class handles everything to do with choosing a custom list to play via the popup
 * window.
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public class ChoseCustomListController extends BaseController{

	@FXML private ComboBox<String> customListOptions;
	@FXML private Button playCustomTestButton;
	@FXML private Label noListsText;
	private ObservableList<String> equationLists;
	private Stage root;
	@FXML private Button backButton;

	/**
	 * Sets up popup controller with a root scene to attach to
	 * @param root
	 */
	public ChoseCustomListController(Stage root) {
		this.root = root;
	}

	/**
	 * Populates combo box on start up
	 */
	public void initialize() {
		noListsText.setVisible(false);
		populateComboBox();
	}

	/**
	 * Once user has chosen a list to play from combo box, method directs user to
	 * that test
	 * @param e
	 */
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

	/**
	 * Populates combo bix with custom list names by reading from their hidden directory.
	 * If there are no custom lists then the combo box and play buttons are disabled
	 */
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
			playCustomTestButton.setDisable(true);
		}
		else {
			equationLists = FXCollections.observableArrayList(equationListNames);
			customListOptions.getItems().addAll(equationLists);
		}
	}

	/**
	 * Return user back to the custom menu by closing the popup
	 * @param e
	 */
	public void backButtonEvent(ActionEvent e) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}


}
