package interpreter;

import java.util.ArrayList;

class CommandTreeBuilder {

	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command";
	public static final String DEFAULT_CONSTANT_IDENTIFIER = "Constant";
	public static final String DEFAULT_BRACKET_IDENTIFIER = "Bracket";
	//	private CommandTreeReader myCommandTreeReader; 
	private String myNumArgsFileName; 
	private ArrayList<CommandNode> myCommandTrees; 
	private CommandTreeReader myCommandTreeReader;

	protected CommandTreeBuilder(String numArgsFileName) {
		myNumArgsFileName = numArgsFileName; 
		myCommandTrees = new ArrayList<CommandNode>(); 
		myCommandTreeReader = new CommandTreeReader();
	}

	protected double buildAndExecute(Turtle turtle, String[] userInput, String[] commandTypes, String[] allInputTypes) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		createCommandTree(turtle, userInput, commandTypes, allInputTypes, 0);
		for (CommandNode n : myCommandTrees) {
			System.out.println(n.toString());
		}
		double finalReturnVal = -1; 
		for (CommandNode commandTree : myCommandTrees) {
			finalReturnVal = myCommandTreeReader.readAndExecute(commandTree);
		}
		return finalReturnVal; 
	}

	private CommandNode createCommandTree(Turtle turtle, String[] userInput, String[] commandTypes, String[] allInputTypes, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (startIdx >= userInput.length) {
			return null; // TODO make this more detailed
		}
		String currCommand = commandTypes[startIdx]; 
		int numArgs = getNumArgs(currCommand);
		CommandNode newParentNode = new CommandNode(currCommand, numArgs, turtle);
		createAndSetChildren(turtle, newParentNode, userInput, commandTypes, allInputTypes, startIdx+1, true);
		return newParentNode;
	}

	private void createAndSetChildren(Turtle turtle, CommandNode parent, String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
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
	
	private void createAndSetDoTimesChildren(Turtle turtle, CommandNode parent, String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (currIdx >= userInput.length) {
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return; 
		}
		if(userInput[currIdx].equals(DEFAULT_BRACKET_IDENTIFIER)) {
			//TODO: add error checking
			currIdx++;
			parent.addChild(new CommandNode(userInput[currIdx]));
			currIdx++;
		}
		if(userInput[currIdx].equals(DEFAULT_BRACKET_IDENTIFIER)) {
			int currIdxCopy = currIdx;
			currIdxCopy++;
			ArrayList<String> withinBrackets;
			while(!userInput[currIdx].equals(DEFAULT_BRACKET_IDENTIFIER)) {
				
			}
		}
		
	}

	private int getNumArgs(String commandType) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
		String numArgsAsString = regexMatcher.findMatchingVal(commandType);
		int numArgs = Integer.parseInt(numArgsAsString);
		return numArgs;
	}

}
