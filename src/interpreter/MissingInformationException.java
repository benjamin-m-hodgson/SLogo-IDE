package interpreter;

/**
 * Exception thrown when a command requires information (like a file) that is not available
 * @author Susie Choi
 *
 */
public class MissingInformationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingInformationException(String problematicCommand) {
		super("MissingInformationException "+problematicCommand);
	}
}
