package interpreter;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * returns random non-negative number strictly less than max
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class RandomCommand extends Command{

    private Command maxCommand;
    private Map<String, Double> myVariables; 

    protected RandomCommand(Command max,Map<String, Double> variables, List<Turtle> activeTurtles) {
	maxCommand = max;
	myVariables = variables;
	setActiveTurtles(activeTurtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    	Random randGenerator = new Random();
    	double returnVal = -1.0;
    	double MAX = -1.0;
    	for( Turtle myTurtle : getActiveTurtles()) {
    		MAX = getCommandValue(maxCommand, myVariables, myTurtle);
    		returnVal = randGenerator.nextDouble()*MAX;
    	}
    	return randGenerator.nextDouble() * MAX;
	
	
    }
}