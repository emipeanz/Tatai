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
	@FXML private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10;
	@FXML private Button nextButton , previousButton ;

	private ArrayList<String> titles = new ArrayList<String>();
	private Map<String, Image> imageList = new HashMap<String, Image>();
	private Map<String, String> textList = new HashMap<String, String>();
	private Map<String, String> englishToMaoriTitles = new HashMap<String, String>();
	private ArrayList<Circle> progressCircles;
	private Integer currentPage = 0;
	private Color GREEN = Color.web("56ab2f");
	private Color TRANSPARENT = Color.TRANSPARENT;
	private Color WHITE = Color.WHITE;

	public void initialize() {

		
		File dir = new File("src/View/Images");
		String extension = "png";
		
		englishToMaoriTitles.put("Main Menu", "tahua matua");
		englishToMaoriTitles.put("Play Menu", "tahua tahua");
		englishToMaoriTitles.put("Making a Custom List", "Te Hanga i te Rarangi Ritenga");
		englishToMaoriTitles.put("Playing a Custom List", "Te Mahi i te Rarangi Ritenga");
		englishToMaoriTitles.put("Playing a Level", "Te takaro i te taumata");
		englishToMaoriTitles.put("Getting it wrong the first time", "Ko te tango i te he i te wa tuatahi");
		englishToMaoriTitles.put("Getting it wrong the second time", "Kei te hapa i te wa tuarua");
		englishToMaoriTitles.put("Getting a question right", "Te whakautu tika");
		englishToMaoriTitles.put("Results", "Hua");
		englishToMaoriTitles.put("Statistics", "Taatauranga");

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
		
		textList.put("Main Menu", "Koinei te waharoa rārangi kai.\n" + 
				"Ka taea e koe te takaro i nga kēmu me te kore he tohu i tuhia i roto i te 'mahi', te takaro rānei, me te whakarite i o ake ake taumata me o raatau hua!.\n "
				+ "Pāwhiritia te paatatau tatauranga ki runga kia kite koe i nga tohu");
		textList.put("Making a Custom List", "Whakauruhia e koe nga whakawhitinga e 10 whārite kōpiko  me nga whakautu 1 - 99 ma te whakamahi i nga tau me te -, *, - me nga "
				+ "tohu. Kia tae mai koe, tirohia mehemea he raruraru ka korerotia ki a koe. Na ka tuku ia ratou ka whiriwhiria he ingoa mo to rarangi!");
		textList.put("Playing a Custom List", "Ka whiriwhirihia he rarangi i hanga e koe, ka tīpakohia te tarairo hei tarai i tenei rārangi ka tohua ki runga!\n" + 
				"Ka taea e koe te hoki atu me te hanga i etahi atu ritenga ritenga i nga wa katoa!");
		textList.put("Playing a Level", "Ko nga paatene e toru kei raro i te takaro o raro, tuhia me te tirotiro ka whakautu. Ko te papa i runga ko te wa kua waiho e koe ki te tuhi.\n" + 
				"Ka taea e koe te hoki ki te tahua i nga wa katoa, engari kaore koe e haere ki te ahunga whakamua.\n" + 
				"Whakamahia te pātene whakatere mēnā e hiahia ana koe ki te peke i tētahi pātai");
		textList.put("Play Menu", "Ko tenei tahua kei hea koe i whiriwhiri i te raruraru e hiahia ana koe ki te takaro. Ko etahi kei te kati kia tae ra ano ki a koe te tohu o te 8/10 i te taumata i raro nei.\n" + 
				"Ka taea hoki e koe te hanga i nga whakamatautau ritenga me te whakamahi i nga whakamatautau ritenga mai i tenei wharangi!");
		textList.put("Results", "Ka whakaaturia nga hua mo te whakamatautau i konei. Ko te tikanga o te whero ko te mea he kino ki a koe, ko nga mea matomato he tikanga tika koe. Ka taea e koe te tarai i te whakamatautau me te paatene tautuhi i raro, ka hoki ki te tahua matua!");
		textList.put("Getting it wrong the second time", "Ko te karere whero he tikanga he he. Na inaianei ki te uiuinga e whai ake nei!");
		textList.put("Getting it wrong the first time", "Ae, kua hara koe. Ko te karere karaka e whakaatu ana he kotahi noa atu te waahi ka mahue ki a koe!");
		textList.put("Getting a question right", "Ko te karere matomato ko te tikanga o nga korero tika! I te take e whai mai nei!");
		textList.put("Statistics", "I konei ka whakaatuhia o taatau tatau. Whakamahia te kiriata i te tihi ki te neke i waenga i nga tatauranga taumata taumatawari, reo, pakeke hoki!");

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
				circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10));

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
		title.setText(englishToMaoriTitles.get(titles.get(currentPage)));
		explanation.setText(textList.get(titles.get(currentPage)));
		screenImage.setImage(imageList.get(titles.get(currentPage)));
	}
}
