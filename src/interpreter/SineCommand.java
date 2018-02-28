package interpreter;

/**
 * returns sine of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class SineCommand implements Command{

    Command degreesCommand;
	protected SineCommand(Command degrees) {
		degreesCommand = degrees;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double DEGREES = degreesCommand.execute();
	    	return Math.sin(Math.toRadians(DEGREES));
	}
	public int getNumArgs() {
		return 1;
	}
}