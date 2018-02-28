package interpreter;

/**
 * returns 1 if the value of expr1 is strictly greater than the value of expr2, otherwise 0
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class GreaterThanCommand implements Command{
    	private final double TRUE = 1;
    	private final double FALSE = 0;
    	Command expr1Command;
    Command expr2Command;
	protected GreaterThanCommand(Command expr1, Command expr2) {
		expr1Command = expr1;
		expr2Command = expr2;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double EXPR1 = expr1Command.execute();
		double EXPR2 = expr2Command.execute();
		if (EXPR1 > EXPR2) {
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
