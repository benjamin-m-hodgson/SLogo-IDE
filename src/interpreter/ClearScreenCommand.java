package interpreter;

/**
 * Command class that, when executed, moves the turtle back to the center of the screen and erases all previous pen
 * lines. Dependent on the CommandFactory to make it correctly and on the Turtle class to properly clear the pen
 * and move the turtle.
 * @author Sarahbland
 *
 */
public class ClearScreenCommand implements Command{
	Turtle myTurtle;
	/**
	 * Creates new instance of command to be executed at the proper time
	 * @param turtle is turtle who needs to be returned to home
	 */
	protected ClearScreenCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * Moves the turtle to the center of the screen, then clears all of the pen lines on the screen
	 * @return distance turtle moved
	 * @see interpreter.Command#execute()
	 */
	public double execute() {
		double returnVal = myTurtle.setXY(0, 0);
		myTurtle.clearPen();
		myTurtle.setAngle(0);
		return returnVal;
	}

}
