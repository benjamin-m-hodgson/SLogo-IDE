package interpreter;

/**
 * Command class that, when executed, puts the Turtle's pen up so that it will not draw a line when the Turtle moves
 * @author Sarahbland
 *
 */
public class PenUpCommand implements Command{
	Turtle myTurtle;
	/**
	 * Creates new instance of command, which can be executed at the correct time
	 * @param turtle is turtle whose pen should be put up
	 */
	protected PenUpCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * Puts turtle's pen up, so that subsequent movements will not leave a trail
	 * @return 0 always
	 */
	public double execute() {
		myTurtle.showPen();
		return 0;
	}

}