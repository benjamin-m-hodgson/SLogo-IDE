package interpreter;


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

	protected RemainderCommand(Command expr1, Command expr2 ,Map<String, Double> variables, Turtle turtles) {
		expr1Command = expr1;
		expr2Command = expr2;
		myVariables = variables;
		setActiveTurtles(turtles);
	}
	@Override
	protected double execute() throws UnidentifiedCommandException {
		double EXPR1 = getCommandValue(expr1Command, myVariables, getActiveTurtles().toSingleTurtle());
		double EXPR2 = getCommandValue(expr2Command, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle -> {
			try {
			getCommandValue(expr1Command, myVariables, myTurtle);
			getCommandValue(expr2Command, myVariables, myTurtle);
			}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
		}); 
		return EXPR1%EXPR2;
	}
}