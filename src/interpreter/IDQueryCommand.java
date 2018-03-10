package interpreter;
/**
 * Returns the ID of the turtle in question
 * @author Sarahbland
 *
 */
class IDQueryCommand extends Command{
		Turtle myTurtle;
	protected IDQueryCommand(Turtle turtle) {
		setActiveTurtles(turtle);
	}
	
	@Override
	protected double execute() {
		double returnVal = -1;
		returnVal = getActiveTurtles().getID();
		return returnVal;
	}
	
}
