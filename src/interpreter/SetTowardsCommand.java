package interpreter;

/**
 * Command class that allows a turtle to be turned toward a specific set of coordinates. Dependent on CommandFactory
 * to make it correctly. Also dependent on Turtle to calculate distances and get/set Angles correctly.
 * @author Sarahbland
 *
 */
public class SetTowardsCommand implements Command{
	private Command myXCommand;
	private Command myYCommand;
	private Turtle myTurtle;
	/**
	 * Creates new instance of command, which can be executed at the proper time
	 * @param x is command that will, when executed, return the x-coordinate of the point turtle is turning toward
	 * @param y is command that will, when executed, return the y-coordinate of the point turtle is turning toward
	 * @param turtle is turtle that must be turned
	 */
	public SetTowardsCommand(Command x, Command y, Turtle turtle) {
		myXCommand = x;
		myYCommand = y;
		myTurtle = turtle;
	}
	@Override 
	/**
	 * Calculates the heading necessary for the turtle to be facing the proper point and turns the turtle to that heading
	 * @return number of degrees turned
	 * @see interpreter.Command#execute()
	 */
	public double execute() throws UnidentifiedCommandException{
		double xTowards = myXCommand.execute();
		double yTowards = myYCommand.execute();
		double dist = myTurtle.calcDistance(myTurtle.getX(), myTurtle.getY(), xTowards, yTowards);
		if(dist == 0) {
			return 0;
		}
		double oldAngle = myTurtle.getAngle();
		double heading = Math.toDegrees(Math.asin((xTowards-myTurtle.getX())/dist));
		System.out.println("absolute angle" + heading);
		if(!upperHemisphere(xTowards, yTowards)) {
			if(heading>0) {
				heading = heading + 90;
			}
			else if(heading<0) {
				heading = heading - 90;
			}
			else if(heading == 0) {
				heading = heading + 180;
			}
			myTurtle.setAngle(heading);
			return (heading-oldAngle);
		}
		else if((oldAngle==heading)) {
			if(!sameDirection(oldAngle, xTowards, yTowards)) {
				heading = heading + 180;
			}
		}
		myTurtle.setAngle(heading);
		return (heading-oldAngle);

	}
	private boolean sameDirection(double heading, double x, double y) {
		double newX = myTurtle.getX()+0.05*Math.sin(-heading);
		double newY = myTurtle.getY()+0.05*Math.cos(-heading);
		System.out.println("same direction? " + "new dist: " + myTurtle.calcDistance(x, y, newX, newY) + " old dist " + myTurtle.calcDistance(x, y, myTurtle.getX(), myTurtle.getY()));
		return (myTurtle.calcDistance(x, y, newX, newY)<myTurtle.calcDistance(x, y, myTurtle.getX(), myTurtle.getY()));
	}
	private boolean upperHemisphere(double x, double y) {
		return ((myTurtle.getY()-y)<=0);
	}

}
