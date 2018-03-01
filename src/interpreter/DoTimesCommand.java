package interpreter;
import java.util.List;
import java.util.ArrayList;

public class DoTimesCommand implements Command{
	String myToExecute;
	String myTempVar;
	Command myEndExpressionCommand;
	CommandTreeBuilder myBuilder;
	Turtle myTurtle;
	protected DoTimesCommand(Command tempVarCommand, Command endExpression, Command toExecuteCommand, Turtle turtle) {
		myTempVar = ((StringCommand)tempVarCommand).getString();
		myToExecute = ((StringCommand)toExecuteCommand).getString();
		myEndExpressionCommand = endExpression;
		myBuilder = new CommandTreeBuilder();
		myTurtle = turtle;
	}
	public double execute() throws UnidentifiedCommandException {
		String[] executeArray = myToExecute.split(" ");
		double ending = myEndExpressionCommand.execute();
		double returnVal = 0.0;
		List<Integer> indices = getTempVarIndices(myTempVar, executeArray);
		for(Double k = 1.0; k<=ending; k+=1) {
			findAndReplace(indices, k, executeArray);
			for(int i = 0; i<executeArray.length; i+=1) {
				System.out.println("executing " + executeArray[i]);
			}
			try {
				returnVal = myBuilder.buildAndExecute(myTurtle, executeArray, true);
			}
			catch(Exception e){
				//TODO fix this! Don't just throw another exception
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
