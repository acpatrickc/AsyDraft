package AsyDraft.ui;

import java.util.Stack;

import AsyDraft.asyEditorObjects.AsyEditorObject;
import javafx.scene.canvas.GraphicsContext;

public class EditorObjectManager {
	/*
	 * visibleobjects, objects that are rendered onto the screen, new objects are added here
	 * invisibleobjects, objects that are not rendered, but objects are moved there if undone
	 */
	private Stack<AsyEditorObject> visibleobjects = new Stack<>();
	private Stack<AsyEditorObject> invisibleobjects = new Stack<>();
	/*
	 * iterates through all visible objects and renders them with the specific graphicscontext
	 */
	public void render(double scale, GraphicsContext gc) {
		for (AsyEditorObject o : visibleobjects) {
			o.render(scale, gc);
		}
	}
	/*
	 * adds a new AsyEditorObject to visibleobjects
	 */
	public void addEditorObject(AsyEditorObject o) {
		visibleobjects.add(o);
	}
	/*
	 * moves top of invisibleobjects to visibleobjects
	 */
	public void redo() {
		if (!invisibleobjects.isEmpty()) visibleobjects.add(invisibleobjects.pop());
	}
	/*
	 * moves top of visibleobjects to invisibleobjects
	 */
	public void undo() {
		if (!visibleobjects.isEmpty()) invisibleobjects.add(visibleobjects.pop());
	}
	
}
