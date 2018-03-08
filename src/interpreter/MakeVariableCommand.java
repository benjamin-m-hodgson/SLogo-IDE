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
		myVarVal = varVal;
		myVariables = variables; 
	}
	
	@Override
	protected double execute(){
		double exprEval = myVarVal.execute();
		myVariables.put(((StringCommand)myVarName).getString(), exprEval);
//		System.out.println("var successfully set "+exprEval);
		return exprEval; 
	}

}
