package interpreter;
import java.util.List;

public class CommandFactory {
	public CommandFactory() {
		
	}

	public Command makeCommand(boolean isDouble, String commandName, List<Command> commandArgs) {
		if(isDouble) {
			double doubleArg = Double.parseDouble(commandName);
			return new DoubleCommand(doubleArg);
		}
		else {
			
		}
	}
}
