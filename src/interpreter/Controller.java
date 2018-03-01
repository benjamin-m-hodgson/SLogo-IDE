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

/**
 * 
 * @author Ben Hodgson
 * 
 * The main class for reading in data files and relaying information about these files 
 * to the front end. Also acts as a mediator and handles front end to back end communication.
 */
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
    private ResourceBundle CURRENT_PEN_COLOR;
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
    public double makeNewTurtleCommand(String name, ImageView turtleImage, String penColor, Group penLines) {
	System.out.println("is it null" + turtleImage==null);
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
     * Loops through the files in the "languages" sub directory to determine which 
     * language options the program can support.
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
     * Loops through the files in the "colors" sub directory to determine which 
     * color options the program can support. 
     * 
     * @return an ImmutableList of all of the color options
     */
    public List<String> getFileNames(String folderName) {
	String currentDir = System.getProperty("user.dir");
	try {
	    File file = new File(currentDir + File.separator + folderName);
	    File[] fileArray = file.listFiles();
	    List<String> fileNames = new ArrayList<String>();
	    for (File aFile : fileArray) {
		String colorName = aFile.getName();
		String[] nameSplit = colorName.split("\\.");
		String fileName = nameSplit[0];
		fileNames.add(fileName);
	    }
	    return Collections.unmodifiableList(fileNames);
	}
	catch (Exception e) {
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY) + System.lineSeparator()
	    + resourceErrorText("ColorErrorPrompt"));
	}
	return Collections.unmodifiableList(new ArrayList<String>());
    }

    /**
     * 
     * @param key
     * @return
     */
    public String resourceDisplayText(String key) {
	try {
	    return CURRENT_TEXT_DISPLAY.getString(key);
	}
	catch (Exception e) {
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY) 
		    + System.lineSeparator() + key + " "
		    + resourceErrorText("DNEin") + System.lineSeparator()
		    + CURRENT_TEXT_DISPLAY.getBaseBundleName());
	    return "";
	}
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
	    //ErrorScreen startScreen = new ErrorScreen(this, 
	    //		resourceErrorText(SCREEN_ERROR_KEY));
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
	    loadErrorScreen(resourceErrorText(SCREEN_ERROR_KEY));
	}
    }

    /**
     * Creates an Error Screen to display to the user indicating an error type by the String
     * @param errorMessage. 
     * 
     * @param errorMessage: The message to be displayed to the user on the Error Screen
     */
    public void loadErrorScreen(String errorMessage) {
	ErrorScreen errorScreen = new ErrorScreen(this, errorMessage);
	Parent errorScreenRoot = errorScreen.getRoot();
	Scene errorScene = new Scene(errorScreenRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	errorScene.getStylesheets().add(DEFAULT_CSS);
	PROGRAM_STAGE.setScene(errorScene);
    }

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


    public List<String> translateColors(List<String> colors){
	List<String> translatedColors = new ArrayList<String>();
	for(int i =0; i<colors.size(); i++) {
	    translatedColors.add(resourceDisplayText(colors.get(i)));
	}
	return translatedColors;
    }

    public String changeBackgroundColor(String color) {
	CURRENT_BACKGROUND_COLOR = findColorFile(color);
	try {
	    return CURRENT_BACKGROUND_COLOR.getString(color+"Code");
	}
	catch(MissingResourceException e){
	    try {
		CURRENT_BACKGROUND_COLOR = findColorFile(DEFAULT_COLOR );
		return CURRENT_BACKGROUND_COLOR.getString(color+"Code");
	    }
	    catch(MissingResourceException e1) {
		loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		return null;
	    }
	}
    }

    private void parseHexCodeandPass(String hexCodeUnParsed) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
	String hexCode = hexCodeUnParsed.substring(1, hexCodeUnParsed.length());
	int hexConvert = Integer.parseInt(hexCode,16);
	parseInput("setpc " + hexConvert);
    }

    public void changePenColor(String color) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
	CURRENT_PEN_COLOR = findColorFile(color);
	try {
	    String hexCodeUnParsed = CURRENT_PEN_COLOR.getString(color+"Code");
	    parseHexCodeandPass(hexCodeUnParsed);
	}
	catch(MissingResourceException e){
	    try {
		CURRENT_PEN_COLOR = findColorFile(DEFAULT_COLOR);
		String hexCodeUnParsed = CURRENT_PEN_COLOR.getString(color+"Code");
		parseHexCodeandPass(hexCodeUnParsed);
	    }
	    catch(MissingResourceException e1) {
		loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
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
    private ResourceBundle findColorFile(String color) {
	String currentDir = System.getProperty("user.dir");
	ResourceBundle bundle;
	try {
	    File file = new File(currentDir);
	    URL[] urls = {file.toURI().toURL()};
	    ClassLoader loader = new URLClassLoader(urls);
	    try {
		bundle = ResourceBundle.getBundle(color, 
			Locale.getDefault(), loader);
		return bundle;

	    }
	    // if .properties file doesn't exist for specified language, default to English
	    catch (Exception e) {
		bundle = ResourceBundle.getBundle(DEFAULT_COLOR, 
			Locale.getDefault(), loader);
		return bundle;
	    }
	}
	catch (MalformedURLException e) {
	    loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
	    return null; //if this is reached the return value will not matter
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
     * 
     * @param key
     * @return
     */
    public String resourceErrorText(String key) {
	return CURRENT_ERROR_DISPLAY.getString(key);
    }
}

