package interpreter;

class GetPenColorCommand extends Command {
	
	public static final String DEFAULT_COLORPALETTE_FILE = "interpreter/ColorPalette";
	Turtle myTurtle; 

	protected GetPenColorCommand(Turtle turtle) {
		myTurtle = turtle; 
	}
	
	@Override
	double execute() {
		RegexMatcher rm = new RegexMatcher(DEFAULT_COLORPALETTE_FILE);
		String idx = "-1";
		try {
			idx = rm.findMatchingKey("#"+myTurtle.getPenColor());
		} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
			System.out.print("Oops! Your pen has no assigned color.");
			e.printStackTrace();
		}
		return Double.parseDouble(idx); 
	}

	
}
