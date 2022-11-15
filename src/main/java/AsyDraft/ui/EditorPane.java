package AsyDraft.ui;

import AsyDraft.asyGenerator.AsyPen;
import AsyDraft.ui.FunctionPointTracker.FunctionSelectionMode;
import AsyDraft.ui.FunctionPointTracker.Functions;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
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
	private Style style = Style.grid;
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
	private boolean isdragging = false;
	/*
	 * gridmousex, gridmousey, location of mouse on drawing plane relative to the grid
	 * (0,0) on top left
	 * mousevalid, if the mouse is able to create a point on the drawing plane
	 */
	private double gridmousex;
	private double gridmousey;
	private boolean mousevalid = false;
	/*
	 * pointtracker, tracks points and creates AsyEditorObjects
	 * objectmanager, stores objects created
	 * the SnapPointContainer for all snap points in this EditorPane
	 * snappoint, the current point the mouse is snapped to
	 */
	private FunctionPointTracker pointtracker = new FunctionPointTracker(FunctionSelectionMode.drop);
	private EditorObjectManager objectmanager = new EditorObjectManager();
	private SnapPointContainer snapcontainer = new SnapPointContainer(true, objectmanager);
	private SnapPoint snappoint = snapcontainer.snap(gridScale(gridmousex), gridScale(gridmousey));
	/*
	 * the pen used to draw new objects
	 */
	private AsyPen currentpen = new AsyPen(0, 0, 0, 0, 0);
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
		 * updates snap point and its validity
		 */
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				gridmousex = e.getX() - shiftx - margin;
				gridmousey = e.getY() - shifty - margin;
				updateMouseValidity();
				layoutChildren();
				if (mousevalid) {
					snappoint = snapcontainer.snap(gridScale(gridmousex), gridScale(gridmousey));
				} else {
					snappoint = new SnapPoint();
				}
			}
		});
		/*
		 * when the mouse is dragged, it moves the drawing plane along with the mouse and repaints
		 * sets isdragging to true
		 * if mouse is pressed then moved, then true
		 * if mouse is pressed then not moved, then false
		 */
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isdragging = true;
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
		 * sets wasdragging to false
		 */
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isdragging = false;
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
		 * updates drawing plane as mouse exits
		 * prevents artifacts of preview shadows
		 */
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				snappoint = new SnapPoint();
				layoutChildren();
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
		 * feeds a point to the current function in the pointtracker when a click occurs
		 * only if the click was stationary, not a drag
		 * checks for newly generated objects in the function point tracker and adds them to the objectmanager
		 */
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (!isdragging && mousevalid) {
					pointtracker.feedPoint(snappoint.getX(), snappoint.getY(), scale, width, height, currentpen);
					while (!pointtracker.waitlistEmpty()) {
						objectmanager.addEditorObject(pointtracker.takeEditorObject());
					}
				}
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
		paintBase(gc);
		paintObjects(gc);
		paintPreviewShadow(gc);
		paintMouseLocation(gc);
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
	/*
	 * paints the base of the editor, background and drawing plane
	 */
	private void paintBase(GraphicsContext gc) {
		/*
		 * fills background
		 * translates to drawing plane start
		 * draws drawing plane
		 * translates to margins
		 * draws grid or selected style
		 * resets dashes
		 */
		gc.setFill(Color.grayRgb(210));
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.translate(shiftx, shifty);
		gc.setFill(Color.WHITE);
		gc.fillRoundRect(0, 0, width * scale + 2 * margin, height * scale + 2 * margin, 8, 8);
		gc.translate(margin, margin);
		gc.setLineWidth(1);
		gc.setLineDashes(0);
		if (style.equals(Style.pegboard)) {
			gc.setStroke(Color.BLACK);
			gc.setFill(Color.BLACK);
			for (double x = 0; x <= width * scale + 1; x += scale) {	
				for (double y = 0; y <= height * scale + 1; y += scale) {
					gc.fillOval(x - 1, y - 1, 2, 2);
				}
			}
		} else if (style.equals(Style.grid)) {
			gc.setStroke(Color.grayRgb(230));
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
		gc.translate(- (shiftx + margin), - (shifty + margin));
	}
	/*
	 * paints what the mouse is currently locked onto on the drawing plane
	 */
	private void paintMouseLocation(GraphicsContext gc) {
		if (mousevalid) {
			gc.setStroke(Color.GREEN);
			gc.setLineWidth(1.5);
			gc.setLineDashes(0);
			gc.translate(shiftx + margin, shifty + margin);
			gc.strokeOval(pxScale(snappoint.getX()) - 4, pxScale(snappoint.getY()) - 4, 8, 8);
			gc.translate(- (shiftx + margin), - (shifty + margin));
		}
	}
	/*
	 * paints a preview of the current drawing function (if applicable)
	 */
	private void paintPreviewShadow(GraphicsContext gc) {
		if (mousevalid) {
			gc.translate(shiftx + margin, shifty + margin);
			pointtracker.paintPreviewShadow(snappoint.getX(), snappoint.getY(), scale, width, height, gc);
			gc.translate(- (shiftx + margin), - (shifty + margin));
		}
	}
	/*
	 * instructs the objectmanager render all visible objects in the current scale
	 */
	private void paintObjects(GraphicsContext gc) {
		gc.translate(shiftx + margin, shifty + margin);
		objectmanager.render(scale, gc);
		gc.translate(- (shiftx + margin), - (shifty + margin));
	}
	/*
	 * scales down to grid scale
	 */
	private double gridScale(double x) {
		return x / scale;
	}
	/*
	 * scales up to pixel scale
	 */
	private double pxScale(double x) {
		return scale * x;
	}
	/*
	 * Sets the current tool function
	 */
	public void setFunction(Functions f) {
		pointtracker.setFunction(f);
	}
	/*
	 * undo last created object
	 */
	public void undo() {
		objectmanager.undo();
		layoutChildren();
	}
	/*
	 * redo
	 */
	public void redo() {
		objectmanager.redo();
		layoutChildren();
	}
	/*
	 * sets selection mode for drawing functions
	 */
	public void setSelectionMode(FunctionSelectionMode m) {
		pointtracker.setSelectionMode(m);
	}
	/*
	 * sets style of the grid
	 */
	public void setStyle(Style style) {
		this.style = style;
		layoutChildren();
	}
	/*
	 * sets whether to snap to significant points or not
	 */
	public void setSnap(boolean snap) {
		snapcontainer.setSnap(snap);
	}
	/*
	 * returns the label settings box
	 */
	public HBox getLabelSettings() {
		return pointtracker.getLabelSettings();
	}
	/*
	 * sets the current used AsyPen
	 */
	public void setPen(AsyPen p) {
		currentpen = p;
	}
}
