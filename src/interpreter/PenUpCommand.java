package interpreter;

import java.util.List;

/**
 * Command class that, when executed, puts the Turtle's pen up so that it will not draw a line when the Turtle moves
 * @author Sarahbland
 *
 */
class PenUpCommand extends Command{
    /**
     * Creates new instance of command, which can be executed at the correct time
     * @param turtle is turtle whose pen should be put up
     */
    protected PenUpCommand(Turtle turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * Puts turtle's pen up, so that subsequent movements will not leave a trail
     * @return 0 always
     */
    @Override
    protected double execute() {
    	getActiveTurtles().hidePen();
	return 0;
    }

}