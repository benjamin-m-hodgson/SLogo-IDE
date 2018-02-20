package mediator;

public interface Executor {
	/**
	 * Executes, in the proper order, each of the Commands in the Command Queue, and returns the appropriate double
	 * value of the final command executed
	 */
	public double executeQueue();
}
