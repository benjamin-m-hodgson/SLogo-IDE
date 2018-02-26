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

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.util.ResourceBundle;

class CommandMaker {

	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final ResourceBundle DEFAULT_LANGUAGE = ResourceBundle.getBundle("interpreter/English");
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command"; //TODO allow this to be client-specified
	public static final String[] DEFAULT_CONTROLFLOW_IDENTIFIERS = {"Repeat", "DoTimes", "For"};

	private ArrayList<Turtle> myTurtles; 
	private ResourceBundle myLanguage; 
	private CommandTreeBuilder myCommandTreeBuilder; 
	private HashMap<String, Double> myVariables; 

	protected CommandMaker() {
		this(DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}

	protected CommandMaker(ResourceBundle languageBundle, String numArgsFileName) {
		myTurtles = new ArrayList<Turtle>(); 
		myLanguage = languageBundle;
		myCommandTreeBuilder = new CommandTreeBuilder(numArgsFileName); 
		myVariables = new HashMap<String, Double>(); 
	}

	protected double parseValidTextArray(String turtleName, String[] userInput, String[] typesOfInput) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		return parseValidTextArray(turtleName, userInput, typesOfInput, DEFAULT_COMMAND_IDENTIFIER);
	}

	private double parseValidTextArray(String turtleName, String[] userInput, String[] typesOfInput, String commandIdentifier) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		Turtle identifiedTurtle = null;
		if (myTurtles.size() > 0) {
			identifiedTurtle = myTurtles.get(0); 
		}
		boolean turtleIdentified = false; 
		for (Turtle turtle : myTurtles) {
			if (turtle.getName().equals(turtleName)) {
				identifiedTurtle = turtle; 
				turtleIdentified = true; 
			}
		}
		String[] commandTypes = new String[userInput.length];
		int startIdx = 0; 
		if (turtleIdentified) {
			startIdx = 1; 
		}
		for (int idx = startIdx; idx < userInput.length; idx++) {
			if (typesOfInput[idx].equals(commandIdentifier)) {
				commandTypes[idx] = getCommandType(userInput[idx]);
			}
		}

		String[] userInputArrayToPass = userInput; 
		String[] commandTypesToPass = commandTypes; 
		String[] typesArrayToPass = typesOfInput; 

		if (turtleIdentified) {
			userInputArrayToPass = Arrays.copyOfRange(userInputArrayToPass, 1, userInputArrayToPass.length); 
			typesArrayToPass = Arrays.copyOfRange(typesOfInput, 1, typesOfInput.length);
		}
		//		makeListForBuilder(myListForBuilder, userInput, commandTypes, 1, DEFAULT_CONTROLFLOW_IDENTIFIERS);
		return myCommandTreeBuilder.buildAndExecute(identifiedTurtle, userInputArrayToPass, commandTypesToPass, typesArrayToPass); 
	}

	//	private void makeListForBuilder(ArrayList<String> currCommandList, String[] inputArray, String[] commandTypes, int numTimesToAdd, String[] controlFlowIdentifiers) {
	//		for (int i = 0; i < inputArray.length; i++) {
	//			if (Arrays.asList(controlFlowIdentifiers).contains(commandTypes[i])) {
	//
	//			}
	//		}
	//	}

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

	public void addNewTurtle(String name, ImageView turtleImage, Color penColor, Group penLines) {
		System.out.println(name);
		myTurtles.add(new Turtle(name, turtleImage, penLines, penColor));
	}
	

}
