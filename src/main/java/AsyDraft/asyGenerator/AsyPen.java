package AsyDraft.asyGenerator;

public class AsyPen {
	/*
	 * colors
	 */
	private double red;
	private double green;
	private double blue;
	/*
	 * stroke distances
	 */
	private double strokelength;
	private double intervallength;
	
	public AsyPen(double r, double g, double b, double strokelength, double intervallength) {
		red = r;
		green = g;
		blue = b;
		this.strokelength = strokelength;
		this.intervallength = intervallength;
	}
	/*
	 * get colors
	 */
	public double getRed() {
		return red;
	}
	
	public double getGreen() {
		return green;
	}
	
	public double getBlue() {
		return blue;
	}
	/*
	 * get stroke distances
	 */
	public double getStrokeLength() {
		return strokelength;
	}
	
	public double getIntervalLength() {
		return intervallength;
	}
}
