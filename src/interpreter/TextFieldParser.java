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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class TextFieldParser {	
	
	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final String DEFAULT_SYNTAX_FILENAME = "Syntax";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMENT_SYMBOL = "Comment";

	private String mySyntaxFileName; 
	private CommandMaker myCommandMaker; 
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
		myCommandQueue = new LinkedList<Command>(); 
	}
	
	
	
	

	
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 * @throws TurtleNotFoundException 
	 */
	protected double parseText(String userInputString) throws TurtleNotFoundException {
		String[] userInputByLine = userInputString.split("\\r?\\n");
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
		return parseTextArray(tokenizedInputArray);
	}
	
	private double parseTextArray(String[] userInputArray) throws TurtleNotFoundException {
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
		return myCommandMaker.getVariables();
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
		try {
			testingParser.parseText("#hello\nfd 50 fd 50\n#bk 50");
		} catch (Exception e) {
			
		}
	}
	
}
