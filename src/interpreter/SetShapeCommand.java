package interpreter;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import javafx.scene.image.Image;

class SetShapeCommand extends Command {

	private Command myShapeIdx; 
	private Map<String, Double> myVars;

	protected SetShapeCommand(Turtle turtle, Command shapeIdx, Map<String, Double> vars) {
		setActiveTurtles(turtle);
		myShapeIdx = shapeIdx; 
		myVars = vars; 
	}

	@Override
	double execute(){
		double idxAsDouble = getCommandValue(myShapeIdx, myVars, getActiveTurtles().toSingleTurtle());
		String idxKey = Integer.toString((int)idxAsDouble); 
		try {
			getActiveTurtles().setShape(idxKey);
		} catch (BadFormatException | MissingInformationException | MalformedURLException | UnidentifiedCommandException e) {
			return -1.0;
		} 
		
		return idxAsDouble;
	}



}
