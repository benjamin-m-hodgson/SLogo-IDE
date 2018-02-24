package interpreter;

public class RotateTurtleCounterclockwiseCommand implements Command{
	Command myDegreesCommand;
	Turtle myTurtle;
	protected RotateTurtleCounterclockwiseCommand(Command degrees, Turtle turtle) {
		myDegreesCommand = degrees;
		myTurtle = turtle;
	}
	public double execute() {
		double degrees = myDegreesCommand.execute();
		myTurtle.setAngle(myTurtle.getAngle()-degrees);
		return degrees;
	}
}
