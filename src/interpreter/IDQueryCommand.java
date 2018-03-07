package interpreter;

import java.util.List;

class IDQueryCommand extends Command{
		Turtle myTurtle;
	protected IDQueryCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	
	@Override
	protected double execute() {
		double returnVal = -1;
		returnVal = myTurtle.getID();
		return returnVal;
	}
	protected void setTurtle(Turtle turtle) {
		myTurtle = turtle;
	}
	
}
