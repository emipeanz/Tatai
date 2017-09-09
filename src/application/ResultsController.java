package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class ResultsController {

	@FXML
	private Button returnButton;
	//will return user to the main menu
	
	@FXML
	private Button tryAgainButton;
	//will take user to the beginning of a new test
	
	@FXML
	private ListView<String> resultsListView;
	
	// Gonna need some sort of 3D map to map together the number name, number in numeral form and if they got it right or not
	private Test _test;
	
	private Difficulty _difficulty;
    
    private ObservableList<String> _dataList;

	public ResultsController(Test test) {
		_test = test;
		_difficulty = _test.getdifficulty();
	}
	
	/**
	 * Issue with initlization of controller and linking to FXML file, ordering is a problem which results in
	 * null pointer excpetions
	 */
	public void setUpResultsTable() {
		_dataList = FXCollections.observableArrayList(_test.getResultsToString());
		resultsListView.setItems(_dataList);		
		 resultsListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new ListCell<String>(){
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);    //To change body of overridden methods use File | Settings | File Templates.
                        if (s.contains("Right!")) {
                        	setStyle("-fx-background-color: linear-gradient(to right, #56ab2f, #a8e063); ");
                        }
                        else {
                        	setStyle("-fx-background-color : linear-gradient(to right, #cb2d3e, #ef473a);");
                        }
                    }
                };
            }
        });
		
	}

	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void returnMainMenu(ActionEvent event) {
		System.out.println("Event triggering return to main menu");
		//main scene should be reloaded.

		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();

		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		mainMenuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageEventBelongsTo.setScene(mainMenuScene);
	}

	/**
	 * Takes the user back to the start of a new round. Need to check that a new
	 * LevelController is instantiated so that there is a new Test/set of results.
	 * @param event
	 */
	public void restartTest(ActionEvent event) {
		System.out.println("Event triggering that user wishes to do another test");
		// Get the main stage to display the scene in
				Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();

				AnchorPane hardScene = null;
				try {
					LevelController controller = new LevelController(_difficulty);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
					loader.setController(controller);
					hardScene = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Scene scene = new Scene(hardScene);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stageEventBelongsTo.setScene(scene);		
	}




}
