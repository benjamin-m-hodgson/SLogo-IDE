package interpreter;

import java.util.ArrayList;

/** 
 * @author Susie Choi
 */

class CommandTreeBuilder {

	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command";
	public static final String DEFAULT_CONSTANT_IDENTIFIER = "Constant";
	public static final String DEFAULT_BRACKET_IDENTIFIER = "Bracket";
	public static final String DEFAULT_IF_IDENTIFIER = "If"; 
	public static final String DEFAULT_IFEXPR_END = "[";
	public static final String DEFAULT_IFBODY_END = "]";
	public static final String DEFAULT_BRACKET_START_IDENTIFIER = "BracketStart";
	public static final String DEFAULT_BRACKET_END_IDENTIFIER = "BracketEnd";
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
		myCommandTrees.clear();
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
		if (startIdx >= userInput.length || commandTypes[startIdx] == null) {
			return null; // TODO make this more detailed
		}
		String currCommand = commandTypes[startIdx]; 
		int numArgs = getNumArgs(currCommand);
		CommandNode newParentNode = new CommandNode(currCommand, numArgs, turtle);
		while (newParentNode.getNumArgs() == 0) { // accounts for multiple 1-arg arguments before args that need child nodes 
			myCommandTrees.add(newParentNode);
			startIdx++; 
			currCommand = commandTypes[startIdx]; 
			numArgs = getNumArgs(currCommand);
			newParentNode = new CommandNode(currCommand, numArgs, turtle);
		}
		createAndSetChildren(turtle, newParentNode, userInput, commandTypes, allInputTypes, startIdx+1, true);
		return newParentNode;
	}

	private void createAndSetChildren(Turtle turtle, CommandNode parent, String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (currIdx >= userInput.length) { //base case if out of bounds
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return; 
		}
//		System.out.println(userInput[currIdx]);
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
		if (commandTypes[currIdx].equals("If")) { // TODO finish dealing with if
			int ifExprEndSearch = 0; 
			while (! userInput[ifExprEndSearch].equals(DEFAULT_IFEXPR_END)) {
				ifExprEndSearch++; 
			}
			// ifExprEndSearch is now at "["
			int ifBodyEndSearch = ifExprEndSearch; 
			while (! userInput[ifBodyEndSearch].equals(DEFAULT_IFBODY_END)) {
				ifBodyEndSearch++; 
			}
			// ifBodyEndSearch is now at "]"
			
			currIdx = ifBodyEndSearch+1; // TODO consider if's parent 
		}
		for (int idx = currIdx+1; idx < userInput.length; idx++) { 
			if (allInputTypes[idx].equals(DEFAULT_CONSTANT_IDENTIFIER)) {
				CommandNode newChildNode = new CommandNode(userInput[idx], turtle);
				int numArgs = getNumArgs(commandTypes[idx-1]);
				CommandNode newCommandNode = new CommandNode(commandTypes[idx-1], numArgs, newChildNode, turtle);
				if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
					createAndSetChildren(turtle, newCommandNode, userInput, commandTypes, allInputTypes, idx+1, false);
				}
				for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) { // TODO re-evaluate back-track for when do times comes into the picture....
					int backTrackNumArgs = getNumArgs(commandTypes[backtrack]);
					CommandNode backtrackCommandNode = new CommandNode(commandTypes[backtrack], backTrackNumArgs, newCommandNode, turtle);
					newCommandNode = backtrackCommandNode; 
				}
				parent.addChild(newCommandNode);
				if (parent.getNumChildren() < parent.getNumArgs()) { 
					createAndSetChildren(turtle, parent, userInput, commandTypes, allInputTypes, idx+1, addToTrees);
				}
				else {
					createCommandTree(turtle, userInput, commandTypes, allInputTypes, idx+1);
				}
				if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
					myCommandTrees.add(parent);
				}
				return; 
			}
		}
	}

	private int createAndSetDoTimesChildren(Turtle turtle, CommandNode parent, String[] userInput, String[] commandTypes, String[] allInputTypes, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (currIdx >= userInput.length) {
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return currIdx; 
		}
		//adding temporary variable name to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			currIdx++;
			parent.addChild(new CommandNode(userInput[currIdx]) );
			currIdx++;
		}
		else {
			throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		}
		ArrayList<String> doTimesEndValInfo = new ArrayList<>;
		while()
		//adding command info to children
		parent.addChild(createCommandTree(turtle, String[] userInput, String[] commandTypes, String[] allInputTypes, int startIdx));
		//adding string info to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_IDENTIFIER)) {
			int currIdxCopy = currIdx;
			currIdxCopy++;
			ArrayList<String> withinBrackets;
			while(!userInput[currIdx].equals(DEFAULT_BRACKET_IDENTIFIER)) {
				
			}
		}
		else {
			throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		}
		return currIdx;

	}

	private int getNumArgs(String commandType) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
//		System.out.println(commandType);
		RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
		String numArgsAsString = regexMatcher.findMatchingVal(commandType);
		int numArgs = Integer.parseInt(numArgsAsString);
		return numArgs;
	}

}
