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
			
			for (int i = 0; i < 100; i++) {
				System.out.println(numberToWord(i));
			}
		

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	
	/**
	 * Converts an integer to the maori word associated to that integer.
	 * @param number: number to be converted
	 * @return String associated to that number
	 */
	private String numberToWord(int number) {
		String _numberWord = "";

		//if number is only a single digit
		if (number < 10) {
			_numberWord = digitToMaori(number);
		//if number is between 10 and 19 does not have prefix tahi
		} else if ((number >= 10) && (number < 20)) {
			_numberWord = "tekau mā " + digitToMaori(number % 10);
		//case for when number is between 20-100
		} else if ((number > 10) && (number < 100)) {
				if (number % 10 == 0) {
					_numberWord = digitToMaori(number / 10) + " tekau";
				} else {
					_numberWord = digitToMaori(number / 10) + " tekau mā "
							+ digitToMaori(number % 10);
				}
		} 		
		
		return _numberWord;
	}
	
	/**
	 * Returns maori name for a single digit
	 * @param number: for name to be found
	 * @return String associated to number
	 */
	private String digitToMaori(int number) {
		String numberWord = "";
		
		if (number == 0) {
			numberWord = "";
		} else if (number == 1) {
			numberWord = "tahi";
		} else if (number == 2) {
			numberWord = "rua";
		} else if (number == 3) {
			numberWord = "toru";
		} else if (number == 4) {
			numberWord = "whā";
		} else if (number == 5) {
			numberWord = "rima";
		} else if (number == 6) {
			numberWord = "ono";
		} else if (number == 7) {
			numberWord = "whitu";
		} else if (number == 8) {
			numberWord = "waru";
		} else if (number == 9) {
			numberWord = "iwa";
		}

		return numberWord;	
	}
}
