package interpreter;

public class IncompleteCommandException extends Exception{
	public IncompleteCommandException() {
		super("One or more commands does not have the proper number of arguments");
	}
}
