package interpreter;

import java.util.List;
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

	protected CosineCommand(Command degrees, Map<String, Double> variables, Turtle turtles) {
		degreesCommand = degrees;
		myVariables = variables; 
		setActiveTurtles(turtles);
	}
	
	@Override
	protected double execute() {
		double degrees = getCommandValue(degreesCommand, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle ->{
			getCommandValue(degreesCommand, myVariables, myTurtle);
		});
	    	return Math.cos(Math.toRadians(degrees));
	}
}