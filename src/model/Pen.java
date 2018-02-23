package model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
/**
 * Class representing the Pen each Turtle holds. Used to change pen attributes and draw lines based on the turtle's movement. 
 * Depends on the UserView to attach to the screen and send to the Controller via the makeNewTurtle command a Group used to 
 * house the Turtle's pen lines.
 * @author Sarahbland
 *
 */
public class Pen {
	Group myPenLines;
	Color myColor;
	double myWidth;
	boolean myIsDown;
	
	/**
	 * Single argument constructor for a pen (default values if the user does not specify)
	 * @param penlines is (already attached to stage) Group of lines drawn by the pen
	 */
	public Pen(Group penlines) {
		myPenLines = penlines;
		myColor = Color.BLACK;
		myWidth = 0.0;
		myIsDown = true;
	}
	/**
	 * Constructor for a pen with default state as pen visible
	 * @param penlines is (already-attached-to-stage) Group which will house lines drawn by Pen
	 * @param color is initial color of Pen
	 * @param width is initial width of Pen
	 */
	public Pen(Group penlines, Color color, double width) {
		myPenLines = penlines;
		myColor = color;
		myIsDown = true;
		myWidth = width;
	}

	/**
	 * Fully populated constructor for a pen
	 * @param penlines is (already-attached-to-stage) Group which will house lines drawn by Pen
	 * @param down is initial state of Pen (true if pen is down, false if up)
	 * @param color is initial color of Pen
	 * @param width is initial width of Pen
	 */
	public Pen(Group penlines, boolean down, Color color, double width) {
		myPenLines = penlines;
		myColor = color;
		myIsDown = down;
		myWidth = width;
	}

	/**
	 * 
	 * @return  true if pen is down (lines drawn are visible) and false if pen is up (invisible)
	 */
	public boolean getPenVisibility() {
		return myIsDown;
	}

	/**
	 * Sets visibility of lines drawn by pen
	 * @param penDown is boolean that is true if pen is visible (down), false if pen is invisible (up)
	 */
	public void setPenVisibility(boolean penDown) {
		myIsDown = penDown;
	}

	/**
	 * @return current width of pen lines
	 */
	public double getPenWidth() {
		return myWidth;
	}

	/**
	 * Sets width of pen to a new value
	 * @param width is new width desired
	 */
	public void setPenWidth(double width) {
		myWidth = width;
	}

	/**
	 * @return pen Color
	 */
	public Color getPenColor() {
		return myColor;
	}

	/**
	 * Sets new pen color
	 * @param newColor is new Color of pen
	 */
	public void setPenColor(Color newColor) {
		myColor = newColor;
	}
	/**
	 * Draws a line on the screen when the Turtle moves, if the pen is currently down
	 * @param oldX is old x coordinate of turtle
	 * @param oldY is old y coordinate of turtle
	 * @param newX is new x coordinate of turtle
	 * @param newY is new y coordinate of turtle
	 */
	public void drawLine(double oldX, double oldY, double newX, double newY) {
		if(myIsDown) {
			Line line = new Line(oldX, oldY, newX, newY);
			line.setFill(myColor);
			line.setStrokeWidth(myWidth);
			myPenLines.getChildren().add(line);
		}
	}
	
}
