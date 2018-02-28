package interpreter;

/**
 * @author Susie Choi
 *
 */
class IfCommand implements Command{
	Turtle myTurtle;
	private Command myIfExprCommand; 
	private String myIfBody; 
	
	protected IfCommand(Command ifExprCommand, Command ifBody, Turtle turtle) {
		myTurtle = turtle;
		myIfExprCommand = ifExprCommand;
		myIfBody = ((StringCommand)ifBody).getString(); 
	}

	public double execute() { // TODO discuss throwing of exceptions 
		double ifExprRetVal = 0;
		double ifBodyRetVal = 0; 
		try {
			ifExprRetVal = myIfExprCommand.execute();
		} catch (UnidentifiedCommandException e1) {
			return ifBodyRetVal; 
		} 
		if (ifExprRetVal > 0) {
			System.out.println("if executed");
			CommandTreeBuilder buildIfBody = new CommandTreeBuilder(); 
			String[] userInput = myIfBody.split("\\s+");
			try {
				ifBodyRetVal = buildIfBody.buildAndExecute(myTurtle, userInput);
			} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
				return ifBodyRetVal; 
			}
		}
		else {
			System.out.println("if DID NOT execute");
		}
		return ifBodyRetVal; 
	}
}
