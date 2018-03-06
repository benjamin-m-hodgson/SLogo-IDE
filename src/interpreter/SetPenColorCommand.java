package interpreter;

import java.util.Map;

/**
 * changes color of pen
 * @author andrewarnold
 *
 */
public class SetPenColorCommand extends Command {
	public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
	boolean myIdxSent; 
	private Turtle myTurtle;
	private Command colorCodeCommand;
	private Map<String, Double> myVariables; 

	protected SetPenColorCommand(Command codeIn, Turtle turtle,Map<String, Double> variables, boolean idxSent) {
		myIdxSent = idxSent; 
		colorCodeCommand = codeIn;
		myTurtle = turtle;
		myVariables = variables;
	}

	@Override
	protected double execute() throws UnidentifiedCommandException {
		double retVal = 0; 
		String hexAsString = "";
		
		double commandInfo = getCommandValue(colorCodeCommand, myVariables);
		
		if (myIdxSent) {
			RegexMatcher rm = new RegexMatcher(DEFAULT_COLORPALETTE_FILE);
			try {
				int idxAsInt = (int) commandInfo;
				hexAsString = rm.findMatchingVal(Integer.toString(idxAsInt)).substring(1);
			} catch (BadFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingInformationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			retVal = commandInfo; 
		}
		else {
			hexAsString = Integer.toHexString((int)commandInfo);
			hexAsString = addLeadingZeros(hexAsString);
			System.out.println(hexAsString);
		}
		myTurtle.setPenColor(hexAsString);
		return retVal;
	}

	private String addLeadingZeros(String hexAsString) {
		while(hexAsString.length() <6) {
			hexAsString = "0" + hexAsString;
		}
		return hexAsString;
	}
}
