package interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CommandTreeBuilder {

	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command";
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
		if (startIdx >= userInput.length) {
			return; // TODO make this more detailed
		}
		String currCommand = commandTypes[startIdx]; 
		int numArgs = getNumArgs(currCommand);
		CommandNode newParentNode = new CommandNode(currCommand, numArgs);
		createAndSetChildren(newParentNode, userInput, commandTypes, allInputTypes, startIdx+1);
	}

	private void createAndSetChildren(CommandNode parent, String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx) {
		if (allInputTypes[currIdx].equals(DEFAULT_CONSTANT_IDENTIFIER)) {
			CommandNode newChildNode = new CommandNode(userInput[currIdx]);
			parent.addChild(newChildNode);
			if (currIdx < userInput.length-1) {
				if (parent.getNumChildren() < parent.getNumArgs()) { 
					createAndSetChildren(parent, userInput, commandTypes, allInputTypes, currIdx+1);
				} 
				else {
					myCommandTrees.add(parent);
					createCommandTree(userInput, commandTypes, allInputTypes, currIdx+1);
				}
			}
			else {
				myCommandTrees.add(parent);
			}
			return; 
		}
		for (int idx = currIdx+1; idx < userInput.length; idx++) { // where else to return in here? 
			if (allInputTypes[idx].equals(DEFAULT_CONSTANT_IDENTIFIER)) {
				CommandNode newChildNode = new CommandNode(userInput[idx]);
				int numArgs = getNumArgs(commandTypes[idx-1]);
				CommandNode newCommandNode = new CommandNode(commandTypes[idx-1], numArgs, newChildNode);
				if (parent.getNumChildren() < parent.getNumArgs()) { 
					createAndSetChildren(newCommandNode, userInput, commandTypes, allInputTypes, idx+1);
				}
				else if (idx < userInput.length-1) {
					if (parent.getNumChildren() < parent.getNumArgs()) { 
						createAndSetChildren(parent, userInput, commandTypes, allInputTypes, idx+1);
					} 
					else {
						createCommandTree(userInput, commandTypes, allInputTypes, idx+1);
					}
				}
				for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) {
					int backTrackNumArgs = getNumArgs(commandTypes[backtrack]);
					CommandNode backtrackCommandNode = new CommandNode(commandTypes[backtrack], backTrackNumArgs, newCommandNode);
					newCommandNode = backtrackCommandNode; 
				}
				parent.addChild(newCommandNode);
				return; 
			}
		}
	}

	private int getNumArgs(String commandType) {
		RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
		String numArgsAsString = regexMatcher.findMatchingVal(commandType);
		int numArgs = Integer.parseInt(numArgsAsString);
		return numArgs;
	}

}
