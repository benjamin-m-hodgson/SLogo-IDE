package mediator;

import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import screen.UserScreen;

public class Controller {
    
    //private final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private String DEFAULT_CSS = Controller.class.getClassLoader().
	    getResource("default.css").toExternalForm(); 
    private ResourceBundle DEFAULT_TEXT_DISPLAY;
    private ResourceBundle DEFAULT_ERROR_DISPLAY;
    private ResourceBundle DEFAULT_LANGUAGE;
    /*private final String DEFAULT_STYLESHEET = 
	    Driver.class.getClassLoader().getResource("default.css").toExternalForm();*/
    private Stage PROGRAM_STAGE;

    public Controller(Stage primaryStage) {
	PROGRAM_STAGE = primaryStage;
    }
    
    // TODO: get language and call findResources(String language)
    public void loadUserScreen() {
	UserScreen programScreen = new UserScreen(this);
	Parent programRoot = programScreen.getRoot();
	Scene programScene = new Scene(programRoot);	
	programScene.getStylesheets().add(DEFAULT_CSS);
	PROGRAM_STAGE.setScene(programScene);
	// TODO: fix below
	findResources("English");
    }
    
    private void findResources(String language) {
	//DEFAULT_LANGUAGE = ResourceBundle.getBundle(language);
    }
}
