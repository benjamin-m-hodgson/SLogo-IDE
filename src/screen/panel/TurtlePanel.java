package screen.panel;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import interpreter.FileIO;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import screen.UserScreen;


/**
 * Panel that manages actual block of space which turtle occupies. Dependent on UserScreen
 * to relay information and FileReader to read in files (as well as the existence of certain
 * files for turtle image, etc.)
 * @author Ben Hodgson and Andrew Arnold
 *
 */
public class TurtlePanel  {
    private final double DEFAULT_TURTLE_SIZE;
    private final String DEFAULT_TURTLE;
    private final String DEFAULT_COLOR_HEXCODE;
    private BorderPane PANEL;
    private final BorderPane USER_PANE;
    private ScrollPane SCROLL_PANE;
    private final UserScreen USER_SCREEN;
    private  final Pane TURTLE_PANEL;
    private HBox ErrorHolder;
    private List<ImageView> TURTLE_LIST;
    private final FileIO FILE_READER;
    private int TURTLE_COUNT = 1;
 


    /**
     * Makes new TurtlePanel
     * @param pane is BorderPane that will display it
     * @param userScreen is screen of current simulation
     * @param fileReader is file-reading class that helps access information
     */
    public TurtlePanel(BorderPane pane, UserScreen userScreen, FileIO fileReader) {
	USER_PANE = pane;
	FILE_READER = fileReader;
	USER_SCREEN = userScreen;
	TURTLE_LIST = new ArrayList<ImageView>();
	TURTLE_PANEL = new Pane();
	DEFAULT_TURTLE_SIZE = Double.parseDouble(
		FILE_READER.resourceSettingsText("defaultTurtleSize"));
	DEFAULT_TURTLE = FILE_READER.resourceSettingsText("defaultTurtleImage");
	DEFAULT_COLOR_HEXCODE = FILE_READER.resourceSettingsText("defaultColorHex");
	
    }
    
  
    /**
     * Makes the panel and attaches it to the screen
     */
    public void makePanel() {
	BorderPane layoutPane = new BorderPane();

	ScrollPane scroll = new ScrollPane(TURTLE_PANEL);
	layoutPane.setCenter(scroll);

	SCROLL_PANE = scroll;
	scroll.setId("turtlePanel");
	createTurtle(TURTLE_PANEL, scroll);

	PANEL = layoutPane;
    }

    /**
     * If property PANEL is null, calls makePanel() to generate the root. 
     * 
     * @return PANEL: The Parent node to be used in the Scene object. 
     */
    public Parent getPanel() {
 	if (PANEL == null) {
 	    makePanel();
 	}
 	return PANEL;
     } 

    private void createTurtle(Pane panel, ScrollPane scrollPane) {
	String currentDir = System.getProperty("user.dir");
	try {
	    File turtleFile = new File(currentDir + File.separator + "turtleimages" 
		    + File.separator + DEFAULT_TURTLE);
	    Image turtleImage = new Image(turtleFile.toURI().toURL().toExternalForm());
	    ImageView turtleView = new ImageView(turtleImage);
	    TURTLE_LIST.add(turtleView);
	    turtleView.setId("turtleView");
	    turtleView.setFitHeight(DEFAULT_TURTLE_SIZE);
	    turtleView.setFitWidth(DEFAULT_TURTLE_SIZE);
	    // center the turtle on the screen
	    turtleView.translateXProperty().bind(Bindings.divide(scrollPane.widthProperty(), 2));
	    turtleView.translateYProperty().bind(Bindings.divide(scrollPane.heightProperty(), 2));
	    turtleView.setX(-DEFAULT_TURTLE_SIZE/2);
	    turtleView.setY(-DEFAULT_TURTLE_SIZE/2);
	    // add button click event
	    String turtleId = Integer.toString(TURTLE_COUNT);
	    turtleView.setOnMousePressed((arg0)-> USER_PANE.setRight(
		    new TurtleInfoPanel(USER_PANE, USER_SCREEN, turtleId, FILE_READER).getPanel()));
	    panel.getChildren().add(turtleView);
	    Group penLines = new Group();
	    penLines.translateXProperty().bind(Bindings.divide(scrollPane.widthProperty(), 2));
	    penLines.translateYProperty().bind(Bindings.divide(scrollPane.heightProperty(), 2));
	    panel.getChildren().add(penLines);
	    USER_SCREEN.makeNewTurtleCommand(turtleId, turtleView,
		    DEFAULT_COLOR_HEXCODE , penLines);
	    TURTLE_COUNT++;
	}
	catch (Exception e) {
	    // TODO: make custom exception super class with sub classes for specifications
	    //String specification = "%nFailed to find language files";
	    System.out.println("FAILED TO LOAD TURTLE IMG");
	}
    } 

    private ImageView setUpImageView(ImageView turtleView, ScrollPane scrollPane, double ID){
    	String currentDir = System.getProperty("user.dir");
    	File turtleFile = new File(currentDir + File.separator + "turtleimages" 
    		+ File.separator + DEFAULT_TURTLE);
    	Image turtleImage;
    	try {
    	    turtleImage = new Image(turtleFile.toURI().toURL().toExternalForm());
    	    turtleView.setImage(turtleImage);
    	    TURTLE_LIST.add(turtleView);
    	    turtleView.setId("turtleView");
    	    turtleView.setFitHeight(DEFAULT_TURTLE_SIZE);
    	    turtleView.setFitWidth(DEFAULT_TURTLE_SIZE);
    	    // center the turtle on the screen
    	    turtleView.translateXProperty().bind(Bindings.divide(scrollPane.widthProperty(), 2));
    	    turtleView.translateYProperty().bind(Bindings.divide(scrollPane.heightProperty(), 2));
    	    turtleView.setOnMousePressed((arg0)-> USER_PANE.setRight(
    		    new TurtleInfoPanel(USER_PANE, USER_SCREEN, Double.toString(ID), FILE_READER).getPanel()));
    	    TURTLE_PANEL.getChildren().add(turtleView);
    	    return turtleView;
    	} catch (MalformedURLException e) {
    	    // TODO Auto-generated catch block
    	    System.out.println("FAILED TO LOAD TURTLE IMG");
    	    return new ImageView();
    		}
      }

    /**
     * Attaches new turtle objects created by the backend during the last run (ImageView
     * and a Group corresponding to the turtle's pen lines)
     * @param image is imageView of new turtle
     * @param penLine is Group of Lines corresponding to pen lines of turtle
     * @param ID is ID of turtle
     */
    public void attachTurtleObjects(ImageView image, Group penLine, double ID) {
    		setUpImageView(image, SCROLL_PANE,ID);
    		penLine.translateXProperty().bind(Bindings.divide(SCROLL_PANE.widthProperty(), 2));
    	    penLine.translateYProperty().bind(Bindings.divide(SCROLL_PANE.heightProperty(), 2));
    		TURTLE_PANEL.getChildren().add(penLine);
    }

    public void displayErrorMessage(String error) {
	Button errorButton = new Button(error);
	errorButton.setId("errorButton");
	ErrorHolder = new HBox(errorButton);
	ErrorHolder.setAlignment(Pos.CENTER);
	HBox.setHgrow(errorButton, Priority.ALWAYS);
	errorButton.setOnMouseClicked((arg0)-> PANEL.getChildren().remove(ErrorHolder));
	errorButton.setMaxWidth(PANEL.widthProperty().get());
	errorButton.setMinWidth(PANEL.widthProperty().get());
	PANEL.setBottom(ErrorHolder);
    }

    /**
     * changes background color to the background color with given string colorcode
     * @param colorCode is hex code of new background color
     */
    public void changeBackgroundColor(String colorCode) {
	SCROLL_PANE.setStyle("-fx-background-color:" + colorCode + ";");
    }

    /**
     * removes an error from the screen when a user clicks on it
     */
    public void removeErrorButton() {
	PANEL.getChildren().remove(ErrorHolder);
    }

  

}
