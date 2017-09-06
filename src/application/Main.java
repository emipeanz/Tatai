package application;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			Scene introScene = new Scene(FXMLLoader.load(getClass().getResource("IntroMenu.fxml")));
			Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));
			
			introScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(introScene);
			primaryStage.setResizable(false);
			primaryStage.setHeight(400);
			primaryStage.setWidth(600);
			primaryStage.show();
			
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
