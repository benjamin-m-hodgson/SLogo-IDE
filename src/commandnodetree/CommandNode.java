package commandnodetree;

import java.util.ArrayList;
import java.util.List;

import interpreter.Command;

class CommandNode {

	public static final int DEFAULT_NUM_ARGS = 0; 

	private String myInfo;
	private boolean isDouble; 
	private int myNumArgs; 
	private Command myCommand; 
	private List<CommandNode> myChildren; 

	public CommandNode(String info) {
		this(info, DEFAULT_NUM_ARGS);
	}

	public CommandNode(String info, int numArgs) { 
		this(info, numArgs, new ArrayList<CommandNode>());
	}

	public CommandNode(String info, int numArgs, CommandNode child) {	
		myInfo = info; 
		try {
			Integer.parseInt(info);
			isDouble = true; 
		}
		catch (NumberFormatException e) {
			isDouble = false; 
		}
		myNumArgs = numArgs;
		myChildren = new ArrayList<CommandNode>(); 
		myChildren.add(child); 
	}

	public CommandNode(String info, int numArgs, List<CommandNode> children) {
		myInfo = info; 
		try {
			Integer.parseInt(info);
			isDouble = true; 
		}
		catch (NumberFormatException e) {
			isDouble = false; 
		}
		myNumArgs = numArgs;
		myChildren = (ArrayList<CommandNode>) children; 
	}

	public String toString() {
		String s = "CommandNode holding info "+myInfo;
		if (myChildren.size() > 0) {
			s+= " has "+myChildren.size()+" children:\n";
			for (int i = 0; i < myChildren.size(); i ++) {
				s += "#"+(i+1)+". "+myChildren.get(i).toString()+"\n";
			}
		}
		else {
			s+= " has no children.";
		}
		return s; 
	}

	public void addChild(CommandNode child) {
		myChildren.add(child); 
	}

	public int getNumArgs() {
		return myNumArgs; 
	}

	public int getNumChildren() {
		return myChildren.size();
	}
	public boolean getIsDouble() {
		return isDouble;
	}
	public List<CommandNode> getChildren() {
		return myChildren;
	}
	public String getInfo() {
		return myInfo;
	}

}
