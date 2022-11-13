package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyCircle;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AsyEditorCircle implements AsyEditorObject {
	/*
	 * center coordinates (on grid)
	 * snappoints, significant points where the editor can snap to
	 * radius of circle
	 */
	private double centerx;
	private double centery;
	private double radius;
	/*
	 * initiates an AsyEditorCircle
	 * center and end coordinates in array as {x, y}
	 */
	public AsyEditorCircle(double[] center, double[] end) {
		centerx = center[0];
		centery = center[1];
		radius = Math.hypot(centerx - end[0], centery - end[1]);
	}
	/*
	 * returns an AsyCircle with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyCircle(MathUtils.toCartesian(centerx, gridwidth), MathUtils.toCartesian(centery, gridheight), radius);
	}
	/*
	 * draws this circle on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.strokeOval((centerx - radius) * scale, (centery - radius) * scale, 2 * radius * scale, 2 * radius * scale);
	}
	/*
	 * returns snappable points on this line
	 */
	@Override
	public SnapPoint[] getSnapPoints() {
		return new SnapPoint[] {new SnapPoint(centerx, centery, "center")};
	}
	/*
	 * draws a preview shadow of the segment
	 * static
	 */
	public static void drawPreview(double scale, GraphicsContext gc, double[] center, double[] end, Color c) {
		gc.setLineWidth(3);
		gc.setStroke(c);
		double tempradius = Math.hypot(center[0] - end[0], center[1] - end[1]);
		gc.strokeOval((center[0] - tempradius) * scale, (center[1] - tempradius) * scale, 2 * tempradius * scale, 2 * tempradius * scale);
	}
}
