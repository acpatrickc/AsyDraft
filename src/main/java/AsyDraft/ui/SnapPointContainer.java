package AsyDraft.ui;

import java.util.ArrayList;
import java.util.HashSet;

import AsyDraft.asyEditorObjects.AsyEditorObject;

public class SnapPointContainer {
	/*
	 * objects, stores all objects that is contained in this SnapPointContainer
	 * points, stores all SnapPoints
	 * snaptolattice, whether or not lattice points are snapped to.
	 */
	private ArrayList<AsyEditorObject> objects = new ArrayList<>();
	private HashSet<SnapPoint> points = new HashSet<>();
	private boolean snaptolattice;
	
	public SnapPointContainer(boolean lattice) {
		snaptolattice = lattice;
	}
	/*
	 * adds an object to this SnapPointContainer
	 * adds the SnapPoints from that object to the set of points
	 */
	public void addObject(AsyEditorObject o) {
		objects.add(o);
		for (SnapPoint p : o.getSnapPoints()) points.add(p);
	}
	/*
	 * returns the closese snappable point
	 */
	public SnapPoint snap(double x, double y) {
		SnapPoint point = new SnapPoint();
		if (snaptolattice) {
			double latticex = Math.round(x);
			double latticey = Math.round(y);
			point.setpoint(latticex, latticey, "lattice point");
		}
		return point;
	}
}
