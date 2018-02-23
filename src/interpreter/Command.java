package interpreter;

public interface Command {
	/**
	 * Executes Commands by changing objects in the back-end (Turtles/Pens) or retrieving information
	 * @return double corresponding to return value of this command in SLogo
	 */
	public double execute();
	
	/**
	 * @return number of arguments necessary to allow this Command to be executed
	 */
	public int getNumArgs();
}
