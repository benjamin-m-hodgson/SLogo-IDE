package interpreter;

import java.util.List;
import java.util.Map;

/**
 * returns 1 if test1 or test2 are non-zero, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class OrCommand extends Command{
    private final double TRUE = 1;
    private final double FALSE = 0;
    private Command test1Command;
    private Command test2Command;
    private Map<String, Double> myVariables; 

    protected OrCommand(Command test1, Command test2, Map<String, Double> variables, List<Turtle> turtles) {
	test1Command = test1;
	test2Command = test2;
	myVariables = variables;
	setActiveTurtles(turtles);
    }
    @Override
    protected double execute() throws UnidentifiedCommandException{
    double TEST1 = 0.0;
    double TEST2 = 0.0;
    	for(Turtle myTurtle : getActiveTurtles()) {
    		TEST1 = getCommandValue(test1Command, myVariables, myTurtle);
    		TEST2  = getCommandValue(test2Command, myVariables, myTurtle);
    	}
	
	if (TEST1 != FALSE || TEST2 != FALSE) {
	    return TRUE;
	}
	else {
	    return FALSE;
	}
    }
}
