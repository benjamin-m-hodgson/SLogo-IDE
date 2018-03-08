package interpreter;


import java.util.Map;

/**
 * returns natural log of expr
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
 class NaturalLogCommand extends Command{

    private Command exprCommand;
    private Map<String, Double> myVariables; 

    protected NaturalLogCommand(Command expr,Map<String, Double> variables, Turtle turtles) {
	exprCommand = expr;
	myVariables = variables;
	setActiveTurtles(turtles);
    }

    @Override
    protected double execute() throws UnidentifiedCommandException {
    	Double EXPR = getCommandValue(exprCommand, myVariables, getActiveTurtles().toSingleTurtle());
    	getActiveTurtles().executeSequentially(myTurtle -> {
    		try {
    		getCommandValue(exprCommand, myVariables, myTurtle);
    		}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
    	});
	return Math.log(EXPR);
    }

}