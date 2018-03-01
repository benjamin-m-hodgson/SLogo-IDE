package interpreter;

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

    protected IfCommand(Command ifExprCommand, Command ifBody, Turtle turtle, Map<String, Double> variables) {
	myTurtle = turtle;
	myIfExprCommand = ifExprCommand;
	myIfBody = ((StringCommand)ifBody).getString(); 
	myVariables = variables; 
    }

    protected double execute() { // TODO discuss throwing of exceptions 
	double ifExprRetVal = 0;
	double ifBodyRetVal = 0; 
	try {
	    ifExprRetVal = myIfExprCommand.execute();
	} catch (UnidentifiedCommandException e1) {
	    return ifBodyRetVal; 
	} 
	if (ifExprRetVal > 0) {
	    System.out.println("if executed");
	    CommandTreeBuilder buildIfBody = new CommandTreeBuilder(myVariables); 
	    String[] userInput = myIfBody.split("\\s+");
	    try {
		ifBodyRetVal = buildIfBody.buildAndExecute(myTurtle, userInput);
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
