package interpreter;

import java.util.Map;

/**
 * 
 * @author susiechoi
 *
 */

class MakeVariableCommand extends Command {
	
	private Command myVarName; 
	private Command myVarVal;
	private Map<String, Double> myVariables; 

	protected MakeVariableCommand(Command varName, Command varVal, Map<String, Double> variables) {
		myVarName = varName; 
		System.out.println(((StringCommand)myVarName).getString());
		myVarVal = varVal;
		myVariables = variables; 
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException {
		double exprEval = myVarVal.execute();
		myVariables.put(((StringCommand)myVarName).getString(), exprEval);
		return exprEval; 
	}

}
