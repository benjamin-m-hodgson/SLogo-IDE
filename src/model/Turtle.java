package model;

public interface Turtle {
    
    /**
     * Returns the current x-position of the turtle
     */
    public double getX();
    
    /**
     * Returns the current y-position of the turtle
     */
    public double getY();
    
    /**
     * Returns the previous x-position of the turtle
     */
    public double getOldX();
    
    /**
     * Returns the previous y-position of the turtle
     */
    public double getOldY();
    
    /**
     * Copies the current values of X and Y into oldX and oldY
     */
    public void setOld();
    
    /**
     * Sets the y-position of the turtle
     */
    public void setY(double y);
    
    /**
     * Sets the x-position of the turtle
     */
    public void setX(double x);
    
    /**
     * Sets the visual image of the turtle to the image contained in filepath
     */
    public void setImage(String filepath);
    
}
