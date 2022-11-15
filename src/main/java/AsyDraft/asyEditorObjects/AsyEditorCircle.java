package AsyDraft.asyEditorObjects;

import AsyDraft.asyGenerator.AsyPen;
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
	 * the pen used to color this object
	 */
	private double centerx;
	private double centery;
	private double radius;
	private AsyPen pen;
	/*
	 * initiates an AsyEditorCircle
	 * center and end coordinates in array as {x, y}
	 */
	public AsyEditorCircle(double[] center, double[] end, AsyPen p) {
		centerx = center[0];
		centery = center[1];
		radius = Math.hypot(centerx - end[0], centery - end[1]);
		pen = p;
	}
	/*
	 * returns an AsyCircle with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyCircle(MathUtils.toCartesian(centerx, gridwidth), MathUtils.toCartesian(centery, gridheight), radius, pen);
	}
	/*
	 * draws this circle on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		gc.setStroke(new Color(pen.getRed(), pen.getGreen(), pen.getBlue(), 1));
		gc.setLineWidth(1);
		gc.setLineDashes(pen.getStrokeLength(), pen.getIntervalLength());
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
		gc.setLineDashes(0);
		gc.setLineWidth(3);
		gc.setStroke(c);
		double tempradius = Math.hypot(center[0] - end[0], center[1] - end[1]);
		gc.strokeOval((center[0] - tempradius) * scale, (center[1] - tempradius) * scale, 2 * tempradius * scale, 2 * tempradius * scale);
	}
}
