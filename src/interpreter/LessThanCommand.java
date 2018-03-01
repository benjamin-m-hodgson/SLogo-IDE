package interpreter;

import java.util.Map;

/**
 * returns 1 if the value of expr1 is strictly less than the value of expr2, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
public class LessThanCommand extends Command{
    
    Command expr1Command;
    Command expr2Command;
    Map<String, Double> myVariables; 
    
    protected LessThanCommand(Command expr1, Command expr2, Map<String, Double> variables) {
		expr1Command = expr1;
		expr2Command = expr2;
		myVariables = variables; 
	}
    
	@Override
	public double execute() throws UnidentifiedCommandException{
		double arg1Val; 
		double arg2Val; 
		if (expr1Command instanceof StringCommand) {
			arg1Val = getValueOfVar(((StringCommand)expr1Command).getString(), myVariables);
		}
		else {
			arg1Val = expr1Command.execute(); 
		}
		if (expr2Command instanceof StringCommand) {
			arg2Val = getValueOfVar(((StringCommand)expr2Command).getString(), myVariables);
		}
		else {
			arg2Val = expr2Command.execute();
		}
		return (arg1Val < arg2Val) ? 1.0 : 0.0; 
	}
	
	public int getNumArgs() {
		return 2;
	}
}
