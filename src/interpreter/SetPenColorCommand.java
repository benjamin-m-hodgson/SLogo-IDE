package interpreter;

import java.util.Map;

/**
 * changes color of pen
 * @author andrewarnold
 *
 */
public class SetPenColorCommand extends Command {
	public static String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
	private Command colorCodeCommand;
	private Map<String, Double> myVariables; 
	private boolean myIdxSent;
	

	protected SetPenColorCommand(Command codeIn, Turtle turtle,Map<String, Double> variables, boolean idxSent) {
		myIdxSent = idxSent; 
		colorCodeCommand = codeIn;
		setActiveTurtles(turtle);
		myVariables = variables;
	}

	@Override
	protected double execute() {
		double retVal = 0; 
		String hexAsString = "";
		
		double commandInfo = getCommandValue(colorCodeCommand, myVariables, getActiveTurtles());
		getActiveTurtles().executeSequentially(turtle -> getCommandValue(colorCodeCommand, myVariables, turtle));
		if (myIdxSent) {
			RegexMatcher rm = new RegexMatcher(DEFAULT_COLORPALETTE_FILE);
			try {
				int idxAsInt = (int) commandInfo;
				hexAsString = rm.findMatchingVal(Integer.toString(idxAsInt)).substring(1);
			} catch (Exception e) {
				return -1;
			}
			retVal = commandInfo; 
		}
		else {
			hexAsString = Integer.toHexString((int)commandInfo);
			hexAsString = addLeadingZeros(hexAsString);
			System.out.println(hexAsString);
		}
		getActiveTurtles().setPenColor(hexAsString);
		return retVal;
	}

	private String addLeadingZeros(String hexAsString) {
		while(hexAsString.length() <6) {
			hexAsString = "0" + hexAsString;
		}
		return hexAsString;
	}
}
