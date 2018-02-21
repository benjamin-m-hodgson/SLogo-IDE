package interpreter;

public class MissingInformationException extends Exception {
	public MissingInformationException(String commandType) {
		super("Missing information about # args for "+commandType+". Cannot proceed with making Command.");
	}
}
