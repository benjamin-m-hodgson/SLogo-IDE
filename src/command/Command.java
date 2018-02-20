package command;

public interface Command {
	/**
	 * Executes Commands by changing objects in the back-end (Turtles/Pens) or retrieving information
	 * @param returnedValue is value returned by previous command if this command is part of a concatenated row of commands
	 * @return double corresponding to return value of this command in SLogo
	 */
	public double execute(double returnedValue);
}
