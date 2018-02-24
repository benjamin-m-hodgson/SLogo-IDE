package interpreter;

class DoubleCommand implements Command {
	double myDouble;
	protected DoubleCommand(double val) {
		myDouble = val;
	}
	public double execute() {
		return myDouble;
	}
	public int getNumArgs() {
		return 1;
	}
}
