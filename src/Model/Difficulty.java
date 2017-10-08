package Model;
/**
 * Two types of difficulty levels possible for the user to choose from.
 * @author Maddie Beagley and Emilie Pearce
 */
public enum Difficulty {
	EASY(9), HARD(99);

	//comment
	int max;
	
	private Difficulty(int max) {
		this.max = max;
	}
	
	public int getMax() {
		return this.max;
	}
}
