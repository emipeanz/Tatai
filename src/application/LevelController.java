package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class LevelController {
	
	@FXML
	private Label numberToTest;
	@FXML
	private Label numberWord;
	@FXML
	private ProgressBar progressBar;
	
	private double progress;
	
	/**
	 * For now just having a play around - this method is called when the make random number
	 * button is clicked and will show the number and the word of that number in maori.
	 * Learning how to use events.
	 * @param event
	 */
	public void updateLabels(ActionEvent event) {
		//instantiating result for shits and gigs
		Result result = new Result(Difficulty.HARD);
		//sets labels that show a number and the maori word corresponding to it
		numberToTest.setText(Integer.toString(result._numberInt));
		numberWord.setText(result._numberWord);
		progress += 0.1;
		progressBar.setProgress(progress);
	}

}
