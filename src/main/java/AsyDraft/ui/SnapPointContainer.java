package AsyDraft.ui;

import java.util.ArrayList;
import java.util.HashSet;

import AsyDraft.asyEditorObjects.AsyEditorObject;

public class SnapPointContainer {
	/*
	 * snaptolattice, whether or not lattice points are snapped to
	 * objectmanager, where visible objects are obtained from
	 */
	private boolean snaptolattice;
	private EditorObjectManager objectmanager;
	
	public SnapPointContainer(boolean lattice, EditorObjectManager objectmanager) {
		snaptolattice = lattice;
		this.objectmanager = objectmanager;
	}
	/*
	 * searches for and returns the closest snappable point
	 */
	public SnapPoint snap(double x, double y) {
		SnapPoint point = new SnapPoint();
		double distance = -1;
		if (snaptolattice) {
			double latticex = Math.round(x);
			double latticey = Math.round(y);
			point.setpoint(latticex, latticey, "lattice point");
			distance = point.distanceTo(x, y);
		}
		/*
		 * for each visible object, check all of its snap points for a close one than the current closest
		 * replace the current snap point with the new closest
		 */
		for (AsyEditorObject eo : objectmanager.getVisibleObjects()) {
			for (SnapPoint p : eo.getSnapPoints()) {
				if (p.distanceTo(x, y) < distance) {
					point = p;
					distance = p.distanceTo(x, y);
				}
			}
		}
		return point;
	}
}
