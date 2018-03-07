package interpreter;

import java.util.List;
import java.util.function.Consumer;

public class MultipleTurtles extends Turtle{
		TurtleHolder myActiveTurtleHolder;
	public MultipleTurtles(List<SingleTurtle> turtles) {
		myActiveTurtleHolder = new TurtleHolder(turtles);
	}

	// GETTERS
	/**
	 * @return double ID of the last Turtle in question
	 */
	public double getID() {
		return getLastTurtle().getID(); 
	}
	public void forEach(Consumer<Turtle> action) {
		myActiveTurtleHolder.applyToAllTurtles(action);
	}
	protected boolean containsTurtleWithID(String ID) {
		try {
			Double id = Double.parseDouble(ID);
			return myActiveTurtleHolder.hasTurtleWithID(id);
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	protected int size() {
		return myActiveTurtleHolder.getCopyTurtleList().size();
	}
	private SingleTurtle getLastTurtle() {
		return myActiveTurtleHolder.getCopyTurtleList().get(myActiveTurtleHolder.getCopyTurtleList().size()-1);
	}
	protected SingleTurtle toSingleTurtle() {
		return getLastTurtle();
	}
	protected void addTurtle(SingleTurtle turtle) {
		myActiveTurtleHolder.addTurtle(turtle);
	}
	public List<SingleTurtle> getAllImmutableTurtles(){
		return myActiveTurtleHolder.getCopyTurtleList();
	}
	
	/**
	 * Returns the current x-position of the last turtle
	 */
	public double getX() {
		return getLastTurtle().getX();
	}

	/**
	 * Returns the current y-position of the turtle
	 */
	public double getY() {
		return getLastTurtle().getY();
	}
		

	/**
	 * Returns the previous x-position of the turtle
	 */
	protected double getOldX() {
		return getLastTurtle().getOldX();
	}

	/**
	 * Returns the previous y-position of the turtle
	 */
	protected double getOldY() {
		return getLastTurtle().getOldY();
	}

	protected double getAngle() {
		return getLastTurtle().getAngle();
	}

	protected boolean getTurtleVisibility() {
		return getLastTurtle().getTurtleVisibility();
	}

	protected boolean getPenVisibility() {
		return getLastTurtle().getPenVisibility();
	}



	// SETTERS
	protected void hideTurtle() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.hideTurtle());
	}

	protected void showTurtle() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.showTurtle());
	}

	/**
	 * Sets the x-position of the turtle
	 */
	protected void setX(double x) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setX(x));
	}
	
	/**
	 * Sets the y-position of the turtle
	 */
	protected void setY(double y) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setY(y));
	}

	protected double setXY(double x, double y) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setXY(x, y));
		SingleTurtle last = getLastTurtle();
		return last.calcDistance(last.getOldX(), last.getOldY(), last.getX(), last.getY());
	}
	/**
	 * Sets the visual image of the turtle to the image contained in filepath
	 */
	public void setImage(String filepath) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setImage(filepath));
	}

	protected void setPenColor(String colorCode) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setPenColor(colorCode));
	}
	
	protected void setPenWidth(double width) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setPenWidth(width));
	}
	
	protected void setAngle(double angle) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setAngle(angle));
	}

	protected void showPen() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.showPen());
	}

	protected void hidePen() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.hidePen());
	}

	protected void clearPen() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.clearPen());
	}
	
	protected String getPenColor() {
		return getLastTurtle().getPenColor();
	}

}
