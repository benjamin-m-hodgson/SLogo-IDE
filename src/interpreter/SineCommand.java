package interpreter;

import java.util.List;
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

    protected SineCommand(Command degrees ,Map<String, Double> variables, List<Turtle> turtles) {
    	setActiveTurtles(turtles);
	degreesCommand = degrees;
	myVariables = variables;
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    	Double DEGREES = -1.0;
    	for(Turtle myTurtle : getActiveTurtles()) {
    		DEGREES = getCommandValue(degreesCommand, myVariables, myTurtle);
    	}
	
	return Math.sin(Math.toRadians(DEGREES));
    }
}