package interpreter;

/**
 * Exception created in the event the user attempts to execute a command with unsupported
 * syntax
 * @author Susie Choi
 *
 */
public class BadFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadFormatException(String text) {
		super("BadFormatException: Unsupported syntax '" + text + "'");
	}
}
