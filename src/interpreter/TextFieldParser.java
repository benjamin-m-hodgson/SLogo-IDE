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

import command.Command;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TextFieldParser {	
	
	public static final String FILEPATH_COMMAND_SYNTAX = "interpreter/Syntax";
	public static final String FILEPATH_LANGUAGE_SETTINGS = "interpreter/English";
	public static final String FILEPATH_NUM_ARGS_FOR_COMMANDS = "interpreter/NumArgsForCommands";
	private ResourceBundle myCommandSyntax;
    private List<Entry<String, Pattern>> mySyntaxSymbols;
	private ResourceBundle myLanguage; 
    private List<Entry<String, Pattern>> myLanguageSymbols;
	private ResourceBundle myNumArgsProperties; 
	private HashMap<String, Double> myVariables; 
	private HashMap<String, Double> myCommandAndReturnHistory;
	private Queue<Command> myCommandQueue;
	
	protected TextFieldParser() {
		this(FILEPATH_COMMAND_SYNTAX, FILEPATH_LANGUAGE_SETTINGS, FILEPATH_NUM_ARGS_FOR_COMMANDS);
	}
	
	protected TextFieldParser(String syntaxFilePath, String languageFilePath, String numArgsForCommandsFilePath) {
		myCommandSyntax = ResourceBundle.getBundle(syntaxFilePath);
		mySyntaxSymbols = new ArrayList<Entry<String, Pattern>>();
		populateWithSymbols(mySyntaxSymbols, myCommandSyntax);
		myLanguage = ResourceBundle.getBundle(languageFilePath);
		myLanguageSymbols = new ArrayList<Entry<String, Pattern>>();
		populateWithSymbols(myLanguageSymbols, myLanguage);
		myNumArgsProperties = ResourceBundle.getBundle(numArgsForCommandsFilePath);
		myVariables = new HashMap<String, Double>(); 
		myCommandAndReturnHistory = new HashMap<String, Double>(); 
		myCommandQueue = new LinkedList<Command>(); 
	}
	
	private void populateWithSymbols(List<Entry<String, Pattern>> listToAddTo, ResourceBundle resourcesToAdd) {
        Enumeration<String> iter = resourcesToAdd.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resourcesToAdd.getString(key);
            listToAddTo.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }
	
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 * @throws BadFormatException
	 * @throws UnidentifiedCommandException 
	 * @throws MissingInformationException 
	 */
	public void parseText(String userInputString) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String[] userInputArray = userInputString.split("\\s+");
		parseTextArray(userInputArray, 0);
	}
	
	private void parseTextArray(String[] userInputArray, int idx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String inputToken = userInputArray[idx];
		String commandType = "";
		int numArgsAsString = -1; 
		if (isValidCommand(inputToken)) {
			commandType = getCommandType(inputToken);
			numArgsAsString = getNumArgs(commandType);
		}		
		// add command to command queue
	}

	private int getNumArgs(String commandType) throws MissingInformationException {
		String propsLookUp = "";
		try {
			propsLookUp = myNumArgsProperties.getString(commandType); 
		} 
		catch (MissingResourceException e) {
			throw new MissingInformationException(commandType);
		}
		return Integer.parseInt(propsLookUp);
	}

	private boolean isValidCommand(String text) throws BadFormatException {
        for (Entry<String, Pattern> e : mySyntaxSymbols) {
            if (match(text, e.getValue())) {
                return true;
            }
        }
        throw new BadFormatException(text);
    }
	
    private String getCommandType(String text) throws UnidentifiedCommandException {
        for (Entry<String, Pattern> e : myLanguageSymbols) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        throw new UnidentifiedCommandException(text);
    }
    
    private boolean match (String text, Pattern regex) {
        return regex.matcher(text).matches();
    }
	
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
		try {
			testingParser.parseText("123");
		} catch (UnidentifiedCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadFormatException e) {
			e.printStackTrace();
		} catch (MissingInformationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
