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
    	double TEST1;
    	double TEST2;
	protected AndCommand(Command test1, Command test2) {
		TEST1 = test1.execute();
		TEST2 = test2.execute();
	}
	@Override
	public double execute() {
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
