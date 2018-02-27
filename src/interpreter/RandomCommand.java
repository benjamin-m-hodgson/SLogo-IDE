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

    	double MAX;
	protected RandomCommand(Command max) {
		MAX = max.execute();
	}
	@Override
	public double execute() {
	    	Random randGenerator = new Random();
	    	return randGenerator.nextDouble() * MAX;
	}
	public int getNumArgs() {
		return 1;
	}
}