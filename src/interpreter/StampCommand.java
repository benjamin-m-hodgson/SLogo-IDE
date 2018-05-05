package interpreter;


/**
 * Command class that can be used to add stamps (static, unclickable images
 * corresponding to the current image of the turtle) to the screen or clear 
 * all stamps. Dependent on the CommandFactory to create it correctly. 
 * @author Sarahbland
 *
 */
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
