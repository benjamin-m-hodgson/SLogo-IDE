package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UserInstructionCommand extends Command {
	private String myUserCommName; // corner
	private String[] myUserCommContent; 
	private Turtle myTurtle; 
	private CommandTreeBuilder myBuilder; 

	protected UserInstructionCommand(Turtle turtle, Command command, Command args, Map<String, Double> vars, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		myBuilder = new CommandTreeBuilder(vars, userDefCommands, userDefCommandsNumArgs);
		myTurtle = turtle; 
		myUserCommName = ((StringCommand)command).getString();
		String unparsedCommContent = userDefCommands.get(myUserCommName);
		ArrayList<String> parsedCommContent = new ArrayList<String>(); 

		Pattern r = Pattern.compile("\\[(.*?)\\]");
		Matcher m = r.matcher(unparsedCommContent);
		int count = 0;
		while (m.find() && count < 2) {
			parsedCommContent.add(unparsedCommContent.substring(m.start()+2, m.end()-2));
			count++; 
		}		
		HashMap<String, String> userCommArgs = new HashMap<String, String>(); 
		String[] argNames = 	parsedCommContent.get(0).split("\\s+"); // [:length, :width]
		String[] argVals = ((StringCommand)args).getString().substring(1).split("\\s+"); // [50, 70] 
		for (int i=0; i<argNames.length; i++) {
			userCommArgs.put(argNames[i], argVals[i]);
		}
		
		String rawCommContent = parsedCommContent.get(1); // Forward :length Right 90 Forward :width
		String[] splitCommContent = rawCommContent.split("\\s+"); // [Forward,:length,Right,90,Forward,:width]
		for (int j=0; j<splitCommContent.length; j++) {
			if (userCommArgs.containsKey(splitCommContent[j])) {
//				System.out.println("REPLACING "+splitCommContent[j]);
				splitCommContent[j] = userCommArgs.get(splitCommContent[j]).toString(); 
//				System.out.println(" WITH "+splitCommContent[j]);
			}
		}
		
		myUserCommContent = splitCommContent; 
		
	}

	@Override
	protected double execute() {
		double retVal = 0.0;
		try {
			retVal = myBuilder.buildAndExecute(myTurtle, myUserCommContent, true);
		} catch (BadFormatException e) {
			// TODO ELABORATE
		} catch (MissingInformationException e) {
			// TODO ELABORATE
		} catch (UnidentifiedCommandException e) {
			// TODO ELABORATE
		}
		return retVal; 
	}

}
