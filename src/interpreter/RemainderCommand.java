package interpreter;

/**
 * returns remainder on dividing the values of expr1 by expr2
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class RemainderCommand implements Command{

	Command expr1Command;
	Command expr2Command;
	protected RemainderCommand(Command expr1, Command expr2) {
		expr1Command = expr1;
		expr2Command = expr2;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double EXPR1 = expr1Command.execute();
		double EXPR2 = expr2Command.execute();
		return EXPR1%EXPR2;
	}
	public int getNumArgs() {
		return 2;
	}
}