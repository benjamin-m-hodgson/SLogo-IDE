package interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Changes size of the pen associated with a turtle, where the new pen size is the only arg.
 * @author Susie Choi 
 *
 */
class SetPenSizeCommand extends Command {

	Command myWidthCommand;
	HashMap<String, Double> myVars;
	
	protected SetPenSizeCommand(Turtle turtle, Command widthCommand, Map<String, Double> vars) {
		setActiveTurtles(turtle); 
		myWidthCommand = widthCommand;
		myVars = (HashMap<String, Double>) vars; 
	}
	
	@Override
	double execute() throws UnidentifiedCommandException{
		double returnVal = getCommandValue(myWidthCommand, myVars, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle -> {
			try {
				getCommandValue(myWidthCommand, myVars, myTurtle);
			}
			catch(UnidentifiedCommandException e) {
    				throw new UnidentifiedCommandError("Improper # arguments");
    			}
		});
		getActiveTurtles().setPenWidth(returnVal);
		return returnVal; 
	}

}
