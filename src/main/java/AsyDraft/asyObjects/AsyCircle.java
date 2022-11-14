package AsyDraft.asyObjects;

import AsyDraft.AsyProperties.AsyPen;

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
	
	@Override
	public String generateAsyString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public AsyPen getAsyPen() {
		return pen;
	}
}
