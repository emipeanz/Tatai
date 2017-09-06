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
	 * Converts an integer to the maori word associated to that integer.
	 * @param number: number to be converted
	 * @return String associated to that number
	 */
	public String numberToWord(int number) {
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

	/**
	 * Stores the result of the test that has been taken.
	 * @param pass
	 */
	public void initialisePass(boolean pass) {
		_pass = pass;
	}

	public void initialiseRecording(File recording) {
		_recording = recording;
	}

}
