package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPen;

public class AsyCircle implements AsyObject {
	/*
	 * center and radius
	 * pen of this object
	 */
	private double centerx;
	private double centery;
	private double radius;
	private AsyPen pen;
	/*
	 * instantiates an AsyCircle with start and end coordinates
	 */
	public AsyCircle(double x, double y, double r, AsyPen p) {
		centerx = x;
		centery = y;
		radius = r;
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
