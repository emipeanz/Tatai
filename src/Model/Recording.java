package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class handles everything to do with recording. That included storing the 
 * recoding and its location, as well as recording and playing it
 * @author se206
 *
 */
public class Recording {
	
	private MediaPlayer _player;
	private final String RECORDINGFILEPATH = "RecordingDir/foo.wav";

	/**
	 * Uses a bash command to take a new recording. This functionality will be run in a 
	 * background thread. Buttons (except return to main menu) will be disabled during the
	 * recording process and re-enabled after. A new media player storing the current 
	 * recording will be instantiated once this recording has been taken.
	 * @param e
	 */
	public void takeRecording() {
		String cmd = "arecord -d 4 -r 22050 -c 1 -i -t wav -f s16_LE  " + RECORDINGFILEPATH;
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			pb.start().waitFor();
		} catch (InterruptedException ignored) { // if process is prematurely terminated
		} catch (IOException ioEvent) { //if process is incorrect (likely programmer error)
			throw new RuntimeException("Programmer messed up command...");
		}
	}

	/**
	 * Gets media player for current recording object
	 * @return the player object
	 */
	public MediaPlayer getMediaPlayer() {
		return _player;
	}

	/**
	 * Creates a new media player which loads in the current media. player.setOnEndOfMedia(...)
	 * creates a runnable that should be executed each time player.onEndOfMediaProperty() method 
	 * called. Will be instantiated each time a new recording is taken.
	 * @return
	 */
	public void newMediaPlayer(Button a, Button b, Button c, Button d) {
		
		Media media = new Media(Paths.get(RECORDINGFILEPATH).toUri().toString());
		//generates a media player to play audio
		MediaPlayer player = new MediaPlayer(media);
		//sets a runnable that will be called when player.onEndOfMediaProperty() called
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				//ensures media can be replayed.
				_player.stop();
				//re-enables buttons for use
				a.setDisable(false);
				b.setDisable(false);
				c.setDisable(false);
				d.setDisable(false);
			}
		});
		_player = player;
	}

	/**
	 * Method checks if the recording that is currently in the recording directory is the
	 * word that is the current test that it is on.  Uses back commands to run the wav file
	 * through HTK and reads from the recout.lmf file to see if all parts of the word have
	 * been picked up in the analysis
	 * @return boolean if the recording is the correct number of now=t
	 */
	public boolean checkRecordingForWord(String numberWord) {
		ArrayList<String> output = new ArrayList<String>();
		String cmd = "HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  "
				+ "-w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  "
				+ "user/dictionaryD user/tiedList " + RECORDINGFILEPATH;
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			Process process = processBuilder.start();
			process.waitFor();
			BufferedReader br = new BufferedReader(new FileReader("recout.mlf"));
			String line = null;
			while((line = br.readLine()) != null) {
				if((!(line.contains("#!MLF!#"))) && (!(line.contains("\"*/foo.rec\""))) && (!(line.contains("."))) && (!(line.contains("sil")))) {
					String newLine = line.replaceAll("aa", "ā");
					output.add(newLine);
				}
			}
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		// Splits word up into corresponding parts
		String[] split = numberWord.split("\\s+");
		
		// If the recorded word in in the list of correct word parts they got it right
		for(String s : split) {
			if(!(output.contains(s))) {
				return false;
			}
		}
		return true;
	}
}
