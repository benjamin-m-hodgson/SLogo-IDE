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

    protected RandomCommand(Command max,Map<String, Double> variables) {
	maxCommand = max;
	myVariables = variables;
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
	double MAX = getCommandValue(maxCommand, myVariables);
	Random randGenerator = new Random();
	return randGenerator.nextDouble() * MAX;
    }
}