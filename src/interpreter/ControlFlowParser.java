/**
 * 
 * For my code masterpiece, I decided to refactor the code used to parse control flow statements. 
 * In the original project, parsing statements such as If & Repeat was done in disparate, statement-specific methods in the CommandTreeBuilder class -- 
 * this severely limits the extendability of control flow statements, and made the CommandTreeBuilder unmanageably large. 
 * 
 * My new implementation formalizes the control flow statement-parsing process through this ControlFlowParser abstract class, with subclasses like IfParser, DoTimesParser, etc. 
 * While incrementing through the user input word-by-word, the CommandTreeBuilder can identify when the user input word is a control flow command. 
 * 	The CommandTreeBuilder will then declare a CommandNode with the control flow statement (as a String) as the info/type attribute per usual, 
 * 		but then pass this node as one of the args into an instantiation of a ControlFlowParser subclass 
 * 		so that the ControlFlowParser can appropriately parse the control flow statement with its populateChildren method. 
 * 	The populateChildren method returns an int; this is the index at which the CommandTreeBuilder may resume parsing 
 * 		(i.e. the index after the conclusion of the control flow statement).
 * 
 * The ControlFlowParser abstract class is better-designed than the original project in that it allows control flow parsing to be a "black box" to the CommandTreeBuilder. 
 * Rather than using CommandTreeBuilder methods to represent the different ways to parse commands, 
 * 		the different implementations of the populateChildren method in ControlFlowParser subclasses do this. 
 * 
 * I envision ControlFlowFactory to be the object that is instantiated by CommandTreeBuilder once CommandTreeBuilder recognizes that it needs to parse a control flow command. 
 * 		ControlFlowFactory returns an instance of the ControlFlowParser subclass that corresponds to the control flow statement that needs to be parsed. 
 * 
 * For a sense of continuity, please read the ControlFlowFactory comments next!
 * 
 */

package interpreter;

abstract class ControlFlowParser {
	
	public static final String DEFAULT_BRACKET_START_IDENTIFIER = "[";
	public static final String DEFAULT_BRACKET_END_IDENTIFIER = "]";
	public static final String DEFAULT_BRACKETNODE_INFO = "[ ]";
	
	protected CommandNode myNode; 
	protected int myStartIdx; 
	protected String[] myUserInput; 
	
	protected Turtle myAllTurtles; 
	protected Turtle myActiveTurtles; 
	protected CommandTreeBuilder myBuilder;
	
	protected ControlFlowParser(CommandNode ctrlFlowNode, int ctrlFlowStartIdx, String[] tokenizedUserInput, Turtle allTurtles, Turtle activeTurtles, CommandTreeBuilder builder) {
		myNode = ctrlFlowNode; 
		myStartIdx = ctrlFlowStartIdx;
		myUserInput = tokenizedUserInput; 
		myAllTurtles = allTurtles;
		myActiveTurtles = activeTurtles; 
		myBuilder = builder;
	}
	
	abstract int populateChildren() throws BadFormatException, UnidentifiedCommandException, MissingInformationException; 
	
}