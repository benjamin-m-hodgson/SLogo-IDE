package interpreter;

class TellCommand extends Command{
	private Command myIdCommand;
	private Turtle myAllTurtles;
	protected TellCommand(Command turtleId, Turtle activeTurtles, Turtle allTurtles) {
		myIdCommand = turtleId;
		setActiveTurtles(activeTurtles);
		myAllTurtles = allTurtles;
	}
	 @Override
	    protected double execute(){
	    		String myIDString = ((StringCommand) myIdCommand).getString();
	    		String[] myIDs = myIDString.split(" ");
	    		//TODO: clear turtles in a safe manner?????
	    		for(int k = 0; k<myIDs.length; k+=1) {
	    			if(!myAllTurtles.containsTurtleWithID(myIDs[k])) {
	    				SingleTurtle newTurtle = new SingleTurtle();
	    				myAllTurtles.addTurtle(newTurtle);
	    				getActiveTurtles().addTurtle(newTurtle);
	    			}
	    			else if(!getActiveTurtles().containsTurtleWithID(myIDs[k]){
	    				//TODO: 
	    			}
	    		}
	    }
}
