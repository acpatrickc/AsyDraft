package AsyDraft.ui;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class EditorPane extends Pane {

	public enum Style {
		pegboard,
		grid,
		blank
	}
	
	private Style style = Style.pegboard;
	
	public final double minscale = 20;
	public final double maxscale = 500;
	
	private int width = 10;
	private int height = 10;
	private double scale = 50;
	
	private int margin = 10;
	private double shiftx = 10;
	private double shifty = 10;
	private double dragx = 0;
	private double dragy = 0;
	
	private boolean free = false;
	//private boolean showdot = true;
	
	//check if scaled
	private double mousex;
	private double mousey;
	private boolean mousevalid = false;
	private boolean wasscrolling;
	
	private boolean snaptomidpoint;
	private boolean snaptoendpoint;
	private boolean snaptopoint;
	private boolean snaptocenter;
	private boolean snaptocircle;
	private boolean snaptolattice;

	private Canvas canvas = new Canvas();
	
	//private Label infolabel = new Label();
	
	public EditorPane(int l, int h, int s) {
		width = l;
		height = h;
		scale = s;
		getChildren().add(canvas);
		widthProperty().addListener(e -> {canvas.setWidth(getWidth());});
		heightProperty().addListener(e -> {canvas.setHeight(getHeight() /*- info.getHeight()*/);});
		setWidth(width * scale + 2 * (margin + shiftx));
		setHeight(height * scale + 2 * (margin + shifty));
		setMinWidth(45);
		
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent e) {
				mousex = e.getX() - shiftx - margin;
				mousey = e.getY() - shifty - margin;
				if (mousex >= 0 && mousex <= width * scale && mousey >= 0 && mousey <= height * scale) {
					mousevalid = true;
					
				} else {
					mousevalid = false;
				}
				layoutChildren();
			}
		});
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				//System.out.println("dragged");
				double prevshiftx = shiftx;
				double prevshifty = shifty;
				if (dragx != 0 && dragy !=0) {
					shiftx += e.getX() - dragx;
					shifty += e.getY() - dragy;
				}
				
				if (!(shiftx > -width * scale && shiftx < getWidth() - 2 * margin)) {
					shiftx = prevshiftx;
					//shifty = prevshifty;
				}
				if (!(shifty > -height * scale && shifty < getHeight() - 2 * margin)) {
					//shiftx = prevshiftx;
					shifty = prevshifty;
				}
				dragx = e.getX();
				dragy = e.getY();
				layoutChildren();
				//clicknumber = 0;
				wasscrolling = true;
				//clicknumber = 0;
				setCursor(Cursor.CLOSED_HAND);
			}
		});
		
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				dragx = 0;
				dragy = 0;
				setCursor(Cursor.DEFAULT);
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				requestFocus();
				canvas.setCursor(Cursor.CROSSHAIR);
				//Tooltip.install(new TextField(), new Tooltip("123"));
			}
		});
		
		widthProperty().addListener(e -> {fixScroll();});
		heightProperty().addListener(e -> {fixScroll();});
		
		canvas.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent e) {
				double omousex = (e.getX() - shiftx - margin) / scale;
				double omousey = (e.getY() - shifty - margin) / scale;
				setScale(scale += e.getDeltaY() / (200 / scale));
				setScale(scale += e.getDeltaX() / (200 / scale));
				
				shiftx += e.getX() - shiftx - margin - omousex * scale;
				shifty += e.getY() - shifty - margin - omousey * scale;
				fixScroll();
				
				mousex = e.getX() - shiftx - margin;
				mousey = e.getY() - shifty - margin;
				if (mousex >= 0 && mousex <= width * scale && mousey >= 0 && mousey <= height * scale) {
					mousevalid = true;
					
				} else {
					mousevalid = false;
				}
				
				layoutChildren();
			}
		});
		
		/*
		labelletterspinner = new Spinner<>(1, 17576, 2);
		labelletterspinner.setEditable(true);
		labelletterspinner.setPrefWidth(140);
		
		labelletterspinner.getValueFactory().setConverter(new StringConverter<Integer>() {
			
			@Override
			public String toString(Integer i) {
				return MathUtils.intoAlphabet(i);
			}
			
			@Override
			public Integer fromString(String s) {
				char[] letters = s.toUpperCase().toCharArray();
				int i = letters.length - 1;
				int value = 0;
				for (char c : letters) {
					value += (((int) c) - 64) * Math.pow(26, i);
					i--;
				}
				//System.out.println(value);
				return value;
			}
			
		});
		labelletterspinner.getValueFactory().setValue(1);
		labelletterspinner.valueProperty().addListener(e -> {labelletter = labelletterspinner.getValue();});
		
		labelmode = new ComboBox<>();
		labelmode.getItems().add("alphabetical");
		labelmode.getItems().add("custom");
		labelmode.getSelectionModel().select("alphabetical");
		labelmode.valueProperty().addListener(e -> {
			switch (labelmode.getValue()) {
				case "alphabetical":
					labelcustom = false;
					labelsettings.getChildren().remove(labeltext);
					labelsettings.getChildren().add(labelletterspinner);
					break;
				
				case "custom":
					labelcustom = true;
					labelsettings.getChildren().remove(labelletterspinner);
					labelsettings.getChildren().add(labeltext);
					break;
			}
		});
		
		labeltext = new TextField();
		labeltext.setPrefWidth(140);
		
		labelsettings = new HBox(labelmode, labelletterspinner);
		*/
		
	}
	
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//calculateSnapPoint();
		

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
		gc.setLineCap(StrokeLineCap.ROUND);

		gc.setStroke(Color.BLACK);
		for (AsyObject a : renderobjects) {
			a.render(scale, gc, snappoint);
		}
		*/
		
		gc.translate(-shiftx, -shifty);
		gc.translate(-margin, -margin);
	}
	
	public void setStyle(Style style) {
		this.style = style;
		layoutChildren();
	}
	
	//TODO selection mode
	
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
	
	public void undo() {
		//TODO
	}
	
	public void redo() {
		//TODO
	}
	
	public void setScale(double scale) {
		double omousex = (getWidth() / 2 - shiftx - margin) / this.scale;
		double omousey = (getHeight() / 2 - shifty - margin) / this.scale;
		shiftx += getWidth() / 2 - shiftx - margin - omousex * scale;
		shifty += getHeight() / 2 - shifty - margin - omousey * scale;
		
		this.scale = scale < minscale ? minscale : scale > maxscale ? maxscale : scale;
		//TODO sizeslider.setValue(this.scale);
		fixScroll();
		layoutChildren();
	}
	
	private void fixScroll() {
		if (!(shiftx > -width * scale)) {
			shiftx = -width * scale;
			//shifty = prevshifty;
		}
		if (!(shifty > -height * scale)) {
			//shiftx = prevshiftx;
			shifty = -height * scale;
		}
		if (!(shiftx < getWidth() - 2 * margin)) {
			//shiftx = prevshiftx;
			shiftx = getWidth() - 2 * margin;
		} 
		if (!(shifty < getHeight() - 2 * margin)) {
			//shiftx = prevshiftx;
			shifty = getHeight() - 2 * margin;
		} 
	}

	
}
