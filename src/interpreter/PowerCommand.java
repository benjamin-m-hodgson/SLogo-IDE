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
    private Command powerCommand;
    private Map<String, Double> myVariables; 

    protected PowerCommand(Command base, Command power, Map<String, Double> variables, Turtle turtles) {
	baseCommand = base;
	powerCommand = power;
	myVariables = variables;
	setActiveTurtles(turtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException {
    	double BASE = getCommandValue(baseCommand, myVariables, getActiveTurtles().toSingleTurtle());
    	double POWER = getCommandValue(powerCommand, myVariables, getActiveTurtles().toSingleTurtle());
    	getActiveTurtles().executeSequentially(myTurtle -> {
    		try {
    		getCommandValue(baseCommand, myVariables, myTurtle);
    		getCommandValue(powerCommand, myVariables, myTurtle);
    		}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
    	});
	return Math.pow(BASE, POWER);
    }
}
