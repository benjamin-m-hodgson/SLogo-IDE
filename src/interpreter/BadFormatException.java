package interpreter;

class BadFormatException extends Exception {
	protected BadFormatException(String text) {
		super("BadFormatException "+text);
	}
}
