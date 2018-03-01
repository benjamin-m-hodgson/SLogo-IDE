package interpreter;

/**
 * reports the number pi
 * 
 * @author Benjamin Hodgson
 * @date 2/26/18
 *
 */
class PiCommand extends Command{

    @Override
    protected double execute() {
	return Math.PI;
    }
}
