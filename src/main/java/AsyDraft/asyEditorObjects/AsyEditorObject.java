package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyObject;

public interface AsyEditorObject {
	/*
	 * returns AsyObject associated with this Editor Object
	 */
	public AsyObject getAsyObject();
	/*
	 * draws this object on screen
	 */
	public void render();
}
