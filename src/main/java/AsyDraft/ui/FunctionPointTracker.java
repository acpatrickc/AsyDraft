package AsyDraft.ui;

import java.util.HashMap;
import java.util.LinkedList;

import AsyDraft.asyEditorObjects.AsyEditorBeginArrow;
import AsyDraft.asyEditorObjects.AsyEditorCircle;
import AsyDraft.asyEditorObjects.AsyEditorDot;
import AsyDraft.asyEditorObjects.AsyEditorDoubleArrow;
import AsyDraft.asyEditorObjects.AsyEditorEndArrow;
import AsyDraft.asyEditorObjects.AsyEditorLabel;
import AsyDraft.asyEditorObjects.AsyEditorMidArrow;
import AsyDraft.asyEditorObjects.AsyEditorObject;
import AsyDraft.asyEditorObjects.AsyEditorSegment;
import AsyDraft.asyGenerator.AsyPen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class FunctionPointTracker {
	/*
	 * Function, used to select the function this ObjectPointTracker is creating
	 */
	public enum Functions {
		nofunction,
		segment,
		endarrow,
		beginarrow,
		midarrow,
		doublearrow,
		dot,
		label,
		circle,
		incircle,
		circumcircle
	}
	/*
	 * FunctionSelectionMode, how selecting the next point works.
	 * drop, abandons last point once placed
	 * loop, uses last point in previous object as first point in current object
	 * lock, uses the same point as the start for objects
	 */
	public enum FunctionSelectionMode {
		drop,
		loop,
		lock
	}
	/*
	 * points, points stored for input into an AsyEditorObject
	 * requiredpoints, maps each function type to the number of points it is defined by
	 */
	private LinkedList<double[]> points = new LinkedList<>();
	private HashMap<Functions, Integer> requiredpoints = new HashMap<>();
	/*
	 * currentfunction, the current function being appended to
	 * currentselectionmode, the current selection mode for functions
	 * waitlist, AsyEditorObjects that are waiting to be taken
	 */
	private Functions currentfunction = Functions.nofunction;
	private FunctionSelectionMode currentselectionmode;
	private LinkedList<AsyEditorObject> waitlist = new LinkedList<>();
	/*
	 * labelletter, the alphanumberical representation of the current letter
	 * labelletterspinner, displays labelletter
	 * labelmode, custom or alphabetical
	 * labeltext, for inputting custom labels
	 */
	private Spinner<Integer> labelletterspinner;
	private ComboBox<String> labelmode;
	private HBox labelsettings;
	private TextField labeltext;
	/*
	 * contstructor: maps functions to required points
	 */
	public FunctionPointTracker(FunctionSelectionMode m) {
		currentselectionmode = m;
		requiredpoints.put(Functions.segment, 2);
		requiredpoints.put(Functions.endarrow, 2);
		requiredpoints.put(Functions.beginarrow, 2);
		requiredpoints.put(Functions.midarrow, 2);
		requiredpoints.put(Functions.doublearrow, 2);
		requiredpoints.put(Functions.dot, 1);
		requiredpoints.put(Functions.label, 2);
		requiredpoints.put(Functions.circle, 2);
		requiredpoints.put(Functions.incircle, 3);
		requiredpoints.put(Functions.circumcircle, 3);
		/*
		 * label letter spinner which increments alphabetically
		 */
		labelletterspinner = new Spinner<>(1, 17576, 2);
		labelletterspinner.setEditable(true);
		labelletterspinner.setPrefWidth(140);
		labelletterspinner.getValueFactory().setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer i) {
				return MathUtils.toAlphabet(i);
			}
			@Override
			public Integer fromString(String s) {
				char[] letters = s.toUpperCase().toCharArray();
				int i = letters.length - 1;
				int value = 0;
				/*
				 * A=1 B=2 etc.
				 * converts to base 10
				 */
				for (char c : letters) {
					value += (((int) c) - 64) * Math.pow(26, i);
					i--;
				}
				return value;
			}
			
		});
		labelletterspinner.getValueFactory().setValue(1);
		/*
		 * mode of label, either alphabetical or custom tex
		 */
		labelmode = new ComboBox<>();
		labelmode.getItems().add("alphabetical");
		labelmode.getItems().add("custom");
		labelmode.getSelectionModel().select("alphabetical");
		/*
		 * switches modes
		 */
		labelmode.valueProperty().addListener(e -> {
			switch (labelmode.getValue()) {
				case "alphabetical":
					labelsettings.getChildren().remove(labeltext);
					labelsettings.getChildren().add(labelletterspinner);
					break;
				
				case "custom":
					labelsettings.getChildren().remove(labelletterspinner);
					labelsettings.getChildren().add(labeltext);
					break;
			}
		});
		/*
		 * custom text input for labels, and hbox to put labelmode and labelletterspinner side by side
		 */
		labeltext = new TextField();
		labeltext.setPrefWidth(140);
		labelsettings = new HBox(labelmode, labelletterspinner);
	}
	/*
	 * sets focused function
	 */
	public void setFunction(Functions o) {
		points.clear();
		currentfunction = o;
	}
	/*
	 * consumes a point and adds it to the points needed by the current object
	 * accepts LaTeX, in case the function requires text
	 * if the number of points required for a function is fulfilled, the object(s) in this function is/are added to the waitlist
	 */
	public void feedPoint(double x, double y, double scale, int gridw, int gridh, AsyPen p) {
		if (!currentfunction.equals(Functions.nofunction)) {
			/*
			 * makes sure the same point is not selected consecutively, unless a label
			 */
			if (!points.isEmpty() && points.peekLast()[0] == x && points.peekLast()[1] == y && !currentfunction.equals(Functions.label)) return;
			points.add(new double[] {x , y});
			if (points.size() == requiredpoints.get(currentfunction)) {
				/*
				 * saves first and last points in order to facilitate different selection modes
				 */
				double[] lastpoint = points.peekLast();
				double[] firstpoint = points.peekFirst();
				switch (currentfunction) {
					case segment:
						waitlist.add(new AsyEditorSegment(points.remove(), points.remove(), p));
						break;
					case beginarrow:
						waitlist.add(new AsyEditorBeginArrow(points.remove(), points.remove(), p));
						break;
					case endarrow:
						waitlist.add(new AsyEditorEndArrow(points.remove(), points.remove(), p));
						break;
					case doublearrow:
						waitlist.add(new AsyEditorDoubleArrow(points.remove(), points.remove(), p));
						break;
					case midarrow:
						waitlist.add(new AsyEditorMidArrow(points.remove(), points.remove(), p));
						break;
					case label:
						waitlist.add(new AsyEditorLabel(points.remove(), points.remove(), scale, getLabelTeX(), p));
						labelletterspinner.getValueFactory().increment(1);
						break;
					case circle:
						/*
						 * if circle if valid, add circle else removes invalid last point (just added)
						 */
						if (circleValid(firstpoint[0], firstpoint[1], x, y, gridw, gridh)) {
							waitlist.add(new AsyEditorCircle(points.remove(), points.remove(), p));
						} else points.removeLast();
						break;
					case circumcircle:
						break;
					case incircle:
						break;
					case dot:
						waitlist.add(new AsyEditorDot(points.remove(), p));
						break;
					case nofunction:
						break;
					default:
						break;
				}
				/*
				 * reverts to certain point depending on selection modes
				 * only if the point list is empty, meaning an object was successfully created
				 * and only if the function is not a dot, which does nto need lock or loop selection
				 */
				if (points.isEmpty() && !currentfunction.equals(Functions.dot)) {
					switch (currentselectionmode) {
						case drop:
							break;
						case lock:
							points.add(firstpoint);
							break;
						case loop:
							points.add(lastpoint);
							break;
						default:
							break;
					}
				}
			}
		}
	}
	/*
	 * removes the last point and modifies point count to account for that
	 */
	/*
	 * paints the preview shadow of the current object if possible
	 * only occurs when last point of an function is being selected
	 */
	public void paintPreviewShadow(double x, double y, double scale, int gridw, int gridh, GraphicsContext gc) {
		Color validshadowcolor = new Color(0, 0.2, 0.8, 0.3);
		Color invalidshadowcolor = new Color(0.8, 0, 0, 0.3);
		if (!currentfunction.equals(Functions.nofunction) && points.size() == requiredpoints.get(currentfunction) - 1) {
			switch (currentfunction) {
				case segment:
					AsyEditorSegment.drawPreview(scale, gc, points.peek(), new double[] {x, y}, validshadowcolor);
					break;
				case beginarrow:
					AsyEditorBeginArrow.drawPreview(scale, gc, points.peek(), new double[] {x, y}, validshadowcolor);
					break;
				case endarrow:
					AsyEditorEndArrow.drawPreview(scale, gc, points.peek(), new double[] {x, y}, validshadowcolor);
					break;
				case doublearrow:
					AsyEditorDoubleArrow.drawPreview(scale, gc, points.peek(), new double[] {x, y}, validshadowcolor);
					break;
				case midarrow:
					AsyEditorMidArrow.drawPreview(scale, gc, points.peek(), new double[] {x, y}, validshadowcolor);
					break;
				case label:
					AsyEditorLabel.drawPreview(points.peek(), new double[] {x, y}, scale, getLabelTeX(), gc, validshadowcolor);
					break;
				case circle:
					/*
					 * circle preview color based off if a circle is within bounds
					 */
					AsyEditorCircle.drawPreview(scale, gc, points.peek(), new double[] {x, y}, circleValid(points.peek()[0], points.peek()[1], x, y, gridw, gridh) ? validshadowcolor : invalidshadowcolor);
					break;
				case circumcircle:
					break;
				case incircle:
					break;
					/*
					 * no previews
					 */
				case dot:
					break;
				case nofunction:
					break;
				default:
					break;
			}
		}
	}
	/*
	 * sets selection mode for drawing functions
	 */
	public void setSelectionMode(FunctionSelectionMode m) {
		currentselectionmode = m;
	}
	/*
	 * removes head of waitlist
	 */
	public AsyEditorObject takeEditorObject() {
		return waitlist.remove();
	}
	/*
	 * queries if the waitlist is empty
	 */
	public boolean waitlistEmpty() {
		return waitlist.isEmpty();
	}
	/*
	 * returns the label settings box
	 */
	public HBox getLabelSettings() {
		return labelsettings;
	}
	/*
	 * returns the tex string for the label
	 */
	private String getLabelTeX() {
		return labelmode.getValue().equals("custom") ? labeltext.getText() : MathUtils.toAlphabet(labelletterspinner.getValue());
	}
	/*
	 * checks if a circle is valid within the grid
	 */
	private boolean circleValid(double cx, double cy, double x, double y, int gridw, int gridh) {
		double r = Math.hypot(cx - x, cy - y);
		return !(cx - r < 0 || cy - r < 0 || cx + r > gridw || cy + r > gridw);
	}
}
