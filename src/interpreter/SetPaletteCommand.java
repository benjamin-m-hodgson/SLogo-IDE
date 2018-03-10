package interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class SetPaletteCommand extends Command {

	public static final String DEFAULT_COLORPALETTE_FILE = "src/interpreter/ColorPalette.properties"; 
	public static final String DEFAULT_COLORPALETTENAMES_FILE = "src/interpreter/ColorPaletteNames.properties"; 
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
		Properties prop = new Properties();
		InputStream in = null;
		
		try {
			in = new FileInputStream(new File(DEFAULT_COLORPALETTE_FILE));
		} catch (FileNotFoundException e2) {
			throw new UnidentifiedCommandException("Can't find colors");
			
		}
		
		try {
			prop.load(in);
		} catch (IOException e) {
			throw new UnidentifiedCommandException("Can't find colors");
		}
		
		int idxAsInt = (int) idx; 
		String idxAsKey = Integer.toString(idxAsInt);
		
		prop.setProperty(idxAsKey, hex);
		
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(DEFAULT_COLORPALETTE_FILE);
		} catch (FileNotFoundException e1) {
			throw new UnidentifiedCommandException("Can't save new color");
		}
		try {
			prop.store(fileOut, null);
		} catch (IOException e) {
			throw new UnidentifiedCommandException("Can't save new color");
		}
		
		
		
		// WRITING NAME OF NEW COLOR
		try {
			in = new FileInputStream(new File(DEFAULT_COLORPALETTENAMES_FILE));
		} catch (FileNotFoundException e2) {
			throw new UnidentifiedCommandException("Can't save new color");
		}
		
		try {
			prop.load(in);
		} catch (IOException e) {
			throw new UnidentifiedCommandException("Can't save new color");
		}
		
		prop.setProperty(idxAsKey, "User "+idxAsKey);
		
		fileOut = null;
		try {
			fileOut = new FileOutputStream(DEFAULT_COLORPALETTENAMES_FILE);
		} catch (FileNotFoundException e1) {
			throw new UnidentifiedCommandException("Can't save new color");
		}
		try {
			prop.store(fileOut, null);
		} catch (IOException e) {
			throw new UnidentifiedCommandException("Can't save new color");
		}
		
		return idx; 
	}

}

