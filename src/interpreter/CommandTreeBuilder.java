package interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class CommandTreeBuilder {

	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command";
	public static final String DEFAULT_CONSTANT_IDENTIFIER = "Constant";
	//	private CommandTreeReader myCommandTreeReader; 
	private String myNumArgsFileName; 
	private ArrayList<CommandNode> myCommandTrees; 
	private Turtle myTurtle;
	private CommandTreeReader myCommandTreeReader;

	public CommandTreeBuilder(String numArgsFileName, Turtle turtle) {
		myNumArgsFileName = numArgsFileName; 
		myCommandTrees = new ArrayList<CommandNode>(); 
		myTurtle = turtle;
		myCommandTreeReader = new CommandTreeReader();
	}

	public Queue<Command> createCommandQueue(Turtle turtle, String[] userInput, String[] commandTypes, String[] allInputTypes) {
		createCommandTree(turtle, userInput, commandTypes, allInputTypes, 0);
		for (CommandNode n : myCommandTrees) {
			System.out.println(n.toString());
		}
		return new LinkedList<Command>(); // TODO FIX: this return is just so eclipse won't complain...
	}

	private void createCommandTree(Turtle turtle, String[] userInput, String[] commandTypes, String[] allInputTypes, int startIdx) {
		if (startIdx >= userInput.length) {
			return; // TODO make this more detailed
		}
		String currCommand = commandTypes[startIdx]; 
		int numArgs = getNumArgs(currCommand);
		CommandNode newParentNode = new CommandNode(currCommand, numArgs, turtle);
		createAndSetChildren(turtle, newParentNode, userInput, commandTypes, allInputTypes, startIdx+1, true);
	}

	private void createAndSetChildren( Turtle turtle, CommandNode parent, String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx, boolean addToTrees) {
		if (currIdx >= userInput.length) {
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return; 
		}
		if (allInputTypes[currIdx].equals(DEFAULT_CONSTANT_IDENTIFIER)) {
			CommandNode newChildNode = new CommandNode(userInput[currIdx], turtle);
			parent.addChild(newChildNode);
			if (parent.getNumChildren() < parent.getNumArgs()) { 
				createAndSetChildren(turtle, parent, userInput, commandTypes, allInputTypes, currIdx+1, addToTrees);
			} 
			else {
				if (addToTrees) {
					myCommandTrees.add(parent);
				}
				createCommandTree(turtle, userInput, commandTypes, allInputTypes, currIdx+1);
			}
			return; 
		}
		for (int idx = currIdx+1; idx < userInput.length; idx++) { // where else to return in here? 
			if (allInputTypes[idx].equals(DEFAULT_CONSTANT_IDENTIFIER)) {
				CommandNode newChildNode = new CommandNode(userInput[idx], turtle);
				int numArgs = getNumArgs(commandTypes[idx-1]);
				CommandNode newCommandNode = new CommandNode(commandTypes[idx-1], numArgs, newChildNode, turtle);
				if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
					createAndSetChildren(turtle, newCommandNode, userInput, commandTypes, allInputTypes, idx+1, false);
				} 
				else {
					createCommandTree(turtle, userInput, commandTypes, allInputTypes, idx+1);
				}
				for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) {
					int backTrackNumArgs = getNumArgs(commandTypes[backtrack]);
					CommandNode backtrackCommandNode = new CommandNode(commandTypes[backtrack], backTrackNumArgs, newCommandNode, turtle);
					newCommandNode = backtrackCommandNode; 
				}
				parent.addChild(newCommandNode);
				if (addToTrees) {
					myCommandTrees.add(parent);
				}
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
