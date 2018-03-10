package interpreter;

public class TurtleNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TurtleNotFoundException(String invalidTurtleName) {
		super("A turtle with the name "+invalidTurtleName+" does not exist");
	}
}
