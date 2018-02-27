package interpreter;

/**
 * returns cosine of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class CosineCommand implements Command{

    	double DEGREES;
	protected CosineCommand(Command degrees) {
		DEGREES = degrees.execute();
	}
	@Override
	public double execute() {
	    	return Math.cos(Math.toRadians(DEGREES));
	}
	public int getNumArgs() {
		return 1;
	}
}