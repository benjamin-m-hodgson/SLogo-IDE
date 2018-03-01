package interpreter;

import java.util.Map;

class UserInstructionCommand extends Command {
	Command myNodeWithArgs;
	Map<String, Double> myVars;
	Map<String, String> myUserDefCommands;
	Map<String, Integer> myUserCommandNumArgs;

	protected UserInstructionCommand(Command command, Map<String, Double> vars, Map<String, String> userDefCommands, Map<String, Integer> userCommNumArgs) {
		myNodeWithArgs = command;
		myVars = vars;
		myUserDefCommands = userDefCommands;
		myUserCommandNumArgs = userCommNumArgs;
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		// TODO Auto-generated method stub
		return 0;
	}

}
