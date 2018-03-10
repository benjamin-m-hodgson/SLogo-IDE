package interpreter;

/**
 * Returns the pen color (index corresponding to ColorPalette) of the current turtle
 * @author Susie Choi
 *
 */
class GetPenColorCommand extends Command {
	
	public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
	Turtle myTurtle; 

	protected GetPenColorCommand(Turtle turtle) {
		myTurtle = turtle; 
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		RegexMatcher rm = new RegexMatcher(DEFAULT_COLORPALETTE_FILE);
		String idx = "-1";
		try {
			idx = rm.findMatchingKey("#"+myTurtle.getPenColor());
		} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
			System.out.print("Oops! Your pen has no assigned color.");
			e.printStackTrace();
			throw new UnidentifiedCommandException("No pen color");
		}
		return Double.parseDouble(idx); 
	}

	
}
