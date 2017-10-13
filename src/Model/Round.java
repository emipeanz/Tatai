package Model;

public class Round {

	private Question _question;
	private Boolean _pass;
	private Recording _recording = new Recording();
	private int _chances = 2;

	public Round(TestType testType) {
		_question = Equation.create(testType);
	}

	/**
	 * This constructor should only be used to create rounds for a 
	 * custom set of equations.
	 * @param equation
	 */
	public Round(String equation) {
		//GENERATE AN EQUATION FROM THE STRING THAT REPRESENTS IT
		//CUSTOM EQUATION
	}

	public void decreaseChances() {
		_chances--;
	}

	public int getChances() {
		return _chances;
	}

	public void setQuestion(Question question) {
		this._question = question;
	}

	public Question getQuestion() {
		return _question;
	}

	public void setRecording(Recording recording) {
		this._recording = recording;
	}

	public Recording getRecording() {
		return _recording;
	}

	public void setPass(Boolean pass) {
		this._pass = pass;
	}

	public Boolean getPass() {
		return _pass;
	}

}
