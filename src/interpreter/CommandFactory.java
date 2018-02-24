package interpreter;
import java.util.List;

public class CommandFactory {
	public CommandFactory() {
		
	}

	public Command makeDoubleCommand(String doubleString) {
		double doubleArg = Double.parseDouble(doubleString);
		return new DoubleCommand(doubleArg);
	}
	public Command makeCommand(String commandName, List<Command> commandArgs, Turtle turtle) {
		if(commandName.equals("Forward")) {
			return new MoveTurtleForwardCommand(commandArgs.get(0), turtle);
		}
		else if(commandName.equals("Backward")) {
			return new MoveTurtleBackwardCommand(commandArgs.get(0), turtle);
		}
		else if(commandName.equals("Left")) {
			return new RotateTurtleCounterclockwiseCommand(commandArgs.get(0), turtle);
		}
		else if(commandName.equals("Right")) {
			return new RotateTurtleClockwiseCommand(commandArgs.get(0), turtle);
		}
		else if(commandName.equals("SetPosition")) {
			return new SetPositionCommand(commangArgs.get(0), commandArgs.get(1), turtle);
		}
		else return null;
	}
}
