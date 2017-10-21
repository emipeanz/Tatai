package View;

import java.io.IOException;

import Controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class handles the loading of FXML files.
 * @author se206
 *
 */
public class Loader {

	private String sceneName;
	private BaseController controller;

	/**
	 * Creates a new loader instance with a name of the FXML file it is to load and 
	 * a controls it is to load in on
	 * @param sceneName Name of the XML file
	 * @param controller Controller that controls the FXML scene
	 */
	public Loader(String sceneName, BaseController controller) {
		this.sceneName = sceneName;
		this.controller = controller;
	}

	/**
	 * Loads the given FXML file as a popup rather than a complete scene
	 * @return stage The popup stage that is the popup window
	 */
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
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);

		return stage;
	}

	/**
	 * Loads the given FXML file as a scene and attaches the given controller to it
	 * @return scene The scene that is loaded
	 */
	public Scene load() {
		if(controller != null) { // If scene has a custom controller and wasnt set directly in the FXML file
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
		else { // Else there is no custom controller and can just load the FXML file
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
