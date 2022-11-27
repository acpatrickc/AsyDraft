package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPen;

public class AsySegment implements AsyObject {
	/*
	 * start and end coordinates
	 * pen of this object
	 */
	protected double startx;
	protected double starty;
	protected double endx;
	protected double endy;
	protected AsyPen pen;
	/*
	 * instantiates an AsySegment with start and end coordinates and pen
	 */
	public AsySegment(double x0, double y0, double x1, double y1, AsyPen p) {
		startx = x0;
		starty = y0;
		endx = y1;
		endy = y1;
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
