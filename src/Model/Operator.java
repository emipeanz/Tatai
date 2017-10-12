package Model;

public enum Operator {
	ADD("+"), SUBTRACT("-"), MULTIPLY("*");
	
	public final String operator;
	
	private Operator(String operator) {
		this.operator = operator;
	}

}
