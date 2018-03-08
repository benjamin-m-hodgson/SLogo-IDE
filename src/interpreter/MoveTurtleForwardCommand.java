package interpreter;

import java.util.Map;
import java.util.List;

/**
 * Command class used to move Turtles an absolute distance forward. Must be created correctly by the CommandFactory.
 * Also dependent on the Turtle class to relay/set distances correctly.
 * @author Sarahbland
 *
 */
class MoveTurtleForwardCommand extends Command {
	private Command myForwardDistCommand;
	private Map<String, Double> myVariables;
	/**
	 * Creates a new command that can be executed at the proper time
	 * @param forwarddist is Command that returns the absolute distance that the turtle must move forward
	 * @param turtle is Turtle that needs to move
	 */
	protected MoveTurtleForwardCommand(Command forwarddist, Turtle activeTurtle, Map<String, Double> variables){
		setActiveTurtles(activeTurtle);
		myForwardDistCommand = forwarddist;
		myVariables = variables;
	}



	@Override
	/** 
	 * Moves Turtle forward the specified number of pixels and draws a line if the pen is down
	 * @return distance the turtle moved forward
	 * @see interpreter.Command#execute()
	 */
	protected double execute(){
			double returnVal = getCommandValue(myForwardDistCommand, myVariables, getActiveTurtles().toSingleTurtle());
			getActiveTurtles().executeSequentially( turtle -> {
				double forwardDist = -1.0;
				forwardDist = getCommandValue(myForwardDistCommand, myVariables, turtle);
				double angle = Math.toRadians(turtle.getAngle());
				turtle.setXY(turtle.getX()-forwardDist*Math.sin(-angle), turtle.getY()-forwardDist*Math.cos(-angle));
			});
			
			return returnVal;
	}
}
