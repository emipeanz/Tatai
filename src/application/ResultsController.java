package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ResultsController {


	@FXML private Button returnButton;
	//will return user to the main menu

	@FXML private Button tryAgainButton;
	//will take user to the beginning of a new test

	@FXML private ListView<String> resultsListView;

	@FXML private Label resultsLabel;

	public File _resultsFile;

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
		_dataList = FXCollections.observableArrayList(_test.getQuestionsToString());
		this.resultsListView.setItems(_dataList);
		this.resultsListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){

			@Override
			public ListCell<String> call(ListView<String> p) {

				ListCell<String> cell = new ListCell<String>(){

					@Override
					protected void updateItem(String t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null ) {
							setText( t);
							if (t.contains("Right!")) {
								setStyle("-fx-background-color: linear-gradient(to right, #56ab2f, #a8e063); ");
							}
							else {
								setStyle("-fx-background-color : linear-gradient(to right, #cb2d3e, #ef473a);");
							}

						} else {
							setText("");
						}
					}

				};

				return cell;
			}
		});
		resultsLabel.setText("You got " + _test.getOverallMark() + "/10 !");
		
		//saves results of this round to file for use in stats menu
		saveResults();
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

	/**
	 * Information about results is stored in a file called results.txt, first line will
	 * be the average mark attained in the session, second line will be the highest mark 
	 * that the user has gotten in the session, third line will be the number of tests that
	 * have been taken in the session.
	 */
	private void saveResults() {
		
		FileWriter fw;
		BufferedWriter bw;
		String results;
		
		try {
			if (_resultsFile == null) {
				System.out.println("Making file");
				//creates a new file to store results in
				_resultsFile = new File(".results.txt");
				fw = new FileWriter(".results.txt");
				bw = new BufferedWriter(fw);
				//stores results of the test that has just passed
				results = _test.getOverallMark() + " " + _test.getOverallMark() + " 1";
				//average result and highest result are the same in first round
				bw.write(results);
				//close the writer
				bw.close();
			} else {
				//update data in the file with overall stats
				fw = new FileWriter(".results.txt");
				bw = new BufferedWriter(fw);
				
				BufferedReader br = new BufferedReader(new FileReader(".results.txt"));
				String resultsLine = br.readLine();
				
				int[] previousResults = new int[3];
				
				int count = 0;
				
				for (String stringNum : resultsLine.split(" ")) {
					previousResults[count] = Integer.parseInt(stringNum);
					count++;
				}
				
				int numOfTests = previousResults[2] + 1;
				
				int averageMark = (previousResults[0] + _test.getOverallMark()) / numOfTests;
				
				int highestMark = previousResults[1];
				
				if (_test.getOverallMark() > previousResults[1]) {
					highestMark = _test.getOverallMark();
				}
				
				results = averageMark + " " + highestMark + " " + numOfTests;
				
				bw.write(results); 

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
