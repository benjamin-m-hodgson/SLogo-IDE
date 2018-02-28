package interpreter;

/**
 * returns base raised to the power of the exponent
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class PowerCommand implements Command{

    	Command baseCommand;
    	Command powerCommand;
	protected PowerCommand(Command base, Command power) {
		baseCommand = base;
		powerCommand = power;
	}
	@Override
	public double execute() throws UnidentifiedCommandException{
		double BASE = baseCommand.execute();
		double POWER = powerCommand.execute();
		return Math.pow(BASE, POWER);
	}
	public int getNumArgs() {
		return 2;
	}
}
