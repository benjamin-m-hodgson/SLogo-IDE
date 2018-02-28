package interpreter;

/**
 * returns arctangent of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class ArcTangentCommand implements Command{

	Command degreesCommand;
	protected ArcTangentCommand(Command degrees) {
		degreesCommand = degrees;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double DEGREES = degreesCommand.execute();
	    	return Math.toDegrees(Math.atan(Math.toRadians(DEGREES)));
	}
}