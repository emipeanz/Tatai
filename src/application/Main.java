package application;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	/**
	 * Loads in the introductory and the level scene, displays the introductory scene for two seconds
	 * then progresses the user into the scene where they can select what level they would like to play.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//sets root
			BorderPane root = new BorderPane();
			
			//sets both the introductory and level scenes ready for transition from introduction to levels
			Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));
			Scene introScene = new Scene(FXMLLoader.load(getClass().getResource("IntroMenu.fxml")));
			
			//sets the css file with scenes
			introScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Sets the introductory scene
			primaryStage.setScene(introScene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Tātai");
			primaryStage.show();
			
			//Displays introductory scene for two seconds before transitioning to level scene
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished( event -> primaryStage.setScene(mainScene));
			delay.play();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
