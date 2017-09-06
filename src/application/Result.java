package application;

public class Result {
	
	public int _numberInt;
	public String _numberWord;
	public boolean _pass;
	
	/**
	 * Constructor to store state of number currently being tested.
	 * _numberWord stores maori word corresponding to integer number
	 * of _numberInt
	 * @param number: number being tested
	 */
	public Result(int number) {
		_numberInt = number;
		if (number == 1) {
			_numberWord = "tahi";			
		} else if (number == 2) {
			_numberWord = "rua";
		} else if (number == 3) {
			_numberWord = "toru";
		} else if (number == 4) {
			_numberWord = "wha";
		} else if (number == 5) {
			_numberWord = "rima";
		} else if (number == 6) {
			_numberWord = "ono";
		} else if (number == 7) {
			_numberWord = "whitu";
		} else if (number == 8) {
			_numberWord = "waru";
		} else if (number == 9) {
			_numberWord = "iwa";
		} else {
			_numberWord = "number is not between 1-9";
		}
	}

	/**
	 * Stores the result of the test that has been taken.
	 * @param pass
	 */
	public void initialiseResult(boolean pass) {
		_pass = pass;
	}
	
}
