package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPen;

public class AsyEndArrow extends AsySegment {
	/*
	 * Instantiates and AsyArrow with start and end coordinates
	 * the arrow is rendered at the end position
	 */
	public AsyEndArrow(double x0, double y0, double x1, double y1, AsyPen p) {
		super(x0, y0, x1, y1, p);
		// TODO Auto-generated constructor stub
	}
	/*
	 * identifies EndArrow to asymptote
	 */
	@Override
	public String[] getStringArgs() {
		return new String[] {"EndArrow"};
	}
}
