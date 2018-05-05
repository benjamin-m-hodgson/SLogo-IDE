/**
 * 
 * (Wow another factory!!!) 
 * 
 * This class exists for the instantiation of the CommandNode subclass corresponding to the String-specified commandType passed into the class constructor. 
 * The findAppropriateCommandNode method is an embodiment of the Factory Method pattern. 
 * 
 * Note that the findAppropriateCommandNode method would also need to be able to return an IdentifiedCommandNode instance. 
 * To implement this in a way that would minimize the need for the client to explicitly indicate whether the passed-in commandType is an identified command (e.g. MoveForward), 
 * 		this would involve using PropertiesReader to determine whether the commandType inputed to the constructor is identified. 
 * 		I did not do this here, as the focus of this Factory in my masterpiece implementation is its capability to produce a BracketCommandNode. 
 */

package interpreter;

import static interpreter.ControlFlowParser.DEFAULT_BRACKETNODE_INFO; 

class CommandNodeFactory {
		
	protected String myCommandType;
	protected Turtle myAllTurtles;
	protected Turtle myActiveTurtles;

	protected CommandNodeFactory(String commandType, Turtle allTurtles, Turtle activeTurtles) {
		myCommandType = commandType;
		myAllTurtles = allTurtles;
		myActiveTurtles = activeTurtles; 
	}

	protected CommandNode findAppropriateCommandNode() throws UnidentifiedCommandException {
		if (myCommandType.equals(DEFAULT_BRACKETNODE_INFO)) {
			return new BracketCommandNode(myCommandType, myAllTurtles, myActiveTurtles);
		}
		try {
			Double.parseDouble(myCommandType);
			return new DoubleCommandNode(myCommandType, myChildren, myAllTurtles, myActiveTurtles);
		}
		catch (NumberFormatException e) {
			return new VariableCommandNode(myCommandType, myChildren, myAllTurtles, myActiveTurtles);
		}
	}
	
}