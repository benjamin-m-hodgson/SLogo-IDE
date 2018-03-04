package interpreter;

import java.util.Map;

class MakeUserInstructionCommand extends Command {
	private String myCommandName; 
	private String myCommandVars; 
	private String myCommandContent;
	private Map<String, Double> myVariables;
	private Map<String, String> myUserCommands; 
	private Map<String, Integer> myUserCommandsNumArgs; 
	
	protected MakeUserInstructionCommand(Command commandName, Command commandVars, Command commandContent, 
			Map<String, Double> variables, Map<String, String> userCommands, Map<String, Integer> userCommandsNumArgs) {
		myCommandName = ((StringCommand)commandName).getString();
		myCommandVars = ((StringCommand)commandVars).getString();
		myCommandContent = ((StringCommand)commandContent).getString();
		myVariables = variables;
		myUserCommands = userCommands; 
		myUserCommandsNumArgs = userCommandsNumArgs;
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		myUserCommands.put(myCommandName, myCommandContent);
		myUserCommandsNumArgs.put(myCommandName, myCommandVars.split("\\s+").length);
		return 1.0;
	}

}
