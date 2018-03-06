package interpreter;

import java.util.List;

/**
 * Command class that retrieves and returns the X-coordinate of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve its x-coordinate
 * correctly.
 * @author Sarahbland
 *
 */
class XCoordinateQueryCommand extends Command{

    /**
     * @param turtle is turtle whose x-coordinate is desired
     */
    protected XCoordinateQueryCommand(List<Turtle> turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * @return x-coordinate of Turtle
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
    		double returnVal = -1.0;
    		for(Turtle myTurtle : getActiveTurtles()) {
    			returnVal = myTurtle.getX();
    		}
    		return returnVal;
    }
}
