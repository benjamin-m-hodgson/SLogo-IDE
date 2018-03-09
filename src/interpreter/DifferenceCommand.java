package interpreter;


import java.util.Map;

/**
 * returns difference of the values of expr1 and expr2, expr1 - expr2
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
 class DifferenceCommand extends Command{

	private Command expr1Command;
    	private Command expr2Command;
    	private Map<String, Double> myVariables; 
    	
	protected DifferenceCommand(Command expr1, Command expr2, Map<String, Double> variables, Turtle turtles) {
		setActiveTurtles(turtles);
		expr1Command = expr1;
		expr2Command = expr2;
		myVariables = variables; 
	}
	
	@Override
	protected double execute() throws UnidentifiedCommandException{
		double arg1ValRet = getCommandValue(expr1Command, myVariables, getActiveTurtles().toSingleTurtle());
		double arg2ValRet = getCommandValue(expr1Command, myVariables, getActiveTurtles().toSingleTurtle());
		
		getActiveTurtles().executeSequentially(myTurtle ->{
			try {
			getCommandValue(expr1Command, myVariables, myTurtle); 
			getCommandValue(expr2Command, myVariables, myTurtle); 
			}
			catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
		});
		return arg1ValRet - arg2ValRet;
	}

}