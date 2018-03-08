package interpreter;

import java.util.HashMap;
import java.util.Map;

class SetPenSizeCommand extends Command {

	Command myWidthCommand;
	HashMap<String, Double> myVars;
	
	protected SetPenSizeCommand(Turtle turtle, Command widthCommand, Map<String, Double> vars) {
		setActiveTurtles(turtle); 
		myWidthCommand = widthCommand;
		myVars = (HashMap<String, Double>) vars; 
	}
	
	@Override
	double execute() {
		double returnVal = getCommandValue(myWidthCommand, myVars, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle -> getCommandValue(myWidthCommand, myVars, myTurtle));
		getActiveTurtles().setPenWidth(returnVal);
		return returnVal; 
	}

}
