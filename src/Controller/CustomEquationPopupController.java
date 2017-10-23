package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CustomEquationPopupController extends BaseController {

	@FXML private TextField listName;
	@FXML private Label invalidNameText;
	@FXML private Label addedMessage;
	@FXML private Button submitNameButton;
	@FXML private List<String> equationList = new ArrayList<String>();

	public CustomEquationPopupController(List<TextField> equations) {
		for (TextField equation : equations) {
			equationList.add(equation.getText());
		}
	}

	public void checkName(ActionEvent e) {
		System.out.println("checking name method");
		String name = listName.getText();
		if (name.isEmpty() ) {
			System.out.println("Need to enter a name");
			invalidNameText.setText("Need to enter a name");
			invalidNameText.setVisible(true);
		}
		else if (!(name.trim().length() > 0)) {
			System.out.println("Name cant be all whitespace");
			invalidNameText.setText("Name cant be all whitespace");
			invalidNameText.setVisible(true);
		}
		else if(new File(".CustomEquations/" + name).exists()) {
			System.out.println("Name already exists, try a new one");
			invalidNameText.setText("Name already exists, try a new one");
			invalidNameText.setVisible(true);
		} else {
			System.out.println("valid name");
			invalidNameText.setVisible(false);
			createStorageFile();
			Stage stage = (Stage) submitNameButton.getScene().getWindow();
			stage.close();
		}
	}

	public void createStorageFile() {
		String filename = ".CustomEquations/" + listName.getText();
		File listFile = new File(filename);
		try {
			Files.write(Paths.get(filename), equationList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
