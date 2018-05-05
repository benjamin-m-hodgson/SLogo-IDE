/**
 * Representative of bracketed chunks of control flow commands can take an unlimited number of commands. 
 * Bracket nodes can have an unlimited number of children, and therefore they will always be complete (i.e. will never have too few or too many arguments), 
 * 		hence the constant "true" return value. 
 */

package interpreter;

class BracketCommandNode extends CommandNode {
	
	protected BracketCommandNode(String commandType, Turtle allTurtles, Turtle activeTurtles) {
		super(commandType, allTurtles, activeTurtles);
	}

	@Override
	protected boolean isComplete() {
		return true; 
	}

}