package interpreter;

public class CommandNode {
	private String myInfo;
	private boolean imComplete; 
	private int myNumArgs; 
	private CommandNode myLeft; 
	private CommandNode myRight; 

	public CommandNode(String commandType, int numArgs, CommandNode l, CommandNode r) { 
		myInfo = commandType; 
		myNumArgs = numArgs;
		myLeft = l; 
		myRight = r;
	}

}
