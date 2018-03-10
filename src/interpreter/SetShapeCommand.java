package interpreter;


import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Changes shape/imageview of the Turtle, where the new shape is specified with an index into the frontend Shapes list/Shapes Properties file.
 * Depends on the PropertiesReader to retrieve the hex value associated with the color index. 
 *
 */
class SetShapeCommand extends Command {

	public static final String DEFAULT_FILEPATH_PREFIX = "user.dir/";
	public static final String DEFAULT_IMAGES_FOLDER = "turtleimages/";
	public static final String DEFAULT_IMAGE_SUFFIX = ".png";
	public static final String DEFAULT_TURTLESHAPES_FILE = "interpreter/TurtleShapes";
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
		
		try {
			RegexMatcher rm = new RegexMatcher(DEFAULT_TURTLESHAPES_FILE);
			String idxKey = Integer.toString((int)idxAsDouble); 
			String matchingShape = rm.findMatchingVal(idxKey);
			File turtleFile = new File(DEFAULT_IMAGES_FOLDER  + matchingShape + DEFAULT_IMAGE_SUFFIX);
			Image newImg = new Image(turtleFile.toURI().toURL().toExternalForm());
			getActiveTurtles().setShape(newImg, idxAsDouble);
		} catch (BadFormatException | MissingInformationException | MalformedURLException | UnidentifiedCommandException e) {
			throw new UnidentifiedCommandException("Can't set shape to "+idxAsDouble);
		} 

		
		return idxAsDouble;
	}



}
