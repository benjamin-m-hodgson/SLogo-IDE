package interpreter;


import java.util.function.Consumer;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class Turtle {

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

	protected abstract SingleTurtle toSingleTurtle();
	protected abstract int size();
	protected abstract void executeSequentially(Consumer<Turtle> action);
	
}
