package interpreter;

import java.util.Map;

class SetBackgroundColorCommand extends Command {
	
	Command myColorIdxCommand;
	Map<String, Double> myVariables;

	protected SetBackgroundColorCommand(Command colorIdx, Map<String, Double> variables) {
		myColorIdxCommand = colorIdx;
		myVariables = variables; 
	}

	@Override
	double execute() throws UnidentifiedCommandException {
		return getCommandValue(myColorIdxCommand, myVariables); 
	}

}
