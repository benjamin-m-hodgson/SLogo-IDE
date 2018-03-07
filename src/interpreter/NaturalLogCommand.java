package interpreter;

import java.util.List;
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

    protected NaturalLogCommand(Command expr,Map<String, Double> variables, List<Turtle> turtles) {
	exprCommand = expr;
	myVariables = variables;
	setActiveTurtles(turtles);
    }

    @Override
    protected double execute() throws UnidentifiedCommandException{
    	Double EXPR = -1.0;
    	for(Turtle myTurtle : getActiveTurtles()) {
    		EXPR = getCommandValue(exprCommand, myVariables, myTurtle);
    	}
	return Math.log(EXPR);
    }

}