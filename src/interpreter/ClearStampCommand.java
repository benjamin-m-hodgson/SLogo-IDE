package interpreter;

public class ClearStampCommand extends Command {

	protected ClearStampCommand(Turtle turtles) {
		setActiveTurtles(turtles);
	}	
	
	@Override
	double execute() {
		boolean beenStamped = getActiveTurtles().hasBeenStamped(); 
		double hasBeenStamped = beenStamped ? 1 : 0;
		getActiveTurtles().clearStamps();
		return hasBeenStamped; 
	}

}
