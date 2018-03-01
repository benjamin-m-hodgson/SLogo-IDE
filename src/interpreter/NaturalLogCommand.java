package interpreter;

import java.util.Map;

/**
 * returns natural log of expr
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
 class NaturalLogCommand extends Command{

    private Command exprCommand;
    private Map<String, Double> myVariables; 

    protected NaturalLogCommand(Command expr,Map<String, Double> variables) {
	exprCommand = expr;
	myVariables = variables;
    }

    @Override
    protected double execute() throws UnidentifiedCommandException{
	Double EXPR = getCommandValue(exprCommand, myVariables);
	return Math.log(EXPR);
    }

}