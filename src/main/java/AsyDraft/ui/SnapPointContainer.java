package AsyDraft.ui;

import java.util.ArrayList;
import java.util.HashSet;

import AsyDraft.asyEditorObjects.AsyEditorObject;

public class SnapPointContainer {
	/*
	 * objectmanager, where visible objects are obtained from
	 * snap, whether attempts are made to snap to a nearest significant point.
	 */
	private EditorObjectManager objectmanager;
	private boolean snap = true;
	
	public SnapPointContainer(boolean snap, EditorObjectManager objectmanager) {
		this.snap = snap;
		this.objectmanager = objectmanager;
	}
	/*
	 * searches for and returns the closest snappable point
	 */
	public SnapPoint snap(double x, double y) {
		SnapPoint point = new SnapPoint(x, y, "mouse location");
		if (snap) {
			double distance = -1;
			double latticex = Math.round(x);
			double latticey = Math.round(y);
			point.setpoint(latticex, latticey, "lattice point");
			distance = point.distanceTo(x, y);
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
		}
		return point;
	}
	/*
	 * sets whether a snap is attempted
	 */
	public void setSnap(boolean snap) {
		this.snap = snap;
	}
}
