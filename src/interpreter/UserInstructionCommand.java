package interpreter;

import java.util.Map;

class UserInstructionCommand extends Command {

	protected UserInstructionCommand(Command command, Map<String, Double> vars, Map<String, String> userDefCommands, Map<String, Integer> userCommNumArgs) {
		System.out.println("hi");
	}
	
	@Override
	double execute() throws UnidentifiedCommandException {
		// TODO Auto-generated method stub
		return 0;
	}

}
