package interpreter;


import java.util.Map;

/**
 * returns base raised to the power of the exponent
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class PowerCommand extends Command{

    private Command baseCommand;
    private Command exponentCommand;
    private Map<String, Double> myVariables; 

    protected PowerCommand(Command base, Command power, Map<String, Double> variables, Turtle turtles) {
	baseCommand = base;
	exponentCommand = power;
	myVariables = variables;
	setActiveTurtles(turtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException {
    	double BASE = getCommandValue(baseCommand, myVariables, getActiveTurtles().toSingleTurtle());
    	double POWER = getCommandValue(exponentCommand, myVariables, getActiveTurtles().toSingleTurtle());
    	getActiveTurtles().executeSequentially(myTurtle -> {
    		try {
    		getCommandValue(baseCommand, myVariables, myTurtle);
    		getCommandValue(exponentCommand, myVariables, myTurtle);
    		}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
    	});
	return Math.pow(BASE, POWER);
    }
}
