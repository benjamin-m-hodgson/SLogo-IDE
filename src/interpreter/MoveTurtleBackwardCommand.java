package interpreter;

public class MoveTurtleBackwardCommand implements Command{
	private double myBackwardDist;
	private Turtle myTurtle;
	protected MoveTurtleBackwardCommand(Command forwarddist, Turtle turtle){
		myTurtle = turtle;
		myBackwardDist = forwarddist.execute();
	}
	
	@Override
	public double execute() {
		double angle = Math.toRadians(myTurtle.getAngle());
		myTurtle.setXY(myTurtle.getX()-myBackwardDist*Math.sin(angle), myTurtle.getY()-myBackwardDist*Math.cos(angle));
		return myBackwardDist;
	}
}
