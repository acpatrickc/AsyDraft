package AsyDraft.ui;

import javafx.application.Application;
import javafx.scene.Scene;
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
     */
    public static void main(String[] args) {
        launch();
    }

}