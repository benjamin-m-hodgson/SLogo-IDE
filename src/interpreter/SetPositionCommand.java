package interpreter;

import java.util.Map;

/**
 * Command class that sets the absolute position of a Turtle. Dependent on the CommandFactory class to make it correctly.
 * Also dependent on the Turtle class to relay/set distances correctly.
 * @author Sarahbland
 *
 */
 class SetPositionCommand extends Command{
	private Command myNewXCommand;
	private Command myNewYCommand;
	private Turtle myTurtle;
	private Map<String, Double> myVariables; 

	/**
	 * Creates a new instance of the command that can be executed at the proper time
	 * @param newXCommand is Command that when executed returns the new X position
	 * @param newYCommand is Command that when executed returns the new Y position
	 * @param turtle is Turtle that should be moving
	 */
	protected SetPositionCommand(Command newXCommand, Command newYCommand, Turtle turtle		,Map<String, Double> variables) {
		myNewXCommand = newXCommand;
		myNewYCommand = newYCommand;
		myTurtle = turtle;
		myVariables = variables;

	}
	/**
	 * Moves the turtle to the specified location and draws a line if the pen is down
	 * @return distance the Turtle traveled
	 * @see interpreter.Command#execute()
	 */
	@Override 
	protected double execute() throws UnidentifiedCommandException{
		double newX = getCommandValue(myNewXCommand, myVariables);
		double newY = getCommandValue(myNewYCommand, myVariables);
		return myTurtle.setXY(newX, -newY);
	}
}
