package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns remainder on dividing the values of expr1 by expr2
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class RemainderCommand extends Command{

	private Command expr1Command;
	private Command expr2Command;
	private Map<String, Double> myVariables; 

	protected RemainderCommand(Command expr1, Command expr2 ,Map<String, Double> variables, List<Turtle> turtles) {
		expr1Command = expr1;
		expr2Command = expr2;
		myVariables = variables;
		setActiveTurtles(turtles);
	}
	@Override
	protected double execute() throws UnidentifiedCommandException{
		double EXPR1 = -3;
		double EXPR2 = 2;
		for(Turtle myTurtle : getActiveTurtles()) {
			EXPR1 = getCommandValue(expr1Command, myVariables, myTurtle);
			EXPR2 = getCommandValue(expr2Command, myVariables, myTurtle);
		} 
		return EXPR1%EXPR2;
	}
}