package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LevelController {
	
	@FXML
	private Label numberToTest;
	@FXML
	private Label numberWord;
	
	//should update these labels every time update number button is pressed
	public void updateLabels(ActionEvent event) {
		//instantiating result for shits and gigs
		Result result = new Result(Difficulty.EASY);
		//sets labels that show a number and the maori word corresponding to it
		numberToTest.setText(Integer.toString(result._numberInt));
		numberWord.setText(result._numberWord);
	}

}
