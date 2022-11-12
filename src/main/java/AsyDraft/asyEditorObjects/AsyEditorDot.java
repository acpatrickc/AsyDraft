package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyDot;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.asyObjects.AsySegment;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AsyEditorDot implements AsyEditorObject {
	/*
	 * coordinates (on grid)
	 */
	protected double x;
	protected double y;
	/*
	 * initiates an AsyEditorDot
	 * position as array {x, y}
	 */
	public AsyEditorDot(double[] pos) {
		x = pos[0];
		y = pos[1];
	}
	/*
	 * returns an AsySegment with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyDot(MathUtils.toCartesian(x, gridwidth), MathUtils.toCartesian(y, gridheight));
	}
	/*
	 * draws this dot on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1);
		gc.fillOval(x * scale - 3, y * scale - 3, 6, 6);
	}
	/*
	 * returns this point
	 */
	@Override
	public SnapPoint[] getSnapPoints() {
		return new SnapPoint[] {new SnapPoint(x, y, "dot")};
	}

}
