package AsyDraft.asyGenerator;

public class AsyPair {
	/*
	 * x and y coordinates
	 */
	private double x;
	private double y;
	
	public AsyPair(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/*
	 * returns an AsyExpression equal to this pair in asymptote
	 */
	public AsyExpression getAsyExpression() {
		return new AsyExpression("", x + "," + y);
	}
}
