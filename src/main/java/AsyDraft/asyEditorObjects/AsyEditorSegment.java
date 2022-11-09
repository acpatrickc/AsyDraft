package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyObject;
import AsyDraft.asyObjects.AsySegment;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AsyEditorSegment implements AsyEditorObject {
	/*
	 * start and end coordinates (on screen)
	 */
	private double startx;
	private double starty;
	private double endx;
	private double endy;
	private SnapPoint[] snappoints = new SnapPoint[3];
	/*
	 * initiates an AsyEditorSegment and indentifies significant points that can be snapped onto
	 */
	public AsyEditorSegment(double x0, double y0, double x1, double y1) {
		startx = x0;
		starty = y0;
		endx = x1;
		endy = y1;
		snappoints[0] = new SnapPoint(startx, starty, "endpoint");
		snappoints[1] = new SnapPoint(endx, endy, "endpoint");
		snappoints[2] = new SnapPoint((startx + endx) / 2, (starty + endy) / 2, "midpoint");
	}
	/*
	 * returns an AsySegment with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsySegment(MathUtils.toCartesian(startx, gridwidth), MathUtils.toCartesian(starty, gridheight), MathUtils.toCartesian(endx, gridwidth), MathUtils.toCartesian(endy, gridheight));
	}
	/*
	 * draws this segment on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.strokeLine(startx * scale, starty * scale, endx * scale, endy * scale);
	}
	/*
	 * returns snappable points on this line
	 */
	@Override
	public SnapPoint[] getSnapPoints() {
		return snappoints;
	}

}
