package interpreter;

class GetShapeCommand extends Command {

	public static final String DEFAULT_SHAPES_FILE = "interpreter/TurtleShapes";
	private Turtle myTurtle;
	
	protected GetShapeCommand(Turtle turtle) {
		myTurtle = turtle;
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		return Double.parseDouble(myTurtle.getImageName());
	}

}
