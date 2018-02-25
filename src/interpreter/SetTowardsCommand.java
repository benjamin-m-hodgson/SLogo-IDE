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
	/**
	 * Calculates the heading necessary for the turtle to be facing the proper point and turns the turtle to that heading
	 * @retun number of degrees turned
	 * @see interpreter.Command#execute()
	 */
	public double execute() {
		double xTowards = myXCommand.execute();
		double yTowards = myYCommand.execute();
		double dist = myTurtle.calcDistance(myTurtle.getX(), myTurtle.getY(), xTowards, yTowards);
		double oldAngle = myTurtle.getAngle();
		double heading = Math.toDegrees(Math.asin((xTowards-myTurtle.getX())/dist));
		myTurtle.setAngle(heading);
		return (heading-oldAngle);
	}

}
