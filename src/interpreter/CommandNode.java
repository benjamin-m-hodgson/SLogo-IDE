package interpreter;

import java.util.ArrayList;
import java.util.List;

public class CommandNode {

	public static final int DEFAULT_NUM_ARGS = 0; 

	private String myInfo;
	private boolean isDouble; 
	private double myDouble; 
	private int myNumArgs; 
	private Command myCommand; 
	private ArrayList<CommandNode> myChildren; 

	public CommandNode(String info) {
		this(info, DEFAULT_NUM_ARGS);
	}

	public CommandNode(String info, int numArgs) { 
		this(info, numArgs, new ArrayList<CommandNode>());
	}

	public CommandNode(String info, int numArgs, CommandNode child) {	
		myInfo = info; 
		try {
			myDouble = Integer.parseInt(info);
			isDouble = true; 
		}
		catch (NumberFormatException e) {
			isDouble = false; 
		}
		myNumArgs = numArgs;
		myChildren.add(child); 
	}

	public CommandNode(String info, int numArgs, List<CommandNode> children) {
		myInfo = info; 
		try {
			myDouble = Integer.parseInt(info);
			isDouble = true; 
		}
		catch (NumberFormatException e) {
			isDouble = false; 
		}
		myNumArgs = numArgs;
		myChildren = (ArrayList<CommandNode>) children; 
	}

	public String toString() {
		String s = "CommandNode with info: "+myInfo;
		if (myChildren.size() > 0) {
			s+= " has children: ";
			for (int i = 0; i < myChildren.size(); i ++) {
				s += myChildren.get(i).toString()+" ";
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

}
