package interpreter;

import java.util.Map;

class GetPenColorCommand extends Command {
	
	public static final String DEFAULT_COLORPALETTE_FILE = "src/interpreter/ColorPalette.properties";
	Turtle myTurtle; 

	protected GetPenColorCommand(Turtle turtle) {
		myTurtle = turtle; 
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		PropertiesReader pr = new PropertiesReader(DEFAULT_COLORPALETTE_FILE);
		String colorIdx = pr.findKey("#"+myTurtle.getPenColor()); 
		if (colorIdx.length() > 0) {
			return Double.parseDouble(colorIdx); 
		}
		else {
			throw new UnidentifiedCommandException("No pen color");
		} 
	}
	
}
