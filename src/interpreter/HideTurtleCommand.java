package interpreter;

import java.util.List;

/**
 * Command class that, when executed, makes the Turtle invisible
 * @author Sarahbland
 *
 */
class HideTurtleCommand extends Command{
    List<Turtle> myTurtles;
    /**
     * Creates new instance of command, which can be executed at the correct time
     * @param turtle is turtle who should be made invisible
     */
    protected HideTurtleCommand(List<Turtle> turtles) {
    		setActiveTurtles(turtles);
    }
    /**
     * Shows turtle's image to the user
     * @return 0 always
     */
    @Override
    protected double execute() {
    	for(Turtle myTurtle : getActiveTurtles()) {
    	 	myTurtle.hideTurtle();
    	}
    		return 0;
    }

}
