package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Model.TestType;
import View.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomMenuController extends BaseController{

	@FXML private DialogPane dialogChooseTest;
	@FXML private ComboBox comboBox;
	@FXML private Button PlayCustomTestButton;

	public void openCreateCustom(ActionEvent e) {
		Stage stageEventBelongsTo = (Stage) ((Node)e.getSource()).getScene().getWindow();

		Scene scene = new Loader("CustomEquations.fxml", null).load();
		stageEventBelongsTo.setScene(scene);		
	}

	public void returnDifficulty(ActionEvent e) {
		this.returnPreviousScene(e, "Difficulty.fxml");
	}

	public void selectCustomTest(ActionEvent event) {
		Stage stageEventBelongsTo = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Button eventButton = (Button)event.getSource();

			Stage stage = new Loader("ChoseCustomList.fxm", new ChoseCustomListController(stageEventBelongsTo)).loadPopup();
			
			stage.initOwner(eventButton.getScene().getWindow());
			stage.showAndWait();


	}		


}
