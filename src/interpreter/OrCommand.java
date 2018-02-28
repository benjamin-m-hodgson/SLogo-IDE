package interpreter;

/**
 * returns 1 if test1 or test2 are non-zero, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class OrCommand implements Command{
    	private final double TRUE = 1;
    	private final double FALSE = 0;
    	Command test1Command;
    	Command test2Command;
	protected OrCommand(Command test1, Command test2) {
		test1Command = test1;
		test2Command = test2;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double TEST1 = test1Command.execute();
		double TEST2 = test2Command.execute();
		if (TEST1 != FALSE || TEST2 != FALSE) {
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
