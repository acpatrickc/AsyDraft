package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPair;
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
	 * returns no additional string args
	 */
	@Override
	public String[] getStringArgs() {
		return new String[] {};
	}
}
