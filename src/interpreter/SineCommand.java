package interpreter;

/**
 * returns sine of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class SineCommand implements Command{

    	double DEGREES;
	protected SineCommand(Command degrees) {
		DEGREES = degrees.execute();
	}
	@Override
	public double execute() {
	    	return Math.sin(Math.toRadians(DEGREES));
	}
	public int getNumArgs() {
		return 1;
	}
}