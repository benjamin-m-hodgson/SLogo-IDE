package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns negative of the values of expr
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */

class MinusCommand extends Command{

    private Command exprCommand;
    private Map<String, Double> myVariables; 

    protected MinusCommand(Command expr, Map<String, Double> variables, Turtle turtles) {
	exprCommand = expr;
	myVariables = variables; 
	setActiveTurtles(turtles);
    }

    @Override
    protected double execute(){
    	double exprVal = getCommandValue(exprCommand, myVariables, getActiveTurtles());
    getActiveTurtles().executeSequentially(myTurtle -> {
    		getCommandValue(exprCommand, myVariables, myTurtle); 
    	});
	return -exprVal;
    }

}