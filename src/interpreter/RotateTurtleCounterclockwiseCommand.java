package interpreter;

public class RotateTurtleCounterclockwiseCommand implements Command{
	double myDegrees;
	Turtle myTurtle;
	protected RotateTurtleCounterclockwiseCommand(Command degrees, Turtle turtle) {
		myDegrees = degrees.execute();
		myTurtle = turtle;
	}
	@Override
	public double execute() {
		myTurtle.setAngle(myTurtle.getAngle()-myDegrees);
		return myDegrees;
	}
}
