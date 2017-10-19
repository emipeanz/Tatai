package Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import View.Loader;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomEquationController {

	@FXML private TextField equation1,equation2, equation3, equation4, equation5, 
	equation6, equation7, equation8, equation9, equation10;
	@FXML private List<TextField> equationList;
	@FXML private Circle equationCircle1, equationCircle2, equationCircle3, equationCircle4, equationCircle5, 
	equationCircle6, equationCircle7, equationCircle8, equationCircle9, equationCircle10;
	@FXML private List<Circle> circleList;
	@FXML private AnchorPane helpWindow;
	@FXML private Button checkEquationsButton;
	@FXML private Button submitButton;
	@FXML private Label errorMessage;
	
	private ArrayList<Integer> wrongEquations = new ArrayList<Integer>();

	public void initialize() {
		submitButton.setVisible(false);
		errorMessage.setVisible(false);
		equationList = new ArrayList<TextField>(Arrays.asList(equation1,equation2, equation3, equation4, equation5, 
				equation6, equation7, equation8, equation9, equation10));
		circleList = new ArrayList<Circle>(Arrays.asList(equationCircle1, equationCircle2, equationCircle3, equationCircle4, equationCircle5, 
				equationCircle6, equationCircle7, equationCircle8, equationCircle9, equationCircle10));

		BooleanBinding bool = equation1.textProperty().isEmpty().or(equation2.textProperty().isEmpty())
				.or(equation3.textProperty().isEmpty()).or(equation4.textProperty().isEmpty())
				.or(equation5.textProperty().isEmpty()).or(equation6.textProperty().isEmpty())
				.or(equation7.textProperty().isEmpty()).or(equation8.textProperty().isEmpty())
				.or(equation9.textProperty().isEmpty()).or(equation10.textProperty().isEmpty());

		checkEquationsButton.disableProperty().bind(bool);
	}

	public void checkEquations(ActionEvent e) {
		wrongEquations.clear();
		int i = 0;
		int corrrectEquations = 0;
		for (TextField t : equationList) {
			//equation including x instead of *
			String inputEquation = t.getText();
			//equation in form that can be analysed
			String currentEquation = inputEquation.replaceAll("x", "\\*");
			try {
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				int answerInt = (int)engine.eval(currentEquation);
				System.out.println("answer int = " + answerInt);

				if((answerInt>0) && (answerInt<100)) {
					this.changeFeedbackIcon(circleList.get(i), true);
					corrrectEquations++;
				}
				else {
					this.changeFeedbackIcon(circleList.get(i), false);
					wrongEquations.add(Integer.parseInt(t.getId().split("equation")[1]));
				}
			} catch (ScriptException | ClassCastException e1) {
				wrongEquations.add(i);
				this.changeFeedbackIcon(circleList.get(i), false);
			}
			i++;
		}
		if(corrrectEquations != 10) {
			submitButton.setVisible(false);
			String output = "Some equations are invalid. Please fix equation";
			for(int in = 0; in< wrongEquations.size() ; in++) {
				output = output + (" " + wrongEquations.get(in));
				if(!(in == wrongEquations.size()-1)) {
					output = output + (",");
				}
			}
			output = output + ". Make sure to only use numbers and the -, *, + and / symbols. Also make sure the answer is between 1-99 !";
			System.out.println(output);
			errorMessage.setText(output);
			errorMessage.setVisible(true);
		}
		else {
			submitButton.setVisible(true);
			errorMessage.setText("All your equations are good");
			errorMessage.setVisible(true);
		}
	}

	public void getCustomListName(ActionEvent e) { 

		Button eventButton = (Button)e.getSource();
		Stage stage = new Loader("CustomEquationName.fxml", new CustomEquationPopupController(equationList)).loadPopup();
		stage.initOwner(eventButton.getScene().getWindow());
		stage.showAndWait();

		returntoCreationsMenu(e);
	}

	public void returntoCreationsMenu(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("CustomMenu.fxml", null).load();
		stageEventBelongsTo.setScene(scene);

	}


	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void backButtonEvent(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Button eventButton = (Button)event.getSource();

		Stage stage = new Loader("ExitPopup.fxml", new ExitPopupController(stageEventBelongsTo, "/View/CustomMenu.fxml")).loadPopup();
		stage.initOwner(eventButton.getScene().getWindow());
		stage.showAndWait();


	}


	public void showInstructions() {
		helpWindow.setVisible(true);
	}

	public void hideInstructions() {
		helpWindow.setVisible(false);
	}

	public void changeFeedbackIcon(Circle iconCircle, boolean pass) {
		if(pass) { // Sets circle to tick
			iconCircle.setStroke(Color.web("56ab2f"));
			iconCircle.setFill(Color.web("56ab2f"));
		}
		else { // Sets circle to cross
			iconCircle.setStroke(Color.web("ef473a"));
			iconCircle.setFill(Color.web("ef473a"));
		}
	}

}
