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

    protected PowerCommand(Command base, Command power, Map<String, Double> variables) {
	baseCommand = base;
	powerCommand = power;
	myVariables = variables;
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
	double BASE = getCommandValue(baseCommand, myVariables);
	double POWER = getCommandValue(powerCommand, myVariables);
	return Math.pow(BASE, POWER);
    }
}
