package interpreter;

/**
 * Exception thrown in case that a user inputs an unrecognized command
 */

public class UnidentifiedCommandException extends Exception{

	private static final long serialVersionUID = 1L;

	protected UnidentifiedCommandException(String text) {
		super("UnidentifiedCommandException "+ text);
	}
}
