package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyMidArrow;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.MathUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AsyEditorMidArrow extends AsyEditorSegment {
	/*
	 * initiates an AsyEditorMidArrow and indentifies significant points that can be snapped onto
	 * start and end coordinates in array as {x, y}
	 */
	public AsyEditorMidArrow(double[] start, double[] end) {
		super(start, end);
	}
	/*
	 * returns an AsyMidArrow with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyMidArrow(MathUtils.toCartesian(startx, gridwidth), MathUtils.toCartesian(starty, gridheight), MathUtils.toCartesian(endx, gridwidth), MathUtils.toCartesian(endy, gridheight));
	}
	/*
	 * draws this arrow on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		super.render(scale, gc);
		gc.setFill(Color.BLACK);
		/*
		 * calculates angle of rotation with hypotenuse and opposite
		 * reflects rotation direction if endx < startx
		 */
		double rotatedegrees = (endx < startx ? 1 : -1) * (270 - 180 * Math.asin((endy - starty) / Math.hypot(endx - startx, endy - starty)) / Math.PI);
		gc.translate((startx + (endx - startx) / 2) * scale , (starty + (endy - starty) / 2) * scale);
		gc.rotate(rotatedegrees);
		gc.fillPolygon(new double[] {0, -3.5, 3.5}, new double[] {-5.5, 5.5, 5.5}, 3);
		/*
		 * resets rotation and translation
		 */
		gc.rotate(-rotatedegrees);
		gc.translate((startx + (endx - startx) / 2) * -scale , (starty + (endy - starty) / 2) * -scale);
		
	}
}
