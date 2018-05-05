/**
 * 
 * The implementation I envision for ControlFlowParser subclasses is that the CommandNode representing the control flow statement will have 1 or more child nodes 
 * 		with the same identifier (here, the DEFAULT_BRACKETNODE_INFO identifier that is defined in the ControlFlowParser superclass). 
 * 		Those nodes will be able to have an unlimited number of children, 
 * 		representing the fact that there is no limit to the number of commands in the brackets of control flow statements. 
 * By creating a special CommandNode with an unlimited number of children, Command objects need not do additional String parsing within their execute method. 
 * 
 * Note that the parsing process for if commands encapsulated by this populateChildren method does not take into account nested control flow commands; 
 * ideally, I would have used a stack to ensure correct bracket-matching, but the focus of this masterpiece was not parsing improvements.
 * 
 * Please read the CommandNode comments next!
 * 
 */

package interpreter;

import java.util.Arrays;

class IfParser extends ControlFlowParser {

	protected IfParser(CommandNode ctrlFlowNode, int ctrlFlowStartIdx, String[] tokenizedUserInput,
			Turtle allTurtles, Turtle activeTurtles, CommandTreeBuilder builder) {
		super(ctrlFlowNode, ctrlFlowStartIdx, tokenizedUserInput, allTurtles, activeTurtles, builder);
	}

	@Override
	int populateChildren() throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		int ifExprEndSearch = myStartIdx; 
		
		while (! myUserInput[ifExprEndSearch].equals(DEFAULT_BRACKET_START_IDENTIFIER)) {
			ifExprEndSearch++; 
		} // ifExprEndSearch is now at "["
		String[] ifExpr = Arrays.copyOfRange(myUserInput, myStartIdx+1, ifExprEndSearch);
		CommandNode ifExprNode = new CommandNodeFactory(ifExpr[0], myAllTurtles, myActiveTurtles).findAppropriateCommandNode();
		myBuilder.createAndSetChildren(myAllTurtles, myActiveTurtles, ifExprNode, ifExpr, 0, false); 
		myNode.addChild(ifExprNode);

		int ifBodyEndSearch = ifExprEndSearch; 
		while (! myUserInput[ifBodyEndSearch].equals(DEFAULT_BRACKET_END_IDENTIFIER)) {
			ifBodyEndSearch++; 
		} // ifBodyEndSearch is now at "]"
		String[] ifBody = Arrays.copyOfRange(myUserInput, ifExprEndSearch+1, ifBodyEndSearch);
		CommandNode ifBodyNode = new CommandNodeFactory(DEFAULT_BRACKETNODE_INFO, myAllTurtles, myActiveTurtles).findAppropriateCommandNode(); 
		myBuilder.createAndSetChildren(myAllTurtles, myActiveTurtles, ifBodyNode, ifBody, 0, false);
		myNode.addChild(ifBodyNode);
		
		return ifBodyEndSearch+1;
	}

}