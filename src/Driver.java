import interpreter.Controller;
import javafx.application.Application;
import javafx.stage.Stage;


/** 
 * Use the driver JavaFX program to start and animate a simple Logo interpreter.
 *
 * @author Benjamin Hodgson
 * @date 2/18/18
 *
 * 
 */
public class Driver extends Application {  
    
    /**
     * Initialize the program and begin the animation loop 
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
	Controller programController = new Controller(primaryStage);
	programController.loadStartScreen();
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
