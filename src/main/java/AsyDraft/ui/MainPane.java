package AsyDraft.ui;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import AsyDraft.ui.EditorPane.Style;
import AsyDraft.ui.FunctionPointTracker.FunctionSelectionMode;
import AsyDraft.ui.FunctionPointTracker.Functions;
import AsyDraft.ui.IconManager.Icons;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
	/*
	 * draw function buttons
	 */
	private ToolBarItem undo;
	private ToolBarItem redo;
	private ToolBarItem mouse;
	private ToolBarItem segment;
	private ToolBarItem endarrow;
	private ToolBarItem beginarrow;
	private ToolBarItem midarrow;
	private ToolBarItem doublearrow;
	private ToolBarItem circle;
	private ToolBarItem incircle;
	private ToolBarItem circumcircle;
	private ToolBarItem tangent;
	private ToolBarItem parallel;
	private ToolBarItem perpendicular;
	private ToolBarItem rightangle;
	private ToolBarItem dot;
	private ToolBarItem anglebisector;
	private ToolBarItem congruentangle;
	private ToolBarItem congruentsegment;
	private ToolBarItem congruentarc;
	private ToolBarItem label;
	private ToolBarItem center;
	/*
	 * choice of background and mode of selection
	 */
	private ComboBox<String> backgroundchoice;
	private ComboBox<String> selectionmode;
	/*
	 * drawing mode selection
	 */
	private ToggleGroup drawmodegroup;
	private RadioButton snap;
	private RadioButton free;
	/*
	 * two toolbars
	 */
	private VBox toolmenubars;
	private ToolBar settingstoolbar;
	private ToolBar drawingtoolbar;
	/*
	 * top menu bar
	 */
	private MenuBar menu;
	/*
	 * result area, where final asymptote code shows
	 * editor pane and pane that splits 
	 */
	private CodeArea resultarea;
	private EditorPane editor;
	private SplitPane splitpane;
	/*
	 * left vertical box
	 * pen chooser for drawing objects
	 * tabs which display drawn objects and settings
	 * area which drawn objects will be listed
	 * Hierarchy:
	 * TabPane -> Tab -> Accordion -> TitledPane
	 */
	private VBox leftbox;
	private PenChooser penchooser;
	private TabPane lefttabs;
	private Tab componentstab;
	private Tab congruencetab;
	private Tab snaptab;
	private Tab appearancetab;
	private Accordion componentsaccordion;
	private Accordion congruences;
	private Accordion labels;
	private TitledPane linepane;
	private TitledPane pointpane;
	private TitledPane circlepane;
	private TitledPane labelpane;
	private TitledPane anglecongruences;
	private TitledPane segmentcongruences;
	private TitledPane arccongruences;
	private TitledPane pointlabels;
	private TitledPane segmentlabels;
	
	public MainPane() {
		/*
		 * resultarea where code is outputted
		 * the editor pane, where all drawing is done
		 */
		resultarea = new CodeArea();
		resultarea.setParagraphGraphicFactory(LineNumberFactory.get(resultarea));
		resultarea.setWrapText(true);
		editor = new EditorPane(10, 10, 50);
		/*
		 * initiates buttons used to toggle each function of this program
		 */
		undo = new ToolBarItem(Icons.undo, "undo", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.undo();
			}
		});
		redo = new ToolBarItem(Icons.redo, "redo", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.redo();
			}
		});
		mouse = new ToolBarItem(Icons.mouse, "mouse", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.nofunction);
			}
		});
		segment = new ToolBarItem(Icons.segment, "segment", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.segment);
			}
		});
		endarrow = new ToolBarItem(Icons.endarrow, "end arrow", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.endarrow);
			}
		});
		beginarrow = new ToolBarItem(Icons.beginarrow, "begin arrow", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.beginarrow);
			}
		});
		midarrow = new ToolBarItem(Icons.midarrow, "mid arrow", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.midarrow);
			}
		});
		doublearrow = new ToolBarItem(Icons.doublearrow, "double arrow", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.doublearrow);
			}
		});
		dot = new ToolBarItem(Icons.dot, "dot", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.dot);
			}
		});
		circle = new ToolBarItem(Icons.circle, "circle", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.circle);
			}
		});
		label = new ToolBarItem(Icons.label, "label", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setFunction(Functions.label);
			}
		});
		center = new ToolBarItem(Icons.center, "center", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.center();
			}
		});
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
		backgroundchoice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (backgroundchoice.getValue()) {
					case "pegboard":
						editor.setStyle(Style.pegboard);
						break;
					case "grid":
						editor.setStyle(Style.grid);
						break;
					case "blank":
						editor.setStyle(Style.blank);
						break;
				}
			}
		});
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
		selectionmode.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (selectionmode.getValue()) {
					case "drop selection":
						editor.setSelectionMode(FunctionSelectionMode.drop);
						break;
					case "loop selection":
						editor.setSelectionMode(FunctionSelectionMode.loop);
						break;
					case "lock selection":
						editor.setSelectionMode(FunctionSelectionMode.lock);
						break;
				}
			}
		});
		/*
		 * Drawing modes
		 * snap - snaps to snappable points (lattice, midpoints, etc.)
		 */
		drawmodegroup = new ToggleGroup();
		snap = new RadioButton("snap mode");
		snap.setToggleGroup(drawmodegroup);
		snap.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setSnap(true);
			}
		});
		snap.setSelected(true);
		free = new RadioButton("free mode");
		free.setToggleGroup(drawmodegroup);
		free.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				editor.setSnap(false);
			}
		});
		/*
		 * adds toolbar items to toolbars
		 * separators group items into semi-related categories
		 */
		settingstoolbar = new ToolBar(undo, redo, new Separator()
			, backgroundchoice, selectionmode, snap, free, new Separator()
			, editor.getLabelSettings(), new Separator()
			, center);
		settingstoolbar.getStyleClass().add("settingstoolbar");
		drawingtoolbar = new ToolBar(mouse, new Separator()
			, segment, endarrow, beginarrow, midarrow, doublearrow, new Separator()
			, dot, new Separator()
			, label, new Separator()
			, circle, new Separator()
			, tangent, incircle, circumcircle, new Separator()
			, parallel, perpendicular, new Separator()
			, anglebisector, congruentangle, rightangle, congruentsegment, congruentarc, new Separator());
		drawingtoolbar.getStyleClass().add("drawingtoolbar");
		/*
		 * menu bar, file open etc...
		 */
		menu = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("Help"));
		toolmenubars = new VBox(menu, settingstoolbar, drawingtoolbar);
		splitpane = new SplitPane(editor, resultarea);
		splitpane.setDividerPositions(0.6);
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
		 */
		lefttabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		/*
		 * pen chooser
		 * left split pane, pen chooser and tabs
		 * sets maximum width
		 */
		penchooser = new PenChooser(editor);
		leftbox = new VBox(penchooser, lefttabs);
		leftbox.setMaxWidth(260);
		/*
		 * sets locations of objects in borderpane (this)
		 */
		setTop(toolmenubars);
		setLeft(leftbox);
		setCenter(splitpane);
		/*
		 * css files are loaded to aid with styling of UI
		 */
		toolmenubars.getStylesheets().add("toolbar.css");
		lefttabs.getStylesheets().add("tabs.css");
		splitpane.getStylesheets().add("splitpane.css");
		penchooser.getStylesheets().add("penchooser.css");
		/*
		 * centers the editor after initializing
		 */
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				editor.center();
			}
		});
	}
}
