package interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CommandTreeBuilder {
	
	private String myNumArgsFileName; 
	private ArrayList<CommandNode> myCommandTrees; 
	
	public CommandTreeBuilder(String numArgsFileName) {
		myNumArgsFileName = numArgsFileName; 
	}
	
	public Queue<Command> generateCommandQueue(String[] userInput, String[] commandTypes, String[] allInputTypes) {
		generateCommandTree(userInput, commandTypes, allInputTypes, 0);
		return new LinkedList<Command>(); 
	}
	
	private void generateCommandTree(String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx) {
		String currCommand = commandTypes[currIdx]; 
		int numArgs = getNumArgs(currCommand);
		System.out.println(numArgs);
	}
	
	private int getNumArgs(String commandType) {
		RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
        String numArgsAsString = regexMatcher.findMatchingVal(commandType);
        int numArgs = Integer.parseInt(numArgsAsString);
        return numArgs;
	}
	
}
