package interpreter;

public class UnidentifiedCommandException extends Exception {
	protected UnidentifiedCommandException(String text) {
		super("UnidentifiedCommandException "+ text);
	}
}
