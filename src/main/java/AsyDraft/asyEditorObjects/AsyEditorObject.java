package AsyDraft.asyEditorObjects;

import AsyDraft.asyObjects.AsyObject;
import AsyDraft.ui.SnapPoint;
import javafx.scene.canvas.GraphicsContext;

public interface AsyEditorObject {
	/*
	 * returns AsyObject associated with this Editor Object
	 */
	public AsyObject getAsyObject();
	/*
	 * draws this object on screen
	 */
	public void render(double scale, GraphicsContext gc);
	/*
	 * returns an array of SnapPoints contained in this AsyEditorObject
	 */
	public SnapPoint[] getSnapPoints();
}
