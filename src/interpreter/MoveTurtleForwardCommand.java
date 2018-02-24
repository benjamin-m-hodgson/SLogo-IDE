package interpreter;

class MoveTurtleForwardCommand implements Command {
	private Command myForwardDistCommand;
	private Turtle myTurtle;
	protected MoveTurtleForwardCommand(Command forwarddist, Turtle turtle){
		myTurtle = turtle;
		myForwardDistCommand = forwarddist;
	}
	
	public double execute() {
		double forwardDist = myForwardDistCommand.execute();
		double angle = Math.toRadians(myTurtle.getAngle());
		myTurtle.setXY(myTurtle.getX()+forwardDist*Math.sin(angle), myTurtle.getY()+forwardDist*Math.cos(angle));
		return forwardDist;
	}
}
