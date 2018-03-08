package interpreter;

import java.util.Map;

/**
 * Command class that moves a Turtle back a specified number of pixels. Dependent on the CommandFactory class to create
 * it correctly. Also dependent on the Turtle class to relay/set distances correctly.
 * @author Sarahbland
 *
 */

class MoveTurtleBackwardCommand extends Command{

    private Command myBackwardDistCommand;
    private Map<String, Double> myVariables; 

    /**
     * Creates a new instance of this Command that can be executed at the proper time
     * @param backwarddist is the distance the Turtle will move backward
     * @param turtle is the Turtle that should move
     */
    protected MoveTurtleBackwardCommand(Command backwarddist, Turtle turtles, Map<String, Double> variables){
	setActiveTurtles(turtles);
	myBackwardDistCommand = backwarddist;
	myVariables = variables; 
    }

    @Override

    /** 
     * Moves the turtle's image a certain distance backward and draws a line (if the pen is down)
     * @return distance the turtle moved backward
     * @see interpreter.Command#execute()
     */
    protected double execute() throws UnidentifiedCommandException{
    	double distance = getCommandValue(myBackwardDistCommand, myVariables, getActiveTurtles().toSingleTurtle());
    getActiveTurtles().executeSequentially(myTurtle ->{
    		try {
		double dist = getCommandValue(myBackwardDistCommand, myVariables, myTurtle);
		double angle = Math.toRadians(myTurtle.getAngle());
		myTurtle.setXY(myTurtle.getX()+dist*Math.sin(-angle), myTurtle.getY()+dist*Math.cos(-angle));
    		}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
		
    		});
	return distance;
    }
}
