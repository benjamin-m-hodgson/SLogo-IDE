package interpreter;

public class BadFormatException extends Exception {
	public BadFormatException(String text) {
		super("BadFormatException: Unsupported syntax '" + text + "'");
	}
}
