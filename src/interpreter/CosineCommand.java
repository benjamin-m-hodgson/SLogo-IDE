package interpreter;

/**
 * returns cosine of degrees
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class CosineCommand implements Command{

    	Command degreesCommand;
	protected CosineCommand(Command degrees) {
		degreesCommand = degrees;
	}
	@Override
	public double execute() throws UnidentifiedCommandException {
	    double DEGREES = degreesCommand.execute();
		return Math.cos(Math.toRadians(DEGREES));
	}
	public int getNumArgs() {
		return 1;
	}
}