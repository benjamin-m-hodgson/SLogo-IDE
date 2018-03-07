package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns 1 if the value of expr1 and the value of expr2 are not sequal, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class NotEqualCommand extends Command{
    private final double TRUE = 1;
    private final double FALSE = 0;
    private Command expr1Command;
    private Command expr2Command;
    private Map<String, Double> myVariables; 

    protected NotEqualCommand(Command expr1, Command expr2, Map<String, Double> variables, List<Turtle> turtles) {
	expr1Command = expr1;
	expr2Command = expr2;
	myVariables = variables;
	setActiveTurtles(turtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    	double EXPR1 = 1.0;
    	double EXPR2 = 1.0;
    	for(Turtle myTurtle : getActiveTurtles()) {
    		EXPR1 = getCommandValue(expr1Command, myVariables, myTurtle);
    		EXPR2 = getCommandValue(expr2Command, myVariables, myTurtle);
    	}
	
	if (EXPR1 != EXPR2) {
	    return TRUE;
	}
	else {
	    return FALSE;
	}
    }
}
