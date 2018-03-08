package interpreter;



/**
 * Command class that retrieves and returns a boolean 0.0/1.0 indicating whether its Pen is up/down
 * Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve pen visibility
 * @author Susie Choi
 *
 */
class IsPenDownQueryCommand extends Command{
    /**
     * @param turtle is turtle whose pen visibility is desired
     */
    protected IsPenDownQueryCommand(Turtle turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * @return pen visibility of Turtle (0.0 for up/hidden, 1.0 for down/visible)
     * @see interpreter.Command#execute()
     */
    @Override
    protected double execute() {
    double retVal = getActiveTurtles().getPenVisibility() ? 1 : 0; 
	
	return retVal; 
    }
}