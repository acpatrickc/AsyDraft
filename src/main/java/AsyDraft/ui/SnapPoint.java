package AsyDraft.ui;

public class SnapPoint {
	/*
	 * x coordinate
	 * y coordinate
	 * description of point (eg. "midpoint", "endpoint", "center")
	 * valid, if the point is a valid point
	 */
	private double x = Double.NaN;
	private double y = Double.NaN;
	private String description;
	private boolean valid = false;
	/*
	 * A custom point object used for storing a point that is snappable to on the drawing plane
	 */
	public SnapPoint(double x, double y, String description) {
		this.x = x;
		this.y = y;
		this.description = description;
		valid = true;
	}
	/*
	 * creates an invalid snap point
	 */
	public SnapPoint() {}
	/*
	 * sets the properties of this snap point
	 */
	public void setpoint(double x, double y, String description) {
		this.x = x;
		this.y = y;
		this.description = description;
		valid = true;
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
	/*
	 * returns x coordinate
	 */
	public double getX() {
		return x;
	}
	/*
	 * returns y coordinate
	 */
	public double getY() {
		return y;
	}
	/*
	 * returns validity of this point
	 */
	public boolean isValid() {
		return valid;
	}
}
