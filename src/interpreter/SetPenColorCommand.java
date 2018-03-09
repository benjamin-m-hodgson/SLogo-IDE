package interpreter;

import java.util.Map;

/**
 * changes color of pen
 * @author andrewarnold
 *
 */
public class SetPenColorCommand extends Command {
	public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
	private Command colorCodeCommand;
	private Map<String, Double> myVariables; 
	private boolean myIdxSent;


	protected SetPenColorCommand(Command codeIn, Turtle turtle,Map<String, Double> variables) {
		colorCodeCommand = codeIn;
		setActiveTurtles(turtle);
		myVariables = variables;
	}

	@Override
	protected double execute() throws UnidentifiedCommandException {
		double retVal = 0; 
		String hexAsString = "";

		double commandInfo = getCommandValue(colorCodeCommand, myVariables, getActiveTurtles());

		getActiveTurtles().executeSequentially(turtle -> {
			try {
				getCommandValue(colorCodeCommand, myVariables, turtle);
			}
			catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
		});
				
		RegexMatcher rm = new RegexMatcher(DEFAULT_COLORPALETTE_FILE);
		try {
			int idxAsInt = (int) commandInfo;
			hexAsString = rm.findMatchingVal(Integer.toString(idxAsInt)).substring(1);
		} catch (Exception e) {
			return -1;
		}
		retVal = commandInfo; 

		getActiveTurtles().setPenColor(hexAsString);
		return retVal;
	}
	
}
