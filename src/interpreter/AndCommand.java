package interpreter;

import java.util.Map;

/**
 * returns 1 if test1 and test2 are non-zero, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @author Susie Choi 
 * @date 2/26/18
 *
 */
class AndCommand extends Command {
    private Command testOneCommand;
    private Command testTwoCommand;
    private Map<String, Double> myVariables; 

    protected AndCommand(Command test1, Command test2, Map<String, Double> variables) {
	testOneCommand = test1;
	testTwoCommand = test2;
	myVariables = variables; 
    }

    @Override
    protected double execute() throws UnidentifiedCommandException{
	double arg1Val = getCommandValue(testOneCommand, myVariables);
	double arg2Val = getCommandValue(testTwoCommand, myVariables); 
	return ((arg1Val > 0) && (arg2Val > 0)) ? 1.0 : 0.0; 
    }	
}
