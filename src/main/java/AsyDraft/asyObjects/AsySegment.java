package AsyDraft.asyObjects;

public class AsySegment implements AsyObject {
	/*
	 * start and end coordinates
	 */
	private double startx;
	private double starty;
	private double endx;
	private double endy;
	/*
	 * instantiates an AsySegment with start and end coordinates
	 */
	public AsySegment(double x0, double y0, double x1, double y1) {
		startx = x0;
		starty = y0;
		endx = y1;
		endy = y1;
	}
	
	@Override
	public String generateAsyString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
