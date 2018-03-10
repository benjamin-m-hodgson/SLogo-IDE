package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements the "for" command, looping over a certain set of actions from a 
 * specified start to end with a specified increment
 * @author Sarahbland
 *
 */
public class ForCommand extends Command{
		private String myTempVar;
		private Command myStartCommand;
		private Command myEndCommand;
		private Command myIncrement;
		private String myToExecute;
		private Turtle myTurtle;
		CommandTreeBuilder myBuilder;
		Map<String, Double> myVars;
		Map<String, String> myUserDefComs;
		Map<String, Integer> myUserDefComsNumArgs;
		
		protected ForCommand(Command variable, Command start, Command end, Command increment, Command repeated, Turtle turtle,
				Turtle activeTurtles, Map<String, Double> vars, Map<String, String> userDefComs, Map<String, Integer> userDefComsNumArgs) {
			myTempVar = ((StringCommand)variable).getString();
			myToExecute = ((StringCommand)repeated).getString();
			myStartCommand = start;
			myEndCommand = end;
			myIncrement = increment;
			myBuilder = new CommandTreeBuilder(vars, userDefComs, userDefComsNumArgs);
			myTurtle = turtle;
			setActiveTurtles(activeTurtles);
		}
		@Override
		public double execute() throws UnidentifiedCommandException{
			String[] executeArray = myToExecute.split(" ");
			double start = 0;
			start = myStartCommand.execute();
			double ending = myEndCommand.execute();
			double increment = myIncrement.execute();
			double returnVal = 0.0;
			List<Integer> indices = getTempVarIndices(myTempVar, executeArray);
			for(Double k = start; k<=ending; k+=increment) {
				findAndReplace(indices, k, executeArray);
//				for(int i = 0; i<executeArray.length; i+=1) {
//					System.out.println("executing " + executeArray[i]);
//				}
				try {
					returnVal = myBuilder.buildAndExecute(myTurtle, getActiveTurtles(), executeArray, true);
				}
				catch(Exception e){
					return returnVal;
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
