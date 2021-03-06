package Controller;

import View.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sun.xml.internal.bind.v2.schemagen.episode.Bindings;

import Model.Main;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class handles everything to do wit the help page. This includes holding all the images and
 * information related to each page description.
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public class HelpPageController extends BaseController{

	@FXML private ImageView screenImage;
	@FXML private Label title, explanation;
	@FXML private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10;
	@FXML private Button nextButton , previousButton ;

	private ArrayList<String> titles = new ArrayList<String>();
	private Map<String, Image> imageList = new HashMap<String, Image>();
	private Map<String, String> textList = new HashMap<String, String>();
	private ArrayList<Circle> progressCircles;
	private Integer currentPage = 0;
	private Color GREEN = Color.web("1c92d2");
	private Color TRANSPARENT = Color.TRANSPARENT;
	private Color WHITE = Color.WHITE;

	/**
	 * Sets up the page which includes loading images and storing them in hashmaps along with their respective
	 * descriptions
	 */
	public void initialize() {

		titles.add("Main Menu");
		titles.add("Play Menu");
		titles.add("Making a Custom List");
		titles.add("Playing a Custom List");
		titles.add("Playing a Level");
		titles.add("Getting it wrong the first time");
		titles.add("Getting it wrong the second time");
		titles.add("Getting a question right");
		titles.add("Results");
		titles.add("Statistics");

		textList.put("Main Menu", "This is the main menus where you can chose to practice math by playing fun games, or playing the game where it will also track your score. You can view your statistics any time from the button in the top right corner.");
		textList.put("Play Menu", "Here you can chose what difficulty you want to play or create you own equation lists to test by going to custom. You can return to the main menu at any time. Remember, before you can play harder levels, you must get 8/10 on the level below!");
		textList.put("Making a Custom List", "Here you can input 10 of you own equations to test. Make sure to only use numbers, =, -, * and / symbols and make sure the answer is between 1 - 99. Heres an example \" 6 * 4 \". When you have them all, click the green check button to see if they are all valid. Then click submit and think of a list name!");
		textList.put("Playing a Custom List", "Here you can chose what list you want to play. Remebers, you can always go back and create more!");
		textList.put("Playing a Level", "When playing, the equation to answer is shown in the middle. Use the button down the bottom to record, check and play a recording. When recording, the blue bar up the top will show you how much time you have left. You can always return to the main menu but your progress wont be saved!");
		textList.put("Getting it wrong the first time", "When playing, if you get it wrong the first time, an orange warning will show and you have one chance left!");
		textList.put("Getting it wrong the second time", "If you get it wrong the second time a red warning will show, but dont worry. The game will move onto the next question!");
		textList.put("Getting a question right", "When you get a question right a green label will show and the game will move onto the next question");
		textList.put("Results", "Once you have completed a test, results will show you your score, the questions you got right and wrong and their answer");
		textList.put("Statistics", "Here you can see you average score, how many tests you have taken and your high score. Keep playing more games to change them! User the slider bar up the top to change between statistics for easy, medium and hard levels.");
		
		File dir = new File("Resources/Images");
		File[] foundfiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});
		
		for (File f : foundfiles) {
			Image img = new Image(f.toURI().toString());
			String name = f.getName().replaceFirst("[.][^.]+$", "");
			imageList.put(name, img);
			
		}
		
		
		progressCircles = new ArrayList<Circle>(Arrays.asList(circle1, circle2,
				circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10));

		this.refresh();
		this.colourCircle();

	}

	/**
	 * Method handles changing to another scene description by increasing the count of 
	 * currentPage
	 * @param e
	 */
	public void next(ActionEvent e) {
		currentPage++;
		this.refresh();
		this.colourCircle();
	}

	/**
	 * Method handles changing to another scene description by decreasing the count of
	 * currentPage
	 * @param e
	 */
	public void previous(ActionEvent e) {
		currentPage--;
		this.refresh();
		this.uncolourCircle();
	}

	/**
	 * Colors a progress circle down the bottom when a page is increased to the next
	 */
	public void colourCircle() {
		Circle circle =	progressCircles.get(currentPage);
		circle.setFill(GREEN);
		circle.setStroke(GREEN);

	}

	/**
	 * Empties a circles color when the user goes back a page
	 */
	public void uncolourCircle() {
		Circle circle =	progressCircles.get(currentPage + 1);
		circle.setFill(TRANSPARENT);
		circle.setStroke(WHITE);
	}

	/**
	 * Is called whenever the user changed a scene description. It checks if they are on the first or last scene - and if
	 * so disables the respective button so they cant continue going forward/back. It also changes the title of the page 
	 * and updates the image and description from the respective hashmaps
	 */
	private void refresh(){
		if(currentPage >= 9) {
			System.out.println(currentPage);
			nextButton.setDisable(true);
		} else {
			nextButton.setDisable(false);
		}
		if(currentPage < 1) {
			previousButton.setDisable(true);
		} else {
			previousButton.setDisable(false);
		}
		title.setText(titles.get(currentPage));
		explanation.setText(textList.get(titles.get(currentPage)));
		screenImage.setImage(imageList.get(titles.get(currentPage)));
	}
}
