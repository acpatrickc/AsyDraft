package AsyDraft.ui;

public class SnapPoint {
	/*
	 * x coordinate
	 * y coordinate
	 * description of point (eg. "midpoint", "endpoint", "center")
	 */
	private double x;
	private double y;
	private String description;
	/*
	 * A custom point object used for storing a point that is snappable to on the drawing plane
	 */
	public SnapPoint(double x, double y, String description) {
		this.x = x;
		this.y = y;
		this.description = description;
	}
	/*
	 * calculates the distance of a point to this SnapPoint
	 */
	public double distanceTo(double x, double y) {
		return Math.hypot(this.x - x, this.y - y);
	}
	/*
	 * returns description of point
	 */
	public String getDescription() {
		return description;
	}
}
