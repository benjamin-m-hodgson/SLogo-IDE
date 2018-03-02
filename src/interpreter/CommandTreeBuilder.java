package interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
/** 
 * @author Susie Choi
 */

class CommandTreeBuilder {

	public static final String DEFAULT_NUM_ARGS_FNAME = "interpreter/NumArgsForCommands"; 
	public static final String DEFAULT_NUM_BRACKETS_FNAME = "interpreter/NumBracketsControlFlow";
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command";
	public static final String DEFAULT_CONSTANT_IDENTIFIER = "Constant";
	public static final String DEFAULT_BRACKET_IDENTIFIER = "Bracket";
	public static final String DEFAULT_IF_IDENTIFIER = "If"; 
	public static final String DEFAULT_IFELSE_IDENTIFIER = "IfElse"; 
	public static final String DEFAULT_DOTIMES_IDENTIFIER = "DoTimes";
	public static final String DEFAULT_REPEAT_IDENTIFIER = "Repeat";
	public static final String DEFAULT_FOR_IDENTIFIER = "For";
	public static final String DEFAULT_ELSEBODY_END = "]";
	public static final String DEFAULT_BRACKET_START_IDENTIFIER = "[";
	public static final String DEFAULT_BRACKET_END_IDENTIFIER = "]";
	public static final String DEFAULT_VAR_IDENTIFIER = ":";
	public static final String DEFAULT_USERCOMMAND_IDENTIFIER = "MakeUserInstruction";
	public static final String DEFAULT_USERCOMMAND_NAME = "UserInstruction";
	public static final String[] DEFAULT_DOUBLE_SUBSTITUTES = {"PenDown","PenUp","ShowTurtle","HideTurtle","Home","ClearScreen",
			"XCoordinate","YCoordinate","Heading","IsPenDown","IsShowing","Pi"};
	//	private CommandTreeReader myCommandTreeReader; 
	private String myNumArgsFileName; 
	private String myNumBracketsFileName;
	private ArrayList<CommandNode> myCommandTrees; 
	private CommandTreeReader myCommandTreeReader;
	private HashMap<String, String> myUserDefCommands; 
	private HashMap<String, Integer> myUserDefCommandsNumArgs;
	private Map<String, Double> myVariables;
 
	protected CommandTreeBuilder(Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		this(DEFAULT_NUM_ARGS_FNAME, variables, userDefCommands, userDefCommandsNumArgs);
	}

	protected CommandTreeBuilder(String numArgsFileName, Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		myNumArgsFileName = numArgsFileName; 
		myCommandTrees = new ArrayList<CommandNode>();  
		myCommandTreeReader = new CommandTreeReader(variables, userDefCommands, userDefCommandsNumArgs);
		myUserDefCommands = (HashMap<String, String>)userDefCommands; 
		myUserDefCommandsNumArgs = (HashMap<String, Integer>)userDefCommandsNumArgs;
		myVariables = variables;
	}

	protected double buildAndExecute(Turtle turtle, String[] userInput, boolean shouldExecute) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		double finalReturnVal = -1; 
		myCommandTrees.clear(); 
		createCommandTree(turtle, userInput, 0);
		
		//System.out.println("command tree number bigger: " + myCommandTrees.size());
//		for (CommandNode n : myCommandTrees) {
//			System.out.println(n.toString());
//		}
		
	//	System.out.println("number of command trees" + myCommandTrees.size());
		if(shouldExecute) {
			for (CommandNode commandTree : myCommandTrees) {
				finalReturnVal = myCommandTreeReader.readAndExecute(commandTree);
			}
		}
		return finalReturnVal; 
	}

	private CommandNode createCommandTree(Turtle turtle, String[] userInput, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		
		if (startIdx >= userInput.length) { // || commandTypes[startIdx] == null
			return null; // TODO make this more detailed
		}
		Double checkIfDouble; 
		String currCommand = userInput[startIdx]; 
		try {
			checkIfDouble = Double.parseDouble(currCommand);
			return new CommandNode(currCommand, 0, turtle); 
		}
		catch (NumberFormatException e) {
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
//			if (currCommand.equals(DEFAULT_REPEAT_IDENTIFIER)) {
//				CommandNode tempParentNode = new CommandNode(userInput[startIdx], 3, turtle);
//				int startAfterRepeat = createAndSetRepeatChildren(turtle, tempParentNode, userInput, startIdx+1, true); 
//				return createCommandTree(turtle, userInput, startAfterRepeat);
//			}
				
			if (currCommand.equals(DEFAULT_USERCOMMAND_IDENTIFIER)) {
				int startAfterTo = parseMakeUserCommand(turtle, userInput, startIdx);
				return createCommandTree(turtle, userInput, startAfterTo);
			}
  			if (myUserDefCommands.containsKey(currCommand)) {
				parseUserCommand(turtle, userInput, startIdx, myUserDefCommandsNumArgs.get(currCommand));
				return null; // TODO FIX THIS 
//				return createCommandTree(turtle, userInput, startAfterUserCommand);
			}
			if (currCommand.equals(DEFAULT_FOR_IDENTIFIER)) {
				CommandNode tempParentNode = new CommandNode(userInput[startIdx], 5, turtle);
				int startAfterFor = createAndSetForChildren(turtle, tempParentNode, userInput, startIdx+1, true); 
				return createCommandTree(turtle, userInput, startAfterFor);
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
			createAndSetChildren(turtle, newParentNode, userInput, startIdx, true);
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
		int currIdxNonRepeat = currIdx + 1;
		if (currIdxNonRepeat >= userInput.length) { //base case if out of bounds
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return;
		}
		if(userInput[currIdx].equals(DEFAULT_REPEAT_IDENTIFIER)) {
			int afterRepeat = createAndSetRepeatChildren(turtle, parent, userInput, currIdx+1, addToTrees);
			createCommandTree(turtle, userInput, afterRepeat);
			return;
		}
		currIdx++;
	
//				System.out.println(userInput[currIdx]);
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
			if (String.valueOf(userInput[currIdx].charAt(0)).equals(DEFAULT_VAR_IDENTIFIER)) {
				CommandNode newChildNode = new CommandNode(userInput[currIdx]);
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
					if (String.valueOf(userInput[idx].charAt(0)).equals(DEFAULT_VAR_IDENTIFIER)) {
						CommandNode newChildNode = new CommandNode(userInput[idx]);
						parent.addChild(newChildNode);
						if (parent.getNumChildren() < parent.getNumArgs()) { 
							createAndSetChildren(turtle, parent, userInput, idx+1, addToTrees);
						} 
						else {
							if (addToTrees) {
								myCommandTrees.add(parent);
							}
							createCommandTree(turtle, userInput, idx+1);
						}
						return; 
					}
					else {
						continue;
					}
				}
			}
		}
	}

	private int parseIf(Turtle turtle, String[] userInput, int ifIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		int ifExprEndSearch = ifIdx; 
		while (! userInput[ifExprEndSearch].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			ifExprEndSearch++; 
		}
		// ifExprEndSearch is now at "["
		int ifBodyEndSearch = ifExprEndSearch; 
		while (! userInput[ifBodyEndSearch].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
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
		while (! userInput[ifExprEndSearch].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			ifExprEndSearch++; 
		}
		// ifExprEndSearch is now at first "["
		int ifBodyEndSearch = ifExprEndSearch; 
		while (! userInput[ifBodyEndSearch].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
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
		//System.out.println("command trees size within create and set dotimes children: " + myCommandTrees.size());
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
			int repeatCount = 1;
			int endBracketCount = 0;
			while( endBracketCount < repeatCount) {
				if(userInput[currIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)){
					endBracketCount++;
				}
				if(userInput[currIdx].equals(DEFAULT_REPEAT_IDENTIFIER)){
					repeatCount++;
				}
				repeatedCommand = String.join(" ", repeatedCommand, userInput[currIdx]);
				currIdx++;
			}
			//System.out.println("making it to here");
//			currIdx++;
//			String repeatedCommand = userInput[currIdx];
//			currIdx++;
//			while(currIdx < userInput.length-1) {
//				repeatedCommand = String.join(" ", repeatedCommand, userInput[currIdx]);
//				currIdx++;
//			}
//			if(!userInput[currIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
//				throw new BadFormatException("Brackets are messed up in DoTimes");
//			}
			parent.addChild(new CommandNode(repeatedCommand));
			
		}
		else {
			throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		}
		currIdx++;
		return currIdx;
	}
	private int createAndSetRepeatChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//myCommandTrees.add(parent);
		//System.out.println("command tree number: " + myCommandTrees.size());
		if (addToTrees) {
			myCommandTrees.add(parent);
		}
		parent.addChild(new CommandNode(":repcount", turtle));
//		//adding temporary variable name to children
			int repeatCount = 1;
			int startBracketCount = 0;
			//currIdx++;
			int currIdxCopy = currIdx;
			currIdxCopy++;
			try {
				//currIdxCopy = searchForBracket(currIdxCopy, userInput, DEFAULT_BRACKET_START_IDENTIFIER);
				while( startBracketCount!= repeatCount) {
					if(userInput[currIdxCopy].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
						startBracketCount++;
					}
					if(userInput[currIdxCopy].equals(DEFAULT_REPEAT_IDENTIFIER)){
						repeatCount++;
					}
					currIdxCopy++;
				}
			}
			catch(NullPointerException e) {
				throw new UnidentifiedCommandException("Repeat syntax is not correct.");
			}
			//adding command info to children
			System.out.println("currIdx" + currIdx + "currIdxCopy" + currIdxCopy);
			parent.addChild(createCommandTree(turtle, Arrays.copyOfRange(userInput, currIdx, currIdxCopy-1), 0));
		
		currIdx = currIdxCopy - 1;
		//adding string info to children
		System.out.println("current input " + userInput[currIdx]);
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			repeatCount = 1;
			int endBracketCount = 0;
			currIdx++;
			String repeatedCommand = userInput[currIdx];
			if(userInput[currIdx].equals(DEFAULT_REPEAT_IDENTIFIER)) {
				repeatCount++;
			}
			currIdx++;
			
			//currIdx = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER);
			while( endBracketCount < repeatCount) {
				System.out.println("checking" + userInput[currIdx]);
				if(userInput[currIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)){
					endBracketCount++;
				}
				if(userInput[currIdx].equals(DEFAULT_REPEAT_IDENTIFIER)){
					repeatCount++;
				}
				if(endBracketCount!=repeatCount) {
					repeatedCommand = String.join(" ", repeatedCommand, userInput[currIdx]);
				}
				System.out.println("repeatcount: " + repeatCount);
				currIdx++;
			}
			if(!(userInput[currIdx-1].equals(DEFAULT_BRACKET_END_IDENTIFIER))) {
				throw new BadFormatException("Brackets are messed up in Repeat");
			}
			parent.addChild(new CommandNode(repeatedCommand, turtle));
		}
		else {
			throw new UnidentifiedCommandException("Repeat syntax incorrect");
		}
		//currIdx++;
		return currIdx;
	}
	private int createAndSetForChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		myCommandTrees.add(parent);
		if(!userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			throw new UnidentifiedCommandException("No starting bracket for For.");
		}
		//adding variable child
		currIdx++;
		parent.addChild(new CommandNode(userInput[currIdx], turtle));
		currIdx++;
		int currIdxCopy = currIdx;
		//adding command children for start/end/increment
		
		while(!userInput[currIdxCopy].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			currIdxCopy++;
		}
		CommandTreeBuilder tempBuilder = new CommandTreeBuilder(myNumArgsFileName, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		tempBuilder.buildAndExecute(turtle, Arrays.copyOfRange(userInput, currIdx, currIdxCopy), false);
		List<CommandNode> discreteCommands = tempBuilder.getCommandTrees();
		for(CommandNode n: discreteCommands) {
			//System.out.println("angle" + n.getTurtle());
			parent.addChild(n);
		}
		
		// should be first element after end bracket
		currIdx = currIdxCopy + 1;
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			currIdx++;
			String repeatedCommand = userInput[currIdx];
			currIdx++;
			int repeatCount = 1;
			int endBracketCount = 0;
			while( endBracketCount < repeatCount) {
				if(userInput[currIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)){
					endBracketCount++;
				}
				if(userInput[currIdx].equals(DEFAULT_REPEAT_IDENTIFIER)){
					repeatCount++;
				}
				if(endBracketCount!=repeatCount) {
					repeatedCommand = String.join(" ", repeatedCommand, userInput[currIdx]);
				}
				
				currIdx++;
			}
			if(!(userInput[currIdx-1].equals(DEFAULT_BRACKET_END_IDENTIFIER))) {
				throw new BadFormatException("Brackets are messed up in loop");
			}
		//add repeated command string last
			parent.addChild(new CommandNode(repeatedCommand, turtle));
		}
		else {
			throw new UnidentifiedCommandException("Repeat syntax incorrect");
		}
		currIdx++;
		return currIdx;
		
	}
	protected List<CommandNode> getCommandTrees(){
		return myCommandTrees;
	}

	private int parseMakeUserCommand(Turtle turtle, String[] userInput, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		int endToIdx = startIdx; 
		while (! userInput[endToIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			endToIdx++; 
		} // endToIdx is at FIRST ']' 
		String[] varsArray = Arrays.copyOfRange(userInput, startIdx+3, endToIdx);
		CommandNode varsNode = new CommandNode(String.join(" ", varsArray)); 

		int finalEndToIdx = endToIdx+1;
		while (! userInput[finalEndToIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			finalEndToIdx++; 
		} // finalEndToIdx is at FINAL ']'
		
		String[] commandContent = Arrays.copyOfRange(userInput, endToIdx+2, finalEndToIdx);
		String userCommandString = String.join(" ", commandContent);
		CommandNode userCommandContent = new CommandNode(userCommandString);

		
		String userCommandName = userInput[startIdx+1];
		CommandNode userCommandNameNode = new CommandNode(userCommandName);
		
		CommandNode userCommandNode = new CommandNode(userInput[startIdx], getNumArgs(userInput[startIdx]), userCommandNameNode, turtle);
		userCommandNode.addChild(varsNode);
		userCommandNode.addChild(userCommandContent);
		myCommandTrees.add(userCommandNode);
		return finalEndToIdx+1;
	}

	private void parseUserCommand(Turtle turtle, String[] userInput, int startIdx, int numArgs) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String userCommandName = userInput[startIdx];
		CommandNode userCommandNameNode = new CommandNode(userCommandName, numArgs, turtle, true); 
		createAndSetChildren(turtle, userCommandNameNode, userInput, startIdx+1, false);
		CommandNode userCommandNode = new CommandNode(DEFAULT_USERCOMMAND_NAME, 1, userCommandNameNode, turtle);
		myCommandTrees.add(userCommandNode);
		System.out.println(userCommandNode.toString());
	}

	private int getNumArgs(String commandType) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
//		System.out.println(commandType);
		try {
			Double.parseDouble(commandType);
			return 0;
		}
		catch(NumberFormatException e) {
			RegexMatcher regexMatcher = new RegexMatcher(myNumArgsFileName);
			String numArgsAsString = regexMatcher.findMatchingVal(commandType);
			int numArgs = Integer.parseInt(numArgsAsString);
			return numArgs;
		}
	}
	private int getNumBrackets(String commandType) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		 	RegexMatcher regexMatcher = new RegexMatcher(myNumBracketsFileName);
		 	String numBracketsAsString = new String();
		 	int numArgs = 0;
		 	try {
		 		numBracketsAsString = regexMatcher.findMatchingVal(commandType);
		 	}
		 	catch(Exception e) {
		 		return 0;
		 	}
		 	try {
		 		numArgs = Integer.parseInt(numBracketsAsString);
		 	}
		 	catch(Exception e) {
		 		return 0;
		 	}
			return numArgs;
	}

	private boolean isDoubleSubstitute(String inputToken) {
		for (int j = 0; j < DEFAULT_DOUBLE_SUBSTITUTES.length; j ++) {
			if (inputToken.equals(DEFAULT_DOUBLE_SUBSTITUTES[j])) {
				return true;
			}
		}
		return false; 
	}
//	private int searchForBracket(int currIdxCopy, String[] userInput, String bracketIdentifier) throws BadFormatException, UnidentifiedCommandException, MissingInformationException{
//		int bracketNeededCount = 1;
//		int bracketSeenCount = 0;
//		while(bracketSeenCount < bracketNeededCount) {
//			System.out.println(userInput[currIdxCopy]);
//			if(userInput[currIdxCopy].equals(bracketIdentifier)) {
//				bracketSeenCount++;
//			}
//			bracketNeededCount = bracketNeededCount + getNumBrackets(userInput[currIdxCopy]);
//			currIdxCopy++;
//		}
//		return currIdxCopy;
//	}

}
