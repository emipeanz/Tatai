package Model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation extends Question {

	private String[] operators = {"-", "+", "*"};
	private String equationString;
	private String equationOperator;
	private int max;

	public Equation(Difficulty difficulty) {
		super(difficulty);

		if (difficulty == difficulty.HARD) {
			equationOperator = operators[randomNumber(3) - 1];
		} else {
			equationOperator = operators[randomNumber(2) - 1];
		}
		
		equationString = randomNumber(max) + equationOperator + randomNumber(max);

		//evaluates equation
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			answerInt = (int) engine.eval(equationString);
			
			while ((answerInt <= 0) && (answerInt >= max)){
				equationString = randomNumber(max) + equationOperator + randomNumber(max);
				answerInt = (int) engine.eval(equationString);
			}
		} catch (ScriptException e) {
		}

		//stores answer as a string
		answerString = numberToWord(answerInt);
		//stores equation as a string to display - doesn't have * 
		displayString = equationString.replaceAll("*", "x");
	}

}
