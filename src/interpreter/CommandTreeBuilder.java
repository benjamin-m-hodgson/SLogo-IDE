package interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CommandTreeBuilder {
	
	public static final String DEFAULT_CONSTANT_IDENTIFIER = "Constant";
	
//	private CommandTreeReader myCommandTreeReader; 
	private String myNumArgsFileName; 
	private ArrayList<CommandNode> myCommandTrees; 
	
	public CommandTreeBuilder(String numArgsFileName) {
		myNumArgsFileName = numArgsFileName; 
		myCommandTrees = new ArrayList<CommandNode>(); 
	}
	
	public Queue<Command> createCommandQueue(String[] userInput, String[] commandTypes, String[] allInputTypes) {
		createCommandTree(userInput, commandTypes, allInputTypes, 0);
		// generate queue from reading in tree
		return new LinkedList<Command>(); // TODO FIX: this return is just so eclipse won't complain...
	}
	
	private void createCommandTree(String[] userInput, String[] commandTypes, String[] allInputTypes, int startIdx) {
		String currCommand = commandTypes[startIdx]; 
		int numArgs = getNumArgs(currCommand);
		CommandNode newParentNode = new CommandNode(userInput[startIdx], numArgs);
	}
	
	private int getNumArgs(String commandType) {
		RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
        String numArgsAsString = regexMatcher.findMatchingVal(commandType);
        int numArgs = Integer.parseInt(numArgsAsString);
        return numArgs;
	}
	
}
