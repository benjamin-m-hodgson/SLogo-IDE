package interpreter;


import java.util.Map;

/**
 * returns 1 if test is 0 and 0 if test is non-zero
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class NotCommand extends Command{
    	private final double TRUE = 1;
    	private final double FALSE = 0;
    	private Command testCommand;
    	private Map<String, Double> myVariables; 

    	
	protected NotCommand(Command test, Map<String, Double> variables, Turtle turtles) {
		testCommand = test;
		setActiveTurtles(turtles);
	}
	@Override
	protected double execute() throws UnidentifiedCommandException {
		double TEST = getCommandValue(testCommand, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle -> {
			try {
			getCommandValue(testCommand, myVariables, myTurtle);
			}
			catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
		});
		if (TEST == FALSE) {
		    return TRUE;
		}
		else {
		    return FALSE;
		}
	}
}
