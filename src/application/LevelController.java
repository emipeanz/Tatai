package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LevelController {
	
	@FXML
	private Label numberToTest;
	@FXML
	private Label numberWord;
	
	/**
	 * For now just having a play around - this method is called when the make random number
	 * button is clicked and will show the number and the word of that number in maori.
	 * Learning how to use events.
	 * @param event
	 */
	public void updateLabels(ActionEvent event) {
		//instantiating result for shits and gigs
		Result result = new Result(Difficulty.EASY);
		//sets labels that show a number and the maori word corresponding to it
		numberToTest.setText(Integer.toString(result._numberInt));
		numberWord.setText(result._numberWord);
	}

}
