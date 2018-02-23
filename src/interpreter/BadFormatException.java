package interpreter;

public class BadFormatException extends Exception {
	public BadFormatException(String text) {
		super(text+" is not formated as a recognizable user input.");
	}
}
