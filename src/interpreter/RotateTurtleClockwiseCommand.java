package interpreter;

public class RotateTurtleClockwiseCommand implements Command{
	double myDegrees;
	Turtle myTurtle;
	protected RotateTurtleClockwiseCommand(Command degrees, Turtle turtle) {
		myDegrees = degrees.execute();
		myTurtle = turtle;
	}
	public double execute() {
		myTurtle.setAngle(myTurtle.getAngle()+myDegrees);
		return myDegrees;
	}
}
