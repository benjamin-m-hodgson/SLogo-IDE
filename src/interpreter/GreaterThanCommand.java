package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns 1 if the value of expr1 is strictly greater than the value of expr2, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
class GreaterThanCommand extends Command{

    private Command expr1Command;
    private Command expr2Command;
    private Map<String, Double> myVariables; 

    protected GreaterThanCommand(Command expr1, Command expr2, Map<String, Double> variables, List<Turtle> turtles) {
	expr1Command = expr1;
	expr2Command = expr2;
	myVariables = variables; 
	setActiveTurtles(turtles);
    }

    @Override
    protected double execute() throws UnidentifiedCommandException{
    	double arg1Val = -1.0;
    	double arg2Val = 1;
    	for(Turtle myTurtle : getActiveTurtles()) {
    		arg1Val= getCommandValue(expr1Command, myVariables, myTurtle); 
    		arg2Val= getCommandValue(expr2Command, myVariables, myTurtle); 
    	}
	
	return (arg1Val > arg2Val) ? 1.0 : 0.0; 
    }

}
