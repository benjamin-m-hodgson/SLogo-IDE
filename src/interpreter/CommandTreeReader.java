package interpreter;
import java.util.ArrayList;

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
	protected CommandTreeReader(){
		myCommandFactory = new CommandFactory();
	}
	/**
	 * Error checks to make sure that the tree that was constructed is complete (all commands, even concatenated commands,
	 * have the proper number of arguments)
	 * @param root is root of CommandNode tree
	 * @return true if the tree is complete
	 */
	private boolean treeIsComplete(CommandNode root) {
		if(root.getIsDouble()) {
			return true;
		}
		int completedChildren = 0;
		for (CommandNode n: root.getChildren()) {
			if(treeIsComplete(n)) {
				completedChildren++;
			}
		}
		System.out.println("pu numargs: " + root.getNumArgs());
		return completedChildren==root.getNumArgs();
	}
	/**
	 * Reads a CommandTree (passed in the form of its root node)
	 * @param root
	 * @return
	 */
	protected double readAndExecute(CommandNode root) {
		if(!treeIsComplete(root)) {
			throw new IllegalArgumentException("One or more of your commands does not have the proper number of arguments");
		}
		Command compressedCommand = compressTree(root);
		return compressedCommand.execute();	
	}
	/**
	 * Compressed the CommandNodeTree into a single Command with Command arguments (which, in turn, may have Command
	 * arguments, etc.) that can be executed as part of the Command Queue
	 * @param root
	 * @return command that when executed will be entire tree
	 */
	private Command compressTree(CommandNode root) {
		ArrayList<Command> args = new ArrayList<>();
		if(root.getIsDouble()) {
			return myCommandFactory.makeDoubleCommand(root.getInfo());
		}
		//TODO: StringCommand
		for(CommandNode k: root.getChildren()) {
			args.add(compressTree(k));
		}
		return myCommandFactory.makeCommand(root.getInfo(), args, root.getTurtle());
	}
//	public static void main(String[] args) {
//		Turtle turtle = new Turtle();
//		CommandNode forty = new CommandNode("40", turtle);
//		CommandNode forward = new CommandNode("Forward", 1, forty, turtle);
//		CommandNode thirty = new CommandNode("30", turtle);
//		ArrayList<CommandNode> testKids = new ArrayList<>();
//		testKids.add(forward);
//		testKids.add(thirty);
//		CommandNode test = new CommandNode("SetPosition", 2, testKids, turtle);
//		CommandTreeReader reader = new CommandTreeReader();
//		double testVal = reader.readAndExecute(test);
//		System.out.println(testVal);
//	}
}
