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
import commandnodetree.CommandMaker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class TextFieldParser {	
	
	protected static final String DEFAULT_FILEPATH = "interpreter/";
	protected static final String DEFAULT_SYNTAX_FILENAME = "Syntax";
	protected static final String DEFAULT_LANGUAGE = "English";
	protected static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";

	private String mySyntaxFileName; 
	private CommandMaker myCommandMaker; 
    private HashMap<String, Double> myVariables; 
	private HashMap<String, Double> myCommandAndReturnHistory;
	private Queue<Command> myCommandQueue;
	
	protected TextFieldParser() {
		this(DEFAULT_FILEPATH+DEFAULT_SYNTAX_FILENAME, DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}
	
	protected TextFieldParser(String languageFileName) {
		this(DEFAULT_FILEPATH+DEFAULT_SYNTAX_FILENAME, languageFileName, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}
	
	protected TextFieldParser(String syntaxFileName, String languageFileName, String numArgsFileName) {
		mySyntaxFileName = syntaxFileName;
		myCommandMaker = new CommandMaker(languageFileName, numArgsFileName); 
		myVariables = new HashMap<String, Double>(); 
		myCommandAndReturnHistory = new HashMap<String, Double>(); 
		myCommandQueue = new LinkedList<Command>(); 
	}
	
	
	
	

	
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 */
	protected double parseText(String userInputString) {
		String[] userInputArray = userInputString.split("\\s+");
		return parseTextArray(userInputArray);
	}
	
	private double parseTextArray(String[] userInputArray) {
		String[] listOfTypes = new String[userInputArray.length];
		RegexMatcher regexMatcher = new RegexMatcher(mySyntaxFileName);
		for (int idx = 0; idx < userInputArray.length; idx++) {
			listOfTypes[idx] = regexMatcher.findMatchingKey(userInputArray[idx]);
		}
		double finalReturnVal = myCommandMaker.parseValidTextArray(userInputArray[0], Arrays.copyOfRange(userInputArray, 1, userInputArray.length-1), listOfTypes); 
		return finalReturnVal; 
	}
    
    
    
    
    // GETTERS
	/**
	 * Returns and UnmodifiableMap of string variable keys to their double values
	 */
	protected Map<String, Double> getVariables() {
		return myVariables;
	}
	
	/**
	 * Returns an ImmutableList of the user's command history (where commands are Strings) 
	 */
	protected Map<String, Double> getCommandHistory() {
		return myCommandAndReturnHistory; 
	}
	
	/**
	 * Returns an ImmutableQueue of the Command Queue (where commands are Command objects) 
	 */
	protected Queue<Command> getCurrentCommandQueue() {
		return myCommandQueue;
	}
	
	
	
	
	// SETTERS
	protected void changeLanguageFile(String fileName) {
		myCommandMaker.changeLanguageFile(fileName);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		TextFieldParser testingParser = new TextFieldParser();
		testingParser.parseText("fd 50 setxy 20 50");
	}
	
}
