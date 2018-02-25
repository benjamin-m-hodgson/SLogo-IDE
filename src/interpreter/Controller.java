package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import interpreter.TextFieldParser;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import screen.ErrorScreen;
import screen.StartScreen;
import screen.UserScreen;

public class Controller {
    private final String FILE_ERROR_KEY = "FileErrorPrompt";
    private final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
    private final String SYNTAX_FILE_NAME = "Syntax.properties";
    private final String DEFAULT_LANGUAGE = "English";
    private final String DEFAULT_SETTINGS = "settings";
    // TODO: Read this in from files rather than storing as instance variables
    private final double DEFAULT_HEIGHT = 650;
    private final double DEFAULT_WIDTH = 900;
    private String DEFAULT_CSS = Controller.class.getClassLoader().
	    getResource("default.css").toExternalForm(); 
    private ResourceBundle CURRENT_TEXT_DISPLAY;
    private ResourceBundle CURRENT_ERROR_DISPLAY;
    private ResourceBundle CURRENT_LANGUAGE;
    private ResourceBundle CURRENT_SETTINGS;
    private Stage PROGRAM_STAGE;
    // TODO: add in program titles
    private String PROGRAM_TITLE;
    
    private TextFieldParser myTextFieldParser;
    	private Map<String, Double> myVariables; 
	private List<String> myCommandHistory; 

    public Controller(Stage primaryStage) {
	PROGRAM_STAGE = primaryStage;
	//myTextFieldParser = new TextFieldParser();
	myVariables = new HashMap<String, Double>();
	myCommandHistory = new ArrayList<String>(); 
	findSettings();
	findResources(DEFAULT_LANGUAGE);
    }
    
    //TODO what does this wrap?
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
     * 
     * @return ReadOnlyDoubleProperty: the height property of the application
     */
    public ReadOnlyDoubleProperty getHeightProperty() {
	return PROGRAM_STAGE.heightProperty();
    }
    
    /**
     * 
     * @return ReadOnlyDoubleProperty: the height property of the application
     */
    public ReadOnlyDoubleProperty getWidthProperty() {
	return PROGRAM_STAGE.widthProperty();
    }

    /**
     * Returns an ImmutableList of available User Commands
     */
    public List<String> getUserCommands() {
	return null;
    }
    
    /**
     * 
     * @return an ImmutableList of all of the language options
     */
    public List<String> getLanguages() {
	String currentDir = System.getProperty("user.dir");
	try {
	    File file = new File(currentDir + File.separator + "languages");
	    File syntaxFile = new File(currentDir + File.separator + "languages" + File.separator
		    + SYNTAX_FILE_NAME);
	    File[] languageFiles = file.listFiles();
	    List<String> languages = new ArrayList<String>();
	    for (File languageFile : languageFiles) {
		// ignore the syntax file used for input parsing
		if (!languageFile.equals(syntaxFile)) { 
		    String languageName = languageFile.getName();
		    String[] nameSplit = languageName.split("\\.");
		    String language = nameSplit[0];
		    languages.add(language);
		}
	    }
	    return Collections.unmodifiableList(languages);
	}
	catch (Exception e) {
	    // TODO: make custom exception super class with sub classes for specifications
	    //String specification = "%nFailed to find language files";
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
	}

	/**
	 * Returns an UnmodifiableMap of variables to their values
	 */
	public Map<String, Double> getVariables() {
		return myTextFieldParser.getVariables();
	}
	catch (Exception e) {
	    loadErrorScreen(resourceErrorText(SCREEN_ERROR_KEY));
	}

	/**
	 * 
	 * @return ReadOnlyDoubleProperty: the height property of the application
	 */
	public ReadOnlyDoubleProperty getHeightProperty() {
		return PROGRAM_STAGE.heightProperty();
	}
	catch (Exception e) {
	    // TODO: make screen error exception class to handle error specification
	    loadErrorScreen(resourceErrorText(SCREEN_ERROR_KEY));
	}

	//    public List<Turtle> onScreenTurtles() {
	//	return null;
	//    }

	/**
	 * 
	 * @return an ImmutableList of all of the language options
	 */
	public List<String> getLanguages() {
		String currentDir = System.getProperty("user.dir");
		try {
			File file = new File(currentDir + File.separator + "languages");
			File syntaxFile = new File(currentDir + File.separator + "languages" + File.separator
					+ SYNTAX_FILE_NAME);
			File[] languageFiles = file.listFiles();
			List<String> languages = new ArrayList<String>();
			for (File languageFile : languageFiles) {
				// ignore the syntax file used for input parsing
				if (!languageFile.equals(syntaxFile)) { 
					String languageName = languageFile.getName();
					String[] nameSplit = languageName.split("\\.");
					String language = nameSplit[0];
					languages.add(language);
				}
			}
			return Collections.unmodifiableList(languages);
		}
		catch (Exception e) {
			String specification = "%nFailed to find language files";
			loadErrorScreen(FILE_ERROR_PROMPT + specification);
		}
		return Collections.unmodifiableList(new ArrayList<String>());
	}
	catch (MalformedURLException e) {
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
	}

	/**
	 * Parses input from a text field or button press by the user
	 * @throws TurtleNotFoundException 
	 */
	public double parseInput(String userTextInput) throws TurtleNotFoundException {
		myCommandHistory.add(userTextInput); // TODO consider whether we should only add if the command is valid? 
		return myTextFieldParser.parseText(userTextInput);
	}
	catch (MalformedURLException e) {
	    // TODO: make screen error exception class to handle error specification
	    //String specification = "%nFailed to find settings files";
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
	}
    }

    /**
     * Creates an Error Screen to display to the user indicating an error type by the String
     * @param errorMessage. 
     * 
     * @param errorMessage: The message to be displayed to the user on the Error Screen
     */
    private void loadErrorScreen(String errorMessage) {
	ErrorScreen errorScreen = new ErrorScreen(this, errorMessage);
	Parent errorScreenRoot = errorScreen.getRoot();
	Scene errorScene = new Scene(errorScreenRoot);
	errorScene.getStylesheets().add(DEFAULT_CSS);
	PROGRAM_STAGE.setScene(errorScene);
    }
    
    /**
     * 
     * @param key
     * @return
     */
    private String resourceErrorText(String key) {
	return CURRENT_ERROR_DISPLAY.getString(key);
    }
}
