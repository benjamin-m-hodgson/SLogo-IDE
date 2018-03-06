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
	private List<Turtle> myTurtles;
	private List<Turtle> myActiveTurtles;
	private boolean isString;

	protected CommandNode(String info, List<Turtle> turtles, List<Turtle> activeTurtles) {
		this(info, DEFAULT_NUM_ARGS, new ArrayList<CommandNode>(), turtles, activeTurtles, true);
	}

	protected CommandNode(String info, int numArgs, List<Turtle> turtles, List<Turtle> activeTurtles) { 
		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, false);
	}
	protected CommandNode(String info, int numArgs, CommandNode child, List<Turtle> turtles, List<Turtle> activeTurtles) {
		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, false);
		myChildren.add(child);
	}
	protected CommandNode(String info, int numArgs, List<Turtle> turtles, List<Turtle> activeTurtles, boolean isString) {
		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, isString); 
	}
	

	protected CommandNode(String info, int numArgs, List<CommandNode> children, List<Turtle> turtles, List<Turtle> activeTurtles, boolean isStringID) {	
		myInfo = info; 
		try {
			Double.parseDouble(info);
			isDouble = true; 
		}
		catch (NumberFormatException e) {
			isDouble = false; 
		}
		isString = isStringID;
		myNumArgs = numArgs;
		myChildren = new ArrayList<CommandNode>(); 
		myChildren.addAll(children); 
		myTurtles = turtles;
		myActiveTurtles = activeTurtles;
		
	}

	protected CommandNode(String info, int numArgs, List<CommandNode> children) {
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
		isString = false;
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
	
	public String childrenToString() {
		String s = "";
		for (int i = 0; i < myChildren.size(); i++) {
			s += myChildren.get(i).getInfo();
			s += myChildren.get(i).childrenToString(); 
			s += " ";
		}
		return s; 
	}

	protected void addChild(CommandNode child) {
		myChildren.add(child); 
	}

	protected int getNumArgs() {
		return myNumArgs; 
	}

	protected int getNumChildren() {
		return myChildren.size();
	}
	protected boolean getIsDouble() {
		return isDouble;
	}
	protected boolean getIsString() {
		return isString;
	}
	protected List<CommandNode> getChildren() {
		return myChildren;
	}
	protected String getInfo() {
		return myInfo;
	}
	protected List<Turtle> getActiveTurtles() {
		return myActiveTurtles;
	}
	protected List<Turtle> getTurtles() {
		return myTurtles;
	}

}
