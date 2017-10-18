package Controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.bind.v2.schemagen.episode.Bindings;

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

public class HelpPageController extends BaseController{

	@FXML private ImageView screenImage;
	@FXML private Label title, explanation;
	@FXML private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7;
	@FXML private Button next = new Button(), previous = new Button();

	private ArrayList<String> titles = new ArrayList<String>();
	private Map<String, Image> imageList = new HashMap<String, Image>();
	private Map<String, String> textList = new HashMap<String, String>();
	private ArrayList<Circle> progressCircles;
	private String[] text = new String[] {"Blah1", "Blah2", "Blah3", "Blah4", "Blah5", "Blah6", "Blah7"};
	private Integer currentPage = 0;
	IntegerProperty above = new SimpleIntegerProperty();
	IntegerProperty below = new SimpleIntegerProperty();
	private Color GREEN = Color.web("56ab2f");
	private Color TRANSPARENT = Color.TRANSPARENT;
	private Color WHITE = Color.WHITE;

	public void initialize() {
		
		above = new SimpleIntegerProperty(currentPage);
		below = new SimpleIntegerProperty(currentPage);
		BooleanBinding boolAbove = above.greaterThan(6);
		BooleanBinding boolBelow = above.lessThan(1);

		next.disableProperty().bind(boolAbove);
		previous.disableProperty().bind(boolBelow);
		
		File dir = new File("src/View/Images");
		String extension = "png";

		titles.add("Main Menu");
		titles.add("Playing a Custom List");
		titles.add("Playing a Level");
		titles.add("Play Menu");
		titles.add("Results");

		FilenameFilter imagesFilter = new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				if(name.endsWith(extension)) {
					return true;
				}
				return false;
			}
		};

		int i = 0;

		if(dir.isDirectory()) {
			for ( File f : dir.listFiles()) {
				Image image = new Image(f.toURI().toString());
				String imageName = f.getName().replaceFirst("[.][^.]+$", "");
				imageList.put(imageName, image);

				textList.put(imageName, text[i]);
				i++;
			}
		}
		
		progressCircles = new ArrayList<Circle>(Arrays.asList(circle1, circle2,
				circle3, circle4, circle5, circle6, circle7));

		this.refresh();
		this.colourCircle();

	}

	public void next(ActionEvent e) {
		currentPage++;
		above.add(1);
		below.add(1);
		this.refresh();
		this.colourCircle();
	}

	public void previous(ActionEvent e) {
		currentPage--;
		above.subtract(1);
		below.subtract(1);
		this.refresh();
		this.uncolourCircle();
	}

	public void colourCircle() {
			Circle circle =	progressCircles.get(currentPage);
			circle.setFill(GREEN);
			circle.setStroke(GREEN);

	}

	public void uncolourCircle() {
		Circle circle =	progressCircles.get(currentPage + 1);
		circle.setFill(TRANSPARENT);
		circle.setStroke(WHITE);
	}
	
	

	private void refresh(){
		title.setText(titles.get(currentPage));
		explanation.setText(textList.get(titles.get(currentPage)));
		screenImage.setImage(imageList.get(titles.get(currentPage)));
	}
}
