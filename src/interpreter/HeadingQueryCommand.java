package interpreter;

/**
 * Command class that retrieves and returns the angle of a turtle (relative to the center of the screen being
 * (0,0). Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve angle
 * correctly.
 * @author Susie Choi
 *
 */
class HeadingQueryCommand extends Command{
    private Turtle myTurtle;
    /**
     * @param turtle is turtle whose angle is desired
     */
    protected HeadingQueryCommand(Turtle turtle) {
	myTurtle = turtle;
    }
    /**
     * @return angle of Turtle
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
	return myTurtle.getAngle();
    }
>>>>>>> 5e323c37de74bada5cce9600a12cb66fe7c9c0bf
}