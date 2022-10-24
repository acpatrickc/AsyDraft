package AsyDraft.ui;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class EditorPane extends Pane {
	/*
	 * style of the display grid
	 */
	public enum Style {
		pegboard,
		grid,
		blank
	}
	private Style style = Style.pegboard;
	/*
	 * width, height dimensions of grid
	 * scale, length of space between gridlines
	 */
	private int width = 10;
	private int height = 10;
	private double scale = 50;
	/*
	 * minimum and maximum scales
	 */
	public final double minscale = 20;
	public final double maxscale = 500;
	/*
	 * margin, distance between first gridline and edge of drawing plane
	 * shiftx, shifty, distance between (0,0) on screen and corner of drawing plane
	 */
	private int margin = 10;
	private double shiftx = 10;
	private double shifty = 10;
	/*
	 * dragx, dragy, used to store the drag location of the drawing plane
	 */
	private double dragx = 0;
	private double dragy = 0;
	/*
	 * gridmousex, gridmousey, location of mouse on drawing plane relative to the grid
	 * (0,0) on top left
	 * mousevalid, if the mouse is able to create a point on the drawing plane
	 */
	private double gridmousex;
	private double gridmousey;
	private boolean mousevalid = false;
	/*
	 * canvas, the JavaFX object that the editor is drawn onto
	 */
	private Canvas canvas = new Canvas();
	
	public EditorPane(int w, int h, int s) {
		width = w;
		height = h;
		scale = s;
		/*
		 * adds canvas as child
		 * resizes canvas along with pane
		 */
		getChildren().add(canvas);
		widthProperty().addListener(e -> {canvas.setWidth(getWidth());});
		heightProperty().addListener(e -> {canvas.setHeight(getHeight());});
		/*
		 * sets initial width and height of canvas so as it renders initially, the drawing plane is not misplaced
		 */
		setWidth(width * scale + 2 * (margin + shiftx));
		setHeight(height * scale + 2 * (margin + shifty));
		setMinWidth(45);
		/*
		 * when the mouse moves, it updates gridmousex,y and mouse validity and repaints
		 */
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				gridmousex = e.getX() - shiftx - margin;
				gridmousey = e.getY() - shifty - margin;
				updateMouseValidity();
				layoutChildren();
			}
		});
		/*
		 * when the mouse is dragged, it moves the drawing plane along with the mouse and repaints
		 */
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double prevshiftx = shiftx;
				double prevshifty = shifty;
				if (dragx != 0 && dragy !=0) {
					shiftx += e.getX() - dragx;
					shifty += e.getY() - dragy;
				}
				if (!(shiftx > -width * scale && shiftx < getWidth() - 2 * margin)) {
					shiftx = prevshiftx;
				}
				if (!(shifty > -height * scale && shifty < getHeight() - 2 * margin)) {
					shifty = prevshifty;
				}
				dragx = e.getX();
				dragy = e.getY();
				layoutChildren();
			}
		});
		/*
		 * resets drag coordinates once mouse is released from drag
		 */
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				dragx = 0;
				dragy = 0;
			}
		});
		/*
		 * changes mouse cursor and focuses onto this EditorPane
		 */
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				requestFocus();
				canvas.setCursor(Cursor.CROSSHAIR);
			}
		});
		/*
		 * resizes drawing plane as scrolling occurs
		 */
		canvas.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent e) {
				double omousex = (e.getX() - shiftx - margin) / scale;
				double omousey = (e.getY() - shifty - margin) / scale;
				setScale(scale += e.getDeltaY() / (200 / scale));
				setScale(scale += e.getDeltaX() / (200 / scale));
				shiftx += e.getX() - shiftx - margin - omousex * scale;
				shifty += e.getY() - shifty - margin - omousey * scale;
				fixLocation();
				gridmousex = e.getX() - shiftx - margin;
				gridmousey = e.getY() - shifty - margin;
				updateMouseValidity();
				layoutChildren();
			}
		});
		/*
		 * ensures that the drawing plane does not exceed the bounds of this EditorPane
		 */
		widthProperty().addListener(e -> {fixLocation();});
		heightProperty().addListener(e -> {fixLocation();});
	}
	/*
	 * paints the grid and background and elements on the canvas
	 */
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		/*
		 * fills background
		 * translates to drawing plane start
		 * draws drawing plane
		 * translates to margins
		 * draws grid or selected style
		 */
		gc.setFill(Color.grayRgb(210));
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.translate(shiftx, shifty);
		gc.setFill(Color.grayRgb(255));
		gc.fillRoundRect(0, 0, width * scale + 2 * margin, height * scale + 2 * margin, 8, 8);
		gc.translate(margin, margin);
		gc.setLineWidth(1);
		if (style.equals(Style.pegboard)) {
			gc.setStroke(Color.BLACK);
			gc.setFill(Color.BLACK);
			for (double x = 0; x <= width * scale + 1; x += scale) {	
				for (double y = 0; y <= height * scale + 1; y += scale) {
					gc.fillOval(x-1, y-1, 2, 2);
				}
			}
		} else if (style.equals(Style.grid)) {
			gc.setStroke(Color.LIGHTGRAY);
			for (double x = 0; x <= width * scale + 1; x += scale) {	
				gc.strokeLine(x, 0, x, height * scale);
			}
			for (double y = 0; y <= height * scale + 1; y += scale) {	
				gc.strokeLine(0, y, width * scale, y);
			}
		}
		/*
		 * translates back for next interation of render loop
		 */
		gc.translate(-shiftx, -shifty);
		gc.translate(-margin, -margin);
	}
	/*
	 * sets style of the grid
	 */
	public void setStyle(Style style) {
		this.style = style;
		layoutChildren();
	}
	/*
	 * centers the drawing plane
	 */
	public void center() {
		if (getWidth() < getHeight()) {
			setScale((getWidth() - 4 * margin) / width);
		} else {
			setScale((getHeight() - 4 * margin) / height);
		}
		shiftx = (getWidth() - (scale * width + 2 * margin)) / 2;
		shifty = (getHeight() - (scale * height + 2 * margin)) / 2;
		layoutChildren();
	}
	/*
	 * sets scale of the drawing plane
	 */
	public void setScale(double scale) {
		double omousex = (getWidth() / 2 - shiftx - margin) / this.scale;
		double omousey = (getHeight() / 2 - shifty - margin) / this.scale;
		shiftx += getWidth() / 2 - shiftx - margin - omousex * scale;
		shifty += getHeight() / 2 - shifty - margin - omousey * scale;
		this.scale = scale < minscale ? minscale : scale > maxscale ? maxscale : scale;
		fixLocation();
		layoutChildren();
	}
	/*
	 * ensures that the drawing plane does not leave the bounds of the EditorPane
	 */
	private void fixLocation() {
		if (!(shiftx > -width * scale)) {
			shiftx = -width * scale;
		}
		if (!(shifty > -height * scale)) {
			shifty = -height * scale;
		}
		if (!(shiftx < getWidth() - 2 * margin)) {
			shiftx = getWidth() - 2 * margin;
		} 
		if (!(shifty < getHeight() - 2 * margin)) {
			shifty = getHeight() - 2 * margin;
		} 
	}
	/*
	 * updates the mousevalid value
	 */
	private void updateMouseValidity() {
		if (gridmousex >= 0 && gridmousex <= width * scale && gridmousey >= 0 && gridmousey <= height * scale) {
			mousevalid = true;
		} else {
			mousevalid = false;
		}
	}
	
}
