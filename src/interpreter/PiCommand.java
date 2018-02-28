package interpreter;

/**
 * reports the number pi
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
public class PiCommand implements Command{

	@Override
	public double execute() {
		return Math.PI;
	}
}
