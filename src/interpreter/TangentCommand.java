package interpreter;

/**
 * returns tangent of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class TangentCommand implements Command{

    	double DEGREES;
	protected TangentCommand(Command degrees) {
		DEGREES = degrees.execute();
	}
	@Override
	public double execute() {
	    	return Math.tan(Math.toRadians(DEGREES));
	}
	public int getNumArgs() {
		return 1;
	}
}