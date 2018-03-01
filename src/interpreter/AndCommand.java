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
public class AndCommand extends Command {
    Command testOneCommand;
    	Command testTwoCommand;
    	Map<String, Double> myVariables; 
    	
	protected AndCommand(Command test1, Command test2, Map<String, Double> variables) {
		testOneCommand = test1;
		testTwoCommand = test2;
		myVariables = variables; 
	}
	
	@Override
	public double execute() throws UnidentifiedCommandException{
		double arg1Val; 
		double arg2Val; 
		if (testOneCommand instanceof StringCommand) {
			arg1Val = getValueOfVar(((StringCommand)testOneCommand).getString(), myVariables);
		}
		else {
			arg1Val = testOneCommand.execute(); 
		}
		if (testTwoCommand instanceof StringCommand) {
			arg2Val = getValueOfVar(((StringCommand)testTwoCommand).getString(), myVariables);
		}
		else {
			arg2Val = testTwoCommand.execute();
		}
		return ((arg1Val > 0) && (arg2Val > 0)) ? 1.0 : 0.0; 
	}
	
	public int getNumArgs() {
		return 2;
	}
	
}
