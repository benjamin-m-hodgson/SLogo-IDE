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
import java.util.MissingResourceException;
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
	private final String DEFAULT_COLOR = "Grey";
	private final String DEFAULT_SETTINGS = "settings";
	// TODO: Read this in from files rather than storing as instance variables
	private final double DEFAULT_HEIGHT = 650;
	private final double DEFAULT_WIDTH = 900;
	private String DEFAULT_CSS = Controller.class.getClassLoader().
			getResource("default.css").toExternalForm(); 
	private ResourceBundle CURRENT_TEXT_DISPLAY;
	private ResourceBundle CURRENT_ERROR_DISPLAY;
	private ResourceBundle CURRENT_BACKGROUND_COLOR;
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
	myTextFieldParser = new TextFieldParser();
	myVariables = new HashMap<String, Double>();
	myCommandHistory = new ArrayList<String>(); 
	findSettings();
	findResources(DEFAULT_LANGUAGE);
    }

    /**
     * Makes a new Turtle given a name, an ImageView (previously attached to the Stage), a penColor, and an empty Group
     * that has already been attached to the Stage to hold lines for the pen
     */
    public double makeNewTurtleCommand(String name, ImageView turtleImage, Color penColor, Group penLines) {
    		myTextFieldParser.addNewTurtle(name, turtleImage, penColor, penLines);
    		return 1.0; 
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
	return Collections.unmodifiableList(new ArrayList<String>());
    }

	/**
	 * 
	 * @return an ImmutableList of all of the color options
	 */
	public List<String> getColors() {
		String currentDir = System.getProperty("user.dir");
		try {
			File file = new File(currentDir + File.separator + "colors");
			File[] colorFiles = file.listFiles();
			List<String> colors = new ArrayList<String>();
			for (File colorFile : colorFiles) {
				String colorName = colorFile.getName();
				String[] nameSplit = colorName.split("\\.");
				String color = nameSplit[0];
				colors.add(color);
			}
			return Collections.unmodifiableList(colors);
		}
		catch (Exception e) {
			// TODO: make custom exception super class with sub classes for specifications
			//String specification = "%nFailed to find color files";
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		}
		return Collections.unmodifiableList(new ArrayList<String>());
	}
	
	public List<String> translateColors(List<String> colors){
		List<String> translatedColors = new ArrayList<String>();
		for(int i =0; i<colors.size(); i++) {
			translatedColors.add(resourceDisplayText(colors.get(i)));
		}
		return translatedColors;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String resourceDisplayText(String key) {
		return CURRENT_TEXT_DISPLAY.getString(key);
	}


    /**
     * Parses input from a text field or button press by the user
     * @throws MissingInformationException 
     * @throws UnidentifiedCommandException 
     * @throws BadFormatException 
     * @throws TurtleNotFoundException 
     */
    public double parseInput(String userTextInput) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
	return myTextFieldParser.parseText(userTextInput);
    }

    public void loadStartScreen() {
	try {
	    StartScreen startScreen = new StartScreen(this);
	    // test the ErrorScreen
	    //ErrorScreen startScreen = new ErrorScreen(this, START_ERROR_PROMPT);
	    Parent programRoot = startScreen.getRoot();
	    Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	    programScene.getStylesheets().add(DEFAULT_CSS);
	    PROGRAM_STAGE.setScene(programScene);
	    PROGRAM_STAGE.show();	
	}
	catch (Exception e) {
	    loadErrorScreen(resourceErrorText(SCREEN_ERROR_KEY));
	}
    }

    public void loadUserScreen() {
	try {
	    UserScreen programScreen = new UserScreen(this);
	    Parent programRoot = programScreen.getRoot();
	    Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);	
	    programScene.getStylesheets().add(DEFAULT_CSS);
	    PROGRAM_STAGE.setScene(programScene);
	}
	catch (Exception e) {
	    // TODO: make screen error exception class to handle error specification
	    e.printStackTrace();
	    loadErrorScreen(resourceErrorText(SCREEN_ERROR_KEY));
	}
    }

    //    public List<Turtle> onScreenTurtles() {
    //	return null;
    //    }

    /**
     * Searches through the class path to find the appropriate resource files to use for 
     * the program. If it can't locate the files, it displays an error screen to the user
     * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
     * 
     * @param language: The language to define which .properties files to use in the Program
     */
    private void findResources(String language) {
	String currentDir = System.getProperty("user.dir");
	try {
	    File file = new File(currentDir);
	    URL[] urls = {file.toURI().toURL()};
	    ClassLoader loader = new URLClassLoader(urls);
	    try {
		CURRENT_TEXT_DISPLAY = ResourceBundle.getBundle(language + "Prompts", 
			Locale.getDefault(), loader);
		CURRENT_ERROR_DISPLAY = ResourceBundle.getBundle(language + "Errors", 
			Locale.getDefault(), loader);
	    }
	    // if .properties file doesn't exist for specified language, default to English
	    catch (Exception e) {
		CURRENT_TEXT_DISPLAY = ResourceBundle.getBundle(DEFAULT_LANGUAGE + "Prompts", 
			Locale.getDefault(), loader);
		CURRENT_ERROR_DISPLAY = ResourceBundle.getBundle(DEFAULT_LANGUAGE + "Errors", 
			Locale.getDefault(), loader);
	    }
	    CURRENT_LANGUAGE = ResourceBundle.getBundle(language, Locale.getDefault(), loader);
	    // TODO: fix -> myTextFieldParser.changeLanguage(CURRENT_LANGUAGE);
	}
	catch (MalformedURLException e) {
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
	}
    }

	public String changeBackgroundColor(String color) {
		findColorFile(color);
		try {
			return CURRENT_BACKGROUND_COLOR.getString(color+"Code");
		}
		catch(MissingResourceException e){
			try {
			findColorFile(DEFAULT_COLOR);
			return CURRENT_BACKGROUND_COLOR.getString(color+"Code");
			}
			catch(MissingResourceException e1) {
				loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
				return null;
			}
		}
	}


	/**
	 * Searches through the class path to find the appropriate resource files to use for 
	 * the program. If it can't locate the files, it displays an error screen to the user
	 * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
	 * 
	 * @param language: The language to define which .properties files to use in the Program
	 */
	private void findColorFile(String color) {
		String currentDir = System.getProperty("user.dir");
		try {
			File file = new File(currentDir);
			URL[] urls = {file.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			try {
				CURRENT_BACKGROUND_COLOR = ResourceBundle.getBundle(color, 
						Locale.getDefault(), loader);

			}
			// if .properties file doesn't exist for specified language, default to English
			catch (Exception e) {
				CURRENT_BACKGROUND_COLOR = ResourceBundle.getBundle(DEFAULT_COLOR, 
						Locale.getDefault(), loader);
			}
		}
		catch (MalformedURLException e) {
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		}
	}

	/**
	 * Searches through the class path to find the appropriate settings resource file to use for 
	 * the program. If it can't locate the file, it displays an error screen to the user
	 * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
	 */
	private void findSettings() {
		String currentDir = System.getProperty("user.dir");
		try {
			File file = new File(currentDir);
			URL[] urls = {file.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			CURRENT_SETTINGS = ResourceBundle.getBundle(DEFAULT_SETTINGS, 
					Locale.getDefault(), loader);
		}
		catch (MalformedURLException e) {
			// TODO: make screen error exception class to handle error specification
			//String specification = "%nFailed to find settings files";
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		}
	}
    /**
     * Change the Language. Changes the prompts displayed in the user interface as well as
     * acceptable commands by changing the ResourceBundles used by the program.
     * 
     * @param language: the new language to be used in the program
     */
    public void changeLanguage(String language) {
	findResources(language);
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

