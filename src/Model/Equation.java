package Model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation extends Question {

	private String equationString;
	private Operator _operator;
	

	/**
	 * Generates an equation of a specified difficulty level
	 * @param difficulty
	 */
	public Equation(int left, Operator op, int right) {
		//stores answer as a string
		answerString = numberToWord(evaluateEquation(left, right, op));

		//stores equation as a string to display - doesn't have * 
		if (op == op.MULTIPLY) {
			displayString = Integer.toString(left) + " x " + Integer.toString(right); 
		} else {
			displayString = Integer.toString(left) + " " + op.symbol + " " + Integer.toString(right);
		}
	}
	
	/**
	 * This equation constructor is only called for a practice test with a
	 * specified operator.
	 * @param operator: operator for particular practice round
	 */
	public Equation(Operator operator) {
		System.out.println("generating equation");
		_operator = operator;
		int answer = -1;
		int left = 0, right = 0;
		
		while((answer < 1) || (answer > 100)) {
			left = randomNumber(1,99);
			right = randomNumber(1,99);
			answer = evaluateEquation(left,right, _operator);
			System.out.println(left + _operator.getSymbol() + right);
		}
		
		answerString = numberToWord(answer);
		String equation = left + _operator.getSymbol() + right;
		displayString = equation.replace("*", " x ");
	}

	/**
	 * This equation constructor is only called for a custom equation where
	 * the string for the equation is passed through to the constructor from 
	 * the file storing user input.
	 * @param equation
	 */
	public Equation(String equation) {
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			int answerInt = (int)engine.eval(equation);
			setAnswerInt(answerInt);
			
		} catch (Exception e) {
			System.out.println("Invalid custom equation - exception thrown");
		}
		answerString = numberToWord(answerInt);
		displayString = equation.replace("*", " x ");
	}

	static public Equation create(TestType testType) {
		switch(testType) {
		case EASY:
			return easyEquation();
		case MEDIUM:
			return mediumEquation();
		case HARD:
			return hardEquation();
		default:
			throw new IllegalArgumentException("Given difficulty " + testType + " is not a proper one");
		}
	}

	/**
	 * Method called to generate an easy equation.
	 * @return
	 */
	public static Equation easyEquation() {
		//choosing between all possible operators
		Operator operator = Operator.choose();
		int left = 0, right = 0;

		int answer = -1;
		while (answer <= 0 || answer > 10) {
			left = randomNumber(1,9);
			right = randomNumber(1,9);
			answer = evaluateEquation(left, right, operator);
		}

		Equation equation = new Equation(left, operator, right);
		equation.setAnswerInt(answer);
		return equation;
	}

	/**
	 * Method called to create a medium difficulty equation
	 * @return
	 */
	private static Equation mediumEquation() {
		//choosing between all possible operators
		Operator operator = Operator.choose();
		int left, right;
		int[] basicMultiples = {2,5,10};

		int answerInt = 101;
		do {
			if (operator == Operator.ADD || operator == Operator.SUBTRACT) {
				left = randomNumber(1,9) * 5;
				right = randomNumber(1,9) * 5;
			} else { 
				left = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				right = randomNumber(1,10);
			}
			answerInt = evaluateEquation(left, right, operator);
			System.out.println("supposefuly the answer is... " + answerInt);
		} while (answerInt < 10 || answerInt > 100);

		Equation equation = new Equation(left, operator, right);
		equation.setAnswerInt(answerInt);
		return equation;
	}

	/**
	 * Method called to create a hard difficulty equation.
	 * @return
	 */
	private static Equation hardEquation() {
		//choosing between all possible operators
		int left, right;
		Operator operator = Operator.choose();

		int answerInt = 0;
		do {
			if (operator == Operator.ADD || operator == Operator.SUBTRACT) {
				left = randomNumber(15,99);
				right = randomNumber(15,99);
			} else { 
				left = randomNumber(1,12);
				right = randomNumber(1,6);
			}
			answerInt = evaluateEquation(left, right, operator);
		} while (answerInt < 10 || answerInt > 100);

		Equation equation = new Equation(left, operator, right);
		equation.setAnswerInt(answerInt);
		return equation;
	}

	
	public static int evaluateEquation(int left, int right, Operator operator) {
		switch(operator) {
		case ADD:
			return left + right;
		case SUBTRACT:
			return left - right;
		case MULTIPLY:
			return left * right;
		case DIVIDE:
			return  left / right;
		default:
			throw new IllegalArgumentException("Invalid operator entered: " + operator);
		}
	}

}
