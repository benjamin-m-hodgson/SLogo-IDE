package interpreter;

 class StringCommand extends Command{
	private String myString;
	protected StringCommand(String argument) {
		myString = argument;
	}
	
	protected String getString() {
		return myString;
	}
	
	@Override
	public double execute() throws UnidentifiedCommandException{
		throw new UnidentifiedCommandException("You entered an unrecognized word in the command field.");
	}
	
	@Override
	public String toString() {
	    return getString();
	}
}
