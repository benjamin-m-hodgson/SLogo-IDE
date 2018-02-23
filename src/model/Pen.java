package model;

import javafx.scene.paint.Color;

public interface Pen {
	/**
	 * Returns true if pen is up, false if it is down
	 */
	public boolean getPenVisibility();
	/**
	 * Sets pen visibility to invisible if argument is true, visible if it is false 
	 */
	public void setPenVisibility(boolean penUp);
	/**
	 * Returns pen width
	 */
	public double getPenWidth();
	/**
	 * Sets new pen width
	 */
	public void setPenWidth(double Width);
	/**
	 * Returns pen color
	 */
	public Color getPenColor();
	/**
	 * Sets new pen color
	 */
	public void setPenColor(Color newColor);
}
