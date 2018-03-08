package interpreter;

import java.util.List;
import java.util.Map;

/**
 * Command class that allows a turtle to be turned toward a specific set of coordinates. Dependent on CommandFactory
 * to make it correctly. Also dependent on Turtle to calculate distances and get/set Angles correctly.
 * @author Sarahbland
 *
 */
class SetTowardsCommand extends Command{
    private Command myXCommand;
    private Command myYCommand;
    private Map<String, Double> myVariables; 


    /**
     * Creates new instance of command, which can be executed at the proper time
     * @param x is command that will, when executed, return the x-coordinate of the point turtle is turning toward
     * @param y is command that will, when executed, return the y-coordinate of the point turtle is turning toward
     * @param turtle is turtle that must be turned
     */
    protected SetTowardsCommand(Command x, Command y, Turtle turtles,Map<String, Double> variables) {
	myXCommand = x;
	myYCommand = y;
	this.setActiveTurtles(turtles);
	myVariables = variables;

    }
    @Override 
    /**
     * Calculates the heading necessary for the turtle to be facing the proper point and turns the turtle to that heading
     * @return number of degrees turned
     * @see interpreter.Command#execute()
     */
    protected double execute() {
    double oldAngleRet = getActiveTurtles().toSingleTurtle().getAngle();
    	getActiveTurtles().executeSequentially(myTurtle -> {
    		double xTowards = getCommandValue(myXCommand, myVariables, myTurtle);
    		double yTowards = getCommandValue(myYCommand, myVariables, myTurtle);
    		double dist = myTurtle.toSingleTurtle().calcDistance(myTurtle.getX(), myTurtle.getY(), xTowards, yTowards);
    		if(dist == 0) {
    		    return;
    		}
    		double oldAngle = myTurtle.getAngle();
    		double heading = Math.toDegrees(Math.asin((xTowards-myTurtle.getX())/dist));
    		System.out.println("absolute angle" + heading);
    		if(!upperHemisphere(xTowards, yTowards, myTurtle)) {
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
    		}
    		else if((oldAngle==heading)) {
    		    if(!sameDirection(oldAngle, xTowards, yTowards, myTurtle)) {
    			heading = heading + 180;
    		    }
    		}
    		myTurtle.setAngle(heading);
    	});

	return (getActiveTurtles().getAngle()-oldAngleRet);

    }
    private boolean sameDirection(double heading, double x, double y, Turtle turtle) {
	double newX = turtle.getX()+0.05*Math.sin(-heading);
	double newY = turtle.getY()+0.05*Math.cos(-heading);
	//System.out.println("same direction? " + "new dist: " + myTurtle.calcDistance(x, y, newX, newY) + " old dist " + myTurtle.calcDistance(x, y, myTurtle.getX(), myTurtle.getY()));
	return (turtle.calcDistance(x, y, newX, newY)< turtle.calcDistance(x, y, turtle.getX(), turtle.getY()));
    }
    private boolean upperHemisphere(double x, double y, Turtle turtle) {
	return ((turtle.getY()-y)<=0);
    }

}
