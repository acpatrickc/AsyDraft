package AsyDraft.asyObjects;

import AsyDraft.asyGenerator.AsyPen;

public class AsyMidArrow extends AsySegment {
	/*
	 * Instantiates and AsyMidArrow with start and end coordinates
	 * the arrow is rendered between the start and end position
	 */
	public AsyMidArrow(double x0, double y0, double x1, double y1, AsyPen p) {
		super(x0, y0, x1, y1, p);
	}
	/*
	 * identifies MidArrow to asymptote
	 */
	@Override
	public String[] getStringArgs() {
		return new String[] {"MidArrow"};
	}
}
