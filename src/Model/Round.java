package Model;
/**
 * An instantiation of Round stores all the information associated to
 * one round of a test. It will store the information about the question
 * being asked, whether or not the user answered correctly and the recording
 * information for play back and taking a recording.
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public class Round {

	private Question _question;
	private Boolean _pass = false;
	private Boolean _skip = false;
	private Recording _recording = new Recording();
	private int _chances = 2;

	/**
	 * This constructor is called to instantiate a "standard" test so
	 * the equation generated will be based on the difficulty level
	 * of the test. 
	 * @param testType
	 */
	public Round(TestType testType) {
		_question = new Equation(testType);
	}
	
	/**
	 * This constructor is called to instantiate a test during a practice
	 * round so will be based on the operator the user has chosen.
	 * @param operator
	 */
	public Round(Operator operator) {
		_question = new Equation(operator);
	}

	/**
	 * This constructor should only be used to create rounds for a 
	 * custom set of equations. Equation will be generated from the 
	 * string storing the equation on file.
	 * @param equation
	 */
	public Round(String equation) {
		_question = new Equation(equation);
	}

	/**
	 * User is only allowed two chances to correctly answer a question,
	 * their number of chances decreases each attempt.
	 */
	public void decreaseChances() {
		_chances--;
	}

	/**
	 * Returns number of chances the user has remaining in the particular round.
	 * @return chances left
	 */
	public int getChances() {
		return _chances;
	}

	/**
	 * Returns the question associated to the particular round.
	 * @return the rounds question
	 */
	public Question getQuestion() {
		return _question;
	}
	
	/**
	 * 4 second long recording is taken from user.
	 */
	public void takeRecording() {
		_recording.takeRecording();
	}

	/**
	 * Recording is returned.
	 * @return the rounds recording
	 */
	public Recording getRecording() {
		return _recording;
	}

	/** 
	 * Sets whether the user passed the round
	 * @param pass
	 */
	public void setPass(Boolean pass) {
		this._pass = pass;
		_question.setPass(pass);
	}

	/**
	 * Returns whether the user passed the round.
	 * @return the rounds pass value
	 */
	public Boolean getPass() {
		return _pass;
	}
	
	/**
	 * Is set only when user has chosen to skip this round
	 */
	public void setSkip() {
		this._skip = true;
		_question.setSkip();
	}
	
	/**
	 * Get's skip for the round
	 * @return boolean if the round has been skipped
	 */
	public Boolean getSkip() {
		return this._skip;
	}
	

}
