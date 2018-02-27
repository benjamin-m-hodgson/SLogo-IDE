package interpreter;

/**
 * returns negative of the values of expr
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class MinusCommand implements Command{

    	double EXPR;
	protected MinusCommand(Command expr) {
		EXPR = expr.execute();
	}
	@Override
	public double execute() {
		return -EXPR;
	}
	public int getNumArgs() {
		return 1;
	}
}