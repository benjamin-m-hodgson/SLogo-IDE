package interpreter;

/**
 * @author Susie Choi
 *
 */
class IfCommand implements Command{
	Turtle myTurtle;
	private Command myIfExprCommand; 
	private String myIfBody; 
	
	protected IfCommand(Command ifExprCommand, String ifBody, Turtle turtle) {
		myTurtle = turtle;
		myIfExprCommand = ifExprCommand;
		myIfBody = ifBody; 
	}

	public double execute() {
		double ifExprRetVal = myIfExprCommand.execute(); 
		if (ifExprRetVal > 0) {
			CommandTreeBuilder buildIfBody = new CommandTreeBuilder(); 
			String[] userInput = myIfBody.split("\\s+");
			buildIfBody.buildAndExecute(myTurtle, );
		}
	}
}
