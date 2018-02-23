package interpreter;

public class CommandTreeReader {
	CommandFactory myCommandFactory;
	public CommandTreeReader(){
		myCommandFactory = new CommandFactory();
	}
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
	private int checkDoubleChildren(CommandNode root) {
		
	}
	protected Command compressTree(CommandNode root) {
		int numArgs = root.getNumArgs();
		for(CommandNode k: root.getChildren()) {
			if(root.isDouble) {
				return myCommandFactory.makeDoubleCommand(root.getInfo());
			}
			else {
				
			}
		}
	}
}
