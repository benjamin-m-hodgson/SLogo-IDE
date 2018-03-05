package interpreter;
import java.util.ArrayList;
import java.util.Map;

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
	
	protected CommandTreeReader(Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs){
		myCommandFactory = new CommandFactory(variables, userDefCommands, userDefCommandsNumArgs);
	}
	/**
	 * Error checks to make sure that the tree that was constructed is complete (all commands, even concatenated commands,
	 * have the proper number of arguments)
	 * @param root is root of CommandNode tree
	 * @return true if the tree is complete
	 */
	private boolean treeIsComplete(CommandNode root) throws UnidentifiedCommandException{
		//System.out.println("reading a node");
		//System.out.println("node" + root.getInfo());
//		for(int k = 0; k<root.getNumChildren(); k+=1) {
//			System.out.println("node children " + root.getChildren().get(k).getInfo());
//		}
		if(root.getIsDouble()||root.getIsString()) {
			return true;
		}
		int completedChildren = 0;
		for (CommandNode n: root.getChildren()) {
			if(treeIsComplete(n)) {
				completedChildren++;
			}
		}
		//System.out.println("completed children: " + completedChildren);
		if(completedChildren==root.getNumArgs()) {
			return true;
		}
		else {
			throw new UnidentifiedCommandException("The command: " + root.getInfo() + " does not have the proper number of arguemts.");
		}
		
	}
	
	/**
	 * Reads a CommandTree (passed in the form of its root node)
	 * @param root
	 * @return
	 */
	protected double readAndExecute(CommandNode root) throws UnidentifiedCommandException{
		if(treeIsComplete(root)) {
			Command compressedCommand = compressTree(root);
			return compressedCommand.execute();	
		}
		return -1;
	}
	
	/**
	 * Compressed the CommandNodeTree into a single Command with Command arguments (which, in turn, may have Command
	 * arguments, etc.) that can be executed as part of the Command Queue
	 * @param root
	 * @return command that when executed will be entire tree
	 */
	private Command compressTree(CommandNode root) {
		ArrayList<Command> args = new ArrayList<>();
		//System.out.println("root info" + root.getInfo());
		//System.out.println("children number" + root.getNumChildren());
		if(root.getIsDouble()) {
			return myCommandFactory.makeDoubleCommand(root.getInfo());
		}
		if(root.getIsString()) {
			return myCommandFactory.makeCommand(root.getInfo(), args, root.getTurtle());
		}
		for(CommandNode k: root.getChildren()) {
//			System.out.println("child info" + k.getInfo());
			args.add(compressTree(k));
		}
		//System.out.println("Making a command");
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
