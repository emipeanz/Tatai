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


public class CustomEquationPopupController {

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
		if (listName.getText().isEmpty()) {
			System.out.println("invalid name");
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
		String filename = ".CustomEquations/." + listName.getText();
		File listFile = new File(filename);
		try {
			Files.write(Paths.get(filename), equationList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
