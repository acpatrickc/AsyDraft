package AsyDraft.ui;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Init extends Application {
	/*
	 * starts application and creates a scene with a MainPane
	 */
    @Override
    public void start(Stage stage) {
    	Scene scene = new Scene(new MainPane(), 1000, 600);
        stage.setScene(scene);
        stage.show();
    }
    /*
     * main method
     * loads JLatexMath, as it seems slow to start during drawing
     */
    public static void main(String[] args) {
    	TeXFormula.setDPITarget(120);
		new TeXFormula("test").createTeXIcon(TeXConstants.STYLE_DISPLAY, 12);
        launch();
    }

}