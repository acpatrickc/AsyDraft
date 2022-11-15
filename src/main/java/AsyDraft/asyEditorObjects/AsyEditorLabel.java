package AsyDraft.asyEditorObjects;

import java.awt.image.BufferedImage;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import AsyDraft.asyGenerator.AsyPen;
import AsyDraft.asyObjects.AsyLabel;
import AsyDraft.asyObjects.AsyLabel.Direction;
import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.MathUtils;
import AsyDraft.ui.SnapPoint;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class AsyEditorLabel implements AsyEditorObject {
	/*
	 * coordinates (on grid)
	 * direction of label
	 * LaTeX string
	 * LaTex image
	 * the pen used in drawing this object
	 */
	private double x;
	private double y;
	private Direction dir;
	private String tex;
	private WritableImage img;
	private AsyPen pen;
	/*
	 * initiates an AsyEditorLabel
	 * position as array {x, y}
	 */
	public AsyEditorLabel(double[] pos, double[] dir, double scale, String tex, AsyPen p) {
		x = pos[0];
		y = pos[1];
		this.tex = tex;
		this.dir = getDirection(pos, dir, scale);
		this.pen = p;
		/*
		 * generates image of this label
		 */
		TeXIcon icon; 
		try {
			icon = new TeXFormula(tex).createTeXIcon(TeXConstants.STYLE_TEXT, 12);
		} catch (ParseException e) {
			icon = new TeXFormula("ERROR").createTeXIcon(TeXConstants.STYLE_TEXT, 12);
		}
		icon.setForeground(new java.awt.Color((float) pen.getRed(), (float) pen.getGreen(), (float) pen.getBlue() ,1));
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
		return new AsyLabel(MathUtils.toCartesian(x, gridwidth), MathUtils.toCartesian(y, gridheight), dir, tex, pen);
	}
	/*
	 * draws this label on the drawing plane
	 */
	@Override
	public void render(double scale, GraphicsContext gc) {
		double[] dxdy = getChangeDirection(img.getWidth(), img.getHeight(), dir);
		gc.drawImage(img, x * scale + dxdy[0], y * scale + dxdy[1]);
	}
	/*
	 * calculates the change in x and y needed for the given direction
	 * {dx, dy}
	 */
	private static double[] getChangeDirection(double w, double h, Direction dir) {
		double dx = 0;
		double dy = 0;
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
		return new double[] {dx, dy};
	}
	/*
	 * returns this point
	 */
	@Override
	public SnapPoint[] getSnapPoints() {
		return new SnapPoint[] {new SnapPoint(x, y, "label")};
	}
	/*
	 * calculates direction
	 */
	private static Direction getDirection(double[] pos, double[] dir, double scale) {
		Direction direction;
		if (Math.hypot(pos[0] - dir[0], pos[1] - dir[1]) * scale < 10) {
			direction = Direction.CENTER;
		} else {
			/*
			 * dividend = divisor * quotient + remainder
			 * quotient = (dividend - remainder) / divisor
			 */
			double degrees = (dir[0] < pos[0] ? 1 : -1) * (270 - 180 * Math.asin((dir[1] - pos[1]) / Math.hypot(dir[0] - pos[0], dir[1] - pos[1])) / Math.PI);
			double dividend = (((degrees + 22.5) % 360 + 360) % 360);
			double divisor = 45;
			double remainder = dividend % 45;
			int quotient = (int) ((dividend - remainder) / divisor);
			direction = (new Direction[] {Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SW, Direction.W, Direction.NW})[quotient];		
		}
		return direction;
	}
	/*
	 * paints a preview version of this label
	 */
	public static void drawPreview(double[] pos, double[] dir, double scale, String tex, GraphicsContext gc, Color c) {
		TeXIcon icon; 
		try {
			icon = new TeXFormula(tex).createTeXIcon(TeXConstants.STYLE_TEXT, 12);
		} catch (ParseException e) {
			icon = new TeXFormula("ERROR").createTeXIcon(TeXConstants.STYLE_TEXT, 12);
		}
		icon.setForeground(new java.awt.Color((float) c.getRed(), (float) c.getGreen(), (float) c.getBlue() ,(float) c.getOpacity()));
		int margin = 3;
		BufferedImage tempimg = new BufferedImage(icon.getIconWidth() + 2 * margin, icon.getIconHeight() + 2 * margin, BufferedImage.TYPE_INT_ARGB);
		gc.setFill(c);
		gc.fillOval(pos[0] * scale - 3, pos[1] * scale - 3, 6, 6);
		icon.paintIcon(null, tempimg.getGraphics(), margin, margin);
		WritableImage img = SwingFXUtils.toFXImage(tempimg, null);
		double[] dxdy = getChangeDirection(img.getWidth(), img.getHeight(), getDirection(pos, dir, scale));
		gc.drawImage(img, pos[0] * scale + dxdy[0], pos[1] * scale + dxdy[1]);
	}
}
