package interpreter;


import java.util.Map;

/**
 * Command class that turns the turtle's heading a specified number of degrees counterclockwise. Dependent on CommandFactory 
 *  to create correctly. Also dependent on the Turtle class to relay/set angles correctly.
 * @author Sarahbland
 *
 */
 class RotateTurtleCounterclockwiseCommand extends Command{
	private Command myDegreesCommand;
	private Map<String, Double> myVariables; 

	/**
	 * Creates a new instance of the command, which can be executed at the correct time
	 * @param degrees is Command that, when executed, will return the number of degrees the turtle should move
	 * @param turtle is Turtle whose heading should change
	 */
	protected RotateTurtleCounterclockwiseCommand(Command degrees, Turtle activeTurtles,Map<String, Double> variables) {
		myDegreesCommand = degrees;
		this.setActiveTurtles(activeTurtles);
		myVariables = variables;
	}


	@Override
	/**
	 * Sets the heading of the turtle to the specified number of degrees counterclockwise of its current position
	 * @return number of degrees moved
	 * @see interpreter.Command#execute()
	 */
	protected double execute() throws UnidentifiedCommandException {
		double degreesRet = getCommandValue(myDegreesCommand, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle -> {
			try {
			double degrees = getCommandValue(myDegreesCommand, myVariables, myTurtle);
			myTurtle.setAngle(myTurtle.getAngle()-degrees);
			}
    			catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
		});
		return degreesRet;
	}
}
