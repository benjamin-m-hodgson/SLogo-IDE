//package interpreter;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Queue;
//
//import command.Command;
//
//public interface TextFieldParser {	
//	/**
//	 * Returns and UnmodifiableMap of string variable keys to their double values
//	 */
//	public Map<String, Double> getVariables();
//	/**
//	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
//	 * and sends them individually to CommandMaker)
//	 */
//	public Queue<Command> parseText(String text);
//	/**
//	 * Returns an ImmutableList of the available/stored User Commands
//	 */
//	public List<String> getUserCommands();
//}

package interpreter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/** 
 * @author Susie Choi
 * First "stage" of the command-parsing process: ensuring valid syntax for all commands, 
 * e.g. no command should have semicolons. Dependency on RegexMatcher and existence of 
 * Syntax file to check user input against valid syntax. parseText is intended to be called (by Controller) 
 * with the user's commands as a string. Dependency on CommandMaker to continue the parsing process. 
 * 
 */

class TextFieldParser {	

	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final String DEFAULT_SYNTAX_FILENAME = "Syntax";
	public static final ResourceBundle DEFAULT_LANGUAGE = ResourceBundle.getBundle("interpreter/English");
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMENT_SYMBOL = "Comment";
	
	private String mySyntaxFileName; 
	private CommandMaker myCommandMaker; 
	private Queue<Command> myCommandQueue;
	private IntegerProperty myBackColor;
	private BooleanProperty myBackColorChangeHeard;

	protected TextFieldParser() {
		this(DEFAULT_FILEPATH+DEFAULT_SYNTAX_FILENAME, DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}

	protected TextFieldParser(ResourceBundle language) {
		this(DEFAULT_FILEPATH+DEFAULT_SYNTAX_FILENAME, language, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}

	protected TextFieldParser(String syntaxFileName, ResourceBundle languageBundle, String numArgsFileName) {
		mySyntaxFileName = syntaxFileName;
		myCommandMaker = new CommandMaker(languageBundle, numArgsFileName); 
		myCommandQueue = new LinkedList<Command>(); 
		myBackColor = new SimpleIntegerProperty(0);
		myBackColorChangeHeard = new SimpleBooleanProperty(false);
		setUpBackColorChangeListener();
	}

	private void setUpBackColorChangeListener() {
		myCommandMaker.getBackColorChangeHeard().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean t1, Boolean t2) {
				myBackColor = myCommandMaker.getBackColor();
				myBackColorChangeHeard.set(!myBackColorChangeHeard.getValue());
			}
		});
	}

	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 * @throws TurtleNotFoundException 
	 * @throws MissingInformationException 
	 * @throws UnidentifiedCommandException 
	 * @throws BadFormatException 
	 */
	protected double parseText(String userInputString) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String[] userInputByLine = userInputString.split("\\r?\\n");
		ArrayList<String> userInputList = new ArrayList<String>();
		for (int i = 0; i < userInputByLine.length; i++) {
			if (userInputByLine[i] != null && userInputByLine[i].length() > 0) {
				userInputList.add(userInputByLine[i].trim());
			}
		}
		userInputByLine = userInputList.toArray(new String[userInputList.size()]);
		String[] userInputTypes = new String[userInputByLine.length];

		RegexMatcher regexMatcher = new RegexMatcher(mySyntaxFileName);
		for (int idx = 0; idx < userInputByLine.length; idx++) { 
			userInputTypes[idx] = regexMatcher.findMatchingKey(userInputByLine[idx].substring(0, 1));
		}

		ArrayList<String> nonCommentInputByLine = new ArrayList<String>();
		for (int idx = 0; idx < userInputTypes.length; idx ++) {
			if (! userInputTypes[idx].equals(DEFAULT_COMMENT_SYMBOL)) {
				nonCommentInputByLine.add(userInputByLine[idx]);
			}
		}

		ArrayList<String> tokenizedInput = new ArrayList<String>();
		for (int idx = 0; idx < nonCommentInputByLine.size(); idx ++) {
			String[] whiteSpaceSplitLine = nonCommentInputByLine.get(idx).split("\\s+");
			for (String token : whiteSpaceSplitLine) {
				if (token.length() > 0 && !token.equals("")) {
					tokenizedInput.add(token);
				}
			}
		}

		String[] tokenizedInputArray = tokenizedInput.toArray(new String[tokenizedInput.size()]);
		System.out.println(tokenizedInputArray[0] + " " + tokenizedInputArray[1] + " IS BEING PASSED ");
		return parseTextArray(tokenizedInputArray);
	}



	private double parseTextArray(String[] userInputArray) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String[] listOfTypes = new String[userInputArray.length];
		RegexMatcher regexMatcher = new RegexMatcher(mySyntaxFileName);

		for (int idx = 0; idx < userInputArray.length; idx++) {
			listOfTypes[idx] = regexMatcher.findMatchingKey(userInputArray[idx]);
			//			System.out.println(listOfTypes[idx]+" "+userInputArray[idx]);
		}
		//		for (String s : userInputArray) System.out.println(s);
		double finalReturnVal = myCommandMaker.parseValidTextArray(userInputArray[0], userInputArray, listOfTypes); // TODO consider special case in which turtle name is command name; 
		
		return finalReturnVal; 
	}




	// GETTERS
	/**
	 * Returns and UnmodifiableMap of string variable keys to their double values
	 */
	protected Map<String, Double> getVariables() {
		return myCommandMaker.getVariables();
	}

	protected Map<String, String> getUserDefined() {
		return myCommandMaker.getUserDefined();
	}

	/**
	 * Returns an ImmutableQueue of the Command Queue (where commands are Command objects) 
	 */
	protected Queue<Command> getCurrentCommandQueue() {
		return myCommandQueue;
	}

	protected IntegerProperty getBackColor()  {
		return myBackColor;
	}

	protected BooleanProperty getBackColorChangeHeard()  {
		return myBackColorChangeHeard;
	}
	
	protected List<SingleTurtle>  getAllTurtles(){
		return myCommandMaker.getAllTurtles();
	}
	protected List<SingleTurtle> getActiveTurtles(){
		return myCommandMaker.getActiveTurtles();
	}
	protected ImageView getTurtleWithIDImageView(double ID) {
		return myCommandMaker.getTurtleWithIDImageView(ID);
	}
	protected Group getTurtleWithIDPenLines(double ID) {
		return myCommandMaker.getTurtleWithIDPenLines(ID);
	}



	// SETTERS
	protected void changeLanguage(ResourceBundle languageBundle) {
		myCommandMaker.changeLanguage(languageBundle);
	}

	protected void addNewTurtle(String id, ImageView turtleImage, String penColor, Group penLines) {
		myCommandMaker.addNewTurtle(id, turtleImage, penColor, penLines);
	}

	protected void loadSavedUserDefined() {
		myCommandMaker.loadSavedUserDefined(); 
	}

	protected void loadSavedVariables() {
		myCommandMaker.loadSavedVariables(); 
	}

}

