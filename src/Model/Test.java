package Model;

/**
 * This class is the model for a test. A test consists of the difficulty that is being tested and an array 
 * of test questions, of type questions. The array's length is not fixed so that a long test can be added
 * in the future.
 * @author Maddie Beagley and Emilie Pearce
 */
import Controller.*;
import View.*;
import java.util.ArrayList;
import java.util.Formatter;

public class Test {
	
	private ArrayList<Question> _testQuestions;
	private Difficulty _difficulty;
	
	/**
	 * Stores the difficulty level of the particular test being 
	 * carried out and initialises an array list to store 
	 * the questions of that test.
	 * @param difficulty: difficulty level of the test (enum)
	 */
	public Test(Difficulty difficulty) {
		_difficulty = difficulty;
		_testQuestions = new ArrayList<Question>();
	}
	
	public Difficulty getdifficulty() {
		return _difficulty;
	}
	
	/**
	 * Returns the questions of the test being carried out.
	 * @return: arraylist of test questions
	 */
	public ArrayList<Question> getTestquestions() {
		return _testQuestions;
	}

	/**
	 * Adds a question to the test being carried out.
	 * @param question
	 */
	public void addTestQuestion(Question question) {
		_testQuestions.add(question);
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
		for (Question question : _testQuestions) {
			if (question._pass == true) {
				overallMark++;
			}
		}
		return overallMark;
	}

	/**
	 * Returns the number of questions stored in the test model, 
	 * this will determine how many rounds of the test have been
	 * carried out by the user.
	 * @return
	 */
	public int getNumberofRound() {
		return _testQuestions.size();
	}

	public ArrayList<String> getQuestionsToString() {
		ArrayList<String> data = new ArrayList<String>();
		for(int i = 0 ; i < _testQuestions.size();i++) {
			Question current = _testQuestions.get(i);
			String pass = current.getPass();
			String numWord = current.getNumberWord();
			int numInt = current.getNumberInt();
			String output = String.format("%-2d  %-20s  %10s", numInt, numWord, pass);
			data.add(output);
		}
		System.out.println("Words and their formatting");
		for(int i =0;i<data.size();i++) {
			System.out.println(data.get(i));
		}
		return data;
	}

}
