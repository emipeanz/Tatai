package Controller;

/**
 * This class is the main controller for the main menu. After the first scene has shown the titles and 
 * transitioned, this controller handles going to a hard, easy or stats page.
 * @author Maddie Beagley and Emilie Pearce
 */
import java.io.IOException;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class is the controller for the main class and handles button events
 *
 */
public class MainController{

	@FXML private Button practiceButton;
	@FXML private Button playButton;
	
	private TestType testType;

	/**
	 * This method directs the user to the easy test screen.  A custom controller is made with
	 * the difficulty parameter and attached to the Level scene
	 * @param e ActionEvent when easyButton is clicked
	 */
	public void enterDifficultyOption(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();
		AnchorPane difficultyScene = null;
		
		if (e.getSource().equals(practiceButton)) {
			testType = TestType.PRACTICE;
		} else if (e.getSource().equals(playButton)) {
			testType = TestType.EQUATION;
		}
		
		try {
			DifficultyController controller = new DifficultyController(testType);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Difficulty.fxml"));
			loader.setController(controller);
			difficultyScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(difficultyScene);
		stageEventBelongsTo.setScene(scene);

	}


	public void enterStatsView(ActionEvent e) {
		// Get the main stage to display the scene in
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		AnchorPane statsScene = null;
		try {
			System.out.println("Enterings stats view");
			StatsController controller = new StatsController();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/StatsView.fxml"));
			loader.setController(controller);
			statsScene = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(statsScene);
		stageEventBelongsTo.setScene(scene);
	}

}
