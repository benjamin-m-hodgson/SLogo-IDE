package interpreter;

import java.util.Map;

class MakeUserInstructionCommand extends Command {
	private String myCommandName; 
	private String myCommandVars; 
	private String myCommandContent;
	private Map<String, Double> myVariables;
	private Map<String, String> myUserCommands; 
	
	protected MakeUserInstructionCommand(Command commandName, Command commandVars, Command commandContent, 
			Map<String, Double> variables, Map<String, String> userCommands) {
		myCommandName = ((StringCommand)commandName).getString();
		myCommandVars = ((StringCommand)commandVars).getString();
		myCommandContent = ((StringCommand)commandContent).getString();
		myVariables = variables;
		myUserCommands = userCommands; 
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		myUserCommands.put(myCommandName, myCommandContent);
		return 1.0;
	}

}
