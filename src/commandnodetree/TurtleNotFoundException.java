package commandnodetree;

public class TurtleNotFoundException extends Exception {
	public TurtleNotFoundException(String invalidTurtleName) {
		super("A turtle with the name "+invalidTurtleName+" does not exist");
	}
}
