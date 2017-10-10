package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CustomEquationController {
	
	@FXML private TextField equation1,equation2, equation3, equation4, equation5, 
	equation6, equation7, equation8, equation9, equation10;
	@FXML private List<TextField> equationList;
	@FXML private Circle equationCircle1, equationCircle2, equationCircle3, equationCircle4, equationCircle5, 
	equationCircle6, equationCircle7, equationCircle8, equationCircle9, equationCircle10;
	@FXML private List<Circle> circleList;
	@FXML private AnchorPane helpWindow;
	@FXML private DialogPane dialogueCheckExit;
	@FXML private Button dialogueCheckExitExit;
	@FXML private Button dialogueCheckExitStay;
	@FXML private Button checkEquationsButton;
	@FXML private Label errorMessage;
	
	public void initialize() {
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
		int i = 0;
		int corrrectEquations = 0;
		for (TextField t : equationList) {
			String currentEquation = t.getText();
			try {
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				int answerInt;
				answerInt = (int) engine.eval(currentEquation);
				System.out.println("answer int = " + answerInt);
				
				if((answerInt>0) && (answerInt<100)) {
					this.changeFeedbackIcon(circleList.get(i), true);
					corrrectEquations++;
				}
				else {
					this.changeFeedbackIcon(circleList.get(i), false);
				}
			} catch (ScriptException e1) {
				System.out.println("non equasion entered on equasion " + (i + 1));
				this.changeFeedbackIcon(circleList.get(i), false);
			}
			i++;
		}
		if(corrrectEquations != 10) {
			errorMessage.setText("There are some problems with some equation, please fix and try again");
			errorMessage.setVisible(true);
		}
		else {
			errorMessage.setText("All your equations are good");
			errorMessage.setVisible(true);
		}
	}
	
	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void backButtonEvent(ActionEvent event) {
		System.out.println("Event triggering return to main menu");

		dialogueCheckExit.setVisible(true);
	}
	
	public void returnToGame(ActionEvent e) {
		dialogueCheckExit.setVisible(false);
		return;
	}
	
	public void returnToMainMenu(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//mainMenuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stageEventBelongsTo.setScene(mainMenuScene);
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
