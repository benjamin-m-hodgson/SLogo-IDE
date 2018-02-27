package interpreter;

import java.util.ArrayList;
import java.util.List;

class CommandNode {

	public static final int DEFAULT_NUM_ARGS = 0; 

	private String myInfo;
	private boolean isDouble; 
	private int myNumArgs; 
	//private Command myCommand; 
	private List<CommandNode> myChildren; 
	private Turtle myTurtle;
	private boolean isString;

	public CommandNode(String info, boolean isString) {
		this(info, DEFAULT_NUM_ARGS, new ArrayList<CommandNode>(), new Turtle(), true);
	}
	public CommandNode(String info, Turtle turtle) {
		this(info, DEFAULT_NUM_ARGS, turtle);
	}

	public CommandNode(String info, int numArgs, Turtle turtle) { 
		this(info, numArgs, new ArrayList<CommandNode>(), turtle, false);
	}
	public CommandNode(String info, int numArgs, CommandNode child, Turtle turtle) {
		this(info, numArgs, new ArrayList<CommandNode>(), turtle, false);
		myChildren.add(child);
	}

	public CommandNode(String info, int numArgs, List<CommandNode> children, Turtle turtle, boolean isString) {	
		myInfo = info; 
		try {
			Double.parseDouble(info);
			isDouble = true; 
		}
		catch (NumberFormatException e) {
			isDouble = false; 
		}
		myNumArgs = numArgs;
		myChildren = new ArrayList<CommandNode>(); 
		myChildren.addAll(children); 
		myTurtle = turtle;
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
		myChildren = children; 
	}

	@Override
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
	public boolean getIsString() {
		return isString;
	}
	public List<CommandNode> getChildren() {
		return myChildren;
	}
	public String getInfo() {
		return myInfo;
	}
	public Turtle getTurtle() {
		return myTurtle;
	}

}
