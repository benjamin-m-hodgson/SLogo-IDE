package interpreter;

class DoubleCommand extends Command {
    private double myDouble;
    protected DoubleCommand(double val) {
	myDouble = val;
    }
    @Override
    protected double execute() {
	return myDouble;
    }
    protected int getNumArgs() {
	return 1;
    }
}
