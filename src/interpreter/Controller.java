package interpreter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import screen.ErrorScreen;
import screen.StartScreen;
import screen.UserScreen;

/**
 * 
 * @author Ben Hodgson
 * @author Susie Choi
 * 
 * The main class for reading in data files and relaying information about these files 
 * to the front end. Also acts as a mediator and handles front end to back end communication.
 */
public class Controller {

	public static final String RESOURCE_ERROR = "Could not find resource bundle";
	public static final String COLOR_ERROR = "ColorErrorPrompt";
	public static final String FILE_ERROR_KEY = "FileErrorPrompt";
	public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
	public static final String SYNTAX_FILE_NAME = "Syntax.properties";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_COLOR = "Grey";
	public static final String DEFAULT_SETTINGS = "settings";
	private final String DEFAULT_WORKSPACE_PREF = "default";

	public static final String DEFAULT_SAVEDUSERCOMMANDS = "src/interpreter/SavedUserCommands.properties" ;
	public static final String DEFAULT_SAVEDVARIABLES = "src/interpreter/SavedVariables.properties" ;
	public static final String DEFAULT_FILEPATH_PREFIX = "src/";
	public static final String DEFAULT_PROPSFILE_SUFFIX = ".properties";
	public static final String DEFAULT_SHAPES_FILE = "interpreter/TurtleShapes";
	public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
	public static final String DEFAULT_COLORPALETTENAMES_FILE = "interpreter/ColorPaletteNames";
	private String DEFAULT_CSS = Controller.class.getClassLoader().
			getResource("default.css").toExternalForm(); 
	private ResourceBundle CURRENT_TEXT_DISPLAY;
	private ResourceBundle CURRENT_ERROR_DISPLAY;
	private ResourceBundle CURRENT_BACKGROUND_COLOR;
	private ResourceBundle CURRENT_PEN_COLOR;
	private ResourceBundle CURRENT_LANGUAGE;
	private ResourceBundle CURRENT_SETTINGS;
	private Stage PROGRAM_STAGE;
	private UserScreen USER_SCREEN;
	private String PROGRAM_TITLE;
	private double DEFAULT_HEIGHT;
	private double DEFAULT_WIDTH;
	private TextFieldParser myTextFieldParser; 

	public Controller(Stage primaryStage) {
		PROGRAM_STAGE = primaryStage;
		myTextFieldParser = new TextFieldParser(); 
		findSettings();
		findResources(DEFAULT_LANGUAGE);
		PROGRAM_STAGE.setTitle(PROGRAM_TITLE);
		setUpBackColorListener(); 
	}

	private void setUpBackColorListener() {
		myTextFieldParser.getBackColorChangeHeard().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean t1, Boolean t2) {
				RegexMatcher backColorRegex = new RegexMatcher(DEFAULT_COLORPALETTE_FILE);
				String matchingHex = ""; 
				try {
					matchingHex = backColorRegex.findMatchingVal(myTextFieldParser.getBackColor().getValue().toString());
					USER_SCREEN.changeBackgroundColorHex(matchingHex);
				} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
					System.out.println("error locating that backgorund color"); // TODO make this more elaborate
					USER_SCREEN.changeBackgroundColor(DEFAULT_COLOR);
					loadErrorScreen(COLOR_ERROR);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Loads the StartScreen where the user selects an initial language and launches the program.
	 */
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

	/**
	 * Load the UserScreen that houses the main panels required for program operation. 
	 * These include the input panel where the user can input commands, the info panel
	 * where the user can access and change certain properties about the program, and 
	 * the turtle panel where the turtle and drawings are displayed.
	 */
	public void loadUserScreen() {
		try {
			USER_SCREEN = new UserScreen(this);
			Parent programRoot = USER_SCREEN.getRoot();
			Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);	
			programScene.getStylesheets().add(DEFAULT_CSS);
			PROGRAM_STAGE.setScene(programScene);
		}
		catch (Exception e) {
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
		ErrorScreen errorScreen = new ErrorScreen(errorMessage);
		Parent errorScreenRoot = errorScreen.getRoot();
		Scene errorScene = new Scene(errorScreenRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		errorScene.getStylesheets().add(DEFAULT_CSS);
		PROGRAM_STAGE.setScene(errorScene);
	}

	/**
	 * Makes a new Turtle given a name, an ImageView (previously attached to the Stage), a penColor, and an empty Group
	 * that has already been attached to the Stage to hold lines for the pen
	 */

	public double makeNewTurtleCommand(String id, ImageView turtleImage, String penColor, Group penLines) {
		System.out.println("is it null" + turtleImage==null);
		myTextFieldParser.addNewTurtle(id, turtleImage, penColor, penLines);
		return 1.0; 
	}

	public Map<String, String> getUserDefined() {
		return myTextFieldParser.getUserDefined(); 
	}

	/**
	 * Invokes back-end method to save user's defined commands 
	 * in a Properties file 
	 */
	public void saveUserDefined() {
		Map<String, String> userDefinedMap = this.getUserDefined(); 
		PropertiesWriter pw = new PropertiesWriter(DEFAULT_SAVEDUSERCOMMANDS, userDefinedMap);
		pw.write(); 
	}

	/**
	 * Adds previously-saved user-defined commands to Map of user-defined commands
	 * to display to the user 
	 */
	public void loadSavedUserDefined() {
		myTextFieldParser.loadSavedUserDefined(); 
	}

	/**
	 * TODO: optimize this to return an unmodifiable version of the map
	 * Returns an UnmodifiableMap of variables to their values
	 */
	public Map<String, Double> getVariables() {
		return myTextFieldParser.getVariables();
	}

	/**
	 * Invokes back-end method to save user's defined commands 
	 * in a Properties file 
	 */
	public void saveVariables() {
		Map<String, Double> userDefinedMap = this.getVariables(); 
		HashMap<String, String> parsedMap = new HashMap<String, String>(); 
		for (String key : userDefinedMap.keySet()) {
			parsedMap.put(key.substring(1), Double.toString(userDefinedMap.get(key)));
		}
		PropertiesWriter pw = new PropertiesWriter(DEFAULT_SAVEDVARIABLES, parsedMap);
		pw.write(); 
	}

	/**
	 * Adds previously-saved variables to Map of variables
	 * to display to the user 
	 */
	public void loadSavedVariables() {
		myTextFieldParser.loadSavedVariables(); 
	}
	
//	/**
//	 * Returns information about default & user-defined colors (in hex) corresponding to indices 
//	 * @return Map of String indices to String hex colors
//	 */
//	public Map<String, String> getColors() {
//		PropertiesReader pw = new PropertiesReader(DEFAULT_FILEPATH_PREFIX+DEFAULT_COLORPALETTE_FILE+DEFAULT_PROPSFILE_SUFFIX);
//		Map<String, String> colorsMap = pw.read(); 
//		return colorsMap; 
//	}
	
	/**
	 * Returns information about default shape options for Turtle corresponding to indices
	 * @return Map of String indices to String shape options
	 */
	public Map<String, String> getShapes() {
		PropertiesReader pw = new PropertiesReader(DEFAULT_FILEPATH_PREFIX+DEFAULT_SHAPES_FILE+DEFAULT_PROPSFILE_SUFFIX);
		Map<String, String> shapesMap = pw.read(); 
		return shapesMap; 
	}
	
	public Map<String, String> getColors() {
		PropertiesReader pw = new PropertiesReader(DEFAULT_FILEPATH_PREFIX+DEFAULT_COLORPALETTENAMES_FILE+DEFAULT_PROPSFILE_SUFFIX);
		Map<String, String> shapesMap = pw.read(); 
		return shapesMap; 
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
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY) + System.lineSeparator()
			+ resourceErrorText("LanguageFileError"));
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


	public void changeBackgroundColorHex(String hex) {
		USER_SCREEN.changeBackgroundColorHex(hex);
	}

	/**
	 * Updates the color of lines to be drawn by the turtle
	 * 
	 * @param color: the new color to be used for drawing lines
	 * @throws TurtleNotFoundException
	 * @throws BadFormatException
	 * @throws UnidentifiedCommandException
	 * @throws MissingInformationException
	 */
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
	 * Looks in the CURRENT_TEXT_DISPLAY resourceBundle to determine the String
	 * that should be used for text display.
	 * 
	 * @param key: the key used for look up in the .properties file
	 * @return The string value @param key is assigned to in the .properties file
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
	 * Looks in the CURRENT_ERROR_DISPLAY resourceBundle to determine the String
	 * that should be used to get the String used for error description.
	 * 
	 * @param key: the key used for look up in the .properties file
	 * @return The string value @param key is assigned to in the .properties file
	 */
	public String resourceErrorText(String key) {
		try {
			return CURRENT_ERROR_DISPLAY.getString(key);
		}
		catch (Exception e) {
			loadErrorScreen(RESOURCE_ERROR);
			return "";
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
			myTextFieldParser.changeLanguage(CURRENT_LANGUAGE);
		}
		catch (MalformedURLException e) {
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		}
		catch (Exception e) {
			loadErrorScreen(RESOURCE_ERROR);
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
	
	public double parseSettingInput(String settingCommand) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String[] settingCommandArray = settingCommand.split("\\s+");
		String commandName = settingCommandArray[0];
		String commandArg = settingCommandArray[1];
		RegexMatcher rm = new RegexMatcher(CURRENT_LANGUAGE);
		String appropriateLangCommand = rm.findMatchingVal(commandName);
		if (appropriateLangCommand.contains("|")) {
			String[] splitOnOr = appropriateLangCommand.split("\\|"); 
			appropriateLangCommand = (splitOnOr[0]);
		}
		return myTextFieldParser.parseText(appropriateLangCommand+" "+commandArg);
	}

	/**
	 * Takes a list of String color names and generates a new list of String hex values
	 * taken from the colors.properties file.
	 * 
	 * @param colors: A list of Strings representing color names
	 * @return A List<String> containing string representations of hex codes 
	 */
	public List<String> translateColors(List<String> colors){
		List<String> translatedColors = new ArrayList<String>();
		for(int i =0; i<colors.size(); i++) {
			translatedColors.add(resourceDisplayText(colors.get(i)));
		}
		return translatedColors;
	}


	/**
	 * Used for changing pen color from settings panel through a command sent to parser
	 * @param hexCodeUnParsed
	 * @throws TurtleNotFoundException
	 * @throws BadFormatException
	 * @throws UnidentifiedCommandException
	 * @throws MissingInformationException
	 */
	private void parseHexCodeandPass(String hexCodeUnParsed) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
		try {
			String hexCode = hexCodeUnParsed.substring(1, hexCodeUnParsed.length());
			int hexConvert = Integer.parseInt(hexCode,16);
			changePenColorHex(hexConvert);
		}
		catch (Exception e){
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY) + System.lineSeparator()
			+ resourceErrorText("ColorErrorPrompt"));
		}
	}

	/**
	 * Takes a String color name and generates a new String representing the 
	 * colors associated hex value taken from the colors.properties file.
	 * 
	 * @param color: The String color name for the newly desired background color
	 * @return String representation of @param colors hex value.
	 */
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

	public void changePenColorHex(int hex) {
		try {
			parseInput("setpcbyhex " + hex);
		} catch (TurtleNotFoundException | BadFormatException | UnidentifiedCommandException
				| MissingInformationException e) {
			USER_SCREEN.displayErrorMessage("Invalid Color Chosen");
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
			PROGRAM_TITLE = resourceSettingsText("Title");
			DEFAULT_HEIGHT = Double.parseDouble(resourceSettingsText("ScreenHeight"));
			DEFAULT_WIDTH = Double.parseDouble(resourceSettingsText("ScreenWidth"));
		}
		catch (MalformedURLException e) {
			loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		}
		catch (Exception e) {
			e.printStackTrace();
			loadErrorScreen(RESOURCE_ERROR);
		}
	}

	/**
	 * Looks in the CURRENT_SETTINGS resourceBundle to determine the String
	 * that should be used to get the String used to define some program setting.
	 * 
	 * @param key: the key used for look up in the .properties file
	 * @return The string value @param key is assigned to in the .properties file
	 */
	private String resourceSettingsText(String key) {
		return CURRENT_SETTINGS.getString(key);
	}



	public Map<String, String> getWorkspacePreferences(String fileName) {
		String currentDir = System.getProperty("user.dir");
		try {
			return locatePreferences(currentDir, fileName);
		}
		catch (Exception e) {
			try {
				return locatePreferences(currentDir, DEFAULT_WORKSPACE_PREF);
			} catch (MalformedURLException e1) {
				loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
				return null;
			}
		}
	}

	private Map<String, String> locatePreferences(String currentDir, String fileName) throws MalformedURLException {
		File file = new File(currentDir);
		URL[] urls = {file.toURI().toURL()};
		ClassLoader loader = new URLClassLoader(urls);
		ResourceBundle workspacePref = ResourceBundle.getBundle(fileName, 
				Locale.getDefault(), loader);
		Map<String, String> preferences = new HashMap<String,String>();
		preferences.put("backgroundColor", workspacePref.getString("backgroundColor"));
		preferences.put("penColor", workspacePref.getString("penColor"));
		preferences.put("language", workspacePref.getString("language"));
		return preferences;
	}

	/**
	 * @return immutable list of immutable/temporary Turtles that have been made so far
	 */
	public List<SingleTurtle>  getAllTurtles(){
		return myTextFieldParser.getAllTurtles();
	}
	/**
	 * @return immutable list of immutable/temporary Turtles that have been made so far and are currently active
	 */
	public List<SingleTurtle> getActiveTurtles(){
		return myTextFieldParser.getActiveTurtles();
	}
	/**
	 * Returns ImageView of a particular turtle so it may be attached to the scene
	 * @param ID is ID of turtle whose ImageView is desired
	 * @return ImageView of turtle with corresponding ID
	 */
	public ImageView getTurtleWithIDImageView(double ID) {
		return myTextFieldParser.getTurtleWithIDImageView(ID);
	}
	/**
	 * Returns Group of a particular turtle so it may be attached to the scene
	 * @param ID is ID of turtle whose Group is desired
	 * @return Group of turtle with corresponding ID
	 */
	public Group getTurtleWithIDPenLines(double ID) {
		return myTextFieldParser.getTurtleWithIDPenLines(ID);
	}


}

