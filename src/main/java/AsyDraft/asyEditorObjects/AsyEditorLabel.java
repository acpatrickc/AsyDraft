package AsyDraft.asyEditorObjects;

import java.awt.image.BufferedImage;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import AsyDraft.asyObjects.AsyDot;
import AsyDraft.asyObjects.AsyLabel;
import AsyDraft.asyObjects.AsyLabel.Direction;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.asyObjects.AsySegment;
import AsyDraft.ui.Init;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AsyEditorLabel implements AsyEditorObject {
	/*
	 * coordinates (on grid)
	 * direction of label
	 * LaTeX string
	 * LaTex image
	 */
	private double x;
	private double y;
	private Direction dir;
	private String tex;
	private WritableImage img;
	/*
	 * initiates an AsyEditorLabel
	 * position as array {x, y}
	 */
	public AsyEditorLabel(double[] pos, double[] dir, double scale, String tex) {
		x = pos[0];
		y = pos[1];
		this.tex = tex;
		if (Math.hypot(pos[0] - dir[0], pos[1] - dir[1]) * scale < 10) {
			this.dir = Direction.CENTER;
		} else {
			/*
			 * dividend = divisor * quotient + remainder
			 * quotient = (dividend - remainder) / divisor
			 */
			double degrees = (dir[0] < x ? 1 : -1) * (270 - 180 * Math.asin((dir[1] - y) / Math.hypot(dir[0] - x, dir[1] - y)) / Math.PI);
			double dividend = (((degrees + 22.5) % 360 + 360) % 360);
			double divisor = 45;
			double remainder = dividend % 45;
			int quotient = (int) ((dividend - remainder) / divisor);
			this.dir = (new Direction[] {Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SW, Direction.W, Direction.NW})[quotient];		
		}
		/*
		 * generates image of this label
		 */
		TeXIcon icon; 
		try {
			icon = new TeXFormula(tex).createTeXIcon(TeXConstants.STYLE_TEXT, 12);
		} catch (ParseException e) {
			icon = new TeXFormula("ERROR").createTeXIcon(TeXConstants.STYLE_TEXT, 12);
		}
		int margin = 3;
		BufferedImage tempimg = new BufferedImage(icon.getIconWidth() + 2 * margin, icon.getIconHeight() + 2 * margin, BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(null, tempimg.getGraphics(), margin, margin);
		img = SwingFXUtils.toFXImage(tempimg, img);
	}
	/*
	 * returns an AsyLabel with normal cartesian coordinates
	 */
	@Override
	public AsyObject getAsyObject(int gridwidth, int gridheight) {
		return new AsyLabel(MathUtils.toCartesian(x, gridwidth), MathUtils.toCartesian(y, gridheight), dir, tex);
	}
	/*
	 * draws this label on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		double dx = 0;
		double dy = 0;
		double w = img.getWidth();
		double h = img.getHeight();
		switch (dir) {
		case CENTER:
			dx = w / -2;
			dy = h / -2;
			break;
		case E:
			dx = 2;
			dy = h / -2;
			break;
		case N:
			dx = w / -2;
			dy = h * -1 - 2;
			break;
		case NE:
			dy = h * -1;
			break;
		case NW:
			dx = w * -1;
			dy = h * -1;
			break;
		case S:
			dx = w / -2;
			dy = 2;
			break;
		case SE:
			break;
		case SW:
			dx = w * -1;
			break;
		case W:
			dx = w * -1 - 2;
			dy = h / -2;
			break;
	}
		gc.drawImage(img, x * scale + dx, y * scale + dy);
	}
	/*
	 * returns this point
	 */
	@Override
	public SnapPoint[] getSnapPoints() {
		return new SnapPoint[] {new SnapPoint(x, y, "label")};
	}
}
