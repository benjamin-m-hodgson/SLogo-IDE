package interpreter;

/**
 * Command class that retrieves and returns a boolean 0.0/1.0 indicating whether it is in/visible
 * Dependent on the CommandFactory to make it correctly and on the Turtle class to retrieve visibility
 * @author Susie Choi
 *
 */
public class IsShowingQueryCommand implements Command{
	Turtle myTurtle;
	/**
	 * @param turtle is turtle whose visibility is desired
	 */
	protected IsShowingQueryCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * @return visibility of Turtle (0.0 for hidden, 1.0 for visible)
	 * @see interpreter.Command#execute()
	 */
	@Override
	public double execute() {
		int retVal = myTurtle.getTurtleVisibility() ? 1 : 0; 
		return retVal; 
	}
}