package interpreter;

import java.util.Map;

/**
 * returns difference of the values of expr1 and expr2, expr1 - expr2
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
 class DifferenceCommand extends Command{

	private Command expr1Command;
    	private Command expr2Command;
    	private Map<String, Double> myVariables; 
    	
	protected DifferenceCommand(Command expr1, Command expr2, Map<String, Double> variables) {
		expr1Command = expr1;
		expr2Command = expr2;
		myVariables = variables; 
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException{
		double arg1Val= getCommandValue(expr1Command, myVariables); 
		double arg2Val= getCommandValue(expr2Command, myVariables); 
	
		return arg1Val - arg2Val;
	}

}