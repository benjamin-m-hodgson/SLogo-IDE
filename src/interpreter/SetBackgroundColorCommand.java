package interpreter;

import java.util.Map;

/**
 * Sets the background color of the Turtle area according to an index corresponding to a ColorPalette color
 * @author susiechoi
 *
 */
class SetBackgroundColorCommand extends Command {
	
	public static final String DEFAULT_COLORPALETTE_FILE = "src/interpreter/ColorPalette.properties";
	Command myColorIdxCommand;
	Map<String, Double> myVariables;

	protected SetBackgroundColorCommand(Command colorIdx, Map<String, Double> variables, Turtle activeTurtles) {
		myColorIdxCommand = colorIdx;
		myVariables = variables; 
		setActiveTurtles(activeTurtles);
	}

	@Override
	double execute() throws UnidentifiedCommandException {
		double colorIdx = getCommandValue(myColorIdxCommand, myVariables, getActiveTurtles().toSingleTurtle());
		getActiveTurtles().executeSequentially(myTurtle ->{ 
			try{getCommandValue(myColorIdxCommand, myVariables, myTurtle);
			}
			catch(UnidentifiedCommandException e) {
				throw new UnidentifiedCommandError("Improper # arguments");
			}
			});
		PropertiesReader pr = new PropertiesReader(DEFAULT_COLORPALETTE_FILE);
		String colorIdxAsString = Integer.toString((int) colorIdx);
		if (pr.containsKey(colorIdxAsString)) {
			return colorIdx; 
		}
		throw new UnidentifiedCommandException("Color not found");
	}

}
