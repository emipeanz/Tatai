package Model;

/**
 * This class is the model for a question. A questions consists of the number being tested, as an int.
 * The number being tested as a strong. And a boolean to show if they passed the question, ie. got it
 * right.
 * @author Maddie Beagley and Emilie Pearce
 */
import Controller.*;
import View.*;
import java.util.Arrays;
import java.util.List;

public class Question {

	//integer value of the number that is the answer
	public Integer question;
	//maori word expecting to hear back
	public boolean pass;
	//String that is displayed onscreen during a level
	protected String displayString;
	//Answer as a maori word
	protected String answerString;
	//Answer as an integer
	protected int answerInt;
	protected String passString;
	
	/**
	 * Generates a random number between min and max (inclusive)
	 */
	public static int randomNumber(int min, int max) {
		return (int)(Math.random() * (max - min + 1)) + min;
	}

	/**
	 * Converts an integer to the maori word associated to that integer.
	 * @param number: number to be converted
	 * @return String associated to that number
	 */
	protected String numberToWord(int number) {
		String _numberWord = "";

		//if number is only a single digit
		if (number < 10) {
			_numberWord = digitToMaori(number);
		} else if (number == 10) {
			_numberWord = "tekau";
		//if number is between 10 and 19 does not have prefix tahi
		} else if ((number > 10) && (number < 20)) {
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
	
	public String getDisplayString() {
		return displayString;
	}
	
	public String getAnswerString() {
		return answerString;
	}
	
	public int getAnswerInt() {
		return answerInt;
	}
	
	public void setDisplayString(String s) {
		displayString = s;
	}
	
	public void setAnswerString(String s) {
		answerString = s;
	}
	
	public void setAnswerInt(int i) {
		answerInt = i;
	}
	
	public String getPassString() {
		return passString;
	}
	
	public void setPassString(boolean pass) {
		if(pass) {
			passString = "Wrong";
		}
		else {
			passString = "Right!";
		}
	}
	
}