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
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class CommandMaker {
	
	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	
	private String myLanguageFileName; 
	private String myNumArgsFileName; 
	
	public CommandMaker() {
		this(DEFAULT_FILEPATH+DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}
	
	public CommandMaker(String languageFileName, String numArgsFileName) {
		myLanguageFileName = languageFileName;
		myNumArgsFileName = numArgsFileName; 
	}
	
	private String getCommandType(String text) {
        RegexMatcher regexMatcher = new RegexMatcher(myLanguageFileName);
        String commandType = regexMatcher.findMatch(text);
        return commandType;
    }
	
	private int getNumArgs(String commandType) {
		RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
        String numArgsAsString = regexMatcher.findMatch(commandType);
        int numArgs = Integer.parseInt(numArgsAsString);
        return numArgs;
	}
	
}
