package interpreter;

public class UnidentifiedCommandException extends Exception {
	public UnidentifiedCommandException(String text) {
		super("UnidentifiedCommandException "+ text);
	}
}
