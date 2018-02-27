package interpreter;

/**
 * returns 1 if test1 or test2 are non-zero, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class NotCommand implements Command{
    	private final double TRUE = 1;
    	private final double FALSE = 0;
    	double EXPR1;
	protected NotCommand(Command expr1) {
		EXPR1 = expr1.execute();
	}
	@Override
	public double execute() {
		if (EXPR1 == FALSE) {
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
