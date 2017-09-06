package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		
			
			
			for (int i = 1; i < 100; i++) {
				System.out.println(numToWord(i));
			}
			
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String numToWord(int number) {
		String _numberWord = "";

		if ((number >= 10) && (number < 20)) {
			_numberWord += "tekau ";
		} else if ((number >= 20) && (number < 30)) {
			_numberWord += "rua ";
		} else if ((number >= 30) && (number < 40)) {
			_numberWord += "toru ";
		} else if ((number >= 40) && (number < 50)){
			_numberWord += "whā ";
		} else if ((number >= 50) && (number < 60)){
			_numberWord += "rima ";
		} else if ((number >= 60) && (number < 70)){
			_numberWord += "ono ";
		} else if ((number >= 70) && (number < 80)){
			_numberWord += "whitu ";
		} else if ((number >= 80) && (number < 90)){
			_numberWord += "waru ";
		} else if ((number >= 90) && (number < 100)){
			_numberWord += "iwa ";
		} else if (number > 100) {
			return "number is over 100";
		}

		if ((number > 10) && ((number % 10) != 0)) {
			_numberWord += "tekau mā ";
		}
		

		if ((number % 10) == 0) {
			_numberWord += "tekau";
		} else if ((number % 10) == 1) {
			_numberWord += "tahi";
		} else if ((number % 10) == 2) {
			_numberWord += "rua";
		} else if ((number % 10) == 3) {
			_numberWord += "toru";
		} else if ((number % 10) == 4) {
			_numberWord += "whā";
		} else if ((number % 10) == 5) {
			_numberWord += "rima";
		} else if ((number % 10) == 6) {
			_numberWord += "ono";
		} else if ((number % 10) == 7) {
			_numberWord += "whitu";
		} else if ((number % 10) == 8) {
			_numberWord += "waru";
		} else if ((number % 10) == 9) {
			_numberWord += "iwa";
		}

		return _numberWord;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
