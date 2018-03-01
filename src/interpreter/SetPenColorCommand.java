package interpreter;

public class SetPenColorCommand implements Command {
	private Turtle myTurtle;
	private Command colorCode;

	protected SetPenColorCommand(Command codeIn, Turtle turtle) {
		colorCode = codeIn;
		myTurtle = turtle;
	}

	@Override
	public double execute() throws UnidentifiedCommandException {
		double hexAsDouble = colorCode.execute();
		String hexAsString = Integer.toHexString((int)hexAsDouble);
		hexAsString = addLeadingZeros(hexAsString);
		myTurtle.setPenColor(hexAsString);
		return 0;
	}

	private String addLeadingZeros(String hexAsString) {
		while(hexAsString.length() <6) {
			hexAsString = "0" + hexAsString;
		}
		return hexAsString;
	}
}
