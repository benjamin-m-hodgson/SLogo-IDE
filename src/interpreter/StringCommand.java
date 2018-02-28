package interpreter;

public class StringCommand implements Command{
	String myString;
	protected StringCommand(String argument) {
		myString = argument;
	}
	protected String getString() {
		return myString;
	}
	public double execute() throws UnidentifiedCommandException{
		throw new UnidentifiedCommandException("You entered an unrecognized word in the command field.");
	}
}
