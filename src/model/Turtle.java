package model;

import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

//public interface Turtle {
//    
//    /**
//     * Returns the current x-position of the turtle
//     */
//    public double getX();
//    
//    /**
//     * Returns the current y-position of the turtle
//     */
//    public double getY();
//    
//    /**
//     * Returns the previous x-position of the turtle
//     */
//    public double getOldX();
//    
//    /**
//     * Returns the previous y-position of the turtle
//     */
//    public double getOldY();
//    
//    /**
//     * Copies the current values of X and Y into oldX and oldY
//     */
//    public void setOld();
//    
//    /**
//     * Sets the y-position of the turtle
//     */
//    public void setY(double y);
//    
//    /**
//     * Sets the x-position of the turtle
//     */
//    public void setX(double x);
//    
//    /**
//     * Sets the visual image of the turtle to the image contained in filepath
//     */
//    public void setImage(String filepath);
//    
//}

// TODO repackage so turtle is with controller? 
class Turtle {
	
	public static final String DEFAULT_NAME = "";
	public static final Color DEFAULT_PEN_COLOR = Color.BLACK;
//	public static final ImageView DEFAULT_IMAGE = ;
	
	private String myName;
	private ImageView myImage; 
	private Pen myPen; // TODO pen should take color + Group
	private boolean myVisibility; 
	
	protected Turtle() {
		this(DEFAULT_NAME, new ImageView(), new Group(), DEFAULT_PEN_COLOR);
	}
	
	protected Turtle(String name, ImageView image, Group penGroup) {
		this(name, image, penGroup, DEFAULT_PEN_COLOR);
	}
	
	protected Turtle(String name, ImageView image, Group penGroup, Color penColor) {
		myName = name; 
		myImage = image; 
//		myPen = new Pen(penColor, penGroup); 
		myVisibility = true; 
	}
	
	/**
	 * Returns the current x-position of the turtle
	 */
	public double getX() {
		return 0;
	}

	/**
	 * Returns the current y-position of the turtle
	 */
	public double getY() {
		return 0;
	}

	/**
	 * Returns the previous x-position of the turtle
	 */
	public double getOldX() {
		return 0;
	}

	/**
	 * Returns the previous y-position of the turtle
	 */
	public double getOldY() {
		return 0;
	}

	/**
	 * Copies the current values of X and Y into oldX and oldY
	 */
	public void setOld() {
	}

	/**
	 * Sets the y-position of the turtle
	 */
	public void setY(double y) {
	}

	/**
	 * Sets the x-position of the turtle
	 */
	public void setX(double x) {
	}

	/**
	 * Sets the visual image of the turtle to the image contained in filepath
	 */
	public void setImage(String filepath) {
	}

}