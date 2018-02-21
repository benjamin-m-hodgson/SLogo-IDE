import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mediator.Controller;
import screen.StartScreen;

/** 
 * Use the driver JavaFX program to start and animate a simple Logo interpreter.
 *
 * @author Benjamin Hodgson
 * @date 2/18/18
 *
 * 
 */
public class Driver extends Application {  
    
    private final double DEFAULT_HEIGHT = 650;
    private final double DEFAULT_WIDTH = 900;
    private String DEFAULT_CSS = Controller.class.getClassLoader().
	    getResource("default.css").toExternalForm(); 
    
    /**
     * Initialize the program and begin the animation loop 
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
	Controller programController = new Controller(primaryStage);
	StartScreen startScreen = new StartScreen(programController);
	Parent programRoot = startScreen.getRoot();
	Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	programScene.getStylesheets().add(DEFAULT_CSS);
	primaryStage.setScene(programScene);
	primaryStage.show();		
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
