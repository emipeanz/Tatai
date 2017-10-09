package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CustomEquationController {
	
	@FXML private TextField equation1,equation2, equation3, equation4, equation5, 
	equation6, equation7, equation8, equation9, equation10;
	@FXML private List<TextField> equationList;
	@FXML private Label equationLabel1, equationLabel2, equationLabel3, equationLabel4, equationLabel5, 
	equationLabel6, equationLabel7, equationLabel8, equationLabel9, equationLabel10;
	@FXML private List<Label> labelList;
	
	public void initialize() {
		equationList = new ArrayList<TextField>(Arrays.asList(equation1,equation2, equation3, equation4, equation5, 
				equation6, equation7, equation8, equation9, equation10));
		labelList = new ArrayList<Label>(Arrays.asList(equationLabel1, equationLabel2, equationLabel3, equationLabel4, equationLabel5, 
				equationLabel6, equationLabel7, equationLabel8, equationLabel9, equationLabel10));
		
	}
	
	public void changeFeedbackIcon(Label iconLabel, boolean pass) {
		if(pass) { // Sets circle to tick
			iconLabel.setText("\uf05d");
		}
		else { // Sets circle to cross
			iconLabel.setText("\uf05c");
		}
	}

}
