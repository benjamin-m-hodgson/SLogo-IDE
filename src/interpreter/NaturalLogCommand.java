package interpreter;

/**
 * returns natural log of expr
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class NaturalLogCommand implements Command{

    	Command exprCommand;
	protected NaturalLogCommand(Command expr) {
		exprCommand = expr;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		Double EXPR = exprCommand.execute();
	    	return Math.log(EXPR);
	}
	public int getNumArgs() {
		return 1;
	}
}