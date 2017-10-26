package Model;

/**
 * This enum is the type of operator an equation question can use
 * @author Emilie Pearce and Maddie Beagley
 *
 */
public enum Operator {
	ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");
	
	public final String symbol;
	
	/**
	 * Constructor for an operator
	 * @param operator
	 */
	private Operator(String operator) {
		this.symbol = operator;
	}
	
	/**
	 * Gets corresponding symbol associated with the enum
	 * @return
	 */
	public String getSymbol() {
		return this.symbol;
	}

	/**
	 * Chooses a random operator
	 * @return
	 */
	public static Operator choose() {
		int index = (int) Math.floor(Math.random() * (Operator.values().length));
		return Operator.values()[index];
	}
}
