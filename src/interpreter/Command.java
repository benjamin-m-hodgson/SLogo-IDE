/**
 * 
 * As an abstract class to be extended by all commands, Command encapsulates data and methods that are required for all commands.
 * 
 * The myTurtle instance variable is necessary for all commands because all commands need to be executed with reference to a Turtle, 
 * 		or have arguments that need to be executed with reference to a Turtle. 
 * 		This also necessitates the methods setMyTurtles and getActiveTurtles to coordinate which Turtle(s) to execute on. 
 * The myVariables instance variable, getCommandValue method, and getVariableValue method are all integral components 
 * 		for the decoding of argument values (whether they be variables or nested commands). 
 * 
 */

package interpreter;

//import java.util.Map;
//
//abstract class Command {
//
//	protected Turtle myTurtle; 
//	protected Map<String, Double> myVariables; 
//
//	protected abstract double execute() throws BadFormatException, UnidentifiedCommandException, MissingInformationException;
//
//	protected void setMyTurtles(Turtle turtle) {
//		myTurtle = turtle; 
//	} 
//
//	protected Turtle getActiveTurtles() {
//		return myTurtle; 
//	}
//
//	/**
//	 * Method used in the event that a Command takes another Command as an argument. 
//	 * If the command in question is a StringCommand, will recognize that it must be a variable and return the corresponding double. 
//	 * Otherwise, executes the command in question and returns the proper value. 
//	 */
//	protected double getCommandValue(Command command, Turtle turtle) throws UnidentifiedCommandException, BadFormatException, MissingInformationException {
//		if(command instanceof StringCommand) {
//			return getVariableValue(((StringCommand)command).toString());
//		}
//		else {
//			command.setMyTurtles(turtle);
//			return command.execute();
//		}
//	}
//
//	protected double getVariableValue(String variable) {
//		double varVal = 0; 
//		if (myVariables.containsKey(variable)) {
//			varVal = myVariables.get(variable);
//		} 
//		return varVal; 
//	}
//
//}


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

	/**
	 * Retrieves the value of a variable or sends back the default value (0) if the variable
	 * has not been set yet.
	 * @param variable is string corresponding to potential variable
	 * @param varsMap is map of previously made variables to their double counterparts
	 * @return value of variable
	 */
	protected double getValueOfVar(String variable, Map<String, Double> varsMap) {
		double varVal = 0; 
		if (varsMap.containsKey(variable)) {
			varVal = varsMap.get(variable);
		} 
		return varVal; 
	}

	/**
	 * Sets active turtles that this command will execute upon
	 * @param turtle is Turtle containing turtle(s) desired
	 */
	protected void setActiveTurtles(Turtle turtle) {
		myTurtle = turtle;
	}
	/**
	 * Gets active turtles that this command will execute upon
	 * @return Turtle containing turtle(s) desired
	 */
	protected Turtle getActiveTurtles() {
		return myTurtle;
	}
}