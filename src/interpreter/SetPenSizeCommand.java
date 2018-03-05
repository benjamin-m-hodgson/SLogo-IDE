package interpreter;

import java.util.HashMap;
import java.util.Map;

class SetPenSizeCommand extends Command {

	Turtle myTurtle;
	Command myWidthCommand;
	HashMap<String, Double> myVars;
	
	protected SetPenSizeCommand(Turtle turtle, Command widthCommand, Map<String, Double> vars) {
		myTurtle = turtle; 
		myWidthCommand = widthCommand;
		myVars = (HashMap<String, Double>) vars; 
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		double returnVal = getCommandValue(myWidthCommand, myVars);
		myTurtle.setPenWidth(returnVal);
		return returnVal; 
	}

}
