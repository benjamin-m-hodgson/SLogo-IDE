package interpreter;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
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

    public static final String FILE_ERROR_KEY = "FileErrorPrompt";
    public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
    public static final String COLOR_ERROR_KEY = "ColorErrorPrompt";
    public static final String DEFAULT_LANGUAGE = "English";
    public static final String DEFAULT_COLOR = "Grey";
    public static final String DEFAULT_SETTINGS = "settings";
    public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";

 
    private String DEFAULT_CSS = Controller.class.getClassLoader().
	    getResource("default.css").toExternalForm(); 

    private ResourceBundle CURRENT_PEN_COLOR;

    private Stage PROGRAM_STAGE;
    private UserScreen USER_SCREEN;
    private String PROGRAM_TITLE;
    private double DEFAULT_HEIGHT;
    private double DEFAULT_WIDTH;
    private TextFieldParser myTextFieldParser; 
    private final FileIO FILE_READER;

    public Controller(Stage primaryStage) {
	FILE_READER = new FileIO(this);
	PROGRAM_STAGE = primaryStage;
	myTextFieldParser = new TextFieldParser(); 
	findSettings();
	FILE_READER.bundleUpdateToNewLanguage(DEFAULT_LANGUAGE);
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
		    loadErrorScreen(COLOR_ERROR_KEY);
		}
	    }
	});
    }
    
    /**
     * Searches through the class path to find the appropriate settings resource file to use for 
     * the program. If it can't locate the file, it displays an error screen to the user
     * with the default @param FILE_ERROR_PROMPT defined at the top of the Controller class
     */
    private void findSettings() {
	FILE_READER.loadSettings();
	PROGRAM_TITLE = FILE_READER.resourceSettingsText("Title");
	DEFAULT_HEIGHT = Double.parseDouble(FILE_READER.resourceSettingsText("ScreenHeight"));
	DEFAULT_WIDTH = Double.parseDouble(FILE_READER.resourceSettingsText("ScreenWidth"));
    }

    /**
     * Loads the StartScreen where the user selects an initial language and launches the program.
     */
    public void loadStartScreen() {
	try {
	    StartScreen startScreen = new StartScreen(this, FILE_READER);
	    Parent programRoot = startScreen.getRoot();
	    Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	    programScene.getStylesheets().add(DEFAULT_CSS);
	    PROGRAM_STAGE.setScene(programScene);
	    PROGRAM_STAGE.show();
	}
	catch (Exception e) {
	    loadErrorScreen(FILE_READER.resourceErrorText(SCREEN_ERROR_KEY));
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
	    USER_SCREEN = new UserScreen(this, FILE_READER);
	    Parent programRoot = USER_SCREEN.getRoot();
	    Scene programScene = new Scene(programRoot, DEFAULT_WIDTH, DEFAULT_HEIGHT);	
	    programScene.getStylesheets().add(DEFAULT_CSS);
	    PROGRAM_STAGE.setScene(programScene);
	}
	catch (Exception e) {
	    loadErrorScreen(FILE_READER.resourceErrorText(SCREEN_ERROR_KEY));
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

    /**
     * 
     * @return
     */
    public Map<String, String> getUserDefined() {
	return myTextFieldParser.getUserDefined(); 
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
     * Adds previously-saved variables to Map of variables
     * to display to the user 
     */
    public void loadSavedVariables() {
	myTextFieldParser.loadSavedVariables(); 
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
	CURRENT_PEN_COLOR = FILE_READER.getSpecificBundle(color,DEFAULT_COLOR);
	try {
	    String hexCodeUnParsed = CURRENT_PEN_COLOR.getString(color+"Code");
	    parseHexCodeandPass(hexCodeUnParsed);
	}
	catch(MissingResourceException e){
	    try {
		CURRENT_PEN_COLOR = FILE_READER.getSpecificBundle(DEFAULT_COLOR,DEFAULT_COLOR);
		String hexCodeUnParsed = CURRENT_PEN_COLOR.getString(color+"Code");
		parseHexCodeandPass(hexCodeUnParsed);
	    }
	    catch(MissingResourceException e1) {
		loadErrorScreen(FILE_READER.resourceErrorText(FILE_ERROR_KEY));
	    }
	}
    }


    public void changeParserLanguage(ResourceBundle languageBundle) {
	myTextFieldParser.changeLanguage(languageBundle);
    }

    /**
     * Parses input from a text field or button press by the user
     * @throws MissingInformationException 
     * @throws UnidentifiedCommandException 
     * @throws BadFormatException 
     * @throws TurtleNotFoundException 
     */
    public double parseInput(String userTextInput) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
	for (String key : FILE_READER.getColors().keySet()) {
		System.out.println(key);
	}
    	return myTextFieldParser.parseText(userTextInput);
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
	    loadErrorScreen(FILE_READER.resourceErrorText(FILE_ERROR_KEY) + System.lineSeparator()
	    + FILE_READER.resourceErrorText("ColorErrorPrompt"));
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

