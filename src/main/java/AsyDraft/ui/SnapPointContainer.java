package AsyDraft.ui;

import java.util.ArrayList;
import java.util.HashSet;

import AsyDraft.asyEditorObjects.AsyEditorObject;

public class SnapPointContainer {
	/*
	 * stores all objects that is contained in this SnapPointContainer
	 */
	private ArrayList<AsyEditorObject> objects = new ArrayList<>();
	/*
	 * stores all SnapPoints
	 * set avoids duplicates
	 */
	private HashSet<SnapPoint> points = new HashSet<>();
	/*
	 * adds an object to this SnapPointContainer
	 * adds the SnapPoints from that object to the set of points
	 */
	public void addObject(AsyEditorObject o) {
		objects.add(o);
		for (SnapPoint p : o.getSnapPoints()) points.add(p);
	}
}
