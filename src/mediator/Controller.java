package mediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import interpreter.TextFieldParser;
import interpreter.Turtle;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import screen.ErrorScreen;
import screen.UserScreen;

public class Controller {
	//private final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private String DEFAULT_CSS = Controller.class.getClassLoader().
			getResource("default.css").toExternalForm(); 
	private ResourceBundle DEFAULT_TEXT_DISPLAY;
	private ResourceBundle DEFAULT_ERROR_DISPLAY;
	private ResourceBundle DEFAULT_LANGUAGE_RESOURCE;
	// TODO: Read this in from files rather than storing as instance variables
	private final double DEFAULT_HEIGHT = 650;
	private final double DEFAULT_WIDTH = 900;
	/*private final String DEFAULT_STYLESHEET = 
	    Driver.class.getClassLoader().getResource("default.css").toExternalForm();*/
	private Stage myStage;

	private TextFieldParser myTextFieldParser;
	private Map<String, Double> myVariables; 
	private List<String> myCommandHistory; 

	public Controller(Stage primaryStage) {
		myStage = primaryStage;
		// TODO figure out where to put Controller in relation to TextFieldParser
		// TODO figure out how TextFieldParser will get language
		myTextFieldParser = new TextFieldParser();
		myVariables = new HashMap<String, Double>();
		myCommandHistory = new ArrayList<String>(); 
	}

	// TODO what does this wrap? 
	/**
	 * Makes a new Turtle given a name, an ImageView (previously attached to the Stage), a penColor, and an empty Group
	 * that has already been attached to the Stage to hold lines for the pen
	 */
	public double makeNewTurtleCommand(String name, ImageView turtleImage, Color penColor, Group penLines) {
		return 0.0;
	}

	/**
	 * Returns an UnmodifiableMap of variables to their values
	 */
	public Map<String, Double> getVariables() {
		return myVariables;
	}

	/**
	 * Returns an ImmutableList of available User Commands
	 */
	public List<String> getUserCommands() {
		return myCommandHistory;
	}

	/**
	 * Parses input from a text field or button press by the user
	 */
	public double parseInput(String userTextInput) {
		return myTextFieldParser.parseText(userTextInput);
	}

	// TODO: get language and call findResources(String language)
	public void loadUserScreen() {
		try {
			UserScreen programScreen = new UserScreen(this);
			Parent programRoot = programScreen.getRoot();
			Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);	
			programScene.getStylesheets().add(DEFAULT_CSS);
			myStage.setScene(programScene);
			// TODO: fix below
			findResources("English");
		}
		catch (Exception e) {
			String errorMessage = "Error loading User Screen!";
			ErrorScreen errorScreen = new ErrorScreen(this, errorMessage);
			Parent errorScreenRoot = errorScreen.getRoot();
			Scene errorScene = new Scene(errorScreenRoot);
			myStage.setScene(errorScene);
		}
	}

	// TODO necessary? 
	public List<Turtle> onScreenTurtles() {
		return null;
	}

	/**
	 * Change the Language. Changes the prompts displayed in the user interface as well as
	 * acceptable commands.
	 * 
	 * @param language: the new language to be used in the program
	 */
	public void changeLanguage(String language) {

	}

	private void findResources(String language) {
		//DEFAULT_LANGUAGE = ResourceBundle.getBundle(language);
	}
}
