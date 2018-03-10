package interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	public static final String DEFAULT_TELL_IDENTIFIER = "Tell";
	public static final String DEFAULT_FOR_IDENTIFIER = "For";
	public static final String DEFAULT_ASK_WITH_IDENTIFIER = "AskWith";
	public static final String DEFAULT_ASK_IDENTIFIER = "Ask";
	public static final String DEFAULT_BRACKET_START_IDENTIFIER = "[";
	public static final String DEFAULT_BRACKET_END_IDENTIFIER = "]";
	public static final String DEFAULT_VAR_IDENTIFIER = ":";
	public static final String DEFAULT_USERCOMMAND_IDENTIFIER = "MakeUserInstruction";
	public static final String DEFAULT_USERCOMMAND_NAME = "UserInstruction";
	protected static final String[] DEFAULT_DOUBLE_SUBSTITUTES = {"PenDown","PenUp","ShowTurtle","HideTurtle","Home","ClearScreen",
			"XCoordinate","YCoordinate","Heading","IsPenDown","IsShowing","Pi", "ID", "Turtles"};
	//	private CommandTreeReader myCommandTreeReader; 
	private String myNumArgsFileName; 
	private String myNumBracketsFileName;
	private ArrayList<CommandNode> myCommandTrees; 
	private CommandTreeReader myCommandTreeReader;
	private HashMap<String, String> myUserDefCommands; 
	private HashMap<String, Integer> myUserDefCommandsNumArgs;
	private Map<String, Double> myVariables;
	private IntegerProperty myBackColor; 
	private BooleanProperty myBackColorChangeHeard; 

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
		myBackColorChangeHeard = new SimpleBooleanProperty(false);
		setUpBackColorChangeListener();
	}
	
	protected void setUpBackColorChangeListener() {
		myCommandTreeReader.getBackColor().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number t1, Number t2) {
				myBackColor = myCommandTreeReader.getBackColor();
				myBackColorChangeHeard.set(!myBackColorChangeHeard.getValue());
			}
		});
	}

	protected double buildAndExecute(Turtle turtles, Turtle activeTurtles, String[] userInput, boolean shouldExecute) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
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
				Double.parseDouble(userInput[currIdx]);
				myCommandTrees.add(new CommandNode(userInput[currIdx], 0, turtles, activeTurtles));
				currIdx++;
			}
			catch(NumberFormatException e) {
				break;
			}
		}
		if(!(currIdx>=userInput.length)) {
			createCommandTree(turtles, activeTurtles, userInput, currIdx);
			//System.out.println("command tree number: " + myCommandTrees.size());
		}


		//System.out.println("command tree number bigger: " + myCommandTrees.size());
		System.out.println("PRINTING OUT COMM TREES ------------------------------------");
		for (CommandNode n : myCommandTrees) {
			System.out.println(n.toString());
		}
		System.out.println("DONE PRINTING OUT COMM TREES --------------------------------");

		//	System.out.println("number of command trees" + myCommandTrees.size());
		if(shouldExecute) {
			for (CommandNode commandTree : myCommandTrees) {
				finalReturnVal = myCommandTreeReader.readAndExecute(commandTree);
			}
		}
		return finalReturnVal; 
	}

	private CommandNode createCommandTree(Turtle turtles, Turtle activeTurtles, String[] userInput, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		System.out.println("trying to make a command tree");
	
		if (startIdx >= userInput.length||(userInput[startIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER))) { // || commandTypes[startIdx] == null //TODO fix this so not so obvious
			System.out.println("WARNING: CREATE COMMAND TREE RETURNING NULL");
			return null; // TODO make this more detailed
		}
		System.out.println("currCommand: " + userInput[startIdx]);
		if(myVariables.containsKey(userInput[startIdx])) {
			userInput[startIdx] = myVariables.get(userInput[startIdx]).toString();
		}
 
		String currCommand = userInput[startIdx]; 
		try {
			Double.parseDouble(currCommand);
			return new CommandNode(currCommand, 0, turtles, activeTurtles); 
		}
		catch (NumberFormatException e) {
			if (currCommand.equals(DEFAULT_IF_IDENTIFIER)) { // TODO deal with if "if" is not first 
				int startAfterIf = parseIf(turtles, activeTurtles, userInput, startIdx); 
				return createCommandTree(turtles, activeTurtles, userInput, startAfterIf);
			}
			if (currCommand.equals(DEFAULT_IFELSE_IDENTIFIER)) {
				int startAfterIfElse = parseIfElse(turtles, activeTurtles, userInput, startIdx); 
				return createCommandTree(turtles, activeTurtles, userInput, startAfterIfElse);
			}
			if (currCommand.equals(DEFAULT_USERCOMMAND_IDENTIFIER)) {
				int startAfterTo = parseMakeUserCommand(turtles, activeTurtles, userInput, startIdx);
				return createCommandTree(turtles, activeTurtles, userInput, startAfterTo);
			}
			if (myUserDefCommands.containsKey(currCommand)) {
				parseUserCommand(turtles, activeTurtles, userInput, startIdx, myUserDefCommandsNumArgs.get(currCommand));
				return null; // TODO FIX THIS 
				//				return createCommandTree(turtle, userInput, startAfterUserCommand);
			}
			//System.out.println("currCommand: " + currCommand);
			int numArgs = getNumArgs(currCommand);
			CommandNode newParentNode = new CommandNode(currCommand, numArgs, turtles, activeTurtles);
			while (newParentNode.getNumArgs() == 0 ) { // accounts for multiple 1-arg arguments before args that need child nodes 
				if (startIdx >= userInput.length) {
					return newParentNode; 
				}
				myCommandTrees.add(newParentNode);
				startIdx++; 
				if (startIdx < userInput.length) {
					currCommand = userInput[startIdx]; 
					numArgs = getNumArgs(currCommand);
					newParentNode = new CommandNode(currCommand, numArgs, turtles, activeTurtles);
				}
				else {
					return newParentNode;
				}
			}
			if (myUserDefCommands.containsKey(currCommand)) {
				parseUserCommand(turtles, activeTurtles, userInput, startIdx, myUserDefCommandsNumArgs.get(currCommand));
				return null; // TODO FIX THIS 
				//				return createCommandTree(turtle, userInput, startAfterUserCommand);
			}
			createAndSetChildren(turtles, activeTurtles, newParentNode, userInput, startIdx+1, true);
			return newParentNode;
		}
	}

	// THIS IS THE RIGHT ONE
	private void createAndSetChildren(Turtle turtles, Turtle activeTurtles, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//int currIdxNonRepeat = currIdx + 1; CHANGED THIS
		if ((currIdx) >= userInput.length) { //base case if out of bounds
			if (addToTrees) {
				myCommandTrees.add(parent);
			}
			return;
		}
		if(userInput[currIdx-1].equals(DEFAULT_REPEAT_IDENTIFIER)) { //CHANGED CURRIDX-1
			int afterRepeat = createAndSetRepeatChildren(turtles, activeTurtles, parent, userInput, currIdx, addToTrees);
			createCommandTree(turtles, activeTurtles, userInput, afterRepeat);
			return;
		}
		if(userInput[currIdx-1].equals(DEFAULT_DOTIMES_IDENTIFIER)) { //CHANGED CURRIDX-1
			int afterDoTimes = createAndSetDoTimesChildren(turtles, activeTurtles, parent, userInput, currIdx, addToTrees);
			createCommandTree(turtles, activeTurtles, userInput, afterDoTimes);
			return;
		}
		if(userInput[currIdx-1].equals(DEFAULT_FOR_IDENTIFIER)) { //CHANGED CURRIDX-1
			int afterFor = createAndSetForChildren(turtles, activeTurtles, parent, userInput, currIdx, addToTrees);
			createCommandTree(turtles, activeTurtles, userInput, afterFor);
			return;
		}
		if (userInput[currIdx-1].equals(DEFAULT_IF_IDENTIFIER)) { // TODO deal with if "if" is not first 
			int startAfterIf = parseIf(turtles, activeTurtles, userInput, currIdx); 
			createCommandTree(turtles, activeTurtles,  userInput, startAfterIf);
			return;
		}
		if (userInput[currIdx-1].equals(DEFAULT_IFELSE_IDENTIFIER)) {
			int startAfterIfElse = parseIfElse(turtles, activeTurtles, userInput, currIdx); 
			createCommandTree(turtles, activeTurtles, userInput, startAfterIfElse);
			return;
		}
		if(userInput[currIdx-1].equals(DEFAULT_TELL_IDENTIFIER)) {
			int startAfterTell = parseTell(turtles, activeTurtles, userInput, currIdx, addToTrees, parent); 
			createCommandTree(turtles, activeTurtles, userInput, startAfterTell);
			return;
		}
		if(userInput[currIdx-1].equals(DEFAULT_ASK_IDENTIFIER)||userInput[currIdx-1].equals(DEFAULT_ASK_WITH_IDENTIFIER)) {
			int startAfterAsk = parseAsks(turtles, activeTurtles, userInput, currIdx, addToTrees, parent); 
			System.out.println("SHOULD BE TRYING TO MAKE COMMAND TREE");
			createCommandTree(turtles, activeTurtles, userInput, startAfterAsk);
			return;
		}


		 
		try {
			Double.parseDouble(userInput[currIdx]);
			CommandNode newChildNode = new CommandNode(userInput[currIdx], 0, turtles, activeTurtles);
			parent.addChild(newChildNode);
			if (parent.getNumChildren() < parent.getNumArgs()) { 
				createAndSetChildren(turtles, activeTurtles, parent, userInput, currIdx+1, addToTrees);
			} 
			else {
				if (addToTrees) {
					myCommandTrees.add(parent);
				}
				createCommandTree(turtles, activeTurtles, userInput, currIdx+1);
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
					CommandNode newChildNode = new CommandNode(userInput[currIdx], 0, turtles, activeTurtles);
					parent.addChild(newChildNode);
					if (parent.getNumChildren() < parent.getNumArgs()) { 
						createAndSetChildren(turtles, activeTurtles, parent, userInput, currIdx+1, addToTrees);
					} 
					else {
						if (addToTrees) {
							myCommandTrees.add(parent);
						}
						createCommandTree(turtles, activeTurtles, userInput, currIdx+1);
					}
					return; 
				}
			}
			if (String.valueOf(userInput[currIdx].charAt(0)).equals(DEFAULT_VAR_IDENTIFIER)) {
				CommandNode newChildNode = new CommandNode(userInput[currIdx], 0, turtles, activeTurtles);
				parent.addChild(newChildNode);
				if (parent.getNumChildren() < parent.getNumArgs()) { 
					createAndSetChildren(turtles, activeTurtles, parent, userInput, currIdx+1, addToTrees);
				} 
				else {
					if (addToTrees) {
						myCommandTrees.add(parent);
					}
					createCommandTree(turtles, activeTurtles, userInput, currIdx+1);
				}
				return; 
			}
			for (int idx = currIdx+1; idx < userInput.length; idx++) { 
				try {
					Double.parseDouble(userInput[idx]);
					CommandNode newChildNode = new CommandNode(userInput[idx], 0, turtles, activeTurtles);
					int numArgs = getNumArgs(userInput[idx-1]);
					CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtles, activeTurtles);
					for (int backtrack = idx-2; backtrack >= currIdx; backtrack--) { 
						int backTrackNumArgs = getNumArgs(userInput[backtrack]);
						CommandNode backtrackCommandNode = new CommandNode(userInput[backtrack], backTrackNumArgs, newCommandNode, turtles, activeTurtles);
						newCommandNode = backtrackCommandNode; 
					}

					parent.addChild(newCommandNode);
					if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
						myCommandTrees.add(parent);
					}
					if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
						createAndSetChildren(turtles, activeTurtles, newCommandNode, userInput, idx+1, false);
					}
					if (parent.getNumChildren() < parent.getNumArgs()) { 
						createAndSetChildren(turtles, activeTurtles, parent, userInput, idx+1, addToTrees);
					}
					else {
						createCommandTree(turtles, activeTurtles, userInput, idx+1);
					}
					return; 
				} 
				catch (NumberFormatException e1) {
					if (String.valueOf(userInput[idx].charAt(0)).equals(DEFAULT_VAR_IDENTIFIER)) {
						CommandNode newChildNode = new CommandNode(userInput[idx], turtles, activeTurtles);
						int numArgs = getNumArgs(userInput[idx-1]);
						//						System.out.println("parent to string"+parent.toString());
						if (numArgs == 0) {
							if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) { // TODO check this logic
								myCommandTrees.add(parent);
							} 
							return;
						}
						CommandNode newCommandNode = new CommandNode(userInput[idx-1], numArgs, newChildNode, turtles, activeTurtles);
						if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
							createAndSetChildren(turtles, activeTurtles, newCommandNode, userInput, idx+1, false);
						}
						parent.addChild(newCommandNode);
						if (parent.getNumChildren() == parent.getNumArgs() && addToTrees && !myCommandTrees.contains(parent)) {
							myCommandTrees.add(parent);
						}
						if (newCommandNode.getNumChildren() < newCommandNode.getNumArgs()) { 
							createAndSetChildren(turtles, activeTurtles, newCommandNode, userInput, idx+1, false);
						}
						if (parent.getNumChildren() < parent.getNumArgs()) { 
							createAndSetChildren(turtles, activeTurtles, parent, userInput, idx+1, addToTrees);
						}
						else {
							createCommandTree(turtles, activeTurtles, userInput, idx+1);
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
	private int parseAsks(Turtle turtles, Turtle activeTurtles, String[] userInput, int currIdx, boolean addToTrees, CommandNode parent) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (addToTrees) {
			myCommandTrees.add(parent);
		}
		currIdx++;
		for(int k = 0; k<2; k+=1) {
			int currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, 1);
			String[] ids = Arrays.copyOfRange(userInput, currIdx, currIdxCopy-1);
			String idString = String.join(" ", ids);
			CommandNode idNode = new CommandNode(idString, turtles, activeTurtles);
			parent.addChild(idNode);
			currIdx = currIdxCopy+1;
		}
		System.out.println("currIdx being used: " + currIdx);
		return currIdx-1;
	}
	private int parseIf(Turtle turtles, Turtle activeTurtles, String[] userInput, int ifIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
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
		CommandNode ifCommandNode = new CommandNode(userInput[ifIdx], numArgs, turtles, activeTurtles);

		ArrayList<String> ifBodyString = new ArrayList<String>(Arrays.asList(ifBody)); 
		String commandNodeInfo = String.join(" ", ifBodyString);
		ifCommandNode.addChild(new CommandNode(commandNodeInfo, turtles, activeTurtles));

		createAndSetChildren(turtles, activeTurtles, ifCommandNode, ifExpr, 0, true);
		return ifBodyEndSearch+1;
	}

	private int parseIfElse(Turtle turtles, Turtle activeTurtles, String[] userInput, int ifIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
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
		while (! userInput[elseBodyEndSearch].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			elseBodyEndSearch++; 
		}
		String[] ifExpr = Arrays.copyOfRange(userInput, ifIdx+1, ifExprEndSearch);
		String[] ifBody = Arrays.copyOfRange(userInput, ifExprEndSearch+1, ifBodyEndSearch);
		String[] elseBody = Arrays.copyOfRange(userInput, ifBodyEndSearch+2, elseBodyEndSearch);

		String ifCommand = userInput[ifIdx]; 
		int numArgs = getNumArgs(ifCommand);
		CommandNode ifElseCommandNode = new CommandNode(userInput[ifIdx], numArgs, turtles, activeTurtles);

		ArrayList<String> ifBodyList = new ArrayList<String>(Arrays.asList(ifBody)); 
		String ifCommandNodeInfo = String.join(" ", ifBodyList);
		ifElseCommandNode.addChild(new CommandNode(ifCommandNodeInfo, turtles, activeTurtles));

		ArrayList<String> elseBodyList = new ArrayList<String>(Arrays.asList(elseBody));
		String elseCommandNodeInfo = String.join(" ", elseBodyList);
		ifElseCommandNode.addChild(new CommandNode(elseCommandNodeInfo, turtles, activeTurtles));

		createAndSetChildren(turtles, activeTurtles, ifElseCommandNode, ifExpr, 0, true);
		return elseBodyEndSearch+1;
	}

	private int createAndSetDoTimesChildren(Turtle turtles,  Turtle activeTurtles, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		if (addToTrees) {
			myCommandTrees.add(parent);
		}
		//adding temporary variable name to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			currIdx++;
			parent.addChild(new CommandNode(userInput[currIdx], turtles, activeTurtles));
			currIdx++;
		}
		int currIdxCopy = currIdx;  
		currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, 1);
		//adding command info to children
		parent.addChild(createCommandTree(turtles, activeTurtles, Arrays.copyOfRange(userInput, currIdx, currIdxCopy), 0));
		currIdxCopy++;
		currIdx = currIdxCopy;
		//adding string info to children
		if(userInput[currIdx-1].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			String repeatedCommand = userInput[currIdx];
			int repeatCount = 1;
			repeatCount = repeatCount + getNumBrackets(userInput[currIdx]);
			currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, repeatCount);
			for(int k = currIdx+1; k < currIdxCopy-1; k+=1) {
				repeatedCommand = String.join(" ", repeatedCommand, userInput[k]);
			}
			parent.addChild(new CommandNode(repeatedCommand, turtles, activeTurtles));

		}
		else {
			throw new UnidentifiedCommandException("Dotimes syntax incorrect");
		}
		//currIdx++;
		return currIdx;
	}
	private int createAndSetRepeatChildren(Turtle turtles, Turtle activeTurtles, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		//myCommandTrees.add(parent);
		//System.out.println("command tree number: " + myCommandTrees.size());
		if (addToTrees) {
			myCommandTrees.add(parent);
		}
		parent.addChild(new CommandNode(":repcount", turtles, activeTurtles));
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
			parent.addChild(createCommandTree(turtles, activeTurtles, Arrays.copyOfRange(userInput, currIdx, currIdxCopy-1), 0));
				currIdx = currIdxCopy - 1;
		//adding string info to children
		if(userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			int repeatCount = 1;
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
			parent.addChild(new CommandNode(repeatedCommand, turtles, activeTurtles));
		}
		else {
			throw new UnidentifiedCommandException("Repeat syntax incorrect");
		}
		//currIdx++;
		return currIdx;
	}

	private int createAndSetForChildren(Turtle turtles, Turtle activeTurtles, CommandNode parent, String[] userInput, int currIdx, boolean addToTrees) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		myCommandTrees.add(parent);
		if(!userInput[currIdx].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			throw new UnidentifiedCommandException("No starting bracket for For.");
		}
		//adding variable child
		currIdx++;
		parent.addChild(new CommandNode(userInput[currIdx], turtles, activeTurtles));
		currIdx++;
		int currIdxCopy = currIdx;
		//adding command children for start/end/increment
		currIdxCopy = searchForBracket(currIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, 1)-1;
		CommandTreeBuilder tempBuilder = new CommandTreeBuilder(myNumArgsFileName, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		tempBuilder.buildAndExecute(turtles, activeTurtles, Arrays.copyOfRange(userInput, currIdx, currIdxCopy), false);
		List<CommandNode> discreteCommands = tempBuilder.getCommandTrees();
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
			parent.addChild(new CommandNode(repeatedCommand, turtles, activeTurtles));
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

	private int parseMakeUserCommand(Turtle turtles, Turtle activeTurtles, String[] userInput, int startIdx) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		int endToIdx = startIdx; 
		while (! userInput[endToIdx].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			endToIdx++; 
		} // endToIdx is at FIRST ']' 
		String[] varsArray = Arrays.copyOfRange(userInput, startIdx+3, endToIdx);
		CommandNode varsNode = new CommandNode(String.join(" ", varsArray), turtles, activeTurtles); 

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
		CommandNode userCommandContent = new CommandNode(userCommandString, turtles, activeTurtles);

		String userCommandName = userInput[startIdx+1];
		CommandNode userCommandNameNode = new CommandNode(userCommandName, turtles, activeTurtles);

		CommandNode userCommandNode = new CommandNode(userInput[startIdx], getNumArgs(userInput[startIdx]), userCommandNameNode, turtles, activeTurtles);
		userCommandNode.addChild(varsNode);
		userCommandNode.addChild(userCommandContent);
		myCommandTreeReader.readAndExecute(userCommandNode);
		myCommandTrees.add(userCommandNode);

		return endCommandContent; 

	}
	private int parseTell(Turtle turtles, Turtle activeTurtles, String[] userInput, int startIdx, boolean addToTrees, CommandNode parent) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		startIdx++;
		int endIdx = searchForBracket(startIdx, userInput, DEFAULT_BRACKET_END_IDENTIFIER, 1);
		String[] idStringArray = Arrays.copyOfRange(userInput, startIdx, endIdx-1);
		String idString = String.join(" ", idStringArray);
		CommandNode idNode = new CommandNode(idString, turtles, activeTurtles);
		CommandNode tellNode = new CommandNode(DEFAULT_TELL_IDENTIFIER, 1, turtles, activeTurtles);
		tellNode.addChild(idNode);
		myCommandTrees.add(tellNode);
		return endIdx;
	}

	private void parseUserCommand(Turtle turtles, Turtle activeTurtles, String[] userInput, int startIdx, int numArgs) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		String userCommandName = userInput[startIdx];
		CommandNode userCommandNameNode = new CommandNode(userCommandName, numArgs, turtles, activeTurtles, true); 
		int treesStartSize = myCommandTrees.size(); 
		createAndSetChildren(turtles, activeTurtles, userCommandNameNode, userInput, startIdx+1, false); // verifying correct # children
		int treesEndSize = myCommandTrees.size();
		CommandNode userCommandArgsNode = new CommandNode(("a"+userCommandNameNode.childrenToString()), turtles, activeTurtles); 

		CommandNode userCommandNode = new CommandNode(DEFAULT_USERCOMMAND_NAME, getNumArgs(DEFAULT_USERCOMMAND_NAME), userCommandNameNode, turtles, activeTurtles);
		userCommandNode.addChild(userCommandArgsNode);
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

	public ObservableValue<Boolean> getBackColorChangeHeard() {
		return myBackColorChangeHeard;
	}

	public IntegerProperty getBackColor() {
		return myBackColor;
	}

}


