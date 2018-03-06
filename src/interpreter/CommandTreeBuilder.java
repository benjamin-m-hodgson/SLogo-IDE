package interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	public static final String DEFAULT_IFEXPR_END = "[";
	public static final String DEFAULT_IFBODY_END = "]";
	public static final String DEFAULT_ELSEBODY_END = "]";
	public static final String DEFAULT_BRACKET_START_IDENTIFIER = "[";
	public static final String DEFAULT_BRACKET_END_IDENTIFIER = "]";
	public static final String DEFAULT_VAR_IDENTIFIER = ":";
	public static final String DEFAULT_USERCOMMAND_IDENTIFIER = "MakeUserInstruction";
	public static final String DEFAULT_USERCOMMAND_NAME = "UserInstruction";
	public static final String DEFAULT_BACKCHANGE_IDENTIFIER = "SetBackground";
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
	private SimpleIntegerProperty myBackColor; 

	protected CommandTreeBuilder(Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		this(DEFAULT_NUM_ARGS_FNAME, variables, userDefCommands, userDefCommandsNumArgs);
	}

	protected CommandTreeBuilder(String numArgsFileName, Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		myNumArgsFileName = numArgsFileName; 
		myNumBracketsFileName = DEFAULT_NUM_BRACKETS_FNAME;
		myCommandTrees = new ArrayList<CommandNode>();  
		myCommandTreeReader = new CommandTreeReader(variables, userDefCommands, userDefCommandsNumArgs);
		myUserDefCommands = (HashMap<String, String>)userDefCommands; 
		myUserDefCommandsNumArgs = (HashMap<String, Integer>)userDefCommandsNumArgs;
		myVariables = variables;
		myBackColor = new SimpleIntegerProperty(0);
	}

	protected double buildAndExecute(Turtle turtle, String[] userInput, boolean shouldExecute) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		System.out.println("BEGINNING OF SENT IN INPUT -------------------------");
		for (String s : userInput) System.out.println(s);
		System.out.println("END OF SENT IN INPUT -------------------------");

		double finalReturnVal = -1; 
		myCommandTrees.clear(); 
		int currIdx = 0;
		//System.out.println("current user input: " + userInput[currIdx]);
		if(myVariables.containsKey(userInput[currIdx])) {
			userInput[currIdx] = myVariables.get(userInput[currIdx]).toString();
		}
		while(currIdx<userInput.length) {
			try {
				Double doubleArg = Double.parseDouble(userInput[currIdx]);
				myCommandTrees.add(new CommandNode(userInput[currIdx], 0, turtle));
				currIdx++;
			}
			catch(NumberFormatException e) {
				break;
			}
		}
		if(!(currIdx>=userInput.length)) {
			createCommandTree(turtle, userInput, currIdx);
			//System.out.println("command tree number: " + myCommandTrees.size());
		}


		//System.out.println("command tree number bigger: " + myCommandTrees.size());
		System.out.println("PRINTING OUT COMM TREES ------------------------------------");
		for (CommandNode n : myCommandTrees) {
			System.out.println(n.toString());
		}

		//	System.out.println("number of command trees" + myCommandTrees.size());
		if(shouldExecute) {
			for (CommandNode commandTree : myCommandTrees) {
				if (commandTree.getInfo().equals(DEFAULT_BACKCHANGE_IDENTIFIER)) {
					myBackColor.set(Integer.parseInt(commandTree.childrenToString().trim())); 
				}
				finalReturnVal = myCommandTreeReader.readAndExecute(commandTree);
			}
		}
		return finalReturnVal; 
	}

	private CommandNode createCommandTree(Turtle turtle, String[] userInput, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {

		if (startIdx >= userInput.length||userInput[startIdx].equals("]")) { // || commandTypes[startIdx] == null //TODO fix this so not so obvious
			return null; // TODO make this more detailed
		}
		if(myVariables.containsKey(userInput[startIdx])) {
			userInput[startIdx] = myVariables.get(userInput[startIdx]).toString();
		}

		Double checkIfDouble; 
		String currCommand = userInput[startIdx]; 
		try {
			checkIfDouble = Double.parseDouble(currCommand);
			return new CommandNode(currCommand, 0, turtle); 
		}
		catch (NumberFormatException e) {
			if (currCommand.equals(DEFAULT_IF_IDENTIFIER)) { 
				int startAfterIf = parseIf(turtle, userInput, startIdx); 
				return createCommandTree(turtle, userInput, startAfterIf);
			}
			if (currCommand.equals(DEFAULT_IFELSE_IDENTIFIER)) {
				int startAfterIfElse = parseIfElse(turtle, userInput, startIdx); 
				return createCommandTree(turtle, userInput, startAfterIfElse);
			}
			if (currCommand.equals(DEFAULT_USERCOMMAND_IDENTIFIER)) {
				int startAfterTo = parseMakeUserCommand(turtle, userInput, startIdx);
				return createCommandTree(turtle, userInput, startAfterTo);
			}
			if (myUserDefCommands.containsKey(currCommand)) {
				parseUserCommand(turtle, userInput, startIdx, myUserDefCommandsNumArgs.get(currCommand));
				return null; // TODO FIX THIS 
				//				return createCommandTree(turtle, userInput, startAfterUserCommand);
			}
			//System.out.println("currCommand: " + currCommand);
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
			if (myUserDefCommands.containsKey(currCommand)) {
				parseUserCommand(turtle, userInput, startIdx, myUserDefCommandsNumArgs.get(currCommand));
				return null; // TODO FIX THIS 
				//				return createCommandTree(turtle, userInput, startAfterUserCommand);
			}
			createAndSetChildren(turtle, newParentNode, userInput, startIdx+1, true);
			return newParentNode;
		}
	}

	private void createAndSetChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//int currIdxNonRepeat = currIdx + 1; CHANGED THIS
		if ((currIdx) >= userInput.length) { //base case if out of bounds
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return;
		}
		if (currIdx >= 1) {
			if(userInput[currIdx-1].equals(DEFAULT_REPEAT_IDENTIFIER)) { //CHANGED CURRIDX-1
				int afterRepeat = createAndSetRepeatChildren(turtle, parent, userInput, currIdx, addToTrees);
				createCommandTree(turtle, userInput, afterRepeat);
				return;
			}
			if(userInput[currIdx-1].equals(DEFAULT_DOTIMES_IDENTIFIER)) { //CHANGED CURRIDX-1
				int afterDoTimes = createAndSetDoTimesChildren(turtle, parent, userInput, currIdx, addToTrees);
				createCommandTree(turtle, userInput, afterDoTimes);
				return;
			}
			if(userInput[currIdx-1].equals(DEFAULT_FOR_IDENTIFIER)) { //CHANGED CURRIDX-1
				int afterFor = createAndSetForChildren(turtle, parent, userInput, currIdx, addToTrees);
				createCommandTree(turtle, userInput, afterFor);
				return;
			}
		}


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
			//			parseIf(turtle, userInput, commandTypes, allInputTypes, currIdx);
			//			System.out.println("returning");
			//			return; 
			//		}
			for (String substitute : DEFAULT_DOUBLE_SUBSTITUTES) {
				if (userInput[currIdx].equals(substitute)) {
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
			}
			if (String.valueOf(userInput[currIdx].charAt(0)).equals(DEFAULT_VAR_IDENTIFIER)) {
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
			for (int idx = currIdx+1; idx < userInput.length; idx++) { 
				Double currDouble; 
				try {
					currDouble = Double.parseDouble(userInput[idx]);
					CommandNode newChildNode = new CommandNode(userInput[idx], turtle);
					int numArgs = getNumArgs(userInput[idx-1]);
					CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtle);
					for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) { 
						int backTrackNumArgs = getNumArgs(userInput[backtrack]);
						CommandNode backtrackCommandNode = new CommandNode(userInput[backtrack], backTrackNumArgs, newCommandNode, turtle);
						newCommandNode = backtrackCommandNode; 
					}
					parent.addChild(newCommandNode);
					if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
						myCommandTrees.add(parent);
					}
					if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
						createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
					}
					if (parent.getNumChildren() < parent.getNumArgs()) { 
						createAndSetChildren(turtle, parent, userInput, idx+1, addToTrees);
					}
					else {
						createCommandTree(turtle, userInput, idx+1);
					}
					return; 
				} 
				catch (NumberFormatException e1) {
					if (String.valueOf(userInput[idx].charAt(0)).equals(DEFAULT_VAR_IDENTIFIER)) {
						CommandNode newChildNode = new CommandNode(userInput[idx], turtle);
						int numArgs = getNumArgs(userInput[idx-1]);
						//						System.out.println("parent to string"+parent.toString());
						if (numArgs == 0) {
							if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) { // TODO check this logic
								myCommandTrees.add(parent);
							} 
							return;
						}
						CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtle);
						if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
							createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
						}
						parent.addChild(newCommandNode);
						if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
							myCommandTrees.add(parent);
						}
						if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
							createAndSetChildren(turtle, newCommandNode, userInput, idx+1, false);
						}
						if (parent.getNumChildren() < parent.getNumArgs()) { 
							createAndSetChildren(turtle, parent, userInput, idx+1, addToTrees);
						}
						else {
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
		ifCommandNode.addChild(new CommandNode(commandNodeInfo, turtle));

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
		ifElseCommandNode.addChild(new CommandNode(ifCommandNodeInfo, turtle));

		ArrayList<String> elseBodyList = new ArrayList<String>(Arrays.asList(elseBody));
		String elseCommandNodeInfo = String.join(" ", elseBodyList);
		ifElseCommandNode.addChild(new CommandNode(elseCommandNodeInfo, turtle));

		createAndSetChildren(turtle, ifElseCommandNode, ifExpr, 0, true);
		return elseBodyEndSearch+1;
	}

	private int createAndSetDoTimesChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (addToTrees) {
			myCommandTrees.add(parent);
		}
		//System.out.println("command trees size within create and set dotimes children: " + myCommandTrees.size());
		//adding temporary variable name to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			currIdx++;
			parent.addChild(new CommandNode(userInput[currIdx], turtle));
			currIdx++;
		}
		//else {
		//	throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		//}
		int currIdxCopy = currIdx;  
		//		for(int k = 0; k<userInput.length; k+=1) {
		//			System.out.println(userInput[k]);
		//		}
		currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, 1);
		//		while(!(userInput[currIdxCopy].equals(DEFAULT_BRACKET_END_IDENTIFIER))) {
		//			currIdxCopy++;
		//		}
		//adding command info to children
		//System.out.println("currIdx" + currIdx + "currIdxCopy" + currIdxCopy);
		parent.addChild(createCommandTree(turtle, Arrays.copyOfRange(userInput, currIdx, currIdxCopy), 0));
		currIdxCopy++;
		currIdx = currIdxCopy;
		//System.out.println("should be start bracket" + userInput[currIdx]);
		//adding string info to children
		if(userInput[currIdx-1].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			String repeatedCommand = userInput[currIdx];
			int repeatCount = 1;
			repeatCount = repeatCount + getNumBrackets(userInput[currIdx]);
			currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, repeatCount);
			for(int k = currIdx+1; k < currIdxCopy-1; k+=1) {
				repeatedCommand = String.join(" ", repeatedCommand, userInput[k]);
			}
			//			int repeatCount = 1;
			//			int endBracketCount = 0;
			//			while( endBracketCount < repeatCount) {
			//				if(userInput[currIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)){
			//					endBracketCount++;
			//				}
			//				if(userInput[currIdx].equals(DEFAULT_REPEAT_IDENTIFIER)){
			//					repeatCount++;
			//				}
			//				repeatedCommand = String.join(" ", repeatedCommand, userInput[currIdx]);
			//				currIdx++;
			//			}

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
			parent.addChild(new CommandNode(repeatedCommand, turtle));

		}
		else {
			throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		}
		//currIdx++;
		return currIdx;
	}
	private int createAndSetRepeatChildren(Turtle turtle, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//myCommandTrees.add(parent);
		//System.out.println("command tree number: " + myCommandTrees.size());
		if (addToTrees) {
			myCommandTrees.add(parent);
		}
		parent.addChild(new CommandNode(":repcount", turtle));
		//adding temporary variable name to children
		int currIdxCopy = currIdx;
		currIdxCopy++;
		try {
			currIdxCopy = searchForBracket(currIdxCopy, userInput, DEFAULT_BRACKET_START_IDENTIFIER, 1);
		}
		catch(NullPointerException e) {
			throw new UnidentifiedCommandException("Repeat syntax is not correct.");
		}
		//adding command info to children
		//System.out.print("command info" + String.join(" ", Arrays.copyOfRange(userInput, currIdx, currIdxCopy-1)));
		parent.addChild(createCommandTree(turtle, Arrays.copyOfRange(userInput, currIdx, currIdxCopy-1), 0));
		currIdx = currIdxCopy - 1;
		//adding string info to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			int repeatCount = 1;
			int endBracketCount = 0;
			currIdx++;
			String repeatedCommand = userInput[currIdx];
			repeatCount = repeatCount + getNumBrackets(userInput[currIdx]);
			currIdx++;
			currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, repeatCount);
			for(int k = currIdx; k < currIdxCopy-1; k+=1) {
				repeatedCommand = String.join(" ", repeatedCommand, userInput[k]);
			}
			currIdx = currIdxCopy;
			//System.out.println("should be bracket" + userInput[currIdx-1]);
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

		//		while(!userInput[currIdxCopy].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
		//			currIdxCopy++;
		//		}
		currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, 1)-1;
		//		System.out.println("currIdx " + currIdx + "currIdxCopy" + currIdxCopy);
		CommandTreeBuilder tempBuilder = new CommandTreeBuilder(myNumArgsFileName, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		tempBuilder.buildAndExecute(turtle, Arrays.copyOfRange(userInput, currIdx, currIdxCopy), false);
		List<CommandNode> discreteCommands = tempBuilder.getCommandTrees();
		//System.out.println("discreteCommands" + discreteCommands.size());
		for(CommandNode n: discreteCommands) {
			parent.addChild(n);
		}

		// should be first element after end bracket
		currIdx = currIdxCopy + 1;
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			int repeatCount = 1;
			currIdx++;
			//			System.out.println("beginnning of repeated command: " + userInput[currIdx]);
			String repeatedCommand = userInput[currIdx];
			repeatCount = repeatCount + getNumBrackets(userInput[currIdx]);
			currIdx++;
			currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, repeatCount);
			for(int k = currIdx; k < currIdxCopy-1; k+=1) {
				repeatedCommand = String.join(" ", repeatedCommand, userInput[k]);
			}
			currIdx = currIdxCopy;
			if(!(userInput[currIdx-1].equals(DEFAULT_BRACKET_END_IDENTIFIER))) {
				throw new BadFormatException("Brackets are messed up in loop");
			}
			//add repeated command string last
			parent.addChild(new CommandNode(repeatedCommand, turtle));
		}
		else {
			throw new UnidentifiedCommandException("Repeat syntax incorrect");
		}
		//currIdx++;
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
		CommandNode varsNode = new CommandNode(String.join(" ", varsArray), turtle); 

		int finalEndToIdx = endToIdx+1;
		while (! userInput[finalEndToIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			finalEndToIdx++; 
		} // finalEndToIdx is at FINAL ']'

		int endCommandContent = finalEndToIdx;
		if (finalEndToIdx == userInput.length-1 || !userInput[finalEndToIdx+1].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			endCommandContent = finalEndToIdx+1; 
		}
		else {
			endCommandContent = finalEndToIdx+2;
		}

		String[] commandContent = Arrays.copyOfRange(userInput, startIdx+2, endCommandContent);
		String userCommandString = String.join(" ", commandContent);
		CommandNode userCommandContent = new CommandNode(userCommandString, turtle);

		String userCommandName = userInput[startIdx+1];
		CommandNode userCommandNameNode = new CommandNode(userCommandName, turtle);

		CommandNode userCommandNode = new CommandNode(userInput[startIdx], getNumArgs(userInput[startIdx]), userCommandNameNode, turtle);
		userCommandNode.addChild(varsNode);
		userCommandNode.addChild(userCommandContent);
		myCommandTreeReader.readAndExecute(userCommandNode);
		myCommandTrees.add(userCommandNode);

		return endCommandContent; 

	}

	private void parseUserCommand(Turtle turtle, String[] userInput, int startIdx, int numArgs) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String userCommandName = userInput[startIdx];
		CommandNode userCommandNameNode = new CommandNode(userCommandName, numArgs, turtle, true); 
		int treesStartSize = myCommandTrees.size(); 
		createAndSetChildren(turtle, userCommandNameNode, userInput, startIdx+1, false); // verifying correct # children
		int treesEndSize = myCommandTrees.size();
		CommandNode userCommandArgsNode = new CommandNode(("a"+userCommandNameNode.childrenToString()), turtle); 

		CommandNode userCommandNode = new CommandNode(DEFAULT_USERCOMMAND_NAME, getNumArgs(DEFAULT_USERCOMMAND_NAME), userCommandNameNode, turtle);
		userCommandNode.addChild(userCommandArgsNode);
		//		if (startIdx == 0 || startIdx == 1) { // TODO make less ambiguous -- this is because the user command may be first or follow a 0-arg
		//			ArrayList<CommandNode> tempTrees = new ArrayList<CommandNode>();
		//			tempTrees.add(userCommandNode);
		//			tempTrees.addAll(myCommandTrees);
		//			myCommandTrees = tempTrees;
		//		} 
		//		else {
		//			myCommandTrees.add(userCommandNode);
		//
		//		}
		if (treesEndSize > treesStartSize) {
			ArrayList<CommandNode> tempTrees = new ArrayList<CommandNode>();
			for (int i = treesStartSize ; i < treesEndSize ; i ++) {
				tempTrees.add(myCommandTrees.get(i));
			}
			for (int i = treesEndSize-1 ; i >= treesStartSize ; i --) {
				myCommandTrees.remove(i);
			}
			myCommandTrees.add(userCommandNode);
			myCommandTrees.addAll(tempTrees);
		}
		else {
			myCommandTrees.add(userCommandNode);
		}
		return; 
	}

	private int getNumArgs(String commandType) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
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
		//System.out.println("didn't made the regex matcher");
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
	//RETURNS INDEX OF BRACKET!!
	private int searchForBracket(int currIdxCopy, String[] userInput, String bracketIdentifier, int initialNeeded) throws BadFormatException, UnidentifiedCommandException, MissingInformationException{
		int bracketNeededCount = initialNeeded;
		int bracketSeenCount = 0;
		while(bracketSeenCount != bracketNeededCount) {
			if(userInput[currIdxCopy].equals(bracketIdentifier)) {
				bracketSeenCount++;
			}
			bracketNeededCount = bracketNeededCount + getNumBrackets(userInput[currIdxCopy]);
			currIdxCopy++;
		}
		return currIdxCopy;
	}

	protected IntegerProperty getBackColor() {
		return myBackColor;
	}

}
