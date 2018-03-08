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

    protected QuotientCommand(Command expr1, Command expr2 ,Map<String, Double> variables, Turtle turtles) {
	setActiveTurtles(turtles);
    	expr1Command = expr1;
	expr2Command = expr2;
	myVariables = variables;
    }
    @Override
    protected double execute(){
    		double EXPR1 = getCommandValue(expr1Command, myVariables, getActiveTurtles().toSingleTurtle());
    		double EXPR2 = getCommandValue(expr2Command, myVariables, getActiveTurtles().toSingleTurtle());
    getActiveTurtles().executeSequentially(myTurtle -> {
    		double test = getCommandValue(expr1Command, myVariables, myTurtle);
    		double test2 = getCommandValue(expr2Command, myVariables, myTurtle);
    	});
	
	return EXPR1/EXPR2;
    }
}