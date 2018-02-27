package interpreter;

/**
 * returns arctangent of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class ArcTangentCommand implements Command{

    	double DEGREES;
	protected ArcTangentCommand(Command degrees) {
		DEGREES = degrees.execute();
	}
	@Override
	public double execute() {
	    	return Math.toDegrees(Math.atan(Math.toRadians(DEGREES)));
	}
	public int getNumArgs() {
		return 1;
	}
}