package interpreter;

public class SetPositionCommand implements Command{
	private Command myNewXCommand;
	private Command myNewYCommand;
	private Turtle myTurtle;
	protected SetPositionCommand(Command newXCommand, Command newYCommand, Turtle turtle) {
		myNewXCommand = newXCommand;
		myNewYCommand = newYCommand;
		myTurtle = turtle;
	}
	public double execute() {
		double newX = myNewXCommand.execute();
		double newY = myNewYCommand.execute();
		return myTurtle.setXY(newX, newY);
	}
}
