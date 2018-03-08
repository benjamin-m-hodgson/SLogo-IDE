package interpreter;

import java.util.List;

/**
 * Command class that retrieves and returns the y-coordinate of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve its y-coordinate
 * correctly.
 * @author Sarahbland
 *
 */
class YCoordinateQueryCommand extends Command{
    /**
     * @param turtle is turtle whose x-coordinate is desired
     */
    protected YCoordinateQueryCommand(Turtle turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * @return x-coordinate of Turtle
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
    		System.out.println("active turtle size" + getActiveTurtles().size());
   		double returnVal = getActiveTurtles().getY();
   		return -returnVal;
    }
}
