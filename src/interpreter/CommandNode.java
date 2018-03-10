package interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susie Choi
 * minor modifications by Sarah Bland
 * 
 * Represents an element of a user-inputed command (e.g. Command name, argument). 
 * Used in constructing a CommandTree to assess the completeness of commands in the parsing process. 
 *
 */
class CommandNode {

	public static final int DEFAULT_NUM_ARGS = 0; 

	private String myInfo;
	private boolean isDouble; 
	private int myNumArgs; 
	private List<CommandNode> myChildren; 
	private Turtle myTurtles;
	private Turtle myActiveTurtles;
	private boolean isString;

	protected CommandNode(String info, Turtle turtles, Turtle activeTurtles) {
		this(info, DEFAULT_NUM_ARGS, new ArrayList<CommandNode>(), turtles, activeTurtles, true);
	}

	protected CommandNode(String info, int numArgs, Turtle turtles, Turtle activeTurtles) { 
		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, false);
	}
	protected CommandNode(String info, int numArgs, CommandNode child, Turtle turtles, Turtle activeTurtles) {
		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, false);
		myChildren.add(child);
	}
	protected CommandNode(String info, int numArgs, Turtle turtles, Turtle activeTurtles, boolean isString) {
		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, isString); 
	}
	

	protected CommandNode(String info, int numArgs, List<CommandNode> children, Turtle turtles, Turtle activeTurtles, boolean isStringID) {	
		myInfo = info;
		isString = isStringID;
		isDouble = false;
		if(!isString) {
			try {
				Double.parseDouble(info);
				isDouble = true; 
			}
			catch (NumberFormatException e) {
				isDouble = false; 
			}
		}
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
		if (!myChildren.isEmpty()) {
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
	protected Turtle getTurtles() {
		return myTurtles;
	}
	protected Turtle getActiveTurtles() {
		return myActiveTurtles;
	}

}
