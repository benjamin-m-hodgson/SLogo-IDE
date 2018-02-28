package interpreter;

/**
 * returns 1 if test1 and test2 are non-zero, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class AndCommand implements Command{
    	private final double TRUE = 1;
    	private final double FALSE = 0;
    Command testOneCommand;
    	Command testTwoCommand;
	protected AndCommand(Command test1, Command test2) {
		testOneCommand = test1;
		testTwoCommand = test2;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double TEST1 = testOneCommand.execute();
		double TEST2 = testTwoCommand.execute();
		if (TEST1 != FALSE && TEST2 != FALSE) {
		    return TRUE;
		}
		else {
		    return FALSE;
		}
	}
	public int getNumArgs() {
		return 2;
	}
}
