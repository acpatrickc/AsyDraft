package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPen;

public class AsyLabel implements AsyObject {
	/*
	 * coordinates
	 * direction
	 * LaTeX string
	 * pen of this object
	 */
	private double x;
	private double y;
	private Direction dir;
	private String tex;
	private AsyPen pen;
	public enum Direction {
		N,
		NE,
		E,
		SE,
		S,
		SW,
		W,
		NW,
		CENTER
	}
	/*
	 * instantiates an AsyLabel with coordinates and direction
	 */
	public AsyLabel(double x, double y, Direction d, String tex, AsyPen p) {
		this.x = x;
		this.y = y;
		dir = d;
		this.tex = tex;
		pen = p;
	}
	/*
	 * returns the pen of this object
	 */
	@Override
	public AsyPen getAsyPen() {
		return pen;
	}
}
