package interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author susiechoi
 * Allows user to add a new color to the ColorPalette -- can then be selected as a Pen or Background color. 
 * 4 args: idx of new color, r, g, b values. 
 * Dependent on file in/out writers to add the new color to ColorPalette and ColorPaletteNames files. 
 */
class SetPaletteCommand extends Command {

	public static final String DEFAULT_COLORPALETTE_FILE = "src/interpreter/ColorPalette.properties"; 
	public static final String DEFAULT_COLORPALETTENAMES_FILE = "src/interpreter/ColorPaletteNames.properties"; 
	public static final String DEFAULT_COLORNOTFOUND_MESSAGE = "Can't find colors";
	public static final String DEFAULT_SAVINGISSUE_MESSAGE = "Can't save new color";
	private HashMap<String, Double> myVars;
	private Command myIdx;
	private Command myR;
	private Command myG;
	private Command myB; 
	
	protected SetPaletteCommand( Command idx, Command r, Command g, Command b, Map<String, Double> vars) {
		myVars = (HashMap<String, Double>) vars; 
		myIdx = idx;
		myR = r;
		myG = g;
		myB = b; 
	}
	
	@Override
	double execute() throws UnidentifiedCommandException{
		
		double idx = getCommandValue(myIdx, myVars, new SingleTurtle());
		double r = getCommandValue(myR, myVars, new SingleTurtle());
		double g = getCommandValue(myG, myVars, new SingleTurtle());
		double b = getCommandValue(myB, myVars, new SingleTurtle());
		String hex = String.format("#%02x%02x%02x", (int) r,  (int) g, (int) b);  
		String idxAsString = Integer.toString((int)idx);
		
		HashMap<String, String> mapWithIdxToHex = new HashMap<String, String>();
		mapWithIdxToHex.put(idxAsString, hex);
		PropertiesWriter pwForHex = new PropertiesWriter(DEFAULT_COLORPALETTE_FILE, mapWithIdxToHex);
		pwForHex.write();
		
		HashMap<String, String> mapWithIdxToName = new HashMap<String, String>();
		mapWithIdxToName.put(idxAsString, "User Color #"+idxAsString);
		PropertiesWriter pwForName = new PropertiesWriter(DEFAULT_COLORPALETTENAMES_FILE, mapWithIdxToName);
		pwForName.write();
		
		return idx; 
	}

}

