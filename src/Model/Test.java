package Model;

/**
 * This class is the model for a test. A test consists of the difficulty that is being tested and an array 
 * of test questions, of type questions. The array's length is not fixed so that a long test can be added
 * in the future.
 * @author Maddie Beagley and Emilie Pearce
 */

import java.util.ArrayList;

import javafx.util.Callback;

public class Test {

	private ArrayList<Round> _testRounds = new ArrayList<Round>();
	private TestType _testType;
	private final int _numRounds = 10;

	/**
	 * Stores the difficulty level of the particular test being 
	 * carried out and initialises an array list to store 
	 * the questions of that test.
	 * @param difficulty: difficulty level of the test (enum)
	 */
	public Test(TestType testType) {
		_testType = testType;
		if (_testType == TestType.CUSTOM) {
			for (int i = 0; i < _numRounds; i++) {
				_testRounds.add(new Round("hello"));
			}
		} else {
			for (int i = 0; i < _numRounds; i++) {
				_testRounds.add(new Round(_testType));
			}
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
		System.out.println("testRounds.get(index) index = " + index);
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
		//cycles through each question checking if it was a pass
		for (Round round : _testRounds) {
			if (round.getPass()) {
				overallMark++;
			}
		}
		return overallMark;
	}
	
	public ArrayList<Question> getTestQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		for(Round r : _testRounds) {
			questions.add(r.getQuestion());
		}
		
		return questions;
	}
	

}
