package interpreter;

import java.util.ArrayList;
import java.util.Map;

public class AskWithCommand extends Command {
	Command myCriteriaCommand;
	Command myActionCommand;
	Turtle myAllTurtles;
	CommandTreeBuilder myBuilder;
	protected AskWithCommand(Command turtleIds, Command actions, Turtle allTurtles,
			Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandNumArgs) {
		myCriteriaCommand = turtleIds;
		myActionCommand = actions;
		myAllTurtles = allTurtles;
		myBuilder = new CommandTreeBuilder(variables, userDefCommands, userDefCommandNumArgs);
	}
	@Override
	protected double execute() throws UnidentifiedCommandException{
		String criteriaString = ((StringCommand) myCriteriaCommand).getString();
		String[] criteria = criteriaString.split(" ");
		String actionString = ((StringCommand) myActionCommand).getString();
		String[] actions = actionString.split(" ");
		ArrayList<Double> iDsToUse = new ArrayList<>();
		for(SingleTurtle turtleSingle : myAllTurtles.getAllImmutableTurtles()) {
			ArrayList<SingleTurtle> singleTurtleList = new ArrayList<>();
			singleTurtleList.add(turtleSingle);
			MultipleTurtles turtle = new MultipleTurtles(singleTurtleList);
			double result = 0;
			try {
				result = myBuilder.buildAndExecute(myAllTurtles, turtle, criteria, true);
			}
			catch(Exception e) {
				throw new UnidentifiedCommandException(e.getMessage());
			}
			if(result==1) {
				iDsToUse.add(turtle.getID());
			}
		}
		ArrayList<SingleTurtle> tempTurtles = new ArrayList<>();
		for(double iD : iDsToUse) {
			tempTurtles.add(myAllTurtles.getTurtleWithID("" + iD));
		}
		MultipleTurtles tempActive = new MultipleTurtles(tempTurtles);
		double returnVal = -1;
		if(iDsToUse.size()==0) {
			return returnVal;
		}
		try {
			returnVal =  myBuilder.buildAndExecute(myAllTurtles, tempActive, actions, true);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new UnidentifiedCommandException(e.getMessage());
		}
		return returnVal;
		
	}
}
