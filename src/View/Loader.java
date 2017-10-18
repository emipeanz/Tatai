package View;

import java.io.IOException;

import Controller.BaseController;
import Controller.CustomEquationPopupController;
import Controller.LevelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Loader {

	private String sceneName;
	private BaseController controller;

	public Loader(String sceneName, BaseController controller) {
		this.sceneName = sceneName;
		this.controller = controller;
	}

	public Stage loadPopup() {


		Stage stage = new Stage(); 
		AnchorPane root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
		loader.setController(controller);
		try {
			root = (AnchorPane)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);

		return stage;
	}


	public Scene load() {
		if(controller != null) {
			AnchorPane pane = null;
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));

				loader.setController(controller);
				pane = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Scene scene = new Scene (pane);
			scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
			return scene;
		}
		else {
			Scene scene = null;
			try {
				scene = new Scene(FXMLLoader.load(getClass().getResource(sceneName)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
			return scene;
		}
	}

}
