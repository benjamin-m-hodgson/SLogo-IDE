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
import java.util.ResourceBundle;


/**
 * Class to handle the basic reading and writing to property files 
 * @author Andrew Arnold
 *
 */
public class FileIO {

    private ResourceBundle CURRENT_TEXT_DISPLAY;
    public static final String DEFAULT_SAVEDUSERCOMMANDS = "src/interpreter/SavedUserCommands.properties" ;
    public static final String DEFAULT_SAVEDVARIABLES = "src/interpreter/SavedVariables.properties" ;
    public static final String DEFAULT_FILEPATH_PREFIX = "src/";
    public static final String DEFAULT_PROPSFILE_SUFFIX = ".properties";
    public static final String DEFAULT_SHAPES_FILE = "interpreter/TurtleShapes";
    public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
    public final String LANGUAGES_FOLDER = "languages";
    private ResourceBundle CURRENT_ERROR_DISPLAY;
    private ResourceBundle CURRENT_BACKGROUND_COLOR;
    private ResourceBundle CURRENT_LANGUAGE;
    private ResourceBundle CURRENT_SETTINGS;
    public static final String RESOURCE_ERROR = "Could not find resource bundle";
    public static final String FILE_ERROR_KEY = "FileErrorPrompt";
    public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
    public static final String SYNTAX_FILE_NAME = "Syntax.properties";
    public static final String DEFAULT_LANGUAGE = "English";
    public static final String DEFAULT_COLOR = "Grey";
    public static final String DEFAULT_SETTINGS = "settings";
    private final String DEFAULT_WORKSPACE_PREF = "default";
    private final Controller CONTROL;


	public FileIO(Controller controlIn) {
		CONTROL = controlIn;

	}

	/**
	 * Invokes back-end method to save user's defined commands 
	 * in a Properties file 
	 */
	public void saveUserDefined(Controller control) {
		Map<String, String> userDefinedMap = control.getUserDefined(); 
		new PropertiesWriter(DEFAULT_SAVEDUSERCOMMANDS, userDefinedMap).write();
	}

	/**
	 * Invokes back-end method to save user's defined commands 
	 * in a Properties file 
	 */
	public void saveVariables() {
		Map<String, Double> userDefinedMap = CONTROL.getVariables(); 
		HashMap<String, String> parsedMap = new HashMap<String, String>(); 
		for (String key : userDefinedMap.keySet()) {
			parsedMap.put(key.substring(1), Double.toString(userDefinedMap.get(key)));
		}
		new PropertiesWriter(DEFAULT_SAVEDVARIABLES, parsedMap).write();
	}

	public Map<String, String> getColors(){
		return getMapFromProperties(DEFAULT_FILEPATH_PREFIX+DEFAULT_COLORPALETTE_FILE+DEFAULT_PROPSFILE_SUFFIX);
	}

	public Map<String, String> getShapes(){
		return getMapFromProperties(DEFAULT_FILEPATH_PREFIX+DEFAULT_SHAPES_FILE+DEFAULT_PROPSFILE_SUFFIX);
	}

	/**
	 * Returns information about default & user-defined colors (in hex) corresponding to indices 
	 * @return Map of String indices to String hex colors
	 */
	//cover getColors and getShapes
	public Map<String, String> getMapFromProperties(String filePath) {
		PropertiesReader pw = new PropertiesReader(filePath);
		Map<String, String> theMap = pw.read(); 
		return theMap; 
	}

	public List<String> getLanguages(){
		return getFileNames(LANGUAGES_FOLDER);
	}

	/**
	 * Loops through the files in the "colors" sub directory to determine which 
	 * color options the program can support. 
	 * 
	 * @return an ImmutableList of all of the color options
	 */
	//covers getLanguages and getFileNames
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
			CONTROL.loadErrorScreen(resourceErrorText(FILE_ERROR_KEY) + System.lineSeparator());
		}
		return Collections.unmodifiableList(new ArrayList<String>());
	}



	/**
	 * Looks in the CURRENT_TEXT_DISPLAY resourceBundle to determine the String
	 * that should be used for text display.
	 * 
	 * @param key: the key used for look up in the .properties file
	 * @return The string value @param key is assigned to in the .properties file
	 */
	public String resourceDisplayText(String key) {
		return resourceText(key, CURRENT_TEXT_DISPLAY);
	}

	/**
	 * Looks in the CURRENT_ERROR_DISPLAY resourceBundle to determine the String
	 * that should be used to get the String used for error description.
	 * 
	 * @param key: the key used for look up in the .properties file
	 * @return The string value @param key is assigned to in the .properties file
	 */
	public String resourceErrorText(String key) {
		return resourceText(key, CURRENT_ERROR_DISPLAY);
	}

	/**
	 * Looks in the CURRENT_SETTINGS resourceBundle to determine the String
	 * that should be used to get the String used to define some program setting.
	 * 
	 * @param key: the key used for look up in the .properties file
	 * @return The string value @param key is assigned to in the .properties file
	 */
	//covers resourceSettingsText
	public String resourceSettingsText(String key) {
		return resourceText(key, CURRENT_SETTINGS);		
	}



	//covers resourceDisplayText and resourceErrorText
	private String resourceText(String key, ResourceBundle bundle) {
		try {
			return bundle.getString(key);
		}
		catch (Exception e) {
			CONTROL.loadErrorScreen(RESOURCE_ERROR);
			return "";
		}
	}

	/**
	 * Searches through the class path to find the appropriate resource files to use for 
	 * the program. If it can't locate the files, it displays an error screen to the user
	 * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
	 * 
	 * @param language: The language to define which .properties files to use in the Program
	 */
	//covers findResources
	public void bundleUpdateToNewLanguage(String language) {
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
			CONTROL.changeParserLanguage(CURRENT_LANGUAGE);
		}
		catch (MalformedURLException e) {
			CONTROL.loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		}
		catch (Exception e) {
			CONTROL.loadErrorScreen(RESOURCE_ERROR);
		}
	}

	/**
	 * Takes a String color name and generates a new String representing the 
	 * colors associated hex value taken from the colors.properties file.
	 * 
	 * @param color: The String color name for the newly desired background color
	 * @return String representation of @param colors hex value.
	 */
	//covers changeBackgroundColor
	public String getColorHexfromName(String color) {
		CURRENT_BACKGROUND_COLOR = getSpecificBundle(color,DEFAULT_COLOR);
		return CURRENT_BACKGROUND_COLOR.getString(color+"Code");
	}

	/**
	 * Searches through the class path to find the appropriate resource files to use for 
	 * the program. If it can't locate the files, it displays an error screen to the user
	 * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
	 * 
	 * @param language: The language to define which .properties files to use in the Program
	 */
	//covers findColorFile fully
	//should be private after penColorChangeIsFixed
	public ResourceBundle getSpecificBundle(String bundleName, String defaultTarget) {
		String currentDir = System.getProperty("user.dir");
		ResourceBundle bundle;
		try {
			File file = new File(currentDir);
			URL[] urls = {file.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			try {
				bundle = ResourceBundle.getBundle(bundleName, 
						Locale.getDefault(), loader);
				return bundle;

			}
			// if .properties file doesn't exist for specified language, default to English
			catch (Exception e) {
				bundle = ResourceBundle.getBundle(defaultTarget, 
						Locale.getDefault(), loader);
				return bundle;
			}
		}
		catch (MalformedURLException e) {
			CONTROL.loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
			return null; //if this is reached the return value will not matter
		}
	}

	/**
	 * Searches through the class path to find the appropriate settings resource file to use for 
	 * the program. If it can't locate the file, it displays an error screen to the user
	 * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
	 */
	//covers findSettings
	public void loadSettings() {
		CURRENT_SETTINGS = getSpecificBundle(DEFAULT_SETTINGS,DEFAULT_SETTINGS);
		//only has default so there is no back up, if it fails an error should be thrown
	}

	public Map<String, String> getWorkspacePreferences(String fileName) {
		ResourceBundle workspacePref = getSpecificBundle(fileName, DEFAULT_WORKSPACE_PREF);
		Map<String, String> preferences = new HashMap<String,String>();
		preferences.put("backgroundColor", workspacePref.getString("backgroundColor"));
		preferences.put("penColor", workspacePref.getString("penColor"));
		preferences.put("language", workspacePref.getString("language"));
		return preferences;
	}

	public void parseSettingInput(String settingInput) {
		String[] settingCommandArray = settingInput.split("\\s+");
		String commandName = settingCommandArray[0];
		String commandArg = settingCommandArray[1];
		RegexMatcher rm = new RegexMatcher(CURRENT_LANGUAGE);
		String appropriateLangCommand = "";
		try {
			appropriateLangCommand = rm.findMatchingVal(commandName);
		} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
			CONTROL.loadErrorScreen(e.getMessage());
		}
		if (appropriateLangCommand.contains("|")) {
			String[] splitOnOr = appropriateLangCommand.split("\\|"); 
			appropriateLangCommand = (splitOnOr[0]);
		}
		try {
			CONTROL.parseInput(appropriateLangCommand+" "+commandArg);
		} catch (TurtleNotFoundException | BadFormatException | UnidentifiedCommandException
				| MissingInformationException e) {
			CONTROL.loadErrorScreen(e.getMessage());
		}
	}
}

