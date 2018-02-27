package interpreter;

/**
 * returns base raised to the power of the exponent
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class PowerCommand implements Command{

    	double BASE;
    	double POWER;
	protected PowerCommand(Command base, Command power) {
		BASE = base.execute();
		POWER = power.execute();
	}
	@Override
	public double execute() {
		return Math.pow(BASE, POWER);
	}
	public int getNumArgs() {
		return 2;
	}
}
