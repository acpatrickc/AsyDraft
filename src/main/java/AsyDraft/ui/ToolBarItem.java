package AsyDraft.ui;

import AsyDraft.ui.IconManager.Icons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ToolBarItem extends Button {
	/*
	 * Creates a button for a toolbar
	 * set up with a convenient constructor for making toolbar items
	 */
	public ToolBarItem(Icons icon, String tooltip, EventHandler<ActionEvent> action) {
		super("", IconManager.getIcon(icon));
		setTooltip(new Tooltip(tooltip));
		setOnAction(action);
	}
}
