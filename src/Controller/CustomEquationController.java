package Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
				}
			} catch (ScriptException | ClassCastException e1) {
				System.out.println("non equation or not integer answer, entered on equation " + (i + 1));
				this.changeFeedbackIcon(circleList.get(i), false);
			}
			i++;
		}
		if(corrrectEquations != 10) {
			submitButton.setVisible(false);
			errorMessage.setText("There are some problems with some equation, please fix and try again");
			errorMessage.setVisible(true);
		}
		else {
			submitButton.setVisible(true);
			errorMessage.setText("All your equations are good");
			errorMessage.setVisible(true);
		}
	}

	public void getCustomListName(ActionEvent e) { 
		String filename = "/View/CustomEquationName.fxml";
		System.out.println("in get custom list name method");
		
		Button eventButton = (Button)e.getSource();
		
		try {
			Stage stage = new Stage(); 
			AnchorPane root;
			CustomEquationPopupController popupController = new CustomEquationPopupController(equationList);
			FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
			loader.setController(popupController);
			root = (AnchorPane)loader.load();
			
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(eventButton.getScene().getWindow());
			stage.showAndWait();
		} catch (IOException e1) {
			System.out.println("exception thrown");
			e1.printStackTrace();
		}
		
		returntoCreationsMenu(e);
	}
	
	public void returntoCreationsMenu(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene mainMenuScene = null;
		try {
			mainMenuScene = new Scene(FXMLLoader.load(getClass().getResource("/View/CustomMenu.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		stageEventBelongsTo.setScene(mainMenuScene);
		
	}

	
	/**
	 * Method takes an action event on the return to menu button.  The main menu scene is
	 * displayed and the level view is taken away
	 * @param event
	 */
	public void backButtonEvent(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Button eventButton = (Button)event.getSource();
		
		try {
			Stage stage = new Stage(); 
			AnchorPane root;
			ExitPopupController popupController = new ExitPopupController(stageEventBelongsTo, "/View/CustomMenu.fxml");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ExitPopup.fxml"));
			loader.setController(popupController);
			root = (AnchorPane)loader.load();
			
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(eventButton.getScene().getWindow());
			stage.showAndWait();
		} catch (IOException e1) {
			System.out.println("exception thrown");
			e1.printStackTrace();
		}
		
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
