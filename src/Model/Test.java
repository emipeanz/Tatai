package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is the model for a test. A test consists of the difficulty that is being tested and an array 
 * of test questions, of type questions. The array's length is not fixed so that a long test can be added
 * in the future.
 * @author Maddie Beagley and Emilie Pearce
 */

import java.util.ArrayList;
import java.util.List;

public class Test {

	private ArrayList<Round> _testRounds = new ArrayList<Round>();
	private TestType _testType;
	private final int _numRounds = 10;
	private List<String> _customEquationList = new ArrayList<String>();
	private String listName;
	private Operator _operator;


	/**
	 * Stores the difficulty level of the particular test being 
	 * carried out and initializes an array list to store 
	 * the questions of that test.
	 * @param difficulty: difficulty level of the test (enumeration)
	 */
	public Test(TestType testType) {
		_testType = testType;
		if (testType.equals(TestType.CUSTOM)) {
			System.out.println("You are in the wrong constructor for custom!");
		} else {
			for (int i = 0; i < _numRounds; i++) {
				_testRounds.add(new Round(_testType));
			}
		}
	}
	/**
	 * Test constructor used only for a practice test.
	 * @param operator
	 */
	public Test(Operator operator) {
		_testType = TestType.PRACTICE;
		_operator = operator;
		_testRounds.add(new Round(_operator));
	}

	/**
	 * Constructor is used only for creating a custom test
	 * @param customListName
	 */
	public Test(String customListName) {
		_testType = TestType.CUSTOM;
		listName = customListName;
		String filename = ".CustomEquations/" + listName;
		try {
			_customEquationList = Files.readAllLines(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String equation : _customEquationList) {
			_testRounds.add(new Round(equation));
		}
	}

	public TestType getTestType() {
		return _testType;
	}

	/**
	 * Returns the questions of the test being carried out.
	 * @return: arraylist of test questions
	 */
	public Round getTestRound(int index) {
		return _testRounds.get(index);
	}

	/**
	 * Will return an integer value representing the number
	 * of questions answered correctly out of ten in the 
	 * current test.
	 * @return
	 */
	public int getOverallMark() {
		int overallMark = 0;
		for (Round round : _testRounds) {
			if (round.getPass()) {
				overallMark++;
			}
		}
		return overallMark;
	}
	
	/**
	 * Returns all the questions associated to the test as an array list.
	 * @return
	 */
	public ArrayList<Question> getTestQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		for(Round r : _testRounds) {
			questions.add(r.getQuestion());
		}
		return questions;
	}

	/**
	 * Should only be used to add rounds during a practice test, in all
	 * other cases rounds are all added in the initial instantiation of the test.
	 */
	public void addRound() {
		_testRounds.add(new Round(_operator));
	}
}
