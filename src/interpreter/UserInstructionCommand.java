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

	protected UserInstructionCommand(Turtle turtle, Turtle activeTurtles, Command command, Command args, Map<String, Double> vars, Map<String, String> userDefCommands, Map<String, Integer> userDefCommandsNumArgs) {
		myBuilder = new CommandTreeBuilder(vars, userDefCommands, userDefCommandsNumArgs);
		myTurtle = turtle; 
		myUserCommName = ((StringCommand)command).getString();
		setActiveTurtles(activeTurtles);
		String unparsedCommContent = userDefCommands.get(myUserCommName);
		ArrayList<String> parsedCommContent = new ArrayList<String>(); 


		Pattern r = Pattern.compile("\\[(.*?)\\]");
		Matcher m = r.matcher(unparsedCommContent);
		int count = 0;
		while (m.find() && count < 2) {
			if (m.start()+2 >= m.end()-2) {
				parsedCommContent.add(""); 
			}
			else {
				if (m.end() + 1 < unparsedCommContent.length() && unparsedCommContent.substring(m.end()+1, m.end()+2).equals("]")) {
					parsedCommContent.add(unparsedCommContent.substring(m.start()+2, m.end()));
				}
				else {
					parsedCommContent.add(unparsedCommContent.substring(m.start()+2, m.end()-2));
				}
			}
			count++; 
		}		
		HashMap<String, String> userCommArgs = new HashMap<String, String>(); 
		String[] argNames = {}; 
		if (parsedCommContent.get(0).length() > 0) {
			argNames = 	parsedCommContent.get(0).split("\\s+"); // [:length, :width]
		}
		String[] argVals = ((StringCommand)args).getString().substring(1).split("\\s+"); // [50, 70] 
		for (int i=0; i<argNames.length; i++) {
			userCommArgs.put(argNames[i], argVals[i]);
		}

		String rawCommContent = parsedCommContent.get(1); // Forward :length Right 90 Forward :width
		String[] splitCommContent = rawCommContent.split("\\s+"); // [Forward,:length,Right,90,Forward,:width]
		for (int j=0; j<splitCommContent.length; j++) {
			if (userCommArgs.containsKey(splitCommContent[j])) {
				splitCommContent[j] = userCommArgs.get(splitCommContent[j]).toString(); 
			}
		}
		myUserCommContent = splitCommContent; 
		for (String s : myUserCommContent) System.out.println(s);
	}

	@Override
	protected double execute() {
		double retVal = 0.0;
		try {
			retVal = myBuilder.buildAndExecute(myTurtle, getActiveTurtles(), myUserCommContent, true);
		} catch (BadFormatException e) {
			e.printStackTrace();
		} catch (MissingInformationException e) {
			e.printStackTrace();
		} catch (UnidentifiedCommandException e) {
			e.printStackTrace();
		}
		return retVal; 
	}

}
