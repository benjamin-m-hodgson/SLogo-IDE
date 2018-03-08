package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns product of the values of expr1 and expr2
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class ProductCommand extends Command{

    private Command expr1Command;
    private Command expr2Command;
    private Map<String, Double> myVariables; 

    protected ProductCommand(Command expr1, Command expr2 ,Map<String, Double> variables, Turtle turtles) {
    		expr1Command = expr1;
    		expr2Command = expr2;
    		myVariables = variables;
    		setActiveTurtles(turtles);
    }
    @Override
    protected double execute(){
    double EXPR1Ret =  getCommandValue(expr1Command, myVariables, getActiveTurtles().toSingleTurtle());
    double EXPR2Ret = getCommandValue(expr2Command, myVariables, getActiveTurtles().toSingleTurtle());;
    getActiveTurtles().executeSequentially(myTurtle -> {
    		double EXPR1 = getCommandValue(expr1Command, myVariables, myTurtle);
    		double EXPR2 = getCommandValue(expr2Command, myVariables, myTurtle);
    	});
	return EXPR1Ret*EXPR2Ret;
    }
}