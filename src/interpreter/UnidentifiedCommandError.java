package interpreter;

public class UnidentifiedCommandError extends Error{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected UnidentifiedCommandError(String text) {
		super("UnidentifiedCommandException "+ text);
	}
}
