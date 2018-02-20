package command.maker;
public interface CommandMaker {
	/**
	 * Creates a Queue of Command objects given a Queue of user-inputted Strings
	 */
	public Queue<Command> parseQueue(Queue<String> stringCommands);
}
