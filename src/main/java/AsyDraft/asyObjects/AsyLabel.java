package AsyDraft.asyObjects;

public class AsyLabel implements AsyObject {
	/*
	 * coordinates
	 * direction
	 * LaTeX string
	 */
	private double x;
	private double y;
	private Direction dir;
	private String tex;
	public enum Direction {
		N,
		NE,
		E,
		SE,
		S,
		SW,
		W,
		NW,
		CENTER
	}
	/*
	 * instantiates an AsyLabel with coordinates and direction
	 */
	public AsyLabel(double x, double y, Direction d, String tex) {
		this.x = x;
		this.y = y;
		dir = d;
		this.tex = tex;
	}
	
	@Override
	public String generateAsyString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
