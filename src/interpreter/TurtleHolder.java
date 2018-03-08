package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

import javafx.scene.Group;

/**
 * Class used to hide/manipulate collection of active Turtles.
 * @author Sarahbland
 * Note: based on CollectionHolder by Robert C. Duvall
 *
 */
public class TurtleHolder {
	private List<SingleTurtle> myCurrentTurtles;
	private List<SingleTurtle> mySharedTurtles;
	public TurtleHolder(List<SingleTurtle> turtles) {
		myCurrentTurtles = turtles;
		mySharedTurtles = deepCopy(myCurrentTurtles);
	}
	protected void applyToAllTurtles (Consumer<Turtle> action) {
		myCurrentTurtles.forEach(action);
		resetTemporaryTurtles();
	}
	protected void applyTemporarily(Consumer<Turtle> action) {
		mySharedTurtles.forEach(action);
	}
	protected void resetTemporaryTurtles() {
		mySharedTurtles = deepCopy(myCurrentTurtles);
	}
	protected void addTurtle(SingleTurtle turtle) {
		myCurrentTurtles.add(turtle);
		resetTemporaryTurtles();
	} 
	public List<SingleTurtle> getCopyTurtleList(){
		return mySharedTurtles;
	}
	public boolean hasTurtleWithID(double ID) {
		long countLong = mySharedTurtles.stream().filter(turtle -> turtle.getID()==ID).count();
		int count = Math.toIntExact(countLong);
		resetTemporaryTurtles();
		return count>0;	
	}
    private List<SingleTurtle> deepCopy (List<SingleTurtle> original) {
        List<SingleTurtle> result = new ArrayList<>();
        for (SingleTurtle c : original) {
            result.add(c.getCopy());
        }
        return result;
    }

}
