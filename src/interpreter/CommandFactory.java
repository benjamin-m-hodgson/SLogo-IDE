package interpreter;
import java.util.List;
import java.util.Map;

/**
 * Factory class to make a command given a list of Command arguments and the String
 * id of the command. Dependent on TextFieldParser to put the commands in their "proper"
 * form (translated from what the user put in), on the Command class to execute proper, and
 * on CommandTreeBuilder to build the tree correctly and thus give each command its proper 
 * number of arguments.
 * @author Sarahbland
 *
 */
public class CommandFactory {
	Map<String, Double> myVariables; 
	Map<String, String> myUserDefCommands;
	Map<String, Integer> myUserDefCommandsNumArgs;
	
	protected CommandFactory(Map<String, Double> variables, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		myVariables = variables; 
		myUserDefCommands = userDefCommands; 
		myUserDefCommandsNumArgs = userDefCommandsNumArgs;
	}


	protected Command makeDoubleCommand(String doubleString) {
		double doubleArg = Double.parseDouble(doubleString);
		return new DoubleCommand(doubleArg);
	}

	protected Command makeCommand(String commandName, List<Command> commandArgs, Turtle turtles, Turtle activeTurtles) {
		if(commandName.equals("Forward")) {
			return new MoveTurtleForwardCommand(commandArgs.get(0), activeTurtles, myVariables);
		}
		else if(commandName.equals("Backward")) {
			return new MoveTurtleBackwardCommand(commandArgs.get(0), activeTurtles, myVariables);
		}
		else if(commandName.equals("Left")) {
			return new RotateTurtleCounterclockwiseCommand(commandArgs.get(0), activeTurtles, myVariables);
		}
		else if(commandName.equals("Right")) {
			return new RotateTurtleClockwiseCommand(commandArgs.get(0), activeTurtles, myVariables);
		}
		else if(commandName.equals("SetHeading")) {
			return new SetHeadingCommand(commandArgs.get(0), activeTurtles, myVariables);
		}
		else if(commandName.equals("SetTowards")) {
			return new SetTowardsCommand(commandArgs.get(0), commandArgs.get(1), activeTurtles, myVariables);
		}
		else if(commandName.equals("SetPosition")) {
			return new SetPositionCommand(commandArgs.get(0), commandArgs.get(1), activeTurtles, myVariables);
		}
		else if(commandName.equals("PenDown")) {
			return new PenDownCommand(activeTurtles);
		}
		else if(commandName.equals("PenUp")) {
			return new PenUpCommand(activeTurtles);
		}
		else if(commandName.equals("ShowTurtle")) {
			return new ShowTurtleCommand(activeTurtles);
		}
		else if(commandName.equals("HideTurtle")) {
			return new HideTurtleCommand(activeTurtles);
		}
		else if(commandName.equals("Home")) {
			return new HomeCommand(activeTurtles);
		}
		else if(commandName.equals("ClearScreen")) {
			return new ClearScreenCommand(activeTurtles);
		}
		else if(commandName.equals("XCoordinate")) {
			return new XCoordinateQueryCommand(activeTurtles);
		}
		else if(commandName.equals("YCoordinate")) {
			return new YCoordinateQueryCommand(activeTurtles);
		}
		else if (commandName.equals("Heading")) {
			return new HeadingQueryCommand(activeTurtles);
		}
		else if (commandName.equals("IsPenDown")) {
			return new IsPenDownQueryCommand(activeTurtles);
		}
		else if (commandName.equals("IsShowing")) {
			return new IsShowingQueryCommand(activeTurtles);
		}
		else if(commandName.equals("Sum")) {
			return new SumCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Difference")) {
			return new DifferenceCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Product")) {
			return new ProductCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Quotient")) {
			return new QuotientCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Remainder")) {
			return new RemainderCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Minus")) {
			return new MinusCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("Random")) {
			return new RandomCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("Sine")) {
			return new SineCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("Cosine")) {
			return new CosineCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("Tangent")) {
			return new TangentCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("ArcTangent")) {
			return new ArcTangentCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("NaturalLog")) {
			return new NaturalLogCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("Power")) {
			return new PowerCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Pi")) {
			return new PiCommand();
		}
		else if(commandName.equals("LessThan")) {
			return new LessThanCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("GreaterThan")) {
			return new GreaterThanCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Equal")) {
			return new EqualCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("NotEqual")) {
			return new NotEqualCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("And")) {
			return new AndCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Or")) {
			return new OrCommand(commandArgs.get(0), commandArgs.get(1), myVariables, activeTurtles);
		}
		else if(commandName.equals("Not")) {
			return new NotCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("DoTimes")||commandName.equals("Repeat")) {
			return new DoTimesCommand(commandArgs.get(0), commandArgs.get(1), commandArgs.get(2), activeTurtles, turtles, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		}
		else if(commandName.equals("If")) {
			return new IfCommand(commandArgs.get(1), commandArgs.get(0), turtles, activeTurtles, myVariables, myUserDefCommands, myUserDefCommandsNumArgs); 
		}
		else if (commandName.equals("IfElse")) {
			return new IfElseCommand(commandArgs.get(2), commandArgs.get(0), commandArgs.get(1), turtles, activeTurtles, myVariables, myUserDefCommands, myUserDefCommandsNumArgs); 
		}
		else if (commandName.equals("MakeVariable")) {
			return new MakeVariableCommand(commandArgs.get(0), commandArgs.get(1), myVariables); 
		}
		else if (commandName.equals("MakeUserInstruction")) {
			return new MakeUserInstructionCommand(commandArgs.get(0), commandArgs.get(1), commandArgs.get(2), myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		}
		else if (commandName.equals("UserInstruction")) {
			return new UserInstructionCommand(turtles, activeTurtles, commandArgs.get(0), commandArgs.get(1), myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		}
		else if(commandName.equals("SetBackground")) {
			return new SetBackgroundColorCommand(commandArgs.get(0), myVariables, activeTurtles);
		}
		else if(commandName.equals("SetPenColor")) {
			return new SetPenColorCommand(commandArgs.get(0), activeTurtles, myVariables);
		}
		else if(commandName.equals("SetPenSize")) {
			return new SetPenSizeCommand(activeTurtles, commandArgs.get(0), myVariables);
		}
		else if (commandName.equals("SetShape")) {
			return new SetShapeCommand(activeTurtles, commandArgs.get(0), myVariables);
		}
		else if (commandName.equals("GetPenColor")) {
			return new GetPenColorCommand(activeTurtles);
		}
		else if (commandName.equals("GetShape")) {
			return new GetShapeCommand(activeTurtles);
		}
		else if (commandName.equals("SetPalette")) {
			return new SetPaletteCommand(commandArgs.get(0), commandArgs.get(1), commandArgs.get(2), commandArgs.get(3), myVariables);
		}
		else if(commandName.equals("For")) {
			return new ForCommand(commandArgs.get(0), commandArgs.get(1), commandArgs.get(2), commandArgs.get(3), commandArgs.get(4), turtles, activeTurtles, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		}
		else if(commandName.equals("ID")) {
			return new IDQueryCommand(activeTurtles);
		}
		else if(commandName.equals("Turtles")) {
			return makeDoubleCommand("" + turtles.size());
		}
		else if(commandName.equals("Tell")) {
			return new TellCommand(commandArgs.get(0), activeTurtles, turtles);
		}
		else if(commandName.equals("Ask")) {
			return new AskCommand(commandArgs.get(0), commandArgs.get(1), turtles, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		}
		else if(commandName.equals("AskWith")) {
			return new AskWithCommand(commandArgs.get(0), commandArgs.get(1), turtles, myVariables, myUserDefCommands, myUserDefCommandsNumArgs);
		}
		else {
			return new StringCommand(commandName);
		}
	}
}
