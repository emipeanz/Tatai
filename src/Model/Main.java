package Model;
import java.util.ArrayList;
import java.util.List;

import View.Loader;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;


/**
 * This class if the main class for our application. Its sets up the transition at the beginning then
 * hands control over to the controllers.
 * 
 * @author Maddie Beagley and Emilie Pearce
 *
 */
public class Main extends Application {
	/**
	 * Loads in the introductory and the level scene, displays the introductory scene for two seconds
	 * then progresses the user into the scene where they can select what level they would like to play.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//sets both the introductory and level scenes ready for transition from introduction to levels
			Scene mainScene = new Loader("MainMenu.fxml", null).load();
			Scene introScene = new Loader("IntroMenu.fxml", null).load();
			
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
	
	/**
	 * Starts the main application
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
