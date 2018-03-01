package interpreter;

import java.util.ArrayList;
import java.util.List;

public class ForCommand implements Command{
		String myTempVar;
		Command myStartCommand;
		Command myEndCommand;
		Command myIncrement;
		String myToExecute;
		Turtle myTurtle;
		CommandTreeBuilder myBuilder;
		protected ForCommand(Command variable, Command start, Command end, Command increment, Command repeated, Turtle turtle) {
			myTempVar = ((StringCommand)variable).getString();
			myToExecute = ((StringCommand)repeated).getString();
			myStartCommand = start;
			myEndCommand = end;
			myIncrement = increment;
		}
		public double execute() throws UnidentifiedCommandException {
			String[] executeArray = myToExecute.split(" ");
			double start = myStartCommand.execute();
			double ending = myEndCommand.execute();
			double increment = myIncrement.execute();
			double returnVal = 0.0;
			List<Integer> indices = getTempVarIndices(myTempVar, executeArray);
			for(Double k = start; k<=ending; k+=increment) {
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
