package commandnodetree;
import java.util.ArrayList;

import interpreter.Command;

/**
 * Class to read the CommandNode tree created to deal with concatenated commands. Has the capacity to check if the tree
 * is valid (all commands have the correct number of arguments), then compress the entire tree into a single Command that
 * can later be executed. Dependent on CommandFactory to make the correct kinds of Commands and on CommandTreeBuilder
 * to make a tree of of CommandNodes with arguments input in the correct order (i.e. first argument of corresponding
 * Command is first child of each node).
 *  
 * @author Sarahbland
 *
 */
class CommandTreeReader {
	CommandFactory myCommandFactory;
	public CommandTreeReader(){
		myCommandFactory = new CommandFactory();
	}
	/**
	 * Error checks to make sure that the tree that was constructed is complete (all commands, even concatenated commands,
	 * have the proper number of arguments)
	 * @param root is root of CommandNode tree
	 * @return true if the tree is complete
	 */
	protected boolean treeIsComplete(CommandNode root) {
		if(root.getIsDouble()) {
			return true;
		}
		int completedChildren = 0;
		for (CommandNode n: root.getChildren()) {
			if(treeIsComplete(n)) {
				completedChildren++;
			}
		}
		return completedChildren==root.getNumArgs();
	}
	/**
	 * Compressed the CommandNodeTree into a single Command with Command arguments (which, in turn, may have Command
	 * arguments, etc.) that can be executed as part of the Command Queue
	 * @param root
	 * @return
	 */
	protected Command compressTree(CommandNode root) {
		ArrayList<Command> args = new ArrayList<>();
		if(root.getIsDouble()) {
			return myCommandFactory.makeDoubleCommand(root.getInfo());
		}
		for(CommandNode k: root.getChildren()) {
			args.add(compressTree(k));
		}
		return myCommandFactory.makeCommand(root.getInfo(), args);
	}
}
