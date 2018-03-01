package interpreter;

import java.util.Map;

/**
 * changes color of pen
 * @author andrewarnold
 *
 */
public class SetPenColorCommand extends Command {
    private Turtle myTurtle;
    private Command colorCodeCommand;
    private Map<String, Double> myVariables; 

    protected SetPenColorCommand(Command codeIn, Turtle turtle,Map<String, Double> variables) {
	colorCodeCommand = codeIn;
	myTurtle = turtle;
	myVariables = variables;
    }

    @Override
    protected double execute() throws UnidentifiedCommandException {
	double hexAsDouble = getCommandValue(colorCodeCommand, myVariables);
	String hexAsString = Integer.toHexString((int)hexAsDouble);
	hexAsString = addLeadingZeros(hexAsString);
	myTurtle.setPenColor(hexAsString);
	return 0;
    }

    private String addLeadingZeros(String hexAsString) {
	while(hexAsString.length() <6) {
	    hexAsString = "0" + hexAsString;
	}
	return hexAsString;
    }
}
