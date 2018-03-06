package interpreter;

class IDQueryCommand extends Command{
	
	protected IDQueryCommand() {
	}
	
	@Override
	protected double execute() {
		double returnVal = -1;
		for(Turtle myTurtle : getActiveTurtles()) {
			returnVal = myTurtle.getID();
		}
		return returnVal;
	}
	
}
