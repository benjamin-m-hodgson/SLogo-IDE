package interpreter;
//
//public interface CommandMaker {
//	/**
//	 * Creates a Queue of Command objects given a Queue of user-inputted Strings
//	 */
//	public Command parseCommand(String stringCommand);
//	
//	/**
//	 * Handle parsing individual text String commands
//	 */
//	public void PARSING_BLACK_BOX();
//
//}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import sun.security.tools.policytool.Resources;

class CommandMaker {

	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final ResourceBundle DEFAULT_LANGUAGE = Resources.getBundle("interpreter/English");
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command"; //TODO allow this to be client-specified
	public static final String[] DEFAULT_CONTROLFLOW_IDENTIFIERS = {"Repeat", "DoTimes", "For"};

	private ArrayList<Turtle> myTurtles; 
	private ResourceBundle myLanguage; 
	private CommandTreeBuilder myCommandTreeBuilder; 
	private HashMap<String, Double> myVariables; 
	private ArrayList<String> myListForBuilder; 

	protected CommandMaker() {
		this(DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}

	protected CommandMaker(ResourceBundle languageBundle, String numArgsFileName) {
		myLanguage = languageBundle;
		myCommandTreeBuilder = new CommandTreeBuilder(numArgsFileName); 
		myVariables = new HashMap<String, Double>(); 
		myListForBuilder = new ArrayList<String>();
	}

	protected double parseValidTextArray(String turtleName, String[] userInput, String[] typesOfInput) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
		myListForBuilder = new ArrayList<String>(); 
		return parseValidTextArray(turtleName, userInput, typesOfInput, DEFAULT_COMMAND_IDENTIFIER);
	}

	private double parseValidTextArray(String turtleName, String[] userInput, String[] typesOfInput, String commandIdentifier) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//		for (String s : userInput) System.out.println(s);
		String[] commandTypes = new String[userInput.length];
		for (int idx = 0; idx < userInput.length; idx++) {
			if (typesOfInput[idx].equals(commandIdentifier)) {
				commandTypes[idx] = getCommandType(userInput[idx]);
			}
		}
		Turtle identifiedTurtle = null; 
		//		boolean foundTurtle = false; 
		//		for (Turtle turtle : myTurtles) {
		//			if (turtle.getName().equals(turtleName)) {
		//				identifiedTurtle = turtle; 
		//				foundTurtle = true; 
		//			}
		//		}
		//		if (! foundTurtle) {
		//			throw new TurtleNotFoundException(turtleName);
		//		}
		makeListForBuilder(myListForBuilder, userInput, commandTypes, 1, DEFAULT_CONTROLFLOW_IDENTIFIERS);
		return myCommandTreeBuilder.buildAndExecute(identifiedTurtle, userInput, commandTypes, typesOfInput); 
	}

	private void makeListForBuilder(ArrayList<String> currCommandList, String[] inputArray, String[] commandTypes, int numTimesToAdd, String[] controlFlowIdentifiers) {
		for (int i = 0; i < inputArray.length; i++) {
			if (Arrays.asList(controlFlowIdentifiers).contains(commandTypes[i])) {
				
			}
		}
	}

	private String getCommandType(String text) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		RegexMatcher regexMatcher = new RegexMatcher(myLanguage);
		String commandType = regexMatcher.findMatchingKey(text);
		return commandType;
	}

	protected void changeLanguage(ResourceBundle languageBundle) {
		myLanguage = languageBundle; 
	}

	protected Map<String, Double> getVariables() {
		return Collections.unmodifiableMap(myVariables);
	}

}
