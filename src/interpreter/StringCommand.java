package interpreter;

 
 class StringCommand extends Command{
	public static final int DEFAULT_STRING_ID = Integer.MAX_VALUE;
	private String myString;
	protected StringCommand(String argument) {
		myString = argument;
	}
	
	protected String getString() {
		return myString;
	}
	
	@Override
	public double execute(){
		return DEFAULT_STRING_ID;
	}
	
	@Override
	public String toString() {
	    return getString();
	}
}
