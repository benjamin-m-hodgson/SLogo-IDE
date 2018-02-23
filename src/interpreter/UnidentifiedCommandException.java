package interpreter;

public class UnidentifiedCommandException extends Exception {
	public UnidentifiedCommandException(String text) {
		super(text+" was not identified as a valid Command");
	}
}
