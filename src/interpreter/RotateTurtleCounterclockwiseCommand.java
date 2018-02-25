package interpreter;

/**
 * Command class that turns the turtle's heading a specified number of degrees counterclockwise. Dependent on CommandFactory 
 *  to create correctly. Also dependent on the Turtle class to relay/set angles correctly.
 * @author Sarahbland
 *
 */
public class RotateTurtleCounterclockwiseCommand implements Command{
	Command myDegreesCommand;
	Turtle myTurtle;
	/**
	 * Creates a new instance of the command, which can be executed at the correct time
	 * @param degrees is Command that, when executed, will return the number of degrees the turtle should move
	 * @param turtle is Turtle whose heading should change
	 */
	protected RotateTurtleCounterclockwiseCommand(Command degrees, Turtle turtle) {
		myDegreesCommand = degrees;
		myTurtle = turtle;
	}

	/**
	 * Sets the heading of the turtle to the specified number of degrees counterclockwise of its current position
	 * @return number of degrees moved
	 * @see interpreter.Command#execute()
	 */
	public double execute() {
		double degrees = myDegreesCommand.execute();
		myTurtle.setAngle(myTurtle.getAngle()-degrees);
		return degrees;
	}
}
