package ui.comp3111;

import core.comp3111.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main class of this GUI application
 *
 * @author cspeter
 *
 */
public class Main extends Application {
    // Attributes: Scene and Stage
	private MainScreen Screen;
    protected static final int SCENE_NUM = 5;
    private static final String[] SCENE_TITLES = { "COMP3111 Chart - [Team Name]", "Sample Line Chart Screen", "Animated Chart" };
    protected static Stage stage = null;
    public static Scene[] scenes = null;

    // To keep this application more structural,
    // The following UI components are used to keep references after invoking
    // createScene()

    /**
     * create all scenes in this application
     */
    private void initScenes() {
        scenes = new Scene[SCENE_NUM];
        scenes[0] = new Scene(Screen.pane(), 1280, 720);
        for (Scene s : scenes) {
            if (s != null)
                // Assumption: all scenes share the same stylesheet
                s.getStylesheets().add("Main.css");
        }
    }

    /**
     * This method is used to pick anyone of the scene on the stage. It handles the
     * hide and show order. In this application, only one active scene should be
     * displayed on stage.
     *
     * @param sceneID
     *            - The sceneID defined above (see SCENE_XXX)
     */
    protected static void putSceneOnStage(int sceneID) {
        // ensure the sceneID is valid
        if (sceneID < 0 || sceneID >= SCENE_NUM)
            return;

        stage.hide();
        stage.setTitle(SCENE_TITLES[sceneID]);
        stage.setScene(scenes[sceneID]);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * All JavaFx GUI application needs to override the start method You can treat
     * it as the main method (i.e. the entry point) of the GUI application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage; // keep a stage reference as an attribute
            Screen = new MainScreen();
            initScenes(); // initialize the scenes
            putSceneOnStage(0); // show the main screen
        } catch (Exception e) {
            e.printStackTrace(); // exception handling: print the error message on the console
        }
    }
    /**
     * main method - only use if running via command line
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}