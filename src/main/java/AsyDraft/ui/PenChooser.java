package AsyDraft.ui;

import AsyDraft.asyGenerator.AsyPen;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class PenChooser extends GridPane {
	/*
	 * display, the canvas displayin the resulting line and color
	 * sliders for colors
	 */
	private Canvas display;
	private Slider redslider = new Slider(0, 1, 0);;
	private Slider greenslider = new Slider(0, 1, 0);;
	private Slider blueslider = new Slider(0, 1, 0);;
	/*
	 * spinners for color values and line distances
	 */
	private Spinner<Number> redspinner = new Spinner<>(0, 1, 0, 0.01);
	private Spinner<Number> greenspinner = new Spinner<>(0, 1, 0, 0.01);
	private Spinner<Number> bluespinner = new Spinner<>(0, 1, 0, 0.01);
	private Spinner<Double> spacespinner = new Spinner<>(0, 20, 0, 0.5);
	private Spinner<Double> linespinner = new Spinner<>(0, 20, 0, 0.5);
	/*
	 * stored color
	 */
	Color setcolor;
	/*
	 * the editor which this sets the pen of
	 */
	EditorPane editor;
	
	public PenChooser(EditorPane editor) {
		this.editor = editor;
		setcolor = new Color(redspinner.getValue().doubleValue(), greenspinner.getValue().doubleValue(), bluespinner.getValue().doubleValue(), 1);			
		display = new Canvas();
		display.setHeight(70);
		/*
		 * slider height for more contrast
		 */
		redslider.setMinHeight(30);
		greenslider.setMinHeight(30);
		blueslider.setMinHeight(30);
		/*
		 * allow spinners to be edited
		 */
		redspinner.setEditable(true);
		bluespinner.setEditable(true);
		greenspinner.setEditable(true);
		spacespinner.setEditable(true);
		linespinner.setEditable(true);
		/*
		 * links spinners to sliders
		 */
		redslider.valueProperty().bindBidirectional(redspinner.getValueFactory().valueProperty());
		greenslider.valueProperty().bindBidirectional(greenspinner.getValueFactory().valueProperty());
		blueslider.valueProperty().bindBidirectional(bluespinner.getValueFactory().valueProperty());
		/*
		 * updates when values are changed
		 */
		redslider.valueProperty().addListener(e -> {updateDisplay();});
		greenslider.valueProperty().addListener(e -> {updateDisplay();});
		blueslider.valueProperty().addListener(e -> {updateDisplay();});
		spacespinner.valueProperty().addListener(e -> {updateDisplay();});
		linespinner.valueProperty().addListener(e -> {updateDisplay();});
		/*
		 * adds components to this PenChooser and sets height of last two rows
		 */
		add(display, 0, 1, 3, 1);
		add(new Label("red"), 0, 2);
		add(new Label("green"), 0, 3);
		add(new Label("blue"), 0, 4);
		add(new Label("interval spacing"), 0, 5, 2, 1);
		add(new Label("stroke length"), 0, 6, 2, 1);
		add(redslider, 1, 2);
		add(greenslider, 1, 3);
		add(blueslider, 1, 4);
		add(redspinner, 2, 2);
		add(greenspinner, 2, 3);
		add(bluespinner, 2, 4);
		add(spacespinner, 2, 5);
		add(linespinner, 2, 6);
		/*
		 * padding of this PenChooser
		 */
		setPadding(new Insets(5));
		/*
		 * sets up column ratios and paints color
		 */
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getColumnConstraints().addAll(new ColumnConstraints(getWidth() * 0.18), new ColumnConstraints(getWidth() * 0.52), new ColumnConstraints(getWidth() * 0.3));
				display.setWidth(getWidth());
				updateDisplay();
			}
		});
	}
	/*
	 * paints a square displaying the selected color with a gray border
	 * paints the line with interval chosen on a white background
	 */
	private void updateDisplay() {
		setcolor = new Color(redslider.getValue(), greenslider.getValue(), blueslider.getValue(), 1);
		GraphicsContext gc = display.getGraphicsContext2D();
		gc.setLineDashes(null);
		gc.clearRect(0, 0, Integer.MAX_VALUE, 70);
		gc.setFill(setcolor);
		gc.setStroke(Color.GRAY);
		gc.setLineCap(StrokeLineCap.ROUND);
		gc.fillRect(2, 2, 65, 65);
		gc.strokeRect(2, 2, 65, 65);
		gc.setLineWidth(21);
		gc.setStroke(Color.LIGHTGRAY);
		gc.strokeLine(90, 35, display.getWidth() - 20, 35);
		gc.setLineWidth(20);
		gc.setStroke(Color.WHITE);
		gc.strokeLine(90, 35, display.getWidth() - 20, 35);
		gc.setLineWidth(1);
		gc.setStroke(setcolor);
		gc.setLineDashes(linespinner.getValue().doubleValue(), spacespinner.getValue().doubleValue());
		gc.strokeLine(90, 35, display.getWidth() - 20, 35);
		editor.setPen(new AsyPen(redslider.getValue(), greenslider.getValue(), blueslider.getValue(), linespinner.getValue(), spacespinner.getValue()));
	}
}
