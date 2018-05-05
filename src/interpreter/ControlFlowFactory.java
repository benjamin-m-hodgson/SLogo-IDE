/**
 * 
 * ControlFlowFactory returns an instance of the ControlFlowParser subclass that corresponds to the control flow command that needs to be parsed. 
 * Instantiated by CommandTreeBuilder once CommandTreeBuilder recognizes that it needs to parse a control flow command. 
 * The design makes two critical assumptions that CommandTreeBuilder will correctly pass into the constructor 
 * 		the CommandNode with the control flow statement (as a String) as the info/type attribute (per usual parsing) without any children,
 * 		and the int index of when the control flow command begins.
 * The findAppropriateParser() method represents the Factory Method pattern, returning some concretization of the ControlFlowParser abstract class. 
 * Note that ControlFlowFactory does not wrap any calls to a ControlFlowParser object's populateChildren method. This is due to the difficulties 
 * 		that my team encountered when we adopted a Chain-of-Responsibility approach. In this masterpiece implementation, I envision CommandTreeBuilder
 * 		as a Mitigator that receives the proper instance of ControlFlowParser and independently invokes that ControlFlowParser's populateChildren method.
 * 		I believe that this better manifests the Single Responsibility Principle, as a Factory should only return the required instance of something, 
 * 		and thus may cause confusion if it also wraps a call to the modification of the object it instantiates. 
 * 
 * Please read the IfParser comments next!
 * 
 */

package interpreter;

class ControlFlowFactory {
	
	private CommandNode myNode; 
	private int myStartIdx; 
	private String[] myUserInput; 
	
	private Turtle myAllTurtles; 
	private Turtle myActiveTurtles; 
	private CommandTreeBuilder myBuilder; 
	
	protected ControlFlowFactory(CommandNode ctrlFlowNode, int ctrlFlowStartIdx, String[] tokenizedUserInput, 
			Turtle allTurtles, Turtle activeTurtles, CommandTreeBuilder builder) {
		myNode = ctrlFlowNode; 
		myStartIdx = ctrlFlowStartIdx;
		myUserInput = tokenizedUserInput; 
		myAllTurtles = allTurtles;
		myActiveTurtles = activeTurtles; 
		myBuilder = builder; 
	}
	
	protected ControlFlowParser findAppropriateParser() throws UnidentifiedCommandException {
		String myType = myNode.getInfo();
		if (myType.equals("If")) {
			return new IfParser(myNode, myStartIdx, myUserInput, myAllTurtles, myActiveTurtles, myBuilder); 
		}
		else if (myType.equals("IfElse")) {
			return new IfElseParser(myNode, myStartIdx, myUserInput, myAllTurtles, myActiveTurtles, myBuilder); 
		}
		else if (myType.equals("Repeat")) {
			return new RepeatParser(myNode, myStartIdx, myUserInput, myAllTurtles, myActiveTurtles, myBuilder); 
		}
		else if (myType.equals("DoTimes")) {
			return new DoTimesParser(myNode, myStartIdx, myUserInput, myAllTurtles, myActiveTurtles, myBuilder); 
		}
		else if (myType.equals("For")) {
			return new ForParser(myNode, myStartIdx, myUserInput, myAllTurtles, myActiveTurtles, myBuilder); 
		}
		else throw new UnidentifiedCommandException(myType);
 	}

}