package Model;

public class Practice extends Question{
	
	public Practice(Difficulty difficulty) {
		super(difficulty);
		
		answerInt = randomNumber(difficulty.getMax());
		displayString = Integer.toString(answerInt);
		answerString = numberToWord(answerInt);
	}

}
