package commandnodetree;
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
import java.util.Queue;

import interpreter.Command;
import interpreter.RegexMatcher;

public class CommandMaker {
	
	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command"; //TODO allow this to be client-specified
	
	private ArrayList<Turtle> myTurtles; 
	private String myLanguageFileName; 
	private CommandTreeBuilder myCommandTreeBuilder; 
	
	public CommandMaker() {
		this(DEFAULT_FILEPATH+DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}
	
	public CommandMaker(String languageFileName, String numArgsFileName) {
		myLanguageFileName = languageFileName;
		myCommandTreeBuilder = new CommandTreeBuilder(numArgsFileName); 
	}
	
	public Queue<Command> parseValidTextArray(String turtleName, String[] userInput, String[] typesOfInput) {
		return parseValidTextArray(turtleName, userInput, typesOfInput, DEFAULT_COMMAND_IDENTIFIER);
	}
	
	public Queue<Command> parseValidTextArray(String turtleName, String[] userInput, String[] typesOfInput, String commandIdentifier) {
		String[] commandTypes = new String[userInput.length];
		for (int idx = 0; idx < userInput.length; idx++) {
			if (typesOfInput[idx].equals(commandIdentifier)) {
				commandTypes[idx] = getCommandType(userInput[idx]);
			}
		}
		boolean foundTurtle = false; 
		Turtle identifiedTurtle; 
		for (Turtle turtle : myTurtles) {
			if (turtle.getName().equals(turtleName)) {
				identifiedTurtle = turtle; 
				foundTurtle = true; 
			}
		}
		if (! foundTurtle) {
			throw new TurtleNotFoundException(turtleName);
		}
		return myCommandTreeBuilder.createCommandQueue(identifiedTurtle, userInput, commandTypes, typesOfInput); 
	}
	
	private String getCommandType(String text) {
        RegexMatcher regexMatcher = new RegexMatcher(myLanguageFileName);
        String commandType = regexMatcher.findMatchingKey(text);
        return commandType;
    }
	
}
