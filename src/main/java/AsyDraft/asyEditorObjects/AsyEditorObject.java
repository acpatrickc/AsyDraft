package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.SnapPoint;

public interface AsyEditorObject {
	/*
	 * returns AsyObject associated with this Editor Object
	 */
	public AsyObject getAsyObject();
	/*
	 * draws this object on screen
	 */
	public void render();
	/*
	 * returns an array of SnapPoints contained in this AsyEditorObject
	 */
	public SnapPoint[] getSnapPoints();
}
