package interpreter;

import java.util.List;
import java.util.Map;

/**
 * Command class used to turn a turtle to a specific absolute heading. Dependent on the CommandFactory to make it 
 * appropriately. Also dependent on the Turtle class to relay/set angles correctly.
 * @author Sarahbland
 *
 */
class SetHeadingCommand extends Command{
	private Command myDegreesCommand;
	private Map<String, Double> myVariables; 

	/**
	 * Creates a new SetHeadingCommand that can be executed at the proper time
	 * @param angleCommand is Command that will eventually return the angle the Turtle should turn to
	 * @param turtle is Turtle whose angle should be changed
	 */
	protected SetHeadingCommand(Command angleCommand, Turtle turtles, Map<String, Double> variables) {
		this.setActiveTurtles(turtles);
		myDegreesCommand = angleCommand;
		myVariables = variables;

	}
	/**
	 * Turns the turtle to the absolute heading determined by the DegreesCommand, then returns the number of degrees moved
	 * @see interpreter.Command#execute()
	 */
	@Override 
	protected double execute(){
		double newAngleRet = getCommandValue(myDegreesCommand, myVariables, getActiveTurtles().toSingleTurtle());
		double oldAngleRet = getActiveTurtles().toSingleTurtle().getAngle();
		this.getActiveTurtles().executeSequentially(myTurtle -> {
			double newAngle = getCommandValue(myDegreesCommand, myVariables, myTurtle);
			myTurtle.setAngle(newAngle);
		});	
		return (newAngleRet-oldAngleRet);
	}
}
