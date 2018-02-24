package interpreter;

class MoveTurtleForwardCommand implements Command {
	private double myForwardDist;
	private Turtle myTurtle;
	protected MoveTurtleForwardCommand(Command forwarddist, Turtle turtle){
		myTurtle = turtle;
		myForwardDist = forwarddist.execute();
	}
	
	public double execute() {
		double angle = Math.toRadians(myTurtle.getAngle());
		myTurtle.setXY(myTurtle.getX()+myForwardDist*Math.sin(angle), myTurtle.getY()+myForwardDist*Math.cos(angle));
		return myForwardDist;
	}
}
