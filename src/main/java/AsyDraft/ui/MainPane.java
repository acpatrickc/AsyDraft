package AsyDraft.ui;

import AsyDraft.ui.IconManager.Icons;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
	/*
	 * draw function buttons
	 */
	ToolBarItem undo;
	ToolBarItem redo;
	ToolBarItem mouse;
	ToolBarItem segment;
	ToolBarItem arrow;
	ToolBarItem reversearrow;
	ToolBarItem midarrow;
	ToolBarItem doublearrow;
	ToolBarItem circle;
	ToolBarItem incircle;
	ToolBarItem circumcircle;
	ToolBarItem tangent;
	ToolBarItem parallel;
	ToolBarItem perpendicular;
	ToolBarItem rightangle;
	ToolBarItem point;
	ToolBarItem anglebisector;
	ToolBarItem congruentangle;
	ToolBarItem congruentsegment;
	ToolBarItem congruentarc;
	ToolBarItem label;
	ToolBarItem center;
	/*
	 * choice of background and mode of selection
	 */
	ComboBox<String> backgroundchoice;
	ComboBox<String> selectionmode;
	/*
	 * drawing mode selection
	 */
	ToggleGroup drawmodegroup;
	RadioButton snap;
	RadioButton free;
	/*
	 * two toolbars
	 */
	VBox toolmenubars;
	ToolBar settingstoolbar;
	ToolBar drawingtoolbar;
	/*
	 * top menu bar
	 */
	MenuBar menu;
	/*
	 * result area, where final asymptote code shows
	 * editor pane and pane that splits 
	 */
	TextArea resultarea;
	EditorPane editor;
	SplitPane splitpane;
	/*
	 * tabs which display drawn objects and settings
	 * area which drawn objects will be listed
	 * Hierarchy:
	 * TabPane -> Tab -> Accordion -> TitledPane
	 */
	TabPane lefttabs;
	Tab componentstab;
	Tab congruencetab;
	Tab snaptab;
	Tab appearancetab;
	Accordion componentsaccordion;
	Accordion congruences;
	Accordion labels;
	TitledPane linepane;
	TitledPane pointpane;
	TitledPane circlepane;
	TitledPane labelpane;
	TitledPane anglecongruences;
	TitledPane segmentcongruences;
	TitledPane arccongruences;
	TitledPane pointlabels;
	TitledPane segmentlabels;
	
	public MainPane() {
		/*
		 * initiates buttons used to toggle each draw function of this program
		 */
		undo = new ToolBarItem(Icons.undo, "", null);
		redo = new ToolBarItem(Icons.redo, "", null);
		mouse = new ToolBarItem(Icons.mouse, "", null);
		segment = new ToolBarItem(Icons.segment, "", null);
		arrow = new ToolBarItem(Icons.arrow, "", null);
		reversearrow = new ToolBarItem(Icons.reversearrow, "", null);
		midarrow = new ToolBarItem(Icons.midarrow, "", null);
		doublearrow = new ToolBarItem(Icons.doublearrow, "", null);
		point = new ToolBarItem(Icons.point, "", null);
		circle = new ToolBarItem(Icons.circle, "", null);
		label = new ToolBarItem(Icons.label, "", null);
		center = new ToolBarItem(Icons.center, "", null);
		incircle = new ToolBarItem(Icons.incircle, "", null);
		circumcircle = new ToolBarItem(Icons.circumcircle, "", null);
		tangent = new ToolBarItem(Icons.tangent, "", null);
		parallel = new ToolBarItem(Icons.parallel, "", null);
		perpendicular = new ToolBarItem(Icons.perpendicular, "", null);		
		rightangle = new ToolBarItem(Icons.rightangle, "", null);		
		anglebisector = new ToolBarItem(Icons.anglebisector, "", null);		
		congruentangle = new ToolBarItem(Icons.congruentangle, "", null);		
		congruentsegment = new ToolBarItem(Icons.congruentsegment, "", null);		
		congruentarc = new ToolBarItem(Icons.congruentarc, "", null);
		/*
		 * choice of background grid of editor pane
		 */	
		backgroundchoice = new ComboBox<>();
		backgroundchoice.getItems().add("pegboard");
		backgroundchoice.getItems().add("grid");
		backgroundchoice.getItems().add("blank");
		backgroundchoice.getSelectionModel().select("grid");
		/*
		 * mode of selection
		 * drop - stops after 1 object is done
		 * loop - last click of one object is first click of the next
		 * lock - same point used as initial point of objects
		 */
		selectionmode = new ComboBox<>();
		selectionmode.getItems().add("drop selection");
		selectionmode.getItems().add("loop selection");
		selectionmode.getItems().add("lock selection");
		selectionmode.getSelectionModel().select("drop selection");
		/*
		 * Drawing modes
		 * snap - snaps to snappable points (lattice, midpoints, etc.)
		 */
		drawmodegroup = new ToggleGroup();
		snap = new RadioButton("snap mode");
		snap.setToggleGroup(drawmodegroup);
		snap.setSelected(true);
		free = new RadioButton("free mode");
		free.setToggleGroup(drawmodegroup);
		/*
		 * adds toolbar items to toolbars
		 * separators group items into semi-related categories
		 */
		settingstoolbar = new ToolBar(undo, redo, new Separator()
			, backgroundchoice, selectionmode, snap, free, new Separator());
		settingstoolbar.getStyleClass().add("settingstoolbar");
		drawingtoolbar = new ToolBar(mouse, new Separator()
			, segment, arrow, reversearrow, midarrow, doublearrow, new Separator()
			, point, new Separator()
			, label, new Separator()
			, circle, new Separator()
			, tangent, incircle, circumcircle, new Separator()
			, parallel, perpendicular, new Separator()
			, anglebisector, congruentangle, rightangle, congruentsegment, congruentarc, new Separator());
		drawingtoolbar.getStyleClass().add("drawingtoolbar");
		/*
		 * menu bar, file open etc...
		 * editor, result instantiated and included in splitpane
		 */
		menu = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("Help"));
		toolmenubars = new VBox(menu, settingstoolbar, drawingtoolbar);
		resultarea = new TextArea();
		editor = new EditorPane(10, 10, 50);
		splitpane = new SplitPane(editor, resultarea);
		/*
		 * tabs and display / list area for drawn objects
		 */
		componentstab = new Tab("components", componentsaccordion);
		snaptab = new Tab("snap settings", new Pane());	
		appearancetab = new Tab("appearance", new Pane());	
		lefttabs = new TabPane(componentstab, snaptab, appearancetab);
		componentsaccordion = new Accordion(linepane, pointpane, circlepane, labelpane);
		congruences = new Accordion(anglecongruences, segmentcongruences, arccongruences);
		labels = new Accordion(pointlabels, segmentlabels);
		congruencetab = new Tab("congruences", congruences);
		anglecongruences = new TitledPane("angle congruences", new Pane());
		segmentcongruences = new TitledPane("segment congruences", new Pane());
		arccongruences = new TitledPane("arc congruences", new Pane());
		pointlabels = new TitledPane("point labels", new Pane());
		segmentlabels = new TitledPane("segment labels", new Pane());
		linepane = new TitledPane("lines", new Pane());
		pointpane = new TitledPane("points", new Pane());
		circlepane = new TitledPane("circles", new Pane());
		labelpane = new TitledPane("labels", new Pane());
		/*
		 * forbids tabs from closing
		 * sets maximum tab pane width
		 */
		lefttabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		lefttabs.setMaxWidth(257);
		/*
		 * sets locations of objects in borderpane (this)
		 */
		setTop(toolmenubars);
		setLeft(lefttabs);
		setCenter(splitpane);
		/*
		 * css files are loaded to aid with styling of UI
		 */
		toolmenubars.getStylesheets().add("toolbar.css");
		lefttabs.getStylesheets().add("tabs.css");
		splitpane.getStylesheets().add("splitpane.css");
	}
}
