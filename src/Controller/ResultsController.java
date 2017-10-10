package Controller;

/**
 * This class is the controller for the results scene. Results is shown once the user has completed
 * 10 questions in a test. The page shows them the number in integer and word form and if they
 * got it right of wrong. Depending on the answer, the rows in the results table will be coloured
 * differently to make it easy to distinguish the questions from each other.
 * @author Maddie Beagley and Emilie Pearce
 */
import Model.*;
import java.io.File;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ResultsController {


	@FXML private Button returnButton;
	@FXML private Button tryAgainButton;
	@FXML private Label resultsLabel;
	@FXML private TableView<Question> tableView;
	@FXML private TableColumn<Question, Integer> question;
	@FXML private TableColumn<Question, String> answerString;
	@FXML private TableColumn<Question, String> answerInt;
	@FXML private TableColumn<Question, String> pass;

	public File _resultsFile;
	private Test _test;
	private Difficulty _difficulty;
	private ObservableList<Question> _dataList;
	private TestType _testType;


	public void initialize() {
		question.setCellValueFactory(new PropertyValueFactory<>("displayString"));
		answerString.setCellValueFactory(new PropertyValueFactory<>("answerString"));
		answerInt.setCellValueFactory(new PropertyValueFactory<>("answerInt"));
		pass.setCellValueFactory(new PropertyValueFactory<>("pass"));
		_dataList = FXCollections.observableArrayList(_test.getTestquestions());


		tableView.setRowFactory(new Callback<TableView<Question>, TableRow<Question>>() {
			@Override public TableRow<Question> call(TableView<Question> q) {
				return new TableRow<Question>() {
					@Override protected void updateItem(Question q, boolean empty) {
						super.updateItem(q, empty);
						if(q != null) {
							if (q.getPass().equals("Right!")) {
								// Colour green for getting it right
								setStyle("-fx-background-color: linear-gradient(to right, #56ab2f, #a8e063); ");
							}
							if (q.getPass().equals("Wrong")){
								// Colour red for getting it wrong
								setStyle("-fx-background-color : linear-gradient(to right, #cb2d3e, #ef473a);");
							}
						}
					}
				};
			}
		});
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


		tableView.setItems(_dataList);
		resultsLabel.setText("You got " + _test.getOverallMark() + "/10 !");

		//saves results of this round to file for use in stats menu
		saveResults();
	}


	public ResultsController(Test test, TestType testType) {
		_test = test;
		_difficulty = _test.getdifficulty();
		_testType = testType;
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
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml")));
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

		AnchorPane levelScene = null;

		try {
			//ARBITRARY ASSIGNMENT OF ENUM
			LevelController controller = new LevelController(_difficulty, _testType);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Level.fxml"));
			loader.setController(controller);
			levelScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(levelScene);
		stageEventBelongsTo.setScene(scene);		
	}



	/**
	 * Information about results is stored in a file called results.txt, first line will
	 * be the average mark attained in the session, second line will be the highest mark 
	 * that the user has gotten in the session, third line will be the number of tests that
	 * have been taken in the session, fourth line will be a running total of all the scores
	 * the user has had.
	 */
	private void saveResults() {


		//will only store results if the test was using equations
		if (_testType == _testType.EQUATION) {
			String filename;

			if (_difficulty == _difficulty.EASY) {
				filename = ".easyResults.txt";
			} else if (_difficulty == _difficulty.HARD) {
				filename = ".hardResults.txt";
			} else {
				filename = ".mediumResults.txt";
			}

			//used to check if the file exists
			File temp = new File(filename);

			try {
				//if file exists update the results

				System.out.println("file has already been made");

				//finds previous high score and converts it to an integer
				String highScoreString;
				highScoreString = Files.readAllLines(Paths.get(filename)).get(1);
				int previousHighScore = Integer.parseInt(highScoreString);

				//finds previous number of tests run and converts to an integer
				String numOfTestsString = Files.readAllLines(Paths.get(filename)).get(2);
				int previousNumOfTests = Integer.parseInt(numOfTestsString);

				//finds previous number of tests run and converts to an integer
				String cumulativeResultsString = Files.readAllLines(Paths.get(filename)).get(3);
				int cumulativeResults = Integer.parseInt(cumulativeResultsString);

				//creates a list to store the new results in
				List<String> newResults = new ArrayList<String>();

				//computes average score
				double averageScore = (_test.getOverallMark() + cumulativeResults) / (previousNumOfTests + 1);
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
				
				newResults.add(String.valueOf(cumulativeResults + _test.getOverallMark()));

				//writes in new results to file
				Files.write(Paths.get(filename), newResults);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
