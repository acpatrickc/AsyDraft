package AsyDraft.asyEditorObjects;

import AsyDraft.asyGenerator.AsyPen;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.asyObjects.AsySegment;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class AsyEditorSegment implements AsyEditorObject {
	/*
	 * start and end coordinates (on grid)
	 * snappoints, significant points where the editor can snap to
	 * the pen used to draw this object
	 */
	protected double startx;
	protected double starty;
	protected double endx;
	protected double endy;
	protected SnapPoint[] snappoints = new SnapPoint[3];
	protected AsyPen pen;
	/*
	 * initiates an AsyEditorSegment and indentifies significant points that can be snapped onto
	 * start and end coordinates in array as {x, y}
	 */
	public AsyEditorSegment(double[] start, double[] end, AsyPen p) {
		startx = start[0];
		starty = start[1];
		endx = end[0];
		endy = end[1];
		pen = p;
		snappoints[0] = new SnapPoint(startx, starty, "endpoint");
		snappoints[1] = new SnapPoint(endx, endy, "endpoint");
		snappoints[2] = new SnapPoint((startx + endx) / 2, (starty + endy) / 2, "midpoint");
	}
	/*
	 * returns an AsySegment with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsySegment(MathUtils.toCartesian(startx, gridwidth), MathUtils.toCartesian(starty, gridheight), MathUtils.toCartesian(endx, gridwidth), MathUtils.toCartesian(endy, gridheight), pen);
	}
	/*
	 * draws this segment on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		gc.setStroke(new Color(pen.getRed(), pen.getGreen(), pen.getBlue(), 1));
		gc.setLineWidth(1);
		gc.setLineDashes(pen.getStrokeLength(), pen.getIntervalLength());
		gc.strokeLine(startx * scale, starty * scale, endx * scale, endy * scale);
	}
	/*
	 * returns snappable points on this line
	 */
	@Override
	public SnapPoint[] getSnapPoints() {
		return snappoints;
	}
	/*
	 * draws a preview shadow of the segment
	 * static
	 */
	public static void drawPreview(double scale, GraphicsContext gc, double[] start, double[] end, Color c) {
		gc.setLineDashes(0);
		gc.setLineWidth(3);
		gc.setLineCap(StrokeLineCap.ROUND);
		gc.setStroke(c);
		gc.strokeLine(start[0] * scale, start[1] * scale, end[0] * scale, end[1] * scale);
	}
}
