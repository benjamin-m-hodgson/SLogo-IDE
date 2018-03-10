package interpreter;

 
 /**
  * Special type of Command that holds a String, rather than a double return value. Used
  * in loops as well as variables to allow the tree to build with string elements and then
  * trust the individual Command classes to parse the unidentified strings as necessary.
 * @author Sarahbland
 *
 */
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
