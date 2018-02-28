package interpreter;

/**
 * returns 1 if test is 0 and 0 if test is non-zero
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class NotCommand implements Command{
    	private final double TRUE = 1;
    	private final double FALSE = 0;
    	Command testCommand;
	protected NotCommand(Command test) {
		testCommand = test;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double TEST = testCommand.execute();
		if (TEST == FALSE) {
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
