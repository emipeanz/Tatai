package Model;

public enum Operator {
	ADD("+"), SUBTRACT("-"), MULTIPLY("*");
	
	public final String symbol;
	
	private Operator(String operator) {
		this.symbol = operator;
	}

	public static Operator choose() {
		int index = (int) Math.floor(Math.random() * (Operator.values().length));
		return Operator.values()[index];
	}
}
