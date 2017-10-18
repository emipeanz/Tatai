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
	@FXML private Button nextButton , previousButton ;

	private ArrayList<String> titles = new ArrayList<String>();
	private Map<String, Image> imageList = new HashMap<String, Image>();
	private Map<String, String> textList = new HashMap<String, String>();
	private ArrayList<Circle> progressCircles;
	private Integer currentPage = 0;
	private Color GREEN = Color.web("56ab2f");
	private Color TRANSPARENT = Color.TRANSPARENT;
	private Color WHITE = Color.WHITE;

	public void initialize() {

		
		File dir = new File("src/View/Images");
		String extension = "png";

		titles.add("Main Menu");
		titles.add("Playing a Custom List");
		titles.add("Playing a Level");
		titles.add("Play Menu");
		titles.add("Results");
		
		textList.put("Main Menu", "Maori translation for Maori menu description");
		textList.put("Playing a Custom List", "Maori translation for Maori Playing a Custom List description");
		textList.put("Playing a Level", "Maori translation for Maori Playing a Level description");
		textList.put("Play Menu", "Maori translation for Maori Play Menu description");
		textList.put("Results", "Maori translation for Maori Results description");

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

			}
		}
		
		progressCircles = new ArrayList<Circle>(Arrays.asList(circle1, circle2,
				circle3, circle4, circle5, circle6, circle7));

		this.refresh();
		this.colourCircle();

	}

	public void next(ActionEvent e) {
		currentPage++;
		this.refresh();
		this.colourCircle();
	}

	public void previous(ActionEvent e) {
		currentPage--;
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
		if(currentPage > 6) {
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
