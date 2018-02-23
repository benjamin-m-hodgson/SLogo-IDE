package interpreter;

public class MissingInformationException extends Exception {
	public MissingInformationException(String propertiesName, String commandType) {
		super("Missing information about "+propertiesName+" for "+commandType+". "
				+ "Cannot proceed with making Command.");
	}
}
