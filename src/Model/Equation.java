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
		max = difficulty.getMax();
		System.out.println("max = " + (max));
		
		while (!((answerInt>0) && (answerInt<max+1))) {
			if (difficulty == difficulty.HARD) {
				equationOperator = operators[randomNumber(3) - 1];
			} else {
				equationOperator = operators[randomNumber(2) - 1];
			}
			System.out.println("EQUAtION-----------------------------------------------------");
			System.out.println("operator = " + equationOperator);
			equationString = randomNumber(max) + equationOperator + randomNumber(max);
			System.out.println("equasion string = " + equationString);

			//evaluates equation
			try {
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				answerInt = (int) engine.eval(equationString);
				System.out.println("answer int = " + answerInt);

			} catch (ScriptException e) {
			}
		}

		//stores answer as a string
		answerString = numberToWord(answerInt);
		//stores equation as a string to display - doesn't have * 
		displayString = equationString.replaceAll("\\*", "x");
	}

}
