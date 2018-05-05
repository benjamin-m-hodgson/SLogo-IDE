///**
// * 
// * The ControlFlowCommand abstract class encapsulates the data and methods that are necessary in control flow execution, 
// * 		but unnecessary elsewhere (i.e. encapsulating the concept that varies): the need to invoke CommandTreeBuilder. 
// * 		Thus, this abstract class has data that is necessary to instantiate a new CommandTreeBuilder (myCommandStr, myUserDefinedComms, myNumArgsOfUserDefinedComms, myBuilder), 
// * 		as well as a method to formalize the process of initiating the CommandTreeBuilder parsing process. 
// * 
// * Assumptions: the "command" parameter to the constructor is a StringCommand type 
// * 
// * alternatively could have "if" node iwth 2 children: the if expr, and a bracket node with an unlimited number of children
// * but it would require bracket-parsing in command tree builder... maybe better to encapsulate that
// * 
// * note: reliance on stringcommand is bad -- assumes input is in string form with single space separation
// * need to match brackets like stack 
// */
//
//package interpreter;
//
//import java.util.Map;
//
//abstract class ControlFlowCommand extends Command {
//
//	public static final String DEFAULT_BRACKET_START = "[";
//	public static final String DEFAULT_BRACKET_END = "]";
//	public static final String DEFAULT_EXCEPTION_MESSAGE = "Control flow command parsing error";
//	
//	protected String myCommandStr; 
//	protected Map<String, String> myUserDefinedComms;
//	protected Map<String, Integer> myNumArgsOfUserDefinedComms;
//	protected CommandTreeBuilder myBuilder; 
//	
//	protected ControlFlowCommand(Command command, Turtle turtle, Turtle activeTurtles, 
//			Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommNumArgs) throws BadFormatException {
//		if (!(command instanceof StringCommand)) {
//			throw new BadFormatException(DEFAULT_EXCEPTION_MESSAGE);
//		}
//		myCommandStr = ((StringCommand)command).getString(); 
//		myTurtle = turtle;
//		myVariables = variables; 
//		myUserDefinedComms = userDefCommands; 
//		myNumArgsOfUserDefinedComms = userDefCommNumArgs; 
//		myBuilder = new CommandTreeBuilder(myVariables, myUserDefinedComms, myNumArgsOfUserDefinedComms); 
//		setMyTurtles(activeTurtles);
//	}
//	
//	protected double sendToBuilder(String[] input) 
//			throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
//		return myBuilder.buildAndExecute(myTurtle, getActiveTurtles(), input, true);
//	} 
//		
//}
