package interpreter;
import java.util.Queue;

public class Executor {
		private Queue<Command> myCommandQueue;
	public Executor(Queue<Command> commandQueue) {
		myCommandQueue = commandQueue;

	}
	/**
	 * Executes, in the proper order, each of the Commands in the Command Queue, and returns the appropriate double
	 * value of the final command executed
	 */
	public double executeQueue() {
		double finalReturn = 0;
		while(myCommandQueue.size()>0) {
			Command current = myCommandQueue.remove();
			finalReturn = current.execute();
		}
		return finalReturn;
	}
}
