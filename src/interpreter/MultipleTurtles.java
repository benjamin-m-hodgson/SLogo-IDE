package interpreter;

import java.net.MalformedURLException;
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
	@Override
	public double getID() {
		return getLastTurtle().getID(); 
	}
	@Override
	public void executeSequentially(Consumer<Turtle> action){
		myActiveTurtleHolder.applyToAllTurtles(action);
		myActiveTurtleHolder.resetTemporaryTurtles();
	}
	
	@Override
	protected boolean containsTurtleWithID(String ID) {
		try {
			Double id = Double.parseDouble(ID);
			return myActiveTurtleHolder.hasTurtleWithID(id);
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	@Override
	protected SingleTurtle getTurtleWithID(String ID) throws UnidentifiedCommandException{
		try {
			double id = Double.parseDouble(ID);
			return myActiveTurtleHolder.getTurtleWithID(id);
		}
		catch(NumberFormatException e) {
			throw new UnidentifiedCommandException("You entered any invalid ID");
		}
	}
	@Override
	protected Turtle replaceTurtles(List<SingleTurtle> newTurtles) {
		myActiveTurtleHolder.replaceTurtleList(newTurtles);
		return this;
	}
	protected int size() {
		return myActiveTurtleHolder.getCopyTurtleList().size();
	}
	private SingleTurtle getLastTurtle() {
		return myActiveTurtleHolder.getCopyTurtleList().get(myActiveTurtleHolder.getCopyTurtleList().size()-1);
	}
	@Override
	protected SingleTurtle toSingleTurtle() {
		return getLastTurtle();
	}
	@Override
	protected MultipleTurtles addTurtle(SingleTurtle turtle) {
		myActiveTurtleHolder.addTurtle(turtle);
		myActiveTurtleHolder.resetTemporaryTurtles();
		return this;
	}
	public List<SingleTurtle> getAllImmutableTurtles(){
		return myActiveTurtleHolder.getCopyTurtleList();
	}
	
	/**
	 * Returns the current x-position of the last turtle
	 */
	@Override
	public double getX() {
		return getLastTurtle().getX();
	}
	@Override
	protected double getImageIdx() {
		return getLastTurtle().getImageIdx();
	}

	/**
	 * Returns the current y-position of the turtle
	 */
	@Override
	public double getY() {
		return getLastTurtle().getY();
	}
		

	/**
	 * Returns the previous x-position of the turtle
	 */
	@Override
	protected double getOldX() {
		return getLastTurtle().getOldX();
	}

	/**
	 * Returns the previous y-position of the turtle
	 */
	@Override
	protected double getOldY() {
		return getLastTurtle().getOldY();
	}

	@Override
	protected double getAngle() {
		return getLastTurtle().getAngle();
	}

	@Override
	protected boolean getTurtleVisibility() {
		return getLastTurtle().getTurtleVisibility();
	}

	@Override
	protected boolean getPenVisibility() {
		return getLastTurtle().getPenVisibility();
	}
	@Override
	protected double calcDistance(double oldX, double oldY, double x, double y) {
		return getLastTurtle().calcDistance(oldX, oldY, x, y);
	}



	// SETTERS
	@Override
	protected void hideTurtle() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.hideTurtle());
		myActiveTurtleHolder.resetTemporaryTurtles();
	}

	@Override
	protected void showTurtle() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.showTurtle());
		myActiveTurtleHolder.resetTemporaryTurtles();
	}

	/**
	 * Sets the x-position of the turtle
	 */
	@Override
	protected void setX(double x) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setX(x));
		myActiveTurtleHolder.resetTemporaryTurtles();
	}
	
	/**
	 * Sets the y-position of the turtle
	 */
	@Override
	protected void setY(double y) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setY(y));
		myActiveTurtleHolder.resetTemporaryTurtles();
	}
//	protected void setImageIdx(double shapeIdx) {
//		myActiveTurtleHolder.applyToAllTurtles(t->t.setImageIdx(shapeIdx));
//	}
	
	@Override
	public void setShape(String idxKey) throws BadFormatException, UnidentifiedCommandException, MissingInformationException, MalformedURLException{
		myActiveTurtleHolder.applyToAllTurtles(t->{
			try {
				t.setShape(idxKey);
			} catch (MalformedURLException | BadFormatException | UnidentifiedCommandException
					| MissingInformationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	@Override
	protected double setXY(double x, double y) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setXY(x, y));
		System.out.println("y coordinate before reset: " + getLastTurtle().getY());
		myActiveTurtleHolder.resetTemporaryTurtles();
		SingleTurtle last = getLastTurtle();
		return last.calcDistance(last.getOldX(), last.getOldY(), last.getX(), last.getY());
	}
	/**
	 * Sets the visual image of the turtle to the image contained in filepath
	 */
	@Override
	public void setImage(String filepath) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setImage(filepath));
		myActiveTurtleHolder.resetTemporaryTurtles();
	}

	@Override
	protected void setPenColor(String colorCode) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setPenColor(colorCode));
		myActiveTurtleHolder.resetTemporaryTurtles();
	}
	
	@Override
	protected void setPenWidth(double width) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setPenWidth(width));
		myActiveTurtleHolder.resetTemporaryTurtles();
	}
	
	@Override
	protected void setAngle(double angle) {
		myActiveTurtleHolder.applyToAllTurtles(t->t.setAngle(angle));
		myActiveTurtleHolder.resetTemporaryTurtles();
	}

	@Override
	protected void showPen() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.showPen());
		myActiveTurtleHolder.resetTemporaryTurtles();
	}

	@Override
	protected void hidePen() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.hidePen());
		myActiveTurtleHolder.resetTemporaryTurtles();
	}

	@Override
	protected void clearPen() {
		myActiveTurtleHolder.applyToAllTurtles(t->t.clearPen());
		myActiveTurtleHolder.resetTemporaryTurtles();
	}
	
	@Override
	protected String getPenColor() {
		return getLastTurtle().getPenColor();
	}

}
