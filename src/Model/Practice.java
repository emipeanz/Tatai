package Model;

public class Practice extends Question{
	
	public Practice() {
		answerInt = randomNumber(1, 10);
		displayString = Integer.toString(answerInt);
		answerString = numberToWord(answerInt);
	}

}
