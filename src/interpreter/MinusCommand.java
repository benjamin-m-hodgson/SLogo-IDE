package interpreter;

/**
 * returns negative of the values of expr
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class MinusCommand implements Command{

    Command exprCommand;
	protected MinusCommand(Command expr) {
		exprCommand = expr;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double EXPR = exprCommand.execute();
		return -EXPR;
	}
	public int getNumArgs() {
		return 1;
	}
}