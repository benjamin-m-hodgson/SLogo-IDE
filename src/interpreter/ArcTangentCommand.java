package interpreter;

import java.util.List;
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
	
	private Command degreesCommand;
	private Map<String, Double> myVariables; 

	protected ArcTangentCommand(Command degrees, Map<String, Double> variables, List<Turtle> turtles) {
		degreesCommand = degrees;
		myVariables = variables; 
		setActiveTurtles(turtles);
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException {
		double degrees = -1.0;
		
		for(Turtle myTurtle : getActiveTurtles()) {
			degrees = getCommandValue(degreesCommand, myVariables, myTurtle);
		}
		
	    	return Math.toDegrees(Math.atan(Math.toRadians(degrees)));
	}
}