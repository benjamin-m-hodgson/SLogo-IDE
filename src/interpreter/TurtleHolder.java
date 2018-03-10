package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Group;
import javafx.scene.image.ImageView;


/**
 * Class used to hide/manipulate collection of active Turtles. Dependent on Turtle to call
 * correct methods at the correct time. The only ways to modify the list/access modifiable
 * turtles is to: ask for a single turtle with a given ID, add a Turtle to the list, or replace
 * the entire list with a new list of turtles. This was designed to protect the Turtles
 * to the greatest degree possible.
 * @author Sarahbland
 * Note: based on CollectionHolder by Robert C. Duvall
 *
 */
/**
 * @author Sarahbland
 *
 */
public class TurtleHolder {
	private List<SingleTurtle> myCurrentTurtles;
	private List<SingleTurtle> mySharedTurtles;
	/**
	 * @param turtles is List of SingleTurtles this TurtleHolder holds and protects
	 */
	public TurtleHolder(List<SingleTurtle> turtles) {
		myCurrentTurtles = turtles;
		mySharedTurtles = deepCopy(myCurrentTurtles);
	}
	/**
	 * @param action is lambda that each turtle should do
	 */
	protected void applyToAllTurtles (Consumer<Turtle> action) {
		myCurrentTurtles.forEach(action);
		resetTemporaryTurtles();
	}
	/**
	 * @param action is lambda that each turtle should complete but should not
	 * affect actual turtles' properties
	 */
	protected void applyTemporarily(Consumer<Turtle> action) {
		mySharedTurtles.forEach(action);
	}
	/**
	 * resets the list of shared/temporary turtles after a change made to permanent turtles
	 */
	protected void resetTemporaryTurtles() {
		mySharedTurtles = deepCopy(myCurrentTurtles);
	}
	/**
	 * @param turtle is new turtle to be added
	 */
	protected void addTurtle(SingleTurtle turtle) {
		myCurrentTurtles.add(turtle);
		resetTemporaryTurtles();
	} 
	/**
	 * @return list of immutable turtles
	 */
	public List<SingleTurtle> getCopyTurtleList(){
		resetTemporaryTurtles();
		return mySharedTurtles;
	}
	/**
	 * @param turtles is list of turtles this holder should now hold
	 */
	protected void replaceTurtleList(List<SingleTurtle> turtles) {
		myCurrentTurtles = turtles;
		resetTemporaryTurtles();
	}
	/**
	 * Checks if collection contains a turtle with a given id
	 * @param ID is id desired
	 * @return true if collection contains it, false if not
	 */
	public boolean hasTurtleWithID(double ID) {
		long countLong = mySharedTurtles.stream().filter(turtle -> turtle.getID()==ID).count();
		int count = Math.toIntExact(countLong);
		resetTemporaryTurtles();
		return count>0;	
	}
	/**
	 * @param ID is id of turtle desired
	 * @return mutable turtle with this ID
	 */
	protected SingleTurtle getTurtleWithID(double ID) {
		for(SingleTurtle turtle : myCurrentTurtles) {
			if(turtle.getID()==ID) {
				return turtle;
			}
		}
		return mySharedTurtles.get(0); //returns a "fake" turtle that changing will not do anything to
	}
	/**
	 * Returns the ImageView held by the turtle with the given ID so it can be attached to scene
	 * @param ID is double ID of turtle whose ImageView is desired
	 * @return ImageView of turtle
	 */
	protected ImageView getTurtleWithIDImageView(double ID) {
		return getTurtleWithID(ID).getImageView();
	}
	/**
	 * This method is used to further protect turtles by having the front end only get
	 * the turtle's group to attach
	 * @param ID is ID of turtle whose penlines are desired
	 * @return Group
	 */
	protected Group getTurtleWithIDPenLines(double ID) {
		return getTurtleWithID(ID).getPenLines();
	}
	
    /**
     * Based on Robert Duvall's method
     * @param original is original list
     * @return deep copy of that list
     */
    private List<SingleTurtle> deepCopy (List<SingleTurtle> original) {
        List<SingleTurtle> result = new ArrayList<>();
        for (SingleTurtle c : original) {
            result.add(c.getCopy());
        }
        return result;
    }

}



