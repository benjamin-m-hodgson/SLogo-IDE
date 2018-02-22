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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TextFieldParser {	
	
	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final String DEFAULT_SYNTAX_FILENAME = "Syntax";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";

	private String mySyntaxFileName; 
	private CommandMaker myCommandMaker; 
    private HashMap<String, Double> myVariables; 
	private HashMap<String, Double> myCommandAndReturnHistory;
	private Queue<Command> myCommandQueue;
	
	protected TextFieldParser() {
		this(DEFAULT_FILEPATH+DEFAULT_SYNTAX_FILENAME, DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}
	
	protected TextFieldParser(String syntaxFileName, String languageFileName, String numArgsFileName) {
		mySyntaxFileName = syntaxFileName;
		myCommandMaker = new CommandMaker(languageFileName, numArgsFileName); 
		myVariables = new HashMap<String, Double>(); 
		myCommandAndReturnHistory = new HashMap<String, Double>(); 
		myCommandQueue = new LinkedList<Command>(); 
	}
	
	
	
	
	
	
	
	
	
	
	// PARSING TEXT, VALIDATING AGAINST COMMAND
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 * @throws BadFormatException
	 * @throws UnidentifiedCommandException 
	 * @throws MissingInformationException 
	 */
	public void parseText(String userInputString) {
		String[] userInputArray = userInputString.split("\\s+");
		parseTextArray(userInputArray);
	}
	
	private void parseTextArray(String[] userInputArray) {
		String[] listOfTypes = new String[userInputArray.length];
		RegexMatcher regexMatcher = new RegexMatcher(mySyntaxFileName);
		for (int idx = 0; idx < userInputArray.length; idx++) {
			String inputToken = userInputArray[idx];
			listOfTypes[idx] = regexMatcher.findMatch(inputToken);
		}
//		myCommandQueue = CommandMaker.parseValidText(userInputArray); 
		// pass array to CommandMaker, ret val is a CommandQueue
	}
    
    
    
    
    // GETTERS
	/**
	 * Returns and UnmodifiableMap of string variable keys to their double values
	 */
	public Map<String, Double> getVariables() {
		return myVariables;
	}
	
	/**
	 * Returns an ImmutableList of the user's command history (where commands are Strings) 
	 */
	public Map<String, Double> getCommandHistory() {
		return myCommandAndReturnHistory; 
	}
	
	/**
	 * Returns an ImmutableQueue of the Command Queue (where commands are Command objects) 
	 */
	public Queue<Command> getCurrentCommandQueue() {
		return myCommandQueue;
	}
	
	public static void main(String[] args) {
		TextFieldParser testingParser = new TextFieldParser();
		testingParser.parseText("fd");
	}
	
}
