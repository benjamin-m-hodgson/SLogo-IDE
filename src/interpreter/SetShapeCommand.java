package interpreter;


import java.net.MalformedURLException;
import java.util.Map;

/**
 * Changes shape/imageview of the Turtle, where the new shape is specified with an index into the frontend Shapes list/Shapes Properties file.
 * Depends on the PropertiesReader to retrieve the hex value associated with the color index. 
 *
 */
class SetShapeCommand extends Command {

	private Command myShapeIdx; 
	private Map<String, Double> myVars;

	protected SetShapeCommand(Turtle turtle, Command shapeIdx, Map<String, Double> vars) {
		setActiveTurtles(turtle);
		myShapeIdx = shapeIdx; 
		myVars = vars; 
	}

	@Override
	double execute() throws UnidentifiedCommandException {
		double idxAsDouble = getCommandValue(myShapeIdx, myVars, getActiveTurtles().toSingleTurtle());
		String idxKey = Integer.toString((int)idxAsDouble); 
		try {
			getActiveTurtles().setShape(idxKey);
		} catch (BadFormatException | MissingInformationException | MalformedURLException | UnidentifiedCommandException e) {
			throw new UnidentifiedCommandException("Can't set shape to "+idxKey);
		} 
		
		return idxAsDouble;
	}



}
