package interpreter;
import java.util.List;

public class CommandFactory {


	public Command makeCommand() {
		return null;
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
		else if(commandName.equals("SetHeading")) {
			return new SetHeadingCommand(commandArgs.get(0), turtle);
		}
		else if(commandName.equals("SetTowards")) {
			return new SetTowardsCommand(commandArgs.get(0), commandArgs.get(1), turtle);
		}
		else if(commandName.equals("SetPosition")) {
			return new SetPositionCommand(commandArgs.get(0), commandArgs.get(1), turtle);
		}
		else if(commandName.equals("PenDown")) {
			return new PenDownCommand(turtle);
		}
		else if(commandName.equals("PenUp")) {
			return new PenUpCommand(turtle);
		}
		else if(commandName.equals("ShowTurtle")) {
			return new ShowTurtleCommand(turtle);
		}
		else if(commandName.equals("HideTurtle")) {
			return new HideTurtleCommand(turtle);
		}
		else if(commandName.equals("Home")) {
			return new HomeCommand(turtle);
		}
		else if(commandName.equals("ClearScreen")) {
			return new ClearScreenCommand(turtle);
		}
		else if(commandName.equals("XCoordinate")) {
			return new XCoordinateQueryCommand(turtle);
		}
		else if(commandName.equals("YCoordinate")) {
			return new YCoordinateQueryCommand(turtle);
		}
		else if(commandName.equals("Sum")) {
			return new SumCommand(commandArgs.get(0), commandArgs.get(1));
		}
		else if(commandName.equals("Difference")) {
			return new DifferenceCommand(commandArgs.get(0), commandArgs.get(1));
		}
		else if(commandName.equals("Product")) {
			return new ProductCommand(commandArgs.get(0), commandArgs.get(1));
		}
		else if(commandName.equals("Quotient")) {
			return new QuotientCommand(commandArgs.get(0), commandArgs.get(1));
		}

		// TODO: handle returning null value
		return null;
	}
}
