package AsyDraft.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import AsyDraft.asyEditorObjects.AsyEditorObject;
import AsyDraft.asyEditorObjects.AsyEditorSegment;
import AsyDraft.asyObjects.AsySegment;
import AsyDraft.ui.FunctionPointTracker.FunctionSelectionMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

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
		point,
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
	 * contstructor: maps functions to required points
	 */
	public FunctionPointTracker(FunctionSelectionMode m) {
		currentselectionmode = m;
		requiredpoints.put(Functions.segment, 2);
		requiredpoints.put(Functions.endarrow, 2);
		requiredpoints.put(Functions.beginarrow, 2);
		requiredpoints.put(Functions.midarrow, 2);
		requiredpoints.put(Functions.doublearrow, 2);
		requiredpoints.put(Functions.circle, 2);
		requiredpoints.put(Functions.incircle, 3);
		requiredpoints.put(Functions.circumcircle, 3);
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
	 * if the number of points required for a function is fulfilled, the object(s) in this function is/are added to the waitlist
	 */
	public void feedPoint(double x, double y) {
		if (!currentfunction.equals(Functions.nofunction)) {
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
						double[] p = points.remove();
						double[] p1 = points.remove();
						waitlist.add(new AsyEditorSegment(p[0], p[1], p1[0], p1[1]));
						break;
					case beginarrow:
						break;
					case endarrow:
						break;
					case doublearrow:
						break;
					case midarrow:
						break;
					case circle:
						break;
					case circumcircle:
						break;
					case incircle:
						break;
					case point:
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
	 * paints the preview shadow of the current object if possible
	 * only occurs when last point of an function is being selected
	 */
	public void paintPreviewShadow(double x, double y, double scale, GraphicsContext gc) {
		Color validshadowcolor = new Color(0, 0.2, 0.8, 0.3);
		Color invalidshadowcolor = new Color(0.8, 0, 0, 0.3);
		gc.setLineWidth(4);
		gc.setLineCap(StrokeLineCap.ROUND);
		if (!currentfunction.equals(Functions.nofunction) && pointcount == requiredpoints.get(currentfunction) - 1) {
			switch (currentfunction) {
				case segment:
					double[] p = points.peek();
					gc.setStroke(validshadowcolor);
					gc.strokeLine(p[0] * scale, p[1] * scale, x * scale, y * scale);
					break;
				case beginarrow:
					break;
				case endarrow:
					break;
				case doublearrow:
					break;
				case midarrow:
					break;
				case circle:
					break;
				case circumcircle:
					break;
				case incircle:
					break;
				case point:
					break;
				case nofunction:
					break;
				default:
					break;
			}
		}
	}
}
