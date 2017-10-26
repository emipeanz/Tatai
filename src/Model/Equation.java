package Model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * This class handles everything to do with an equation. This includes storing the operators and operands
 * as well as answers.
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public class Equation extends Question {

	private Operator _operator;
	private int left = 0, right = 0, answer = 0;
	private Operator[] _operators = {Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY, Operator.DIVIDE};
	private int[] basicMultiples = {2,5,10};

	/**
	 * This equation constructor is only called for a practice test which will
	 * have a specified operator.
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

			answer = evaluateEquation(left+_operator.getSymbol()+right);
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
		answerInt = evaluateEquation(equation);
		answerString = numberToWord(answerInt);
		displayString = equation.replace("*", "x");
	}

	/**
	 * This method uses a JavaScript engine to evaluate an equation that is 
	 * a string. Used for custom equation lists only
	 * @param evaluateEquation
	 * @return answer to equation
	 */
	public int evaluateEquation(String evaluateEquation) {
		int answer = 0;
		String equation = evaluateEquation.replace("x", "*");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			answer = (int)engine.eval(equation);
		} catch (Exception e) {
			System.out.println("Invalid custom equation - exception thrown");
		}
		return answer;
	}
	/**
	 * Generates a "standard" test based on the difficulty level specified
	 * by the user. Will randomly generate numbers and operators for the equation
	 * which will be defined by the difficulty level.
	 * @param testType
	 */
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
			throw new IllegalArgumentException("You are in the wrong test constructor for TestType: " + testType);
		}
	}

	/**
	 * Method called to generate an easy equation. Numerical answers are
	 * only between 1 and 10 and only +, - and x are possible operators.
	 */
	private void easyEquation() {
		_operator = _operators[randomNumber(0,2)];

		while (answer <= 0 || answer > 10) {
			left = randomNumber(2,9);
			right = randomNumber(2,9);
			answer = evaluateEquation(left+_operator.getSymbol()+right);
		}
		generateEquation(left, right, answer);
	}

	/**
	 * Method called to generate a medium equation. Numerical answers are
	 * between 1 and 99 and all operators are possible. Division and 
	 * multiplication is only implemented for divisor and multiples of 2,5 and 10.
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
			answer = evaluateEquation(left+_operator.getSymbol()+right);
		} while (answer < 1 || answer > 99);

		generateEquation(left, right, answer);
	}

	/**
	 * Method called to generate a hard equation. Numerical answers are
	 * between 1 and 99 and all operators are possible. Multiplication is for
	 * all times tables up to 12. Division is again only for basic divisors
	 * like 2,5 and 10.
	 */
	private void hardEquation() {
		_operator = _operators[randomNumber(0,3)];
		do {
			if (_operator.equals(Operator.ADD)|| _operator.equals(Operator.SUBTRACT)) {
				left = randomNumber(15,99);
				right = randomNumber(15,99);
			} else if (_operator.equals(Operator.MULTIPLY)) { 
				left = randomNumber(1,12);
				right = randomNumber(1,6);
			} else {
				right = basicMultiples[randomNumber(0, basicMultiples.length - 1)];
				left = right * randomNumber(2,4);
			}
			answer = evaluateEquation(left+_operator.getSymbol()+right);
		} while (answer < 1 || answer > 100);

		generateEquation(left, right, answer);
	}

	/**
	 * Sets fields of the equation, including the string displayed on screen
	 * the integer answer and the word representing the number in Maori.
	 * @param left: integer on LHS of equation operator
	 * @param right: integer on RHS of equation operator
	 * @param answer: answer of equation
	 */
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
