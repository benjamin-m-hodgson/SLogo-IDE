package interpreter;


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

    protected RandomCommand(Command max,Map<String, Double> variables, Turtle activeTurtles) {
	maxCommand = max;
	myVariables = variables;
	setActiveTurtles(activeTurtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    	Random randGenerator = new Random();
    	double MAX = getCommandValue(maxCommand, myVariables, getActiveTurtles().toSingleTurtle());
     getActiveTurtles().executeSequentially(myTurtle -> {
    	 	try {
    		getCommandValue(maxCommand, myVariables, myTurtle);
    	 	}
    		catch(UnidentifiedCommandException e) {
    			throw new UnidentifiedCommandError("Improper # arguments");
    		}
    	});
    	return randGenerator.nextDouble() * MAX;
	
	
    }
}