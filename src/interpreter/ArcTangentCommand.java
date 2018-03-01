package interpreter;

import java.util.Map;

/**
 * returns arctangent of degrees
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
class ArcTangentCommand extends Command{
	
	Command degreesCommand;
	Map<String, Double> myVariables; 

	protected ArcTangentCommand(Command degrees, Map<String, Double> variables) {
		degreesCommand = degrees;
		myVariables = variables; 
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException {
		double degrees;
		if (degreesCommand instanceof StringCommand) {
			degrees = getValueOfVar(((StringCommand)degreesCommand).getString(), myVariables); 
		} 
		else {
			degrees = degreesCommand.execute();
		}
	    	return Math.toDegrees(Math.atan(Math.toRadians(degrees)));
	}
}