package interpreter;

public class IfElseCommand implements Command {
	Turtle myTurtle;
	private Command myIfExprCommand; 
	private String myIfBody; 
	private String myElseBody;
	
	protected IfElseCommand(Command ifExprCommand, Command ifBody, Command elseBody, Turtle turtle) {
		myTurtle = turtle;
		myIfExprCommand = ifExprCommand;
		myIfBody = ((StringCommand)ifBody).getString(); ;
		myElseBody = ((StringCommand)elseBody).getString(); ; 
	}
	
	@Override
	public double execute() throws UnidentifiedCommandException {
		double ifExprRetVal = 0;
		double ifElseRetVal = 0; 
		try {
			ifExprRetVal = myIfExprCommand.execute();
		} catch (UnidentifiedCommandException e1) {
			return ifElseRetVal; 
		} 
		String[] userInput;
		if (ifExprRetVal > 0) {
			System.out.println("if executed");
			CommandTreeBuilder buildIfBody = new CommandTreeBuilder(); 
			userInput = myIfBody.split("\\s+");
			try {
				ifElseRetVal = buildIfBody.buildAndExecute(myTurtle, userInput, true);
			} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
				return ifElseRetVal; 
			}
		}
		else {
			System.out.println("else executed");
			CommandTreeBuilder buildElseBody = new CommandTreeBuilder(); 
			userInput = myElseBody.split("\\s+");
			try {
				ifElseRetVal = buildElseBody.buildAndExecute(myTurtle, userInput, true);
			} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
				return ifElseRetVal; 
			}
		}
		return ifElseRetVal; 
	}

}
