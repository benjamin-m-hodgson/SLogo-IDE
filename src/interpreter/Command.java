package interpreter;

import java.util.Map;

abstract class Command {
	private Turtle myTurtle;
	/**
	 * Executes Commands by changing objects in the back-end (Turtles/Pens) or retrieving information
	 * @return double corresponding to return value of this command in SLogo
	 */
	abstract double execute() throws UnidentifiedCommandException;
	
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