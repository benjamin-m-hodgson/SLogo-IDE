package interpreter;

public class MissingInformationException extends Exception {
	public MissingInformationException(String problematicCommand) {
		super("MissingInformationException "+problematicCommand);
	}
}
