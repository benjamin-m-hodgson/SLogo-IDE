package interpreter;

public class MissingInformationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingInformationException(String problematicCommand) {
		super("MissingInformationException "+problematicCommand);
	}
}
