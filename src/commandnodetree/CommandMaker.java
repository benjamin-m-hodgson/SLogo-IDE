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

import java.util.Queue;

import interpreter.Command;
import interpreter.RegexMatcher;

public class CommandMaker {
	
	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command"; //TODO allow this to be client-specified
	
	private String myLanguageFileName; 
	private CommandTreeBuilder myCommandTreeBuilder; 
	
	public CommandMaker() {
		this(DEFAULT_FILEPATH+DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}
	
	public CommandMaker(String languageFileName, String numArgsFileName) {
		myLanguageFileName = languageFileName;
		myCommandTreeBuilder = new CommandTreeBuilder(numArgsFileName); 
	}
	
	public Queue<Command> parseValidTextArray(String[] userInput, String[] typesOfInput) {
		return parseValidTextArray(userInput, typesOfInput, DEFAULT_COMMAND_IDENTIFIER);
	}
	
	public Queue<Command> parseValidTextArray(String[] userInput, String[] typesOfInput, String commandIdentifier) {
		String[] commandTypes = new String[userInput.length];
		for (int idx = 0; idx < userInput.length; idx++) {
			if (typesOfInput[idx].equals(commandIdentifier)) {
				commandTypes[idx] = getCommandType(userInput[idx]);
			}
		}
		return myCommandTreeBuilder.createCommandQueue(userInput, commandTypes, typesOfInput); 
	}
	
	private String getCommandType(String text) {
        RegexMatcher regexMatcher = new RegexMatcher(myLanguageFileName);
        String commandType = regexMatcher.findMatchingKey(text);
        return commandType;
    }
	
}
