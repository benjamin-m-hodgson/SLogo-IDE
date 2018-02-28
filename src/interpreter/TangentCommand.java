package interpreter;

/**
 * returns tangent of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class TangentCommand implements Command{

    	Command degreesCommand;
	protected TangentCommand(Command degrees) {
		degreesCommand = degrees;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double DEGREES = degreesCommand.execute();
	    	return Math.tan(Math.toRadians(DEGREES));
	}
	public int getNumArgs() {
		return 1;
	}
}