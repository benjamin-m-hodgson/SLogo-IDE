package interpreter;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import javafx.scene.image.Image;

class SetShapeCommand extends Command {

	public static final String DEFAULT_FILEPATH_PREFIX = "user.dir/";
	public static final String DEFAULT_IMAGES_FOLDER = "turtleimages/";
	public static final String DEFAULT_IMAGE_SUFFIX = ".png";
	public static final String DEFAULT_TURTLESHAPES_FILE = "interpreter/TurtleShapes";
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
		RegexMatcher rm = new RegexMatcher(DEFAULT_TURTLESHAPES_FILE);
		String matchingPNG = "";
		try {
			matchingPNG = rm.findMatchingVal(idxKey);
		} catch (BadFormatException | MissingInformationException e) {
		}

		File turtleFile = new File(DEFAULT_IMAGES_FOLDER  + matchingPNG + DEFAULT_IMAGE_SUFFIX);
		
		try {
			myTurtle.setImage(turtleFile.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
		}
		
		return idxAsDouble;
	}



}
