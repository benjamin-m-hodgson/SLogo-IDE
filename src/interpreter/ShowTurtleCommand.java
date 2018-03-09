package interpreter;



/**
 * Command class that, when executed, makes the Turtle visible
 * @author Sarahbland
 *
 */
class ShowTurtleCommand extends Command{
    /**
     * Creates new instance of command, which can be executed at the correct time
     * @param turtle is turtle who should be made visible
     */
    protected ShowTurtleCommand(Turtle turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * Shows turtle's image to the user
     * @return 1 always
     */
    @Override 
    protected double execute() {
    	getActiveTurtles().showTurtle();
	return 1;
    }

}
