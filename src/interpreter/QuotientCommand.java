package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns quotient of the values of expr1 and expr2, expr1/expr2
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class QuotientCommand extends Command{

    private Command expr1Command;
    private Command expr2Command;
    private Map<String, Double> myVariables; 

    protected QuotientCommand(Command expr1, Command expr2 ,Map<String, Double> variables, List<Turtle> turtles) {
	setActiveTurtles(turtles);
    	expr1Command = expr1;
	expr2Command = expr2;
	myVariables = variables;
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    		double EXPR1 = -1.0;
    		double EXPR2 = 1.0;
    	for(Turtle myTurtle: getActiveTurtles()) {
    		EXPR1 = getCommandValue(expr1Command, myVariables, myTurtle);
    		EXPR2 = getCommandValue(expr2Command, myVariables, myTurtle);
    	}
	
	return EXPR1/EXPR2;
    }
}