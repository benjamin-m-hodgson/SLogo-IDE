package command.maker;

import command.Command;

public interface CommandMaker {
	/**
	 * Creates a Queue of Command objects given a Queue of user-inputted Strings
	 */
	public Command parseCommand(String stringCommand);
	
	/**
	 * Handle parsing individual text String commands
	 */
	public void PARSING_BLACK_BOX();

}
