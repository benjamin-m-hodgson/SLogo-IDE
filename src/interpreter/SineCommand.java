package interpreter;


import java.util.Map;

/**
 * returns sine of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class SineCommand extends Command{

    private Command degreesCommand;
    private Map<String, Double> myVariables; 

    protected SineCommand(Command degrees ,Map<String, Double> variables, Turtle turtles) {
    	setActiveTurtles(turtles);
	degreesCommand = degrees;
	myVariables = variables;
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    	Double DEGREES = getCommandValue(degreesCommand, myVariables, getActiveTurtles());
    	getActiveTurtles().executeSequentially(myTurtle -> {
    		try {
    		getCommandValue(degreesCommand, myVariables, myTurtle);
    		}
    		catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
		}
    	});
	
	return Math.sin(Math.toRadians(DEGREES));
    }
}