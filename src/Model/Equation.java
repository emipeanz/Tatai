package Model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation extends Question {

	private String[] operators = {"-", "+", "*"};
	private String equationString;
	private String equationOperator;
	private int max;

	/**
	 * Generates an equation of a specified difficulty level
	 * @param difficulty
	 */
	public Equation(Difficulty difficulty) {
		super(difficulty);
		max = difficulty.getMax();
		System.out.println("max = " + (max));

		if (difficulty == difficulty.MEDIUM) {
			mediumEquation();
		} else if (difficulty == difficulty.HARD) {
			hardEquation();
		} else {
			easyEquation();
		}

		//stores answer as a string
		answerString = numberToWord(answerInt);
		//stores equation as a string to display - doesn't have * 
		displayString = equationString.replaceAll("\\*", "x");
	}

	public void easyEquation() {
		//choosing between all possible operators
		String operator = operators[randomNumber(2) - 1];
		int left, right;

		while (!((answerInt > 0) && (answerInt < 100))) {
			left = randomNumber(10);
			right = randomNumber(10);
			
			equationString = left + operator + right;
			System.out.println("equation = " + equationString);
			answerInt = evaluateEquation(equationString);
		}
	}


	public void mediumEquation() {
		//choosing between all possible operators
		String operator = operators[randomNumber(3) - 1];
		int left, right;
		int[] basicMultiples = {2,5,10};

		while (!((answerInt >= 10) && (answerInt < 100))) {
			if ((operator.equals("+") || (operator.equals("-")))) {
				left = randomNumber(9) * 5;
				right = randomNumber(9) * 5;
			} else { 
				left = basicMultiples[randomNumber(3) -1];
				right = randomNumber(10);
			}
			equationString = left + operator + right;
			System.out.println("equation = " + equationString);
			answerInt = evaluateEquation(equationString);
		}
	}

	public void hardEquation() {
		//choosing between all possible operators
		String operator = operators[randomNumber(3) - 1];
		int left, right;

		while (!((answerInt >= 10) && (answerInt < 100))) {
			if ((operator.equals("+") || (operator.equals("-")))) {
				left = randomNumber(85) + 15;
				right = randomNumber(85) + 15;
			} else { 
				left = randomNumber(12);
				right = randomNumber(6);
			}
			equationString = left + operator + right;
			System.out.println("equation = " + equationString);
			answerInt = evaluateEquation(equationString);
		}
	}

	public int evaluateEquation(String equation) {
		//evaluates equation
		int answer = 0;

		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			answer = (int) engine.eval(equationString);
			System.out.println("answer int = " + answerInt);

		} catch (ScriptException e) {
		}

		return answer;
	}


}
