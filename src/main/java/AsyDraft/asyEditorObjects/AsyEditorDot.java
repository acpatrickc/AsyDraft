package AsyDraft.asyEditorObjects;

import AsyDraft.asyGenerator.AsyPen;
import AsyDraft.asyObjects.AsyDot;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AsyEditorDot implements AsyEditorObject {
	/*
	 * coordinates (on grid)
	 * the pen used to color this object
	 */
	private double x;
	private double y;
	private AsyPen pen;
	/*
	 * initiates an AsyEditorDot
	 * position as array {x, y}
	 */
	public AsyEditorDot(double[] pos, AsyPen p) {
		x = pos[0];
		y = pos[1];
		pen = p;
	}
	/*
	 * returns an AsySegment with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyDot(MathUtils.toCartesian(x, gridwidth), MathUtils.toCartesian(y, gridheight), pen);
	}
	/*
	 * draws this dot on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		gc.setFill(new Color(pen.getRed(), pen.getGreen(), pen.getBlue(), 1));
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
