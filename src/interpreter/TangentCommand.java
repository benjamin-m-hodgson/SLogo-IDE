package interpreter;

import java.util.Map;

/**
 * returns tangent of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class TangentCommand extends Command{

    private Command degreesCommand;
    private Map<String, Double> myVariables; 

    protected TangentCommand(Command degrees ,Map<String, Double> variables) {
	degreesCommand = degrees;
	myVariables = variables;

    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
	double DEGREES = getCommandValue(degreesCommand, myVariables);
	return Math.tan(Math.toRadians(DEGREES));
    }

}