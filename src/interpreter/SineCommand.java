package interpreter;

import java.util.Map;

/**
 * returns sine of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class SineCommand extends Command{

    private Command degreesCommand;
    private Map<String, Double> myVariables; 

    protected SineCommand(Command degrees ,Map<String, Double> variables) {
	degreesCommand = degrees;
	myVariables = variables;
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
	double DEGREES = getCommandValue(degreesCommand, myVariables);
	return Math.sin(Math.toRadians(DEGREES));
    }
    protected int getNumArgs() {
	return 1;
    }
}