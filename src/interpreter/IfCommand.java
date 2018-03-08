package interpreter;

import java.util.ArrayList;
import java.util.Map;

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
    protected double execute() { // TODO discuss throwing of exceptions 
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
