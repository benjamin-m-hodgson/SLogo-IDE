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
		return null; //TODO: fix this!! HAS TO DO THIS APPARENTLY YIKES YIKES YIKES
	}
	
	@Override
	public String toString() {
	    return getString();
	}
}
