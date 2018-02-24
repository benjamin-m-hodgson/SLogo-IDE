package interpreter;

class SetHeadingCommand implements Command{
	private Command myDegreesCommand;
	Turtle myTurtle;
	/**
	 * Creates a new SetHeadingCommand that can be executed at the proper time
	 * @param angleCommand is Command that will eventually return the angle the Turtle should turn to
	 * @param turtle is Turtle whose angle should be changed
	 */
	protected SetHeadingCommand(Command angleCommand, Turtle turtle) {
		myTurtle = turtle;
		myDegreesCommand = angleCommand;
	}
	/**
	 * Turns the turtle to the absolute heading determined by the DegreesCommand, then returns the number of degrees moved
	 * @see interpreter.Command#execute()
	 */
	public double execute() {
		double newAngle = myDegreesCommand.execute();
		double oldAngle = myTurtle.getAngle();
		myTurtle.setAngle(newAngle);
		return (newAngle-oldAngle);
	}
}
