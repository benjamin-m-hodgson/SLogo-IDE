	package interpreter;

	import java.io.File;
	import java.net.MalformedURLException;
	import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

	import javafx.scene.Group;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.shape.Line;

	/**
	 * @author Sarahbland - Pen inner class
	 * @author Susie Choi - Surrounding Turtle class
	 *
	 */
	public class SingleTurtle extends Turtle{

	    	// TODO: put in setting.properties file
		public static final String DEFAULT_FILEPATH_PREFIX = "user.dir/";
		public static final String DEFAULT_IMAGES_FOLDER = "turtleimages/";
		public static final String DEFAULT_IMAGE_SUFFIX = ".png";
		public static final String DEFAULT_TURTLESHAPES_FILE = "interpreter/TurtleShapes";

	    	public static final double DEFAULT_TURTLE_SIZE = 40;
		public static final double DEFAULT_ID = -1;
		public static final String DEFAULT_PEN_COLORCODE = "#2d3436";
		public static final double DEFAULT_PEN_WIDTH = 1.0;
		public static final double DEFAULT_X_POS = 0.0; 
		public static final double DEFAULT_Y_POS = 0.0; 
		public static final double DEFAULT_ANGLE = 0.0; 
		//	public static final ImageView DEFAULT_IMAGE = ;

		private double myID;
		private ImageView myImage; 
		private Pen myPen; 
		private boolean myVisibility; 

		private double myOldX;
		private double myOldY;
		private double myOldImageX;
		private double myOldImageY;
		private double myX;
		private double myY; 
		private double myAngle; 
		private double myImageIdx; 

		
		protected SingleTurtle() {
			this(DEFAULT_ID, new ImageView(), new Group(), DEFAULT_PEN_COLORCODE);
		}
		protected SingleTurtle(double id) {
			this(id, new ImageView(), new Group(), DEFAULT_PEN_COLORCODE);
		}

		protected SingleTurtle(double id, ImageView image, Group penGroup, String colorCode) {
			myID = id; 
			myImage = image;
			myPen = new Pen(penGroup, colorCode); 
			myVisibility = true; 
			myOldX = DEFAULT_X_POS; 
			myOldY = DEFAULT_Y_POS; 
			myX = DEFAULT_X_POS; 
			myY = DEFAULT_Y_POS; 
			myAngle = DEFAULT_ANGLE; 
			myImageIdx = 1; 
		}	
		public void executeSequentially(Consumer<Turtle> action){
			action.accept(this);
		}
		protected Turtle replaceTurtles(List<SingleTurtle> turtle) {
			if(turtle.size()>1) {
				return new MultipleTurtles(turtle);
			}
			else {
				SingleTurtle oneTurtle = turtle.get(0);
				setX(oneTurtle.getX());
				setY(oneTurtle.getY());
				setOldXY(oneTurtle.getX(), oneTurtle.getY());
				setAngle(oneTurtle.getAngle());
				if(oneTurtle.getTurtleVisibility()) {
					showTurtle();
				}
				else {
					hideTurtle();
				}
				try {
				setShape("" + oneTurtle.getImageIdx());
				}
				catch(UnidentifiedCommandException |MalformedURLException | MissingInformationException | BadFormatException e) {
					throw new UnidentifiedCommandError(e.getMessage());
				}
			}
			return this;
		}
		protected SingleTurtle getCopy() {
			SingleTurtle turtle = new SingleTurtle(myID, new ImageView(), new Group(), myPen.getColor());
			turtle.setX(myX);
			turtle.setY(myY);
			turtle.setOldXY(myOldX, myOldY);
			turtle.setAngle(myAngle);
			try {
				turtle.setShape(Integer.toString((int)myImageIdx));
			} catch (Exception e) {
				throw new UnidentifiedCommandError(e.getMessage());
			} 
			if(myVisibility) {
				turtle.showTurtle();
			}
			else {
				turtle.hideTurtle();
			}
			return turtle;
		}
		protected boolean containsTurtleWithID(String ID) {
			return(ID.equals(new String("" + myID)));
		}
		protected SingleTurtle getTurtleWithID(String ID) throws UnidentifiedCommandException{
			if(containsTurtleWithID(ID)) {
				return this;
			}
			else {
				throw new UnidentifiedCommandException("Invalid ID");
			}
		}
		protected MultipleTurtles addTurtle(SingleTurtle turtle) {
			ArrayList<SingleTurtle> singles = new ArrayList<>();
			singles.add(this);
			singles.add(turtle);
			return new MultipleTurtles(singles);
		}

		
		public void setShape(String idxKey) throws BadFormatException, UnidentifiedCommandException, MissingInformationException, MalformedURLException {
			RegexMatcher rm = new RegexMatcher(DEFAULT_TURTLESHAPES_FILE);
			String matchingShape = "";
			matchingShape = rm.findMatchingVal(idxKey);

			File turtleFile = new File(DEFAULT_IMAGES_FOLDER  + matchingShape + DEFAULT_IMAGE_SUFFIX);
			setImage(turtleFile.toURI().toURL().toExternalForm());
			myImageIdx = Double.parseDouble(idxKey);
		}
		
		protected void setOldXY(double oldX, double oldY) {
			myOldX = oldX;
			myOldY = oldY;
		}


		// GETTERS
		/**
		 * @return double ID of the Turtle in question
		 */
		public double getID() {
			return myID; 
		}
		
		protected double getImageIdx() {
			return myImageIdx;
		}
		
		
		/**
		 * Returns the current x-position of the turtle
		 */
		public double getX() {
			return myX;
		}

		/**
		 * Returns the current y-position of the turtle
		 */
		public double getY() {
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
		
		protected SingleTurtle toSingleTurtle() {
			return this;
		}
		protected ImageView getImageView() {
			return myImage;
		}


		// SETTERS
		protected void hideTurtle() {
			myVisibility = false; 
			myImage.setVisible(false);
		}

		protected void showTurtle() {
			myVisibility = true; 
			myImage.setVisible(true);;
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

		protected double setXY(double x, double y) {
			myOldImageX = myImage.getX();
			myOldImageY = myImage.getY();
			setOld();
			myX = x; 
			myY = y; 
			myImage.setX(myX - DEFAULT_TURTLE_SIZE/2);
			myImage.setY(myY - DEFAULT_TURTLE_SIZE/2);
			System.out.println(myImage.getX());
			System.out.println(myImage.getY());
			myPen.drawLine(myOldX, myOldY, myX, myY);
			return calcDistance(myOldX, myOldY, myX, myY);
		}
		/**
		 * Calculates the distance between two points (as in how far the turtle moved)
		 * @param oldX first x coordinate
		 * @param oldY first y coordinate
		 * @param x new x coordinate
		 * @param y new y coordinate
		 * @return distance traveled
		 */
		protected double calcDistance(double oldX, double oldY, double x, double y) {
//			System.out.println("old x: " + oldX + " new x: "+ x);
//			System.out.println("old y" + oldY + " new y: "+ y);
			double xSquared = Math.pow((oldX-x), 2);
			double ySquared = Math.pow((oldY-y), 2);
			return Math.sqrt(xSquared+ySquared);
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
		public void setImage(String filepath) {
			Image newImg = new Image(filepath);
			myImage.setImage(newImg);
		}

		protected void setPenColor(String colorCode) {
			myPen.setColor(colorCode);
		}
		
		protected void setPenWidth(double width) {
			myPen.setWidth(width);
		}
		
		protected void setAngle(double angle) {
			myAngle = angle;
			System.out.println("angle" + myAngle);
			myImage.setRotate(angle);
		}

		protected void showPen() {
			myPen.putPenDown();
		}

		protected void hidePen() {
			myPen.putPenUp();
		}

		protected void clearPen() {
			myPen.clear();
		}
		
		protected String getPenColor() {
			return myPen.getColor();
		}
		protected Group getPenLines() {
			return myPen.getPenLines();
		}
		protected int size() {
			return 1;
		}





		/**
		 * Class representing the Pen each Turtle holds. Used to change pen attributes and draw lines based on the turtle's movement. 
		 * Depends on the UserView to attach to the screen and send to the Controller via the makeNewTurtle command a Group used to 
		 * house the Turtle's pen lines.
		 * @author Sarahbland
		 * @author Susie Choi
		 * 
		 */
		private class Pen {		
			private Group myPenLines;
			private String myColorCode;
			private double myWidth;
			private boolean myIsDown;

			/**
			 * Single argument constructor for a pen (default values if the user does not specify)
			 * @param penlines is (already attached to stage) Group of lines drawn by the pen
			 */
			private Pen(Group penLines) {
				this(penLines, true, DEFAULT_PEN_COLORCODE, DEFAULT_PEN_WIDTH);
			}
			
			private Pen(Group penLines, String colorCode) {
				this(penLines, true, colorCode, DEFAULT_PEN_WIDTH);
			}
			
			/**
			 * Constructor for a pen with default state as pen visible
			 * @param penlines is (already-attached-to-stage) Group which will house lines drawn by Pen
			 * @param color is initial color of Pen
			 * @param width is initial width of Pen
			 */
			private Pen(Group penLines, String colorCode, double width) {
				this(penLines, true, colorCode, width);
			}

			/**
			 * Fully populated constructor for a pen
			 * @param penlines is (already-attached-to-stage) Group which will house lines drawn by Pen
			 * @param down is initial state of Pen (true if pen is down, false if up)
			 * @param color is initial color of Pen
			 * @param width is initial width of Pen
			 */
			private Pen(Group penlines, boolean down, String colorCode, double width) {
				myPenLines = penlines;
				myColorCode = colorCode;
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
			private String getColor() {
				return myColorCode;
			}
			private Group getPenLines() {
				return myPenLines;
			}

			/**
			 * Sets new pen color
			 * @param newColor is new Color of pen
			 */
			private void setColor(String newColorCode) {
				myColorCode = newColorCode;
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
					Line line = new Line(newX, newY, oldX, oldY);
					//line.setFill(myColor);
					line.setStyle("-fx-stroke: #" + myColorCode + ";");
					line.setStrokeWidth(myWidth);
					myPenLines.getChildren().add(line);
					System.out.println("number of lines" + myPenLines.getChildren().size());
				}
			}
			/**
			 * Clears all lines previously drawn by the pen from the screen
			 */
			private void clear() {
				myPenLines.getChildren().removeAll(myPenLines.getChildren());
			}

		}

	}
