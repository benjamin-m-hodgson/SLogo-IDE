package interpreter;


import java.util.Map;

/**
 * returns negative of the values of expr
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */

class MinusCommand extends Command{

    private Command exprCommand;
    private Map<String, Double> myVariables; 

    protected MinusCommand(Command expr, Map<String, Double> variables, Turtle turtles) {
	exprCommand = expr;
	myVariables = variables; 
	setActiveTurtles(turtles);
    }

    @Override
    protected double execute() throws UnidentifiedCommandException {
    	double exprVal = getCommandValue(exprCommand, myVariables, getActiveTurtles());
    getActiveTurtles().executeSequentially(myTurtle -> {
    		try {
    		getCommandValue(exprCommand, myVariables, myTurtle); 
    		}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
    	});
	return -exprVal;
    }

}