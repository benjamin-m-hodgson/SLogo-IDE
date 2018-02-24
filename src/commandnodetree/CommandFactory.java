package commandnodetree;
import java.util.List;

import interpreter.Command;

class CommandFactory {
	public CommandFactory() {
		
	}

	public Command makeDoubleCommand(String doubleString) {
		double doubleArg = Double.parseDouble(commandName);
		return new DoubleCommand(doubleArg);
	}
	public Command makeCommand(String commandName, List<Command> commandArgs) {

	}
}