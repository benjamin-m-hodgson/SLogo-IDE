package interpreter;

import java.util.Random;

/**
 * returns random non-negative number strictly less than max
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class RandomCommand implements Command{

    Command maxCommand;
	protected RandomCommand(Command max) {
		maxCommand = max;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double MAX = maxCommand.execute();
	    	Random randGenerator = new Random();
	    	return randGenerator.nextDouble() * MAX;
	}
	public int getNumArgs() {
		return 1;
	}
}