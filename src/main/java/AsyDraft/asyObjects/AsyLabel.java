package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPair;
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
	 * returns pairs that identify this object
	 */
	@Override
	public AsyPair[] getAsyPairs() {
		return new AsyPair[] {new AsyPair(x, y)};
	}
	/*
	 * returns the pen of this object
	 */
	@Override
	public AsyPen getAsyPen() {
		return pen;
	}
	/*
	 * returns the tex data of this object as a formatted argument
	 */
	@Override
	public String[] getStringArgs() {
		return new String[] {"$" + tex + "$"};
	}
}
