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
    	double TEST;
	protected NotCommand(Command test) {
		TEST = test.execute();
	}
	@Override
	public double execute() {
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
