package application;

import java.io.File;

public class Result {

	public int _numberInt;
	public String _numberWord = "";
	public boolean _pass;
	public File _recording;

	/**
	 * Constructor to store state of number currently being tested.
	 * _numberWord stores maori word corresponding to integer number
	 * of _numberInt
	 * @param number: number being tested
	 */
	public Result(Difficulty difficulty) {
		int max = 0;

		//specifies maximum value of random number to be generated
		if (difficulty == difficulty.HARD) {
			max = 99;
		} else if (difficulty == difficulty.EASY) {
			max = 9;
		}

		//generates a random number within the desired range
		int number = (int )(Math.random() * max + 1);

		//stores value of random number as the maori word and as an integer.
		_numberInt = number;
		_numberWord = numberToWord(number);

	}

	/**
	 * Stores the result of the test that has been taken.
	 * @param pass
	 */
	public void setPass(boolean pass) {
		_pass = pass;
	}

	/**
	 * Stores a file to hold the recording associated to the the result.
	 * @param recording
	 */
	public void setRecording(File recording) {
		_recording = recording;
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