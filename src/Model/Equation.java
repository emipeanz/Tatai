package Model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation extends Question {

	private String equationString;
	private Operator _operator;
	private int left = 0, right = 0, answer = 0;
	private Operator[] _operators = {Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY, Operator.DIVIDE};
	private int[] basicMultiples = {2,5,10};

	/**
	 * This equation constructor is only called for a practice test with a
	 * specified operator.
	 * @param operator: operator for particular practice round
	 */
	public Equation(Operator operator) {
		_operator = operator;

		do {
			if (_operator.equals(Operator.ADD) || _operator.equals(Operator.SUBTRACT)) {
				left = randomNumber(1,9) * 5;
				right = randomNumber(1,9) * 5;
			} else if (_operator.equals(Operator.MULTIPLY)){ 
				left = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				right = randomNumber(1,10);
			} else {
				right = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				left = right * randomNumber(2,4);
			}

			answer = evaluateEquation(left, right);
			System.out.println(left + _operator.getSymbol() + right + "=" + answer);
		} while (answer < 2 || answer > 99);

		generateEquation(left, right, answer);
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

	public Equation (TestType testType) {
		switch(testType) {
		case EASY:
			easyEquation();
			break;
		case MEDIUM:
			mediumEquation();
			break;
		case HARD:
			hardEquation();
			break;
		default:
			throw new IllegalArgumentException("Given difficulty " + testType + " is not a proper one");
		}
	}

	/**
	 * Method called to generate an easy equation.
	 * @return
	 */
	private void easyEquation() {
		System.out.println("generating an easy equation");
		//choosing between all possible operators
		_operator = _operators[randomNumber(0,2)];

		while (answer <= 0 || answer > 10) {
			left = randomNumber(1,9);
			right = randomNumber(1,9);
			answer = evaluateEquation(left, right);
		}
		generateEquation(left, right, answer);
	}

	/**
	 * Method called to create a medium difficulty equation
	 * @return
	 */
	private void mediumEquation() {
		//choosing between all possible operators
		_operator = _operators[randomNumber(0,3)];

		do {
			if (_operator.equals(Operator.ADD) || _operator.equals(Operator.SUBTRACT)) {
				left = randomNumber(1,9) * 5;
				right = randomNumber(1,9) * 5;
			} else if (_operator.equals(Operator.MULTIPLY)){ 
				left = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				right = randomNumber(1,10);
			} else {
				right = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				left = right * randomNumber(2,4);
			}

			answer = evaluateEquation(left, right);
			System.out.println(left + _operator.getSymbol() + right + "=" + answer);
		} while (answer < 1 || answer > 99);
		
		generateEquation(left, right, answer);
	}

	/**
	 * Method called to create a hard difficulty equation.
	 * @return
	 */
	private void hardEquation() {
		Operator operator = _operators[randomNumber(0,3)];
		
		do {
			if (operator.equals(Operator.ADD)|| operator.equals(Operator.SUBTRACT)) {
				left = randomNumber(15,99);
				right = randomNumber(15,99);
			} else if (operator.equals(Operator.MULTIPLY)) { 
				left = randomNumber(1,12);
				right = randomNumber(1,6);
			} else {
				right = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				left = right * randomNumber(2,4);
			}
			answer = evaluateEquation(left, right);
		} while (answer < 1 || answer > 100);

		generateEquation(left, right, answer);
	}


	private int evaluateEquation(int left, int right) {
		switch(_operator) {
		case ADD:
			return left + right;
		case SUBTRACT:
			return left - right;
		case MULTIPLY:
			return left * right;
		case DIVIDE:
			return  left / right;
		default:
			throw new IllegalArgumentException("Invalid operator entered: " + _operator);
		}
	}

	public void generateEquation(int left, int right, int answer) {
		answerInt = answer;
		String equation = left + " " + _operator.getSymbol() + " " + right;
		answerInt = answer;
		answerString = numberToWord(answerInt);
		displayString = equation;
		
		if (_operator.equals(Operator.MULTIPLY)) {
			displayString = equation.replace("*", "x");
		} else if (_operator.equals(Operator.DIVIDE)) {
			displayString = equation.replace("/", "\u00F7" );
		}
	}

}
