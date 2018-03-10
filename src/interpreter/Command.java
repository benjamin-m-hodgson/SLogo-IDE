package interpreter;

import java.util.Map;

/**
 * Abstract class extended by all commands. Keeps track of the active turtle the command
 * is being executed on, executes concatenated commands (by going down the tree of .execute()
 * functions), and replaces necessary variables with their values. Dependent on the Turtle class
 * as well as StringCommands.
 * @author Sarahbland
 * @author Andrew Arnold
 *
 */
abstract class Command {
	private Turtle myTurtle;
	/**
	 * Executes Commands by changing objects in the back-end (Turtles/Pens) or retrieving information
	 * @return double corresponding to return value of this command in SLogo
	 */
	abstract double execute() throws UnidentifiedCommandException;
	
	/**
	 * Method used in the event that a Command takes another Command as an argument. If
	 * the command in question is a StringCommand, will recognize that it must be a variable
	 * and return the double corresponding to it. Otherwise executes the command in question
	 * and returns the proper value. Executes on one turtle to ensure that concatenated commands
	 * execute on the same turtle.
	 * @param command is Command needed to be executed
	 * @param varsMap is map of variables to their double values
	 * @param turtle is turtle command is being executed on
	 * @return proper return value
	 * @throws UnidentifiedCommandException
	 */
	protected double getCommandValue(Command command, Map<String, Double> varsMap, Turtle turtle) throws UnidentifiedCommandException {
		if(command instanceof StringCommand) {
			return getValueOfVar(((StringCommand)command).toString(), varsMap);
		}
		else {
			command.setActiveTurtles(turtle);
			return command.execute();
		}
	}
	protected double getValueOfVar(String variable, Map<String, Double> varsMap) {
		double varVal = 0; 
		if (varsMap.containsKey(variable)) {
			varVal = varsMap.get(variable);
		} 
		return varVal; 
	}
	
	protected void setActiveTurtles(Turtle turtle) {
		myTurtle = turtle;
		}
	protected Turtle getActiveTurtles() {
		return myTurtle;
	}
	}