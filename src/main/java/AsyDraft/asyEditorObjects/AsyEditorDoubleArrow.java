package AsyDraft.asyEditorObjects;

import AsyDraft.AsyProperties.AsyPen;
import AsyDraft.asyObjects.AsyDoubleArrow;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AsyEditorDoubleArrow extends AsyEditorSegment {
	/*
	 * initiates an AsyEditorDoubleArrow and indentifies significant points that can be snapped onto
	 * start and end coordinates in array as {x, y}
	 */
	public AsyEditorDoubleArrow(double[] start, double[] end, AsyPen p) {
		super(start, end, p);
	}
	/*
	 * returns an AsyDoubleArrow with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyDoubleArrow(MathUtils.toCartesian(startx, gridwidth), MathUtils.toCartesian(starty, gridheight), MathUtils.toCartesian(endx, gridwidth), MathUtils.toCartesian(endy, gridheight), pen);
	}
	/*
	 * draws this arrow on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		super.render(scale, gc);
		gc.setFill(new Color(pen.getRed(), pen.getGreen(), pen.getBlue(), 1));
		/*
		 * calculates angle of rotation with hypotenuse and opposite
		 * reflects rotation direction if endx < startx
		 */
		double rotatedegrees = (endx < startx ? 1 : -1) * (90 - 180 * Math.asin((endy - starty) / Math.hypot(endx - startx, endy - starty)) / Math.PI);
		gc.translate(startx * scale, starty * scale);
		gc.rotate(rotatedegrees);
		gc.fillPolygon(new double[] {0, -3.5, 3.5}, new double[] {0, 11, 11}, 3);
		gc.rotate(-rotatedegrees);
		gc.translate((endx - startx) * scale, (endy - starty) * scale);
		gc.rotate(rotatedegrees + 180);
		gc.fillPolygon(new double[] {0, -3.5, 3.5}, new double[] {0, 11, 11}, 3);
		/*
		 * resets rotation and translation
		 */
		gc.rotate(-180 -rotatedegrees);
		gc.translate(-endx * scale, -endy * scale);
		
	}
}
