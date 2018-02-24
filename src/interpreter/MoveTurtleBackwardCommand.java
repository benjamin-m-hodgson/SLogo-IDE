package interpreter;

public class MoveTurtleBackwardCommand implements Command{
	private Command myBackwardDistCommand;
	private Turtle myTurtle;
	protected MoveTurtleBackwardCommand(Command backwarddist, Turtle turtle){
		myTurtle = turtle;
		myBackwardDistCommand = backwarddist;
	}
	
	public double execute() {
		double dist = myBackwardDistCommand.execute();
		double angle = Math.toRadians(myTurtle.getAngle());
		myTurtle.setXY(myTurtle.getX()-dist*Math.sin(angle), myTurtle.getY()-dist*Math.cos(angle));
		return dist;
	}
}
