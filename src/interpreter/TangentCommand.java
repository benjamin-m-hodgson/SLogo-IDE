package interpreter;


import java.util.Map;

/**
 * returns tangent of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class TangentCommand extends Command{

    private Command degreesCommand;
    private Map<String, Double> myVariables; 

    protected TangentCommand(Command degrees ,Map<String, Double> variables, Turtle turtles) {
	degreesCommand = degrees;
	myVariables = variables;
	setActiveTurtles(turtles);

    }
    @Override
    protected double execute() throws UnidentifiedCommandException {
    	double DEGREES = getCommandValue(degreesCommand, myVariables, getActiveTurtles().toSingleTurtle());
    getActiveTurtles().executeSequentially(myTurtle -> {
    		try {
    		getCommandValue(degreesCommand, myVariables, myTurtle);
    		}
    		catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
    	});
	return Math.tan(Math.toRadians(DEGREES));
    }

}