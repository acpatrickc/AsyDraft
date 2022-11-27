package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPen;

public class AsyDot implements AsyObject {
	/*
	 * dot coordinates
	 * pen of this object
	 */
	private double x;
	private double y;
	private AsyPen pen;
	/*
	 * instantiates an AsyDot with coordinates
	 */
	public AsyDot(double x, double y, AsyPen p) {
		this.x = x;
		this.y = y;
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
