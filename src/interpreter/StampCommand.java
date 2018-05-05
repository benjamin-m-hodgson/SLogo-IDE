package interpreter;


public class StampCommand extends Command {
    boolean myRemove;
    protected StampCommand(Turtle turtle, boolean remove) {
		setActiveTurtles(turtle);
		myRemove = remove;
    }
    	@Override
    protected double execute() throws UnidentifiedCommandException {
		if(myRemove) {
		  return getActiveTurtles().removeStamps();
		}
		else {
		  return getActiveTurtles().stamp();
		}
		
    }
}
