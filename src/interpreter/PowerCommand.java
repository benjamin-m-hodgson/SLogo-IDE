package interpreter;

import java.util.List;
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

    protected PowerCommand(Command base, Command power, Map<String, Double> variables, List<Turtle> turtles) {
	baseCommand = base;
	powerCommand = power;
	myVariables = variables;
	setActiveTurtles(turtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    	double BASE = -1.0;
    	double POWER = 0;
    	for(Turtle myTurtle : getActiveTurtles()) {
    		BASE = getCommandValue(baseCommand, myVariables, myTurtle);
    		POWER = getCommandValue(powerCommand, myVariables, myTurtle);
    	}
	return Math.pow(BASE, POWER);
    }
}
