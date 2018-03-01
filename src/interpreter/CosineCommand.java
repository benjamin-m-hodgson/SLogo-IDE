package interpreter;

import java.util.Map;

/**
 * returns cosine of degrees
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
public class CosineCommand extends Command{
	
	Command degreesCommand;
	Map<String, Double> myVariables; 

	protected CosineCommand(Command degrees, Map<String, Double> variables) {
		degreesCommand = degrees;
		myVariables = variables; 
	}
	
	@Override
	public double execute() throws UnidentifiedCommandException {
		double degrees;
		if (degreesCommand instanceof StringCommand) {
			degrees = getValueOfVar(((StringCommand)degreesCommand).getString(), myVariables); 
		} 
		else {
			degrees = degreesCommand.execute();
		}
	    	return Math.cos(Math.toRadians(degrees));
	}
}