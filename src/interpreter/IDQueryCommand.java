package interpreter;

class IDQueryCommand extends Command{
	Turtle myTurtle;
	
	protected IDQueryCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	
	@Override
	protected double execute() {
		return myTurtle.getID();
	}
	
}
