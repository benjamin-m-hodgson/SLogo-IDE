package interpreter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DoTimesCommand extends Command{
	
	private String toExecute;
	private String myTempVar;
	private Command endExpressionCommand;
	private CommandTreeBuilder myBuilder;
	private List<Turtle> myAllTurtles;
	
	protected DoTimesCommand(Command tempVarCommand, Command endExpression, Command toExecuteCommand, List<Turtle> activeTurtles, List<Turtle> allTurtles, 
			Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandNumArgs) {
		myTempVar = ((StringCommand)tempVarCommand).getString();
		toExecute = ((StringCommand)toExecuteCommand).getString();
		endExpressionCommand = endExpression;
		myBuilder = new CommandTreeBuilder(variables, userDefCommands, userDefCommandNumArgs);
		setActiveTurtles(activeTurtles);
		myAllTurtles = allTurtles;
	}
	@Override
	protected double execute() throws UnidentifiedCommandException {
		String[] executeArray = toExecute.split(" ");
		double ending = endExpressionCommand.execute();
		double returnVal = 0.0;
		List<Integer> indices = getTempVarIndices(myTempVar, executeArray);
		for(Double k = 1.0; k<=ending; k+=1) {
			findAndReplace(indices, k, executeArray);
			for(int i = 0; i<executeArray.length; i+=1) {
				System.out.println("executing " + executeArray[i]);
			}
			try {
				returnVal = myBuilder.buildAndExecute(myAllTurtles, getActiveTurtles() executeArray, true);
			}
			catch(Exception e){
				//TODO fix this! Don't just throw another exception
				e.printStackTrace();
				throw new UnidentifiedCommandException("There was a problem within one of your loops.");
			}

		}
		return returnVal;
	}
	private List<Integer> getTempVarIndices(String tempVar, String[] toExecute){
		ArrayList<Integer> indices = new ArrayList<>();
		for(int k = 0; k < toExecute.length; k+=1) {
			if(toExecute[k].equals(tempVar)) {
				indices.add(k);
			}
		}
		return indices;
	}
	private void findAndReplace(List<Integer> indices, Double replace, String[] toReplace) {
		for(int i: indices) {
			toReplace[i] = replace.toString();
		}
	}
}
