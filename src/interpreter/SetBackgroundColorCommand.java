package interpreter;

import java.util.Map;

class SetBackgroundColorCommand extends Command {
	
	Command myColorIdxCommand;
	Map<String, Double> myVariables;

	protected SetBackgroundColorCommand(Command colorIdx, Map<String, Double> variables, Turtle activeTurtles) {
		myColorIdxCommand = colorIdx;
		myVariables = variables; 
		setActiveTurtles(activeTurtles);
	}

	@Override
	double execute() throws UnidentifiedCommandException {
		double retVal = getCommandValue(myColorIdxCommand, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle ->{ 
			try{getCommandValue(myColorIdxCommand, myVariables, myTurtle);
			}
			catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
			});
		return retVal; 
	}

}
