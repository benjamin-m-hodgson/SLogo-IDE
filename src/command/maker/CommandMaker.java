package command.maker;

import java.util.Queue;

import command.Command;

public interface CommandMaker {
	/**
	 * Creates a Queue of Command objects given a Queue of user-inputted Strings
	 */
	public Queue<Command> parseQueue(Queue<String> stringCommands);
	
	/**
	 * Handle parsing individual text String commands
	 */
	public void PARSING_BLACK_BOX();

}
