package interpreter;

/**
 * Command class that retrieves and returns the angle of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve angle
 * correctly.
 * @author Susie Choi
 *
 */
public class HeadingQueryCommand implements Command{
	Turtle myTurtle;
	/**
	 * @param turtle is turtle whose angle is desired
	 */
	protected HeadingQueryCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * @return angle of Turtle (absolute, between 0 and 360)
	 * @see interpreter.Command#execute()
	 */
	@Override
	public double execute() {
		return myTurtle.getAngle() % 360;
	}
}