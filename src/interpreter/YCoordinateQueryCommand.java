package interpreter;

/**
 * Command class that retrieves and returns the y-coordinate of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve its y-coordinate
 * correctly.
 * @author Sarahbland
 *
 */
class YCoordinateQueryCommand extends Command{
    private Turtle myTurtle;
    /**
     * @param turtle is turtle whose x-coordinate is desired
     */
    protected YCoordinateQueryCommand(Turtle turtle) {
	myTurtle = turtle;
    }
    /**
     * @return x-coordinate of Turtle
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
	return myTurtle.getY();
    }
}
