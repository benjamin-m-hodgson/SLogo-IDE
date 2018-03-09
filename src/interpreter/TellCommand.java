package interpreter;
import java.util.ArrayList;

class TellCommand extends Command{
	private Command myIdCommand;
	private Turtle myAllTurtles;
	protected TellCommand(Command turtleId, Turtle activeTurtles, Turtle allTurtles) {
		myIdCommand = turtleId;
		setActiveTurtles(activeTurtles);
		myAllTurtles = allTurtles;
	}
	 @Override
	    protected double execute() throws UnidentifiedCommandException{
	    		String myIDString = ((StringCommand) myIdCommand).getString();
	    		String[] myIDs = myIDString.split(" ");
	    		System.out.println("length of ids: " + myIDs.length);
	    		//TODO: clear turtles in a safe manner?????
	    		ArrayList<SingleTurtle> newlyActivated = new ArrayList<>();
	    		for(int k = 0; k<myIDs.length; k+=1) {
	    			if(!myAllTurtles.containsTurtleWithID(myIDs[k])) {
	    				System.out.println("thinks this is id" + myIDs[k]);
	    				try {
	    				SingleTurtle newTurtle = new SingleTurtle(Double.parseDouble(myIDs[k]));
	    				myAllTurtles.addTurtle(newTurtle);
	    				newlyActivated.add(newTurtle);
	    				}
	    				catch(NumberFormatException e) {
	    					throw new UnidentifiedCommandException("Invalid ID");
	    				}
	    			}
	    			else if(!getActiveTurtles().containsTurtleWithID(myIDs[k])){
	    				newlyActivated.add(myAllTurtles.getTurtleWithID(myIDs[k]));
	    			}
	    		}
	    		setActiveTurtles(getActiveTurtles().replaceTurtles(newlyActivated));
	    		return Double.parseDouble(myIDs[myIDs.length-1]);
	    }
}

