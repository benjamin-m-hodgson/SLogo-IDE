package interpreter;



import java.util.List;
import java.util.function.Consumer;

import java.net.MalformedURLException;

public abstract class Turtle {

	// GETTERS
	/**
	 * @return double ID of the Turtle in question
	 */
	public abstract double getID();
	

	/**
	 * Returns the current x-position of the turtle
	 */
	public abstract double getX();

	/**
	 * Returns the current y-position of the turtle
	 */
	public abstract double getY();

	/**
	 * Returns the previous x-position of the turtle
	 */
	protected abstract double getOldX();

	/**
	 * Returns the previous y-position of the turtle
	 */
	protected abstract double getOldY();

	protected abstract double getAngle();

	protected abstract boolean getTurtleVisibility();

	protected abstract boolean getPenVisibility();

	protected abstract boolean containsTurtleWithID(String ID);


	// SETTERS
	protected abstract void hideTurtle();

	protected abstract void showTurtle();

	/**
	 * Sets the x-position of the turtle
	 */
	protected abstract void setX(double x);
	
	/**
	 * Sets the y-position of the turtle
	 */
	protected abstract void setY(double y);

	protected abstract double setXY(double x, double y);

	public abstract void setShape(String idxKey) throws BadFormatException, UnidentifiedCommandException, MissingInformationException, MalformedURLException;

	/**
	 * Sets the visual image of the turtle to the image contained in filepath
	 */
	public abstract void setImage(String filepath);


	protected abstract void setPenColor(String colorCode);
	
	protected abstract void setPenWidth(double width);
	
	protected abstract void setAngle(double angle);
	protected abstract void showPen();

	protected abstract void hidePen();

	protected abstract void clearPen();
	
	protected abstract String getPenColor();

//	protected abstract void setImageIdx(double imgIdx);

	protected abstract double getImageIdx();
	
	protected abstract SingleTurtle toSingleTurtle();
	protected abstract int size();
	protected abstract void executeSequentially(Consumer<Turtle> action);
	protected abstract double calcDistance(double oldX, double oldY, double x, double y);
	protected abstract MultipleTurtles addTurtle(SingleTurtle turtle);
	protected abstract SingleTurtle getTurtleWithID(String id) throws UnidentifiedCommandException;
	protected abstract Turtle replaceTurtles(List<SingleTurtle> newTurtles);
}

