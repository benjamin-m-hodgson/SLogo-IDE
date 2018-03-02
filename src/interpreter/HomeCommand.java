package interpreter; 

/**
 * Command class that moves the turtle back to its home position (center of the screen). Dependent on the CommandFactory
 * to make it correctly, and the Turtle class to move itself and calculate distance correctly.
 * @author Sarahbland
 *
 */

class HomeCommand extends Command{
    Turtle myTurtle;
    /**
     * Creates a new instance of the command to be executed at the proper time
     * @param turtle
     */
    protected HomeCommand(Turtle turtle) {
	myTurtle = turtle;
    }
    /**
     * Sets the turtle's position to the center of the screen
     * @return distance the turtle moved
     * @see interpreter.Command#execute()
     */
    protected double execute() {
    	myTurtle.setAngle(0);
	return myTurtle.setXY(0, 0);
    }
}
