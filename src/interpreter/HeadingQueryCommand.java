package interpreter;

import java.util.List;

/**
 * Command class that retrieves and returns the angle of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve angle
 * correctly.
 * @author Susie Choi
 *
 */
class HeadingQueryCommand extends Command{
    /**
     * @param turtle is turtle whose angle is desired
     */
    protected HeadingQueryCommand(List<Turtle> turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * @return angle of Turtle
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
    		double returnVal = -1.0;
    		for( Turtle myTurtle : getActiveTurtles()) {
    			returnVal = (myTurtle.getAngle()%360);
    		}
    		return returnVal;
    }
}