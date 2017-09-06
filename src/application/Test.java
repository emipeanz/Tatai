package application;

import java.util.ArrayList;

public class Test {
	
	public ArrayList<Result> _testResults;
	public Difficulty _difficulty;
	
	/**
	 * Stores the difficulty level of the particular test being 
	 * carried out and initialises an array list to store 
	 * the results of that test.
	 * @param difficulty: difficulty level of the test (enum)
	 */
	public Test(Difficulty difficulty) {
		_difficulty = difficulty;
		_testResults = new ArrayList<Result>();
	}
	
	/**
	 * Returns the results of the test being carried out.
	 * @return: arraylist of test results
	 */
	public ArrayList<Result> getTestResults() {
		return _testResults;
	}

	/**
	 * Adds a result to the test being carried out.
	 * @param result
	 */
	public void addTestResult(Result result) {
		_testResults.add(result);
	}
	
	/**
	 * Will return an integer value representing the number
	 * of questions answered correctly out of ten in the 
	 * current test.
	 * @return
	 */
	public int getOverallMark() {
		int overallMark = 0;
		//cycles through each result checking if it was a pass
		for (Result result : _testResults) {
			if (result._pass == true) {
				overallMark++;
			}
		}
		return overallMark;
	}

}
