package interpreter;

import java.util.Map;

/**
 * Changes color of the pen associated with a turtle, where the new pen color is specified with an index into the ColorPalette.
 * Depends on the PropertiesReader to retrieve the hex value associated with the color index. 
 * @author Susie Choi 
 * @author andrewarnold
 *
 */
public class SetPenColorCommand extends Command {
	public static final String DEFAULT_COLORPALETTE_FILE = "src/interpreter/ColorPalette.properties";
	private Command colorCodeCommand;
	private Map<String, Double> myVariables; 

	protected SetPenColorCommand(Command codeIn, Turtle turtle,Map<String, Double> variables) {
		colorCodeCommand = codeIn;
		setActiveTurtles(turtle);
		myVariables = variables;
	}

	@Override
	protected double execute() throws UnidentifiedCommandException {
	
		double retVal = -1; 
		
		double commandInfo = getCommandValue(colorCodeCommand, myVariables, getActiveTurtles());
		
		getActiveTurtles().executeSequentially(turtle -> {
			try {
				getCommandValue(colorCodeCommand, myVariables, turtle);
			}
			catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
		});
		
		int idxAsInt = (int) commandInfo;
		
		PropertiesReader pr = new PropertiesReader(DEFAULT_COLORPALETTE_FILE);
		String hex = pr.findVal(Integer.toString(idxAsInt));
		if (hex.length() > 0) {
			getActiveTurtles().setPenColor(hex.substring(1));
			retVal = commandInfo; 
		}
		else {
			throw new UnidentifiedCommandException("Invalid color selection!");
		}
					
		return retVal;
	}
	
}
