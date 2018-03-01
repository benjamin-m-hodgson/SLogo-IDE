package interpreter;

import java.util.ArrayList;
import java.util.Arrays;

/** 
 * @author Susie Choi
 */

class CommandTreeBuilder {

	public static final String DEFAULT_NUM_ARGS_FNAME = "interpreter/NumArgsForCommands"; 
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command";
	public static final String DEFAULT_CONSTANT_IDENTIFIER = "Constant";
	public static final String DEFAULT_BRACKET_IDENTIFIER = "Bracket";
	public static final String DEFAULT_IF_IDENTIFIER = "If"; 
	public static final String DEFAULT_IFELSE_IDENTIFIER = "IfElse"; 
	public static final String DEFAULT_DOTIMES_IDENTIFIER = "DoTimes";
	public static final String DEFAULT_IFEXPR_END = "[";
	public static final String DEFAULT_IFBODY_END = "]";
	public static final String DEFAULT_ELSEBODY_END = "]";
	public static final String DEFAULT_BRACKET_START_IDENTIFIER = "[";
	public static final String DEFAULT_BRACKET_END_IDENTIFIER = "]";
	public static final String[] DEFAULT_DOUBLE_SUBSTITUTES = {"PenDown","PenUp","ShowTurtle","HideTurtle","Home","ClearScreen",
			"XCoordinate","YCoordinate","Heading","IsPenDown","IsShowing","Pi"};
	//	private CommandTreeReader myCommandTreeReader; 
	private String myNumArgsFileName; 
	private ArrayList<CommandNode> myCommandTrees; 
	private CommandTreeReader myCommandTreeReader;

	protected CommandTreeBuilder() {
		this(DEFAULT_NUM_ARGS_FNAME);
	}

	protected CommandTreeBuilder(String numArgsFileName) {
		myNumArgsFileName = numArgsFileName; 
		myCommandTrees = new ArrayList<CommandNode>(); 
		myCommandTreeReader = new CommandTreeReader();
	}

	protected double buildAndExecute(Turtle turtle, String[] userInput) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		myCommandTrees.clear(); 
		createCommandTree(turtle, userInput, 0);
		for (CommandNode n : myCommandTrees) {
			System.out.println(n.toString());
		}
		double finalReturnVal = -1; 
		System.out.println("number of command trees" + myCommandTrees.size());
		for (CommandNode commandTree : myCommandTrees) {
			finalReturnVal = myCommandTreeReader.readAndExecute(commandTree);
		}
		return finalReturnVal; 
	}

	private CommandNode createCommandTree(Turtle turtle, String[] userInput, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (startIdx >= userInput.length) { // || commandTypes[startIdx] == null
			return null; // TODO make this more detailed
		}
		Double checkIfDouble; 
		try {
			checkIfDouble = Double.parseDouble(userInput[startIdx]);
			return new CommandNode(userInput[startIdx], 0, turtle); 
		}
		catch (NumberFormatException e) {			
			String currCommand = userInput[startIdx]; 
			if (currCommand.equals(DEFAULT_IF_IDENTIFIER)) { // TODO deal with if "if" is not first 
				int startAfterIf = parseIf(turtle, userInput, startIdx); 
				return createCommandTree(turtle, userInput, startAfterIf);
			}
			if (currCommand.equals(DEFAULT_IFELSE_IDENTIFIER)) {
				int startAfterIfElse = parseIfElse(turtle, userInput, startIdx); 
				return createCommandTree(turtle, userInput, startAfterIfElse);
			}
			if (currCommand.equals(DEFAULT_DOTIMES_IDENTIFIER)) { 
				CommandNode tempParentNode = new CommandNode(userInput[startIdx], 3, turtle);
				int startAfterDoTimes = createAndSetDoTimesChildren(turtle, tempParentNode, userInput, startIdx+1, true); 
				return createCommandTree(turtle, userInput, startAfterDoTimes);
			}
			int numArgs = getNumArgs(currCommand);
			CommandNode newParentNode = new CommandNode(currCommand, numArgs, turtle);
			while (newParentNode.getNumArgs() == 0 ) { // accounts for multiple 1-arg arguments before args that need child nodes 
				if (startIdx >= userInput.length) {
					return newParentNode; 
				}
				myCommandTrees.add(newParentNode);
				startIdx++; 
				if (startIdx < userInput.length) {
					currCommand = userInput[startIdx]; 
					numArgs = getNumArgs(currCommand);
					newParentNode = new CommandNode(currCommand, numArgs, turtle);
				}
				else {
					return newParentNode;
				}
			}
			createAndSetChildren(turtle, newParentNode, userInput, startIdx+1, true);
			return newParentNode;
		}
	}

	//	private void createAndSetChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
	//		if (currIdx >= userInput.length) { //base case if out of bounds
	//			if (addToTrees) {
	//				myCommandTrees.add(parent);
	//			}
	//			return; 
	//		}
	//		if (parent.getNumArgs() == 0) {
	//			return;
	//		}
	//		String currInputToken = userInput[currIdx];
	//		Double firstIsDouble; 
	//		try {
	//			firstIsDouble = Double.parseDouble(currInputToken);
	//			CommandNode newChildNode = new CommandNode(currInputToken, turtle);
	//			parent.addChild(newChildNode);
	//			if (parent.getNumChildren() < parent.getNumArgs()) { 
	//				createAndSetChildren(turtle, parent, userInput, currIdx+1, addToTrees);
	//			} 
	//			else {
	//				if (addToTrees) {
	//					myCommandTrees.add(parent);
	//				}
	//				createCommandTree(turtle, userInput, currIdx+1);
	//			}
	//			return; 
	//		} 
	//		catch (NumberFormatException e) {
	//			if (isDoubleSubstitute(currInputToken)) {
	//				CommandNode newChildNode = new CommandNode(currInputToken, turtle);
	//				parent.addChild(newChildNode);
	//				if (parent.getNumChildren() < parent.getNumArgs()) { 
	//					createAndSetChildren(turtle, parent, userInput, currIdx+1, addToTrees);
	//				} 
	//				else {
	//					if (addToTrees) {
	//						myCommandTrees.add(parent);
	//					}
	//					createCommandTree(turtle, userInput, currIdx+1);
	//				}
	//				return; 
	//			}
	//			//		if (commandTypes[currIdx].equals("DEFAULT_IF_IDENTIFIER")) { 
	//			//			// currIdx = 
	//			//			parseIf(turtle, userInput, commandTypes, allInputTypes, currIdx); // TODO consider if's parent 
	//			//			System.out.println("returning");
	//			//			return; 
	//			//		}
	////			boolean validChildIdentified = false; 
	//			for (int idx = currIdx+1; idx < userInput.length; idx++) { 
	//				Double currDouble; 
	//				try {
	//					currDouble = Double.parseDouble(currInputToken);
	//					CommandNode newChildNode = new CommandNode(currInputToken, turtle);
	//					int numArgs = getNumArgs(userInput[idx-1]);
	//					CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtle);
	//					if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
	//						createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
	//					}
	//					for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) { // TODO re-evaluate back-track for when do times comes into the picture....
	//						int backTrackNumArgs = getNumArgs(userInput[backtrack]);
	//						CommandNode backtrackCommandNode = new CommandNode(userInput[backtrack], backTrackNumArgs, newCommandNode, turtle);
	//						newCommandNode = backtrackCommandNode; 
	//					}
	//					parent.addChild(newCommandNode);
	//					if (parent.getNumChildren() < parent.getNumArgs()) { 
	//						createAndSetChildren(turtle, parent, userInput, idx+1, addToTrees);
	//					}
	//					else {
	//						createCommandTree(turtle, userInput, idx+1);
	//					}
	//					if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
	//						myCommandTrees.add(parent);
	//					}
	//					return; 
	//				} 
	//				catch (NumberFormatException e1) { // TODO EXTRACT BODY INTO HELPER
	//					if (isDoubleSubstitute(currInputToken)) {
	//						CommandNode newChildNode = new CommandNode(currInputToken, turtle);
	//						int numArgs = getNumArgs(userInput[idx-1]);
	//						CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtle);
	//						if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
	//							createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
	//						}
	//						for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) { // TODO re-evaluate back-track for when do times comes into the picture....
	//							int backTrackNumArgs = getNumArgs(userInput[backtrack]);
	//							CommandNode backtrackCommandNode = new CommandNode(userInput[backtrack], backTrackNumArgs, newCommandNode, turtle);
	//							newCommandNode = backtrackCommandNode; 
	//						}
	//						parent.addChild(newCommandNode);
	//						if (parent.getNumChildren() < parent.getNumArgs()) { 
	//							createAndSetChildren(turtle, parent, userInput, idx+1, addToTrees);
	//						}
	//						else {
	//							createCommandTree(turtle, userInput, idx+1);
	//						}
	//						if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
	//							myCommandTrees.add(parent);
	//						}
	//						return; 
	//					}
	//					continue; 
	//				}
	//			}
	//		}
	//	}

	// TODO account for when no-arg commands are used as children (instead of double)
	private void createAndSetChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (currIdx >= userInput.length) { //base case if out of bounds
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
 			return;
		}
		//		System.out.println(userInput[currIdx]);
		Double firstIsDouble; 
		try {
			firstIsDouble = Double.parseDouble(userInput[currIdx]);
			CommandNode newChildNode = new CommandNode(userInput[currIdx], turtle);
			parent.addChild(newChildNode);
			if (parent.getNumChildren() < parent.getNumArgs()) { 
				createAndSetChildren(turtle, parent, userInput, currIdx+1, addToTrees);
			} 
			else {
				if (addToTrees) {
					myCommandTrees.add(parent);
				}
				createCommandTree(turtle, userInput, currIdx+1);
			}
			return; 
		} 
		catch (NumberFormatException e) {
			//		if (commandTypes[currIdx].equals("DEFAULT_IF_IDENTIFIER")) { 
			//			// currIdx = 
			//			parseIf(turtle, userInput, commandTypes, allInputTypes, currIdx); // TODO consider if's parent 
			//			System.out.println("returning");
			//			return; 
			//		}
			for (int idx = currIdx+1; idx < userInput.length; idx++) { 
				Double currDouble; 
				try {
					currDouble = Double.parseDouble(userInput[idx]);
					CommandNode newChildNode = new CommandNode(userInput[idx], turtle);
					int numArgs = getNumArgs(userInput[idx-1]);
					CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtle);
					if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
						createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
					}
					for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) { // TODO re-evaluate back-track for when do times comes into the picture....
						int backTrackNumArgs = getNumArgs(userInput[backtrack]);
						CommandNode backtrackCommandNode = new CommandNode(userInput[backtrack], backTrackNumArgs, newCommandNode, turtle);
						newCommandNode = backtrackCommandNode; 
					}
					parent.addChild(newCommandNode);
					if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
						createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
					}
					if (parent.getNumChildren() < parent.getNumArgs()) { 
						createAndSetChildren(turtle, parent, userInput, idx+1, addToTrees);
					}
					else {
						createCommandTree(turtle, userInput, idx+1);
					}
					if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
						myCommandTrees.add(parent);
					}
					return; 
				} 
				catch (NumberFormatException e1) {
					continue;
				}
			}
		}
	}

	private int parseIf(Turtle turtle, String[] userInput, int ifIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		int ifExprEndSearch = ifIdx; 
		while (! userInput[ifExprEndSearch].equals(DEFAULT_IFEXPR_END)) {
			ifExprEndSearch++; 
		}
		// ifExprEndSearch is now at "["
		int ifBodyEndSearch = ifExprEndSearch; 
		while (! userInput[ifBodyEndSearch].equals(DEFAULT_IFBODY_END)) {
			ifBodyEndSearch++; 
		}
		// ifBodyEndSearch is now at "]"
		String[] ifExpr = Arrays.copyOfRange(userInput, ifIdx+1, ifExprEndSearch);

		String[] ifBody = Arrays.copyOfRange(userInput, ifExprEndSearch+1, ifBodyEndSearch);

		String ifCommand = userInput[ifIdx]; 
		int numArgs = getNumArgs(ifCommand);
		CommandNode ifCommandNode = new CommandNode(userInput[ifIdx], numArgs, turtle);

		ArrayList<String> ifBodyString = new ArrayList<String>(Arrays.asList(ifBody)); 
		String commandNodeInfo = String.join(" ", ifBodyString);
		ifCommandNode.addChild(new CommandNode(commandNodeInfo));

		createAndSetChildren(turtle, ifCommandNode, ifExpr, 0, true);
		return ifBodyEndSearch+1;
	}

	private int parseIfElse(Turtle turtle, String[] userInput, int ifIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		int ifExprEndSearch = ifIdx; 
		while (! userInput[ifExprEndSearch].equals(DEFAULT_IFEXPR_END)) {
			ifExprEndSearch++; 
		}
		// ifExprEndSearch is now at first "["
		int ifBodyEndSearch = ifExprEndSearch; 
		while (! userInput[ifBodyEndSearch].equals(DEFAULT_IFBODY_END)) {
			ifBodyEndSearch++; 
		}
		// ifBodyEndSearch is now at first "]"
		int elseBodyEndSearch = ifBodyEndSearch+2; // skipping over [ 
		while (! userInput[elseBodyEndSearch].equals(DEFAULT_ELSEBODY_END)) {
			elseBodyEndSearch++; 
		}
		String[] ifExpr = Arrays.copyOfRange(userInput, ifIdx+1, ifExprEndSearch);
		String[] ifBody = Arrays.copyOfRange(userInput, ifExprEndSearch+1, ifBodyEndSearch);
		String[] elseBody = Arrays.copyOfRange(userInput, ifBodyEndSearch+2, elseBodyEndSearch);

		String ifCommand = userInput[ifIdx]; 
		int numArgs = getNumArgs(ifCommand);
		CommandNode ifElseCommandNode = new CommandNode(userInput[ifIdx], numArgs, turtle);

		ArrayList<String> ifBodyList = new ArrayList<String>(Arrays.asList(ifBody)); 
		String ifCommandNodeInfo = String.join(" ", ifBodyList);
		ifElseCommandNode.addChild(new CommandNode(ifCommandNodeInfo));

		ArrayList<String> elseBodyList = new ArrayList<String>(Arrays.asList(elseBody));
		String elseCommandNodeInfo = String.join(" ", elseBodyList);
		ifElseCommandNode.addChild(new CommandNode(elseCommandNodeInfo));

		createAndSetChildren(turtle, ifElseCommandNode, ifExpr, 0, true);
		return elseBodyEndSearch+1;
	}

	private int createAndSetDoTimesChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		myCommandTrees.add(parent);
		System.out.println("command trees size within create and set dotimes children: " + myCommandTrees.size());
		//adding temporary variable name to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			currIdx++;
			parent.addChild(new CommandNode(userInput[currIdx]));
			currIdx++;
		}
		//else {
		//	throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		//}
		int currIdxCopy = currIdx;  
//		for(int k = 0; k<userInput.length; k+=1) {
//			System.out.println(userInput[k]);
//		}
		while(!(userInput[currIdxCopy].equals(DEFAULT_BRACKET_END_IDENTIFIER))) {
			currIdxCopy++;
		}
		//adding command info to children
		//System.out.println("currIdx" + currIdx + "currIdxCopy" + currIdxCopy);
		parent.addChild(createCommandTree(turtle, Arrays.copyOfRange(userInput, currIdx, currIdxCopy), 0));
		currIdxCopy++;
		currIdx = currIdxCopy;
		//adding string info to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			currIdx++;
			String repeatedCommand = userInput[currIdx];
			currIdx++;
			while(currIdx < userInput.length-1) {
				repeatedCommand = String.join(" ", repeatedCommand, userInput[currIdx]);
				currIdx++;
			}
			if(!userInput[currIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
				throw new BadFormatException("Brackets are messed up in DoTimes");
			}
			parent.addChild(new CommandNode(repeatedCommand));
		}
		else {
			throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		}
		currIdx++;
		return currIdx;
	}

	private int getNumArgs(String commandType) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//System.out.println(commandType);
		try {
			Double.parseDouble(commandType);
			return 0;
		}
		catch(NumberFormatException e) {
			RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
			String numArgsAsString = regexMatcher.findMatchingVal(commandType);
			System.out.println(commandType);
			int numArgs = Integer.parseInt(numArgsAsString);
			return numArgs;
		}

	}


	private boolean isDoubleSubstitute(String inputToken) {
		for (int j = 0; j < DEFAULT_DOUBLE_SUBSTITUTES.length; j ++) {
			if (inputToken.equals(DEFAULT_DOUBLE_SUBSTITUTES[j])) {
				return true;
			}
		}
		return false; 
	}

}
