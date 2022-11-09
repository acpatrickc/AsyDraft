package AsyDraft.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import AsyDraft.asyEditorObjects.AsyEditorObject;

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
	 * points, points stored for input into an AsyEditorObject
	 * requiredpoints, maps each function type to the number of points it is defined by
	 */
	private LinkedList<double[]> points = new LinkedList<>();
	private HashMap<Functions, Integer> requiredpoints = new HashMap<>();
	/*
	 * currentfunction, the current function being appended to
	 * pointcount, the point that is currently waiting to be inputted, 0 indexed
	 * waitlist, AsyEditorObjects that are waiting to be taken
	 */
	private Functions currentfunction = Functions.nofunction;
	private int pointcount;
	private LinkedList<AsyEditorObject> waitlist = new LinkedList<>();
	/*
	 * contstructor: maps functions to required points
	 */
	public FunctionPointTracker() {
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
				pointcount = 0;
				points.clear();
				//TODO waitlist.add():
			}
		}
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
}
