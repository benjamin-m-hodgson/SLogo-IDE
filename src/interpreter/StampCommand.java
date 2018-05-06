package interpreter;

public class StampCommand extends Command {

	protected StampCommand(Turtle turtles) {
		setActiveTurtles(turtles);
	}	
	
	@Override
	double execute() throws UnidentifiedCommandException {
		getActiveTurtles().stamp();
		return getActiveTurtles().getImageIdx(); 
	}

}
