package interpreter;
import java.util.ArrayList;
import java.util.Map;

/**
 * Command class that makes or reactivates specific Turtles. Relies on the TurtleHolders in Turtle
 * classes to give copies when necessary and actual turtles when necessary (i.e. get turtle with
 * id gives actual turtle, while getAllTurtles gives copy).
 * @author Sarahbland
 *
 */
class TellCommand extends Command{
	private Command myIdCommand;
	private Turtle myAllTurtles;
	Map<String, Double> myVariables;
	protected TellCommand(Command turtleId, Turtle activeTurtles, Turtle allTurtles, Map<String, Double> variables) {
		myIdCommand = turtleId;
		setActiveTurtles(activeTurtles);
		myAllTurtles = allTurtles;
		myVariables = variables;
	}
	 @Override
	    protected double execute() throws UnidentifiedCommandException{
	    		String myIDString = ((StringCommand) myIdCommand).getString();
	    		String[] myIDs = myIDString.split(" ");
	    		ArrayList<SingleTurtle> newlyActivated = new ArrayList<>();
	    		for(int k = 0; k<myIDs.length; k+=1) {
	    			if(myVariables.containsKey(myIDs[k])){
	    				myIDs[k] = myVariables.get(myIDs[k]).toString();
	    			}
	    			if(!myAllTurtles.containsTurtleWithID(myIDs[k])) {
	    				try {
	    				SingleTurtle newTurtle = new SingleTurtle(Double.parseDouble(myIDs[k]));
	    				myAllTurtles.addTurtle(newTurtle);
	    				newlyActivated.add(newTurtle);
	    				}
	    				catch(NumberFormatException e) {
	    					throw new UnidentifiedCommandException("Invalid ID");
	    				}
	    			}
	    			else{
	    				newlyActivated.add(myAllTurtles.getTurtleWithID(myIDs[k]));
	    			}
	    		}
	    		setActiveTurtles(getActiveTurtles().replaceTurtles(newlyActivated));
	    		return Double.parseDouble(myIDs[myIDs.length-1]);
	    }
}

