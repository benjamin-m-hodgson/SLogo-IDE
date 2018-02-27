package interpreter;

/**
 * returns natural log of expr
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class NaturalLogCommand implements Command{

    	double EXPR;
	protected NaturalLogCommand(Command expr) {
		EXPR = expr.execute();
	}
	@Override
	public double execute() {
	    	return Math.log(EXPR);
	}
	public int getNumArgs() {
		return 1;
	}
}