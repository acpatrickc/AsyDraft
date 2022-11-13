package AsyDraft.asyObjects;

public class AsyCircle implements AsyObject {
	/*
	 * center and radius
	 */
	private double centerx;
	private double centery;
	private double radius;
	/*
	 * instantiates an AsyCircle with start and end coordinates
	 */
	public AsyCircle(double x, double y, double r) {
		centerx = x;
		centery = y;
		radius = r;
	}
	
	@Override
	public String generateAsyString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
