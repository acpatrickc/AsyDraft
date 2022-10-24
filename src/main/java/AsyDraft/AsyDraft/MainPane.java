package AsyDraft.AsyDraft;

import AsyDraft.AsyDraft.IconManager.Icons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends BorderPane {
		
	Button undo;
    Button redo;
    Button mouse;
    Button segment;
    Button arrow;
    Button reversearrow;
    Button midarrow;
    Button doublearrow;
	Button circle;
	Button incircle;
	Button circumcircle;
	Button tangent;
	Button parallel;
	Button perpendicular;
	Button rightangle;
	Button point;
	Button anglebisector;
	Button congruentangle;
	Button congruentsegment;
	Button congruentarc;
	Button label;
	Button center;

	ComboBox<String> backgroundchoice;
	ComboBox<String> selectionmode;

	ToggleGroup drawmodegroup;
	
	RadioButton snap;
	RadioButton free;
	
	/*
	 * TODO
	 * used for switching between custom labels and alphabetical labels
	 * 
	ToggleGroup labelsettingsgroup;
	RadioButton alphabetical;
	RadioButton custom;
	*/
	
	ToolBar settingstoolbar;
	ToolBar drawingtoolbar;
	
	MenuBar menu;

	VBox toolmenubars;
	
	TextArea resultarea;
	
	//TODO AsyEditor editor;
	SplitPane splitpane;
	
	TitledPane anglecongruences;
	TitledPane segmentcongruences;
	TitledPane arccongruences;
	
	Accordion congruences;
	
	Tab congruencetab;
	
	TitledPane pointlabels;
	TitledPane segmentlabels;
	
	Accordion labels;
	
	Tab componentstab;
	
	Tab snaptab;
	Tab appearancetab;
	
	TitledPane linepane;
	TitledPane pointpane;
	TitledPane circlepane;
	TitledPane labelpane;
	
	Accordion componentsaccordion;
	
	TabPane lefttabs;
	
	public MainPane() {
		
		/*
		 * These are the buttons used to toggle each draw function of this program
		 */

		undo = new Button("", IconManager.getIcon(Icons.undo));
        undo.setTooltip(new Tooltip("undo"));
        
        redo = new Button("", IconManager.getIcon(Icons.redo));
        redo.setTooltip(new Tooltip("redo"));
        
		mouse = new Button("", IconManager.getIcon(Icons.mouse));
        mouse.setTooltip(new Tooltip("mouse"));
        
        segment = new Button("", IconManager.getIcon(Icons.segment));
        segment.setTooltip(new Tooltip("line segment"));
        
        arrow = new Button("", IconManager.getIcon(Icons.arrow));
        arrow.setTooltip(new Tooltip("end arrow segment"));
        
        reversearrow = new Button("", IconManager.getIcon(Icons.reversearrow));
        reversearrow.setTooltip(new Tooltip("start arrow segment"));
        
        midarrow = new Button("", IconManager.getIcon(Icons.midarrow));
        midarrow.setTooltip(new Tooltip("mid-arrow segment"));
        
        doublearrow = new Button("", IconManager.getIcon(Icons.doublearrow));
        doublearrow.setTooltip(new Tooltip("double arrow segment"));
		
        point = new Button("", IconManager.getIcon(Icons.point));
        point.setTooltip(new Tooltip("point"));
		
        circle = new Button("", IconManager.getIcon(Icons.circle));
		circle.setTooltip(new Tooltip("circle"));
		
		label = new Button("", IconManager.getIcon(Icons.label));
		label.setTooltip(new Tooltip("label"));
		
		center = new Button("", IconManager.getIcon(Icons.center));
		incircle = new Button("", IconManager.getIcon(Icons.incircle));
		
		circumcircle = new Button("", IconManager.getIcon(Icons.circumcircle));
		tangent = new Button("", IconManager.getIcon(Icons.tangent));
		
		parallel = new Button("", IconManager.getIcon(Icons.parallel));
		perpendicular = new Button("", IconManager.getIcon(Icons.perpendicular));
		
		rightangle = new Button("", IconManager.getIcon(Icons.rightangle));
		
		anglebisector = new Button("", IconManager.getIcon(Icons.anglebisector));
		
		congruentangle = new Button("", IconManager.getIcon(Icons.congruentangle));
		
		congruentsegment = new Button("", IconManager.getIcon(Icons.congruentsegment));
		
		congruentarc = new Button("", IconManager.getIcon(Icons.congruentarc));
		
		/*
		 * choice of background of editor pane
		 */
		
		backgroundchoice = new ComboBox<>();
		backgroundchoice.getItems().add("pegboard");
		backgroundchoice.getItems().add("grid");
		backgroundchoice.getItems().add("blank");
		backgroundchoice.getSelectionModel().select("grid");
		backgroundchoice.valueProperty().addListener(e -> {
			switch (backgroundchoice.getValue()) {
				case "pegboard":
	        		//TODO 
					break;
					
				case "grid":
	        		//TODO 
					break;
					
				case "blank":
	        		//TODO 
					break;
			}
		});
		
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
		selectionmode.valueProperty().addListener(e -> {
			switch (selectionmode.getValue()) {
				case "drop selection":
	        		//TODO 
					break;
					
				case "loop selection":
	        		//TODO 
					break;
					
				case "lock selection":
	        		//TODO 
					break;
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
			public void handle(ActionEvent e) {
        		//TODO 
				
			}
		});
		snap.setSelected(true);
		free = new RadioButton("free mode");
		free.setToggleGroup(drawmodegroup);
		free.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
        		//TODO 
				
			}
		});
		
		/*
		labelsettingsgroup = new ToggleGroup();
		
		alphabetical = new RadioButton("alphabetical labels");
		alphabetical.setToggleGroup(labelsettingsgroup);
		alphabetical.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		custom = new RadioButton("custom labels");
		custom.setToggleGroup(labelsettingsgroup);
		custom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		
		
		settingstoolbar = new ToolBar(undo, redo, new Separator()
			, backgroundchoice, selectionmode, snap, free, new Separator()
			/*
			, editor.getLabelSettings(), new Separator() 
			, center, editor.getSizeSlider() 
			*/
			, new Separator());
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
		
		//toolbar.getStyleClass().add("toolbar");
		
		menu = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("Help"));
		
		toolmenubars = new VBox(menu, settingstoolbar, drawingtoolbar);
		
		resultarea = new TextArea();
		
		
		//editscroll = new ScrollPane(editor);
		//REMOVE LATER
		//primaryStage.setOnShown(e -> {editscroll.lookup(".viewport").setStyle("-fx-background: rgb(210, 210, 210)");});
		//^^^^^^^^^^^^^^^^
		splitpane = new SplitPane(/*TODO*/ new Pane(), resultarea);
		
		anglecongruences = new TitledPane("angle congruences", new Pane());
		segmentcongruences = new TitledPane("segment congruences", new Pane());
		arccongruences = new TitledPane("arc congruences", new Pane());
		
		congruences = new Accordion(anglecongruences, segmentcongruences, arccongruences);
		
		congruencetab = new Tab("congruences", congruences);
		
		pointlabels = new TitledPane("point labels", new Pane());
		segmentlabels = new TitledPane("segment labels", new Pane());
		
		labels = new Accordion(pointlabels, segmentlabels);
		
		linepane = new TitledPane("lines", new Pane()/*, editor.getLineList()*/);
		pointpane = new TitledPane("points", new Pane()/*, editor.getPointList()*/);
		circlepane = new TitledPane("circles", new Pane()/*, editor.getCircleList()*/);
		labelpane = new TitledPane("labels", new Pane()/*, editor.getLabelList()*/);
		
		componentsaccordion = new Accordion(linepane, pointpane, circlepane, labelpane);
		
		componentstab = new Tab("components", componentsaccordion);

		snaptab = new Tab("snap settings", new Pane() /*editor.getSnapSettings()*/);
		
		appearancetab = new Tab("appearance", new Pane());
		
		lefttabs = new TabPane(componentstab, snaptab, appearancetab);
		
		lefttabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		lefttabs.setMaxWidth(257);
		
		setTop(toolmenubars);
		setLeft(lefttabs);
		setCenter(splitpane);
		
		// css files
		toolmenubars.getStylesheets().add("toolbar.css");
		lefttabs.getStylesheets().add("tabs.css");
		splitpane.getStylesheets().add("splitpane.css");
	}
}
