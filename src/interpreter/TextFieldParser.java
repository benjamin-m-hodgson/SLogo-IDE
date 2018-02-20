package interpreter;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import command.Command;

public interface TextFieldParser throws ParseException {
	/**
	 * Returns and UnmodifiableMap of string variable keys to their double values
	 */
	public Map<String, Double> getVariables();
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 */
	public Queue<Command> parseText(String text);
	/**
	 * Returns an ImmutableList of the available/stored User Commands
	 */
	public List<String> getUserCommands();
}
