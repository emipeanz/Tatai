package Model;

/**
 * This class is the model for a question. A questions consists of the number being tested, as an int.
 * The number being tested as a strong. And a boolean to show if they passed the question, ie. got it
 * right.
 * @author Maddie Beagley and Emilie Pearce
 */

public class Question {

	//integer value of the number that is the answer
	protected Integer question;
	//maori word expecting to hear back
	protected boolean pass;
	protected boolean skip;
	protected String passString;
	//String that is displayed onscreen during a level
	protected String displayString;
	//Answer as a maori word
	protected String answerString;
	//Answer as an integer
	protected int answerInt;
	
	/**
	 * Generates a random number between min and max (inclusive)
	 */
	public static int randomNumber(int min, int max) {
		return (int)(Math.random() * (max - min + 1)) + min;
	}

	/**
	 * Converts an integer to the Maori word associated to that integer.
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
	
	/**
	 * Gets display string
	 * @return
	 */
	public String getDisplayString() {
		return displayString;
	}
	
	/**
	 * Gets answer string
	 * @return
	 */
	public String getAnswerString() {
		return answerString;
	}
	
	/**
	 * Gets answer as integer
	 * @return
	 */
	public int getAnswerInt() {
		return answerInt;
	}
	
	/**
	 * Sets the display string
	 * @param s
	 */
	public void setDisplayString(String s) {
		displayString = s;
	}
	
	/**
	 * Sets the answer string
	 * @param s
	 */
	public void setAnswerString(String s) {
		answerString = s;
	}
	
	/**
	 * Sets answer as an integer
	 * @param i
	 */
	public void setAnswerInt(int i) {
		answerInt = i;
	}
	
	/**
	 * Sets the pass string that is used in displaying results
	 * @param b
	 */
	public void setPass(boolean b) {
		pass = b;
		if(b) {
			this.setPassString("Correct!");
		}
		else {
			this.setPassString("Wrong");
		}
	}
	
	/**
	 * Gets pass for question
	 * @return
	 */
	public boolean getPass() {
		return pass;
	}
	
	/**
	 * Sets skip as a string value, only used in displaying results
	 */
	public void setSkip() {
		skip = true;
		this.setPassString("Skipped");
	}
	
	/**
	 * Gets skip for question
	 * @return
	 */
	public boolean getSkip() {
		return skip;
	}
	
	/**
	 * Sets the pass string for question, only used in disiplaying results
	 * @param s
	 */
	public void setPassString(String s) {
		passString = s;
	}
	
	/**
	 * Gets the pass string for question
	 * @return
	 */
	public String getPassString()
	{
		return passString;
	}
}