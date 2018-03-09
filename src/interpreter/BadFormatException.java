package interpreter;

public class BadFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadFormatException(String text) {
		super("BadFormatException: Unsupported syntax '" + text + "'");
	}
}
