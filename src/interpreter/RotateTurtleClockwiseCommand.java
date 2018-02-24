package interpreter;

public class RotateTurtleClockwiseCommand implements Command{
	Command myDegreesCommand;
	Turtle myTurtle;
	protected RotateTurtleClockwiseCommand(Command degrees, Turtle turtle) {
		myDegreesCommand = degrees;
		myTurtle = turtle;
	}
	public double execute() {
		double degrees = myDegreesCommand.execute(); 
		myTurtle.setAngle(myTurtle.getAngle()+degrees);
		return degrees;
	}
}
