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
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/** 
 * @author Susie Choi
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
				userInputList.add(userInputByLine[i]);
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
				tokenizedInput.add(token);
			}
		}

		String[] tokenizedInputArray = tokenizedInput.toArray(new String[tokenizedInput.size()]);
//		for (String s : tokenizedInputArray) {
//			System.out.println(s);
//		}
//		for (String s : tokenizedInputArray) {
//			System.out.println(s);
//		}
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

	/**
	 * Returns an ImmutableQueue of the Command Queue (where commands are Command objects) 
	 */
	protected Queue<Command> getCurrentCommandQueue() {
		return myCommandQueue;
	}




	// SETTERS
	protected void changeLanguage(ResourceBundle languageBundle) {
		myCommandMaker.changeLanguage(languageBundle);
	}
	
	public void addNewTurtle(String name, ImageView turtleImage, String penColor, Group penLines) {
		System.out.println("made it to textfieldparser");
		myCommandMaker.addNewTurtle(name, turtleImage, penColor, penLines);
	}






	public static void main(String[] args) {
		TextFieldParser testingParser = new TextFieldParser();
		try {
//			testingParser.parseText("ifelse less? 5 5 [ fd 50 ] [ bk 30 ] rt 90");
//			testingParser.parseText("if less? 1 5 [ fd 50 ] rt 90");
			//			testingParser.parseText("pd pd pd pd fd 50");
//			testingParser.parseText("fd fd fd pd"); // CHECK AGAIN
			testingParser.parseText("dotimes [ :k 360 ] [ fd :k rt 90 ]");
//			testingParser.parseText("fd 1 rt / sin 20 2");
//			testingParser.parseText("fd rt fd 50 bk 30");
//			testingParser.parseText("fd rt bk 50");
			testingParser.parseText("fd 50\n\n\nbk 50");
//			testingParser.parseText("fd rt 100");
//			testingParser.parseText("fd 100\n" + 
//					"rt 90\n" + 
//					"fd 100\n" + 
//					"rt 90\n" + 
//					"fd 100\n" + 
//					"rt 90\n" + 
//					"fd 100\n" + 
//					"rt 90");
//			testingParser.parseText("fd sum sum sum sum 10 20 30 5 5");
//			testingParser.parseText("setxy fd fd fd fd bk 50 rt 90");
		} catch (Exception e) {

		}
	}

}
