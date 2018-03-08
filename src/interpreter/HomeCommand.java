package interpreter;

import java.util.List;

/**
 * Command class that moves the turtle back to its home position (center of the screen). Dependent on the CommandFactory
 * to make it correctly, and the Turtle class to move itself and calculate distance correctly.
 * @author Sarahbland
 *
 */

class HomeCommand extends Command{
    List<Turtle> myTurtles;
    /**
     * Creates a new instance of the command to be executed at the proper time
     * @param turtle
     */
    protected HomeCommand(Turtle turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * Sets the turtle's position to the center of the screen
     * @return distance the turtle moved
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
    double returnVal = -1.0;
    		getActiveTurtles().setAngle(0);
    		returnVal = getActiveTurtles().setXY(0, 0);
    		return returnVal;
    }
}
