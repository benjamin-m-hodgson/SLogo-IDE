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

	protected ArcTangentCommand(Command degrees, Map<String, Double> variables, Turtle turtles) {
		degreesCommand = degrees;
		myVariables = variables; 
		setActiveTurtles(turtles);
	}
	
	@Override
	protected double execute(){
		double degrees = getCommandValue(degreesCommand, myVariables, getActiveTurtles().toSingleTurtle());
		
		getActiveTurtles().executeSequentially(myTurtle -> {
			getCommandValue(degreesCommand, myVariables, myTurtle);
		});
		
	    	return Math.toDegrees(Math.atan(Math.toRadians(degrees)));
	}
}