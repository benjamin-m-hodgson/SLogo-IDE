package interpreter;

//public interface Turtle {
//
///**
//* Returns the current x-position of the turtle
//*/
//public double getX();
//
///**
//* Returns the current y-position of the turtle
//*/
//public double getY();
//
///**
//* Returns the previous x-position of the turtle
//*/
//public double getOldX();
//
///**
//* Returns the previous y-position of the turtle
//*/
//public double getOldY();
//
///**
//* Copies the current values of X and Y into oldX and oldY
//*/
//public void setOld();
//
///**
//* Sets the y-position of the turtle
//*/
//public void setY(double y);
//
///**
//* Sets the x-position of the turtle
//*/
//public void setX(double x);
//
///**
//* Sets the visual image of the turtle to the image contained in filepath
//*/
//public void setImage(String filepath);
//
//}

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle {

	public static final String DEFAULT_NAME = "";
	public static final Color DEFAULT_PEN_COLOR = Color.BLACK;
	public static final double DEFAULT_X_POS = 0; 
	public static final double DEFAULT_Y_POS = 0; 
	public static final double DEFAULT_ANGLE = 0; 
	//	public static final ImageView DEFAULT_IMAGE = ;

	private String myName;
	private ImageView myImage; 
	private Pen myPen; 
	private boolean myVisibility; 

	private double myOldX;
	private double myOldY;
	private double myX;
	private double myY; 
	private double myAngle; 

	protected Turtle() {
		this(DEFAULT_NAME, new ImageView(), new Group());
	}

	protected Turtle(String name, ImageView image, Group penGroup) {
		myName = name; 
		myImage = image; 
		myPen = new Pen(penGroup); 
		myVisibility = true; 
		myOldX = DEFAULT_X_POS; 
		myOldY = DEFAULT_Y_POS; 
		myX = DEFAULT_X_POS; 
		myY = DEFAULT_Y_POS; 
		myAngle = DEFAULT_ANGLE; 
	}	



	// GETTERS
	/**
	 * Returns the current x-position of the turtle
	 */
	protected double getX() {
		return myX;
	}

	/**
	 * Returns the current y-position of the turtle
	 */
	protected double getY() {
		return myY;
	}

	/**
	 * Returns the previous x-position of the turtle
	 */
	protected double getOldX() {
		return myOldX;
	}

	/**
	 * Returns the previous y-position of the turtle
	 */
	protected double getOldY() {
		return myOldY;
	}

	protected double getAngle() {
		return myAngle; 
	}

	protected boolean getTurtleVisibility() {
		return myVisibility; 
	}

	protected boolean getPenVisibility() {
		return myPen.getVisibility(); 
	}



	// SETTERS
	protected void hideTurtle() {
		myVisibility = false; 
	}

	protected void showTurtle() {
		myVisibility = true; 
	}

	/**
	 * Sets the x-position of the turtle
	 */
	protected void setX(double x) {
		setXY(x, myY);
	}
	
	/**
	 * Sets the y-position of the turtle
	 */
	protected void setY(double y) {
		setXY(myX, y);
	}

	private void setXY(double x, double y) {
		setOld();
		myX = x; 
		myY = y; 
		myPen.drawLine(myOldX, myOldY, myX, myY);
	}

	/**
	 * Copies the current values of X and Y into oldX and oldY
	 */
	private void setOld() {
		myOldX = myX;
		myOldY = myY;
	}

	/**
	 * Sets the visual image of the turtle to the image contained in filepath
	 */
	protected void setImage(String filepath) {
		Image newImg = new Image(filepath);
		myImage.setImage(newImg);
	}

	protected void setPenColor(Color color) {
		myPen.setColor(color);
	}
	
	protected void setPenWidth(double width) {
		myPen.setWidth(width);
	}

	protected void showPen() {
		myPen.putPenDown();
	}

	protected void hidePen() {
		myPen.putPenUp();
	}

	// TODO make clear pen method
	protected void clearPen() {
	}





	/**
	 * Class representing the Pen each Turtle holds. Used to change pen attributes and draw lines based on the turtle's movement. 
	 * Depends on the UserView to attach to the screen and send to the Controller via the makeNewTurtle command a Group used to 
	 * house the Turtle's pen lines.
	 * @author Sarahbland
	 *
	 */
	private class Pen {
		Group myPenLines;
		Color myColor;
		double myWidth;
		boolean myIsDown;

		/**
		 * Single argument constructor for a pen (default values if the user does not specify)
		 * @param penlines is (already attached to stage) Group of lines drawn by the pen
		 */
		private Pen(Group penLines) {
			myPenLines = penLines;
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
		private Pen(Group penLines, Color color, double width) {
			myPenLines = penLines;
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
		private Pen(Group penlines, boolean down, Color color, double width) {
			myPenLines = penlines;
			myColor = color;
			myIsDown = down;
			myWidth = width;
		}

		/**
		 * 
		 * @return  true if pen is down (lines drawn are visible) and false if pen is up (invisible)
		 */
		private boolean getVisibility() {
			return myIsDown;
		}

		/**
		 * Makes lines drawn by pen visible
		 */
		private void putPenDown() {
			myIsDown = true;
		}

		/**
		 * Makes lines drawn by pen invisible
		 */
		private void putPenUp() {
			myIsDown = false;
		}


		/**
		 * @return current width of pen lines
		 */
		private double getWidth() {
			return myWidth;
		}

		/**
		 * Sets width of pen to a new value
		 * @param width is new width desired
		 */
		private void setWidth(double width) {
			myWidth = width;
		}

		/**
		 * @return pen Color
		 */
		private Color getColor() {
			return myColor;
		}

		/**
		 * Sets new pen color
		 * @param newColor is new Color of pen
		 */
		private void setColor(Color newColor) {
			myColor = newColor;
		}

		/**
		 * Draws a line on the screen when the Turtle moves, if the pen is currently down
		 * @param oldX is old x coordinate of turtle
		 * @param oldY is old y coordinate of turtle
		 * @param newX is new x coordinate of turtle
		 * @param newY is new y coordinate of turtle
		 */
		private void drawLine(double oldX, double oldY, double newX, double newY) {
			if(myIsDown) {
				Line line = new Line(oldX, oldY, newX, newY);
				line.setFill(myColor);
				line.setStrokeWidth(myWidth);
				myPenLines.getChildren().add(line);
			}
		}
	}

}