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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import command.Command;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TextFieldParser {	
	
	public static final String FILEPATH_NUM_ARGS_FOR_COMMANDS = "interpreter/NumArgsForCommands";
	private ResourceBundle myNumArgsProperties; 
	private HashMap<String, Double> myVariables; 
	private HashMap<String, Double> myCommandAndReturnHistory;
	private Queue<Command> myCommandQueue;
	
	protected TextFieldParser() {
		this(FILEPATH_NUM_ARGS_FOR_COMMANDS);
	}
	
	protected TextFieldParser(String numArgsForCommandsFilePath) {
		myNumArgsProperties = ResourceBundle.getBundle(numArgsForCommandsFilePath);
		
		myVariables = new HashMap<String, Double>(); 
		myCommandAndReturnHistory = new HashMap<String, Double>(); 
		myCommandQueue = new LinkedList<Command>(); 
	}
	
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 * @throws UnidentifiedCommandException 
	 */
	public void parseText(String userInputString) throws UnidentifiedCommandException {
		String[] userInputArray = userInputString.split("\\s+");
		parseTextArray(userInputArray, 0);
	}
	
	public void parseTextArray(String[] userInputArray, int idx) throws UnidentifiedCommandException {
		try {
			String numArgsAsString = myNumArgsProperties.getString(userInputArray[idx]); 
		} 
		catch (MissingResourceException e) {
			throw new UnidentifiedCommandException(userInputArray[idx]+" was not identified as a Command"); 
		}
//        int numArgs = Integer.parseInt();
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
			testingParser.parseText("yo");
		} catch (UnidentifiedCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
