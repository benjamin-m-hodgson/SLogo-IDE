package interpreter;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import javafx.scene.image.Image;

class SetShapeCommand extends Command {

	private Turtle myTurtle;
	private Command myShapeIdx; 
	private Map<String, Double> myVars;

	protected SetShapeCommand(Turtle turtle, Command shapeIdx, Map<String, Double> vars) {
		myTurtle = turtle;
		myShapeIdx = shapeIdx; 
		myVars = vars; 
	}

	@Override
	double execute() throws UnidentifiedCommandException {
		double idxAsDouble = getCommandValue(myShapeIdx, myVars);
		String idxKey = Integer.toString((int)idxAsDouble); 
		try {
			myTurtle.setShape(idxKey);
		} catch (BadFormatException | MissingInformationException | MalformedURLException e) {
		} 
		
		return idxAsDouble;
	}



}
