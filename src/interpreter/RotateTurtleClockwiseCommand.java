package interpreter;

/**
 * Command class that turns the turtle's heading a specified number of degrees clockwise. Dependent on CommandFactory to create
 * correctly. Also dependent on the Turtle class to relay/set angles correctly.
 * @author Sarahbland
 *
 */
public class RotateTurtleClockwiseCommand implements Command{
	Command myDegreesCommand;
	Turtle myTurtle;
	/**
	 * Creates a new instance of the command, which can be executed at the correct time
	 * @param degrees is Command that, when executed, will return the number of degrees the turtle should move
	 * @param turtle is Turtle whose heading should change
	 */
	protected RotateTurtleClockwiseCommand(Command degrees, Turtle turtle) {
		myDegreesCommand = degrees;
		myTurtle = turtle;
	}
	/**
	 * Sets the heading of the turtle to the specified number of degrees clockwise of its current position
	 * @see interpreter.Command#execute()
	 */
	public double execute() {
		double degrees = myDegreesCommand.execute(); 
		myTurtle.setAngle(myTurtle.getAngle()+degrees);
		return degrees;
	}
}
