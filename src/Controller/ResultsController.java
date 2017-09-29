package Controller;

/**
 * This class is the controller for the results scene. Results is shown once the user has completed
 * 10 questions in a test. The page shows them the number in integer and word form and if they
 * got it right of wrong. Depending on the answer, the rows in the results table will be coloured
 * differently to make it easy to distinguish the questions from each other.
 * @author Maddie Beagley and Emilie Pearce
 */
import Model.*;
import View.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	@FXML private Button tryAgainButton;
	@FXML private ListView<String> resultsListView;
	@FXML private Label resultsLabel;

	public File _resultsFile;
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
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("../View/MainMenu.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//mainMenuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Level.fxml"));
			loader.setController(controller);
			hardScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(hardScene);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageEventBelongsTo.setScene(scene);		
	}

	/**
	 * Information about results is stored in a file called results.txt, first line will
	 * be the average mark attained in the session, second line will be the highest mark 
	 * that the user has gotten in the session, third line will be the number of tests that
	 * have been taken in the session.
	 */
	private void saveResults() {
		//used to check if the file exists
		File temp = new File(".results.txt");

		try {
			//if file exists update the results
			if (temp.exists()) {
				System.out.println("file has already been made");

				//finds previous average score and converts it to an integer
				String averageScoreString = Files.readAllLines(Paths.get(".results.txt")).get(0);
				double previousAverageScore = Double.parseDouble(averageScoreString);

				//finds previous high score and converts it to an integer
				String highScoreString;
				highScoreString = Files.readAllLines(Paths.get(".results.txt")).get(1);
				int previousHighScore = Integer.parseInt(highScoreString);

				//finds previous number of tests run and converts to an integer
				String numOfTestsString = Files.readAllLines(Paths.get(".results.txt")).get(2);
				int previousNumOfTests = Integer.parseInt(numOfTestsString);

				//creates a list to store the new results in
				List<String> newResults = new ArrayList<String>();

				//computes average score
				double averageScore = (_test.getOverallMark() + previousAverageScore) / (previousNumOfTests + 1);
				newResults.add(String.format("%.1f", averageScore));

				//sees if a new highscore has been made
				if (_test.getOverallMark() > previousHighScore ) {
					newResults.add(String.valueOf(_test.getOverallMark()));
				} else {
					newResults.add(String.valueOf(previousHighScore));
				}

				//new number of tests that have been made
				newResults.add(String.valueOf(previousNumOfTests + 1));
				System.out.println(String.valueOf(previousNumOfTests + 1));	

				//writes in new results to file
				Files.write(Paths.get(".results.txt"), newResults);
			} else {
				System.out.println("Making file");
				//creates a new file to store results in
				_resultsFile = new File(".results.txt");		

				//score from current round is stored
				String score = String.valueOf(_test.getOverallMark());		

				//writes information from current round to new file
				Files.write(Paths.get(".results.txt"), Arrays.asList(score, score, "1"));

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
