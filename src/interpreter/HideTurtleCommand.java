package interpreter;

/**
 * Command class that, when executed, makes the Turtle invisible
 * @author Sarahbland
 *
 */
class HideTurtleCommand extends Command{
    Turtle myTurtle;
    /**
     * Creates new instance of command, which can be executed at the correct time
     * @param turtle is turtle who should be made invisible
     */
    protected HideTurtleCommand(Turtle turtle) {
	myTurtle = turtle;
    }
    /**
     * Shows turtle's image to the user
     * @return 0 always
     */
    @Override
    protected double execute() {
	myTurtle.hideTurtle();
	return 0;
    }

}
