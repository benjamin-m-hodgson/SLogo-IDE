package interpreter;

public interface TextFieldParser {
	/**
	 * Returns and UnmodifiableMap of string variable keys to their double values
	 */
	public Map<String, Double> getVariables();
	/**
	 * Returns a Queue of commands given a String of concatenated commands (chops up the commands 
	 * and sends them individually to CommandMaker)
	 */
	public Queue<Command> parseText(String);
	/**
	 * Returns an ImmutableList of the available/stored User Commands
	 */
	public List<String> getUserCommands();
}
