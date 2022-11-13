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
	 * pointcount, the point that is currently waiting to be inputted, 0 indexed
	 * waitlist, AsyEditorObjects that are waiting to be taken
	 */
	private Functions currentfunction = Functions.nofunction;
	private FunctionSelectionMode currentselectionmode;
	private int pointcount;
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
		pointcount = 0;
	}
	/*
	 * consumes a point and adds it to the points needed by the current object
	 * accepts LaTeX, in case the function requires text
	 * if the number of points required for a function is fulfilled, the object(s) in this function is/are added to the waitlist
	 */
	public void feedPoint(double x, double y, double scale) {
		if (!currentfunction.equals(Functions.nofunction)) {
			/*
			 * makes sure the same point is not selected consecutively, unless a label
			 */
			if (!points.isEmpty() && points.peekLast()[0] == x && points.peekLast()[1] == y && !currentfunction.equals(Functions.label)) return;
			points.add(new double[] {x , y});
			pointcount ++;
			if (pointcount == requiredpoints.get(currentfunction)) {
				/*
				 * saves first and last points in order to facilitate different selection modes
				 */
				double[] lastpoint = points.peekLast();
				double[] firstpoint = points.peekFirst();
				switch (currentfunction) {
					case segment:
						waitlist.add(new AsyEditorSegment(points.remove(), points.remove()));
						break;
					case beginarrow:
						waitlist.add(new AsyEditorBeginArrow(points.remove(), points.remove()));
						break;
					case endarrow:
						waitlist.add(new AsyEditorEndArrow(points.remove(), points.remove()));
						break;
					case doublearrow:
						waitlist.add(new AsyEditorDoubleArrow(points.remove(), points.remove()));
						break;
					case midarrow:
						waitlist.add(new AsyEditorMidArrow(points.remove(), points.remove()));
						break;
					case label:
						waitlist.add(new AsyEditorLabel(points.remove(), points.remove(), scale, getLabelTeX()));
						labelletterspinner.getValueFactory().increment(1);
						break;
					case circle:
						waitlist.add(new AsyEditorCircle(points.remove(), points.remove()));
						break;
					case circumcircle:
						break;
					case incircle:
						break;
					case dot:
						waitlist.add(new AsyEditorDot(points.remove()));
						break;
					case nofunction:
						break;
					default:
						break;
				}
				points.clear();
				/*
				 * reverts to certain point depending on selection modes
				 */
				switch (currentselectionmode) {
					case drop:
						pointcount = 0;
						break;
					case lock:
						points.add(firstpoint);
						pointcount = 1;
						break;
					case loop:
						points.add(lastpoint);
						pointcount = 1;
						break;
					default:
						break;
				}
			}
		}
	}
	/*
	 * paints the preview shadow of the current object if possible
	 * only occurs when last point of an function is being selected
	 */
	public void paintPreviewShadow(double x, double y, double scale, GraphicsContext gc) {
		Color validshadowcolor = new Color(0, 0.2, 0.8, 0.3);
		Color invalidshadowcolor = new Color(0.8, 0, 0, 0.3);
		if (!currentfunction.equals(Functions.nofunction) && pointcount == requiredpoints.get(currentfunction) - 1) {
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
					AsyEditorCircle.drawPreview(scale, gc, points.peek(), new double[] {x, y}, validshadowcolor);
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
}
