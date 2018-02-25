package interpreter;

/**
 * Command class that retrieves and returns the X-coordinate of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve its x-coordinate
 * correctly.
 * @author Sarahbland
 *
 */
public class XCoordinateQueryCommand implements Command{
	Turtle myTurtle;
	/**
	 * @param turtle is turtle whose x-coordinate is desired
	 */
	protected XCoordinateQueryCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * @return x-coordinate of Turtle
	 * @see interpreter.Command#execute()
	 */
	@Override
	public double execute() {
		return myTurtle.getX();
	}
}
