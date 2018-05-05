/**
 * 
 * Superclass for CommandNodes. The original implementation had boolean instance variables such as isString and isDouble to signify 
 * what type of information the CommandNode encapsulates (e.g. if isString were true, it holds a variable argument; if isDouble is true; it holds a double argument). 
 * This, however, would have been better replaced with polymorphism, which I seek to do in this masterpiece implementation. 
 * 
 * This CommandNode superclass keeps many of the same methods from the original implementation, 
 * 		as methods such as addChild() are ones that need to be supported by all CommandNodes for the tree-building process to go smoothly. 
 * However, the superclass allows its subclasses to determine whether the Node is considered complete (i.e. having all children) through the isComplete method. 
 * This was added to account for the fact that bracketed chunks of control flow commands can take an unlimited number of commands, 
 * 		i.e. bracket nodes can have an unlimited number of children, and therefore they will always be complete (i.e. will never have too few or too many arguments).
 * On the other hand, CommandNodes representing official commands such as MoveForward would only have isComplete evaluate to true if 
 * 		the number of CommandNode children == the number of expected arguments (2).  
 * 
 * One modification I made was to the getChildren method. The method now returns an Iterator, as opposed to a List of CommandNodes. 
 * Though this is an improvement, it still allows the potential for the client to, say, remove a child node. 
 * However, the getChildren method is necessary to correctly populate the argument fields when converting CommandNodes to Command objects. 
 * This could potentially be done by creating a method to allow CommandNodes to make Commands out of themselves. 
 * (As the primary focus of this masterpiece is to encapsulate control flow parsing, I have not done this here.)
 * 
 * Please read CommandNodeFactory comments next!
 * 
 */

package interpreter;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

abstract class CommandNode {

	protected String myCommandType;
	protected List<CommandNode> myChildren;
	protected Turtle myAllTurtles;
	protected Turtle myActiveTurtles;

	protected CommandNode(String commandType, Turtle allTurtles, Turtle activeTurtles) {
		myCommandType = commandType;
		myChildren = new ArrayList<CommandNode>();
		myAllTurtles = allTurtles;
		myActiveTurtles = activeTurtles; 
	}

	protected String getInfo() {
		return myCommandType;
	}

	protected void addChild(CommandNode child) {
		myChildren.add(child); 
	}

	protected String getType() {
		return myCommandType;
	}

	protected Iterator<CommandNode> getChildren() {
		return myChildren.iterator(); 
	}

	abstract protected boolean isComplete();

}

//import java.util.ArrayList;
//
///**
// * @author Susie Choi
// * minor modifications by Sarah Bland
// * 
// * Represents an element of a user-inputed command (e.g. Command name, argument). 
// * Used in constructing a CommandTree to assess the completeness of commands in the parsing process. 
// *
// */
//class CommandNode {
//
//	public static final int DEFAULT_NUM_ARGS = 0; 
//
//	private String myInfo;
//	private boolean isDouble; 
//	private int myNumArgs; 
//	private List<CommandNode> myChildren; 
//	private Turtle myTurtles;
//	private Turtle myActiveTurtles;
//	private boolean isString;
//
//	protected CommandNode(String info, Turtle turtles, Turtle activeTurtles) {
//		this(info, DEFAULT_NUM_ARGS, new ArrayList<CommandNode>(), turtles, activeTurtles, true);
//	}
//
//	protected CommandNode(String info, int numArgs, Turtle turtles, Turtle activeTurtles) { 
//		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, false);
//	}
//	protected CommandNode(String info, int numArgs, CommandNode child, Turtle turtles, Turtle activeTurtles) {
//		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, false);
//		myChildren.add(child);
//	}
//	protected CommandNode(String info, int numArgs, Turtle turtles, Turtle activeTurtles, boolean isString) {
//		this(info, numArgs, new ArrayList<CommandNode>(), turtles, activeTurtles, isString); 
//	}
//	
//
//	protected CommandNode(String info, int numArgs, List<CommandNode> children, Turtle turtles, Turtle activeTurtles, boolean isStringID) {	
//		myInfo = info;
//		isString = isStringID;
//		isDouble = false;
//		if(!isString) {
//			try {
//				Double.parseDouble(info);
//				isDouble = true; 
//			}
//			catch (NumberFormatException e) {
//				isDouble = false; 
//			}
//		}
//		myNumArgs = numArgs;
//		myChildren = new ArrayList<CommandNode>(); 
//		myChildren.addAll(children); 
//		myTurtles = turtles;
//		myActiveTurtles = activeTurtles;
//		
//	}
//
//	protected CommandNode(String info, int numArgs, List<CommandNode> children) {
//		myInfo = info; 
//		try {
//			Integer.parseInt(info);
//			isDouble = true; 
//		}
//		catch (NumberFormatException e) {
//			isDouble = false; 
//		}
//		myNumArgs = numArgs;
//		myChildren = children;
//		isString = false;
//	}
//
//	@Override
//	public String toString() {
//		String s = "CommandNode holding info "+myInfo;
//		if (!myChildren.isEmpty()) {
//			s+= " has "+myChildren.size()+" children:\n";
//			for (int i = 0; i < myChildren.size(); i ++) {
//				s += "#"+(i+1)+". "+myChildren.get(i).toString()+"\n";
//			}
//		}
//		else {
//			s+= " has no children.";
//		}
//		return s; 
//	}
//	
//	public String childrenToString() {
//		String s = "";
//		for (int i = 0; i < myChildren.size(); i++) {
//			s += myChildren.get(i).getInfo();
//			s += myChildren.get(i).childrenToString(); 
//			s += " ";
//		}
//		return s; 
//	}
//
//	protected void addChild(CommandNode child) {
//		myChildren.add(child); 
//	}
//
//	protected int getNumArgs() {
//		return myNumArgs; 
//	}
//
//	protected int getNumChildren() {
//		return myChildren.size();
//	}
//	protected boolean getIsDouble() {
//		return isDouble;
//	}
//	protected boolean getIsString() {
//		return isString;
//	}
//	protected List<CommandNode> getChildren() {
//		return myChildren;
//	}
//	protected String getInfo() {
//		return myInfo;
//	}
//	protected Turtle getTurtles() {
//		return myTurtles;
//	}
//	protected Turtle getActiveTurtles() {
//		return myActiveTurtles;
//	}
//
//}