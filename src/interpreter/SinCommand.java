package interpreter;

import java.util.Random;

/**
 * returns random non-negative number strictly less than max
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class SinCommand implements Command{

    	double DEGREES;
	protected SinCommand(Command degrees) {
		DEGREES = degrees.execute();
	}
	@Override
	public double execute() {
	    	return Math.sin(DEGREES);
	}
	public int getNumArgs() {
		return 1;
	}
}