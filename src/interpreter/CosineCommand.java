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
 class CosineCommand extends Command{
	
	private Command degreesCommand;
	private Map<String, Double> myVariables; 

	protected CosineCommand(Command degrees, Map<String, Double> variables) {
		degreesCommand = degrees;
		myVariables = variables; 
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException {
		double degrees = getCommandValue(degreesCommand, myVariables);
	    	return Math.cos(Math.toRadians(degrees));
	}
}