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
	
	private Command degreesCommand;
	private Map<String, Double> myVariables; 

	protected ArcTangentCommand(Command degrees, Map<String, Double> variables, Turtle turtles) {
		degreesCommand = degrees;
		myVariables = variables; 
		setActiveTurtles(turtles);
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException{
		double degrees = getCommandValue(degreesCommand, myVariables, getActiveTurtles().toSingleTurtle());
		
		getActiveTurtles().executeSequentially(myTurtle -> {
			try {
			getCommandValue(degreesCommand, myVariables, myTurtle);
			}
			catch(UnidentifiedCommandException e){
				throw new UnidentifiedCommandError("Improper # arguments");
			}
		});
		
	    	return Math.toDegrees(Math.atan(Math.toRadians(degrees)));
	}
}