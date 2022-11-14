package AsyDraft.asyObjects;

import AsyDraft.AsyProperties.AsyPen;

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
