package mediator;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import screen.UserScreen;
import turtle.Turtle;

public class Controller throws IllegalArgumentException{
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
    
    /**
     * Makes a new Turtle given a name, an ImageView (previously attached to the Stage), a penColor, and an empty Group
     * that has already been attached to the Stage to hold lines for the pen
     */
    public double makeNewTurtleCommand(String name, ImageView turtleImage, 
	    Color penColor, Group penLines) {
	return 0.0;
    }
    
    /**
     * Returns an UnmodifiableMap of variables to their values
     */
    public Map<String, Double> getVariables() {
	return null;
    }
    /**
     * Returns an ImmutableList of available User Commands
     */
    
    public List<String> getUserCommands() {
	return null;
    }
    /**
     * Parses input from a text field or button press by the user
     */
    
    public double parseInput(String userTextInput) {
	return 0.0;
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
    
    public List<Turtle> onScreenTurtles() {
	return null;
    }
    
    private void findResources(String language) {
	//DEFAULT_LANGUAGE = ResourceBundle.getBundle(language);
    }
}
