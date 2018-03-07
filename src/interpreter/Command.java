package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class Command {
	private Turtle myTurtle;
	/**
	 * Executes Commands by changing objects in the back-end (Turtles/Pens) or retrieving information
	 * @return double corresponding to return value of this command in SLogo
	 */
	abstract double execute();
	
	protected double getCommandValue(Command command, Map<String, Double> varsMap, Turtle turtle) {
		if(command instanceof StringCommand) {
			return getValueOfVar(((StringCommand)command).toString(), varsMap);
		}
		else if(command instanceof IDQueryCommand) { //|| command instanceof XCoordinateQueryCommand || command instanceof YCoordinateQueryCommand || command instanceof HeadingQueryCommand || command instanceof IsPenDownQueryCommand || command instanceof IsShowingQueryCommand) {
			SingleTurtle singleTurtle = turtle.toSingleTurtle();
			command.setActiveTurtles(singleTurtle);
			return command.execute();
		}
		else {
			//TODO: might want single turtle here too? tbd!!
			return command.execute();
		}
	}
	protected double getValueOfVar(String variable, Map<String, Double> varsMap) {
		double varVal = 0; 
		if (varsMap.containsKey(variable)) {
			varVal = varsMap.get(variable);
		} 
		System.out.println("returning "+varVal);
		return varVal; 
	}
//		protected void setActiveTurtles( List<Turtle> newTurtles) {
//			myActiveTurtles= newTurtles;
//		}
//		protected List<Turtle> getActiveTurtles(){
//			return myActiveTurtles;
//		}
	
	protected void setActiveTurtles(Turtle turtle) {
		myTurtle = turtle;
		}
	protected Turtle getActiveTurtles() {
		return myTurtle;
	}
	}