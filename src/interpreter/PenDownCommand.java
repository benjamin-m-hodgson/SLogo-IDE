package interpreter;

/**
 * Command class that, when executed, puts the Turtle's pen down
 * @author Sarahbland
 *
 */
 class PenDownCommand extends Command{
    private Turtle myTurtle;
	/**
	 * Creates new instance of command, which can be executed at the correct time
	 * @param turtle is turtle whose pen should be put down
	 */
	protected PenDownCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * Puts turtle's pen down, so that subsequent movements will leave a trail
	 * @return 1 always
	 */
	protected double execute() {
		myTurtle.showPen();
		return 1;
	}

}
