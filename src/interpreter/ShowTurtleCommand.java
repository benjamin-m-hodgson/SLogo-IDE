package interpreter;

/**
 * Command class that, when executed, makes the Turtle visible
 * @author Sarahbland
 *
 */
public class ShowTurtleCommand implements Command{
	Turtle myTurtle;
	/**
	 * Creates new instance of command, which can be executed at the correct time
	 * @param turtle is turtle who should be made visible
	 */
	protected ShowTurtleCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * Shows turtle's image to the user
	 * @return 1 always
	 */
	public double execute() {
		myTurtle.showTurtle();
		return 1;
	}

}
