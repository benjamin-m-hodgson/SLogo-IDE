package interpreter;

import java.util.ArrayList;
import java.util.Map;

class IfElseCommand extends Command {
	private Turtle myTurtle;
	private Command myIfExprCommand; 
	private String myIfBody; 
	private String myElseBody;
	private Map<String, Double> myVariables; 
	private Map<String, String> myUserDefCommands; 
	private Map<String, Integer> myUserDefComNumArgs; 
	
	protected IfElseCommand(Command ifExprCommand, Command ifBody, Command elseBody, Turtle turtle, Turtle activeTurtles,
			Map<String, Double> variables, Map<String, String> userDefCommands, 
			Map<String, Integer> userDefComNumArgs) {
		myTurtle = turtle;
		myIfExprCommand = ifExprCommand;
		myIfBody = ((StringCommand)ifBody).getString(); ;
		myElseBody = ((StringCommand)elseBody).getString(); ; 
		myVariables = variables; 
		myUserDefCommands = userDefCommands;
		myUserDefComNumArgs = userDefComNumArgs;
		setActiveTurtles(activeTurtles);
	}

	@Override
	protected double execute() {
		double ifExprRetVal = 0;
		double ifElseRetVal = 0; 
			ifExprRetVal = myIfExprCommand.execute();
		String[] userInput;
		if (ifExprRetVal > 0) {
			System.out.println("if executed");
			CommandTreeBuilder buildIfBody = new CommandTreeBuilder(myVariables, myUserDefCommands, myUserDefComNumArgs); 
			userInput = myIfBody.split("\\s+");
			try {
				ifElseRetVal = buildIfBody.buildAndExecute(myTurtle, getActiveTurtles(), userInput, true);
			} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
				return ifElseRetVal; 
			}
		}
		else {
			System.out.println("else executed");
			CommandTreeBuilder buildElseBody = new CommandTreeBuilder(myVariables, myUserDefCommands, myUserDefComNumArgs); 
			userInput = myElseBody.split("\\s+");
			try {
				ArrayList<Turtle> turtleList = new ArrayList<Turtle>();
				turtleList.add(myTurtle);
				ifElseRetVal = buildElseBody.buildAndExecute(myTurtle, getActiveTurtles(), userInput, true);
			} catch (BadFormatException | UnidentifiedCommandException | MissingInformationException e) {
				return ifElseRetVal; 
			}
		}
		return ifElseRetVal; 
	}

}
