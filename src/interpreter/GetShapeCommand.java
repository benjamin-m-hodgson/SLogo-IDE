package interpreter;

/**
 * Returns the shape (index corresponding to Shapes file) of the turtle in question
 * @author Susie Choi
 *
 */
class GetShapeCommand extends Command {

	public static final String DEFAULT_SHAPES_FILE = "interpreter/TurtleShapes";
	private Turtle myTurtle;
	
	protected GetShapeCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	
	@Override
	double execute(){
		return myTurtle.getImageIdx();
	}

}
