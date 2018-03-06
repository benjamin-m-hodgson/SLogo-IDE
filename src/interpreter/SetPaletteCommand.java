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
	double execute() throws UnidentifiedCommandException {
		double idx = getCommandValue(myIdx, myVars);
		double r = getCommandValue(myR, myVars);
		double g = getCommandValue(myG, myVars);
		double b = getCommandValue(myB, myVars);
		String hex = String.format("#%02x%02x%02x", (int) r,  (int) g, (int) b);  
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream(DEFAULT_COLORPALETTE_FILE);
		
		try {
			in = new FileInputStream(new File(DEFAULT_COLORPALETTE_FILE));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			prop.load(in);
		} catch (IOException e) {
			System.out.println("Oops! There were a problem processing the color palette file.");
			e.printStackTrace();
		}
		
		int idxAsInt = (int) idx; 
		String idxAsKey = Integer.toString(idxAsInt);
		
		prop.setProperty(idxAsKey, hex);
		
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(DEFAULT_COLORPALETTE_FILE);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			prop.store(fileOut, null);
		} catch (IOException e) {
			System.out.println("Oops! There were a problem processing the color palette file.");
			e.printStackTrace();
		}
		
		return idx; 
	}

}
