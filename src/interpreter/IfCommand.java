package interpreter;

import java.util.Map;

///**
// * @author susiechoi
// *
// */
//class IfCommand extends ControlFlowCommand {
//
//	protected IfCommand(Command ifCommand, Turtle turtle, Turtle activeTurtles, 
//			Map<String, Double> variables, Map<String, String> userDefinedComms, Map<String, Integer> numArgsOfUserDefinedComms) 
//			throws BadFormatException {
//		super(ifCommand, turtle, activeTurtles, variables, userDefinedComms, numArgsOfUserDefinedComms);
//	}
//
//	@Override
//	protected double execute() throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
//		int endOfIfExpr = myCommandStr.indexOf(DEFAULT_BRACKET_START);
//		String ifExpr = myCommandStr.substring(0, endOfIfExpr);
//		String[] ifExprTokenized = ifExpr.split("\\s+");
//		double ifBodyResult = 0; 
//		if (sendToBuilder(ifExprTokenized) > 0) {
//			String ifBody = myCommandStr.substring(endOfIfExpr, myCommandStr.length()-1); 
//			String[] ifBodyTokenized = ifBody.split("\\s+");
//			ifBodyResult = sendToBuilder(ifBodyTokenized);
//		}		
//		return ifBodyResult;
//	}
//}


//
/**
 * @author Susie Choi
 *
 */
class IfCommand extends Command{
    private Turtle myTurtle;
    private Command myIfExprCommand; 
    private String myIfBody; 
    private Map<String, Double> myVariables; 
    private Map<String, String> myUserDefCommands;
    private Map<String, Integer> myUserDefComNumArgs;
    
    protected IfCommand(Command ifExprCommand, Command ifBody, Turtle turtle, Turtle activeTurtles,
    		Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommNumArgs) {
	myTurtle = turtle;
	myIfExprCommand = ifExprCommand;
	myIfBody = ((StringCommand)ifBody).getString(); 
	myVariables = variables; 
	myUserDefCommands = userDefCommands; 
	myUserDefComNumArgs = userDefCommNumArgs; 
	setActiveTurtles(activeTurtles);
    }

    @Override
    protected double execute() throws UnidentifiedCommandException{ 
	double ifExprRetVal = 0;
	double ifBodyRetVal = 0; 
	ifExprRetVal = myIfExprCommand.execute();
	if (ifExprRetVal > 0) {
	    System.out.println("if executed");
	    CommandTreeBuilder buildIfBody = new CommandTreeBuilder(myVariables, myUserDefCommands, myUserDefComNumArgs); 
	    String[] userInput = myIfBody.split("\\s+");
	    try {
			ifBodyRetVal = buildIfBody.buildAndExecute(myTurtle, getActiveTurtles(), userInput, true);
	    } catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
		return ifBodyRetVal; 
	    }
	}
	else {
	    System.out.println("if DID NOT execute");
	}
	return ifBodyRetVal; 
    }
}
