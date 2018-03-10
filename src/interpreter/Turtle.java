package interpreter;



import java.util.List;
import java.util.function.Consumer;

import java.net.MalformedURLException;

/**
 * Abstract class to represent functionality necessary for both single turtles and groups of
 * turtles. For all getters, for MultipleTurtles the last 
 * @author Sarahbland
 *
 */
/**
 * @author Sarahbland
 *
 */
public abstract class Turtle {

	// GETTERS
	/**
	 * @return double ID of the Turtle in question
	 */
	protected abstract double getID();
	

	/**
	 * Returns the current x-position of the turtle
	 */
	protected abstract double getX();

	/**
	 * Returns the current y-position of the turtle
	 */
	protected abstract double getY();

	/**
	 * Returns the previous x-position of the turtle
	 */
	protected abstract double getOldX();

	/**
	 * Returns the previous y-position of the turtle
	 */
	protected abstract double getOldY();

	/**
	 * @return angle of the turtle
	 */
	protected abstract double getAngle();

	/**
	 * @return true if turtle is visible, false if not
	 */
	protected abstract boolean getTurtleVisibility();

	/**
	 * @return true if turtle's pen is down, false if not
	 */
	protected abstract boolean getPenVisibility();

	/**
	 * @param ID is Id search for
	 * @return true if this Turtle has within it a SingleTurtle with this id
	 */
	protected abstract boolean containsTurtleWithID(String ID);


	// SETTERS
	/**
	 * hides turtle
	 */
	protected abstract void hideTurtle();

	/**
	 * shows turtle
	 */
	protected abstract void showTurtle();

	/**
	 * Sets the x-position of the turtle
	 */
	protected abstract void setX(double x);
	
	/**
	 * Sets the y-position of the turtle
	 */
	protected abstract void setY(double y);

	/**
	 * @param x is x-coordinate desired
	 * @param y is y-coordinate desire
	 * @return distance turtle moved
	 */
	protected abstract double setXY(double x, double y);

	/**
	 * Sets shape of turtle based
	 * @param idxKey is index of the desired shape in the TurtleShapes properties file
	 * @throws BadFormatException
	 * @throws UnidentifiedCommandException
	 * @throws MissingInformationException
	 * @throws MalformedURLException
	 */
	protected abstract void setShape(String idxKey) throws BadFormatException, UnidentifiedCommandException, MissingInformationException, MalformedURLException;

	/**
	 * Sets the visual image of the turtle to the image contained in filepath
	 */
	protected abstract void setImage(String filepath);


	/**
	 * sets pen color based on a hex colorcode
	 * @param colorCode is hex colorcode
	 */
	protected abstract void setPenColor(String colorCode);
	
	/**
	 * @param width is width of pen desired
	 */
	protected abstract void setPenWidth(double width);
	
	/**
	 * @param angle is angle of turtle desired
	 */
	protected abstract void setAngle(double angle);
	
	/**
	 * shows pen
	 */
	protected abstract void showPen();

	/**
	 * hides pen
	 */
	protected abstract void hidePen();

	/**
	 * clears pen
	 */
	protected abstract void clearPen();
	
	/**
	 * @return current color code string of Pen
	 */
	protected abstract String getPenColor();

	/**
	 * @return image Idx of current image
	 */
	protected abstract double getImageIdx();
	
	/**
	 * @return Immutable single turtle (either copy of this single turtle or copy of last
	 * single turtle in list of multiple turtles)
	 */
	protected abstract SingleTurtle toSingleTurtle();
	/**
	 * @return number of turtles in this Turtle
	 */
	protected abstract int size();
	/**
	 * Executes the given lambda for each turtle contained in the Turtle in turn
	 * @param action is lambda of action to be done on each turtle
	 */
	protected abstract void executeSequentially(Consumer<Turtle> action);
	/**
	 * Calculates distance between two points
	 * @param oldX is first x coord
	 * @param oldY is first y coord
	 * @param x is second x coord
	 * @param y is second y coord
	 * @return distance
	 */
	protected abstract double calcDistance(double oldX, double oldY, double x, double y);
	/**
	 * Adds a turtle to the given Turtle
	 * @param turtle is SingleTurtle to be added
	 * @return MultipleTurtles containing updated list of turtles
	 */
	protected abstract MultipleTurtles addTurtle(SingleTurtle turtle);
	/**
	 * Returns actual (mutable) turtle with the given id
	 * @param id
	 * @return mutable SingleTurtle with given ID
	 * @throws UnidentifiedCommandException
	 */
	protected abstract SingleTurtle getTurtleWithID(String id) throws UnidentifiedCommandException;
	/**
	 * Replaces entire list of turtles within this Turtle with a new one
	 * @param newTurtles is list of new turtles
	 * @return this updated Turtle
	 */
	protected abstract Turtle replaceTurtles(List<SingleTurtle> newTurtles);
	
	/**
	 * @return immutable list of immutable Turtles contained in this Turtle
	 */
	protected abstract List<SingleTurtle> getAllImmutableTurtles();
}


