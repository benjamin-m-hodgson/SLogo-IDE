package interpreter;

import java.util.Map;

/**
 * changes color of pen
 * @author andrewarnold
 *
 */
public class SetPenColorCommand extends Command {
	private Command colorCodeCommand;
	private Map<String, Double> myVariables; 

	protected SetPenColorCommand(Command codeIn, Turtle activeTurtles,Map<String, Double> variables) {
		colorCodeCommand = codeIn;
		setActiveTurtles(activeTurtles);
		myVariables = variables;
	}

	@Override
	protected double execute(){
		double hexAsDouble = getCommandValue(colorCodeCommand, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle -> getCommandValue(colorCodeCommand, myVariables, myTurtle));
		String hexAsString = Integer.toHexString((int)hexAsDouble);
		hexAsString = addLeadingZeros(hexAsString);
		getActiveTurtles().setPenColor(hexAsString);
		return 0;
	}

	private String addLeadingZeros(String hexAsString) {
		while(hexAsString.length() <6) {
			hexAsString = "0" + hexAsString;
		}
		return hexAsString;
	}
}
