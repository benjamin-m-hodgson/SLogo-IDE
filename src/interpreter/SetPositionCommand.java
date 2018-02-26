package interpreter;

/**
 * Command class that sets the absolute position of a Turtle. Dependent on the CommandFactory class to make it correctly.
 * Also dependent on the Turtle class to relay/set distances correctly.
 * @author Sarahbland
 *
 */
public class SetPositionCommand implements Command{
	private Command myNewXCommand;
	private Command myNewYCommand;
	private Turtle myTurtle;
	/**
	 * Creates a new instance of the command that can be executed at the proper time
	 * @param newXCommand is Command that when executed returns the new X position
	 * @param newYCommand is Command that when executed returns the new Y position
	 * @param turtle is Turtle that should be moving
	 */
	protected SetPositionCommand(Command newXCommand, Command newYCommand, Turtle turtle) {
		myNewXCommand = newXCommand;
		myNewYCommand = newYCommand;
		myTurtle = turtle;
	}
	/**
	 * Moves the turtle to the specified location and draws a line if the pen is down
	 * @return distance the Turtle traveled
	 * @see interpreter.Command#execute()
	 */
	@Override 
	public double execute() {
		double newX = myNewXCommand.execute();
		double newY = myNewYCommand.execute();
		return myTurtle.setXY(newX, -newY);
	}
}
