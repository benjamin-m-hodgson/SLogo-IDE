package screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import interpreter.BadFormatException;
import interpreter.Controller;
import interpreter.FileIO;
import interpreter.MissingInformationException;
import interpreter.SingleTurtle;
import interpreter.TurtleNotFoundException;
import interpreter.UnidentifiedCommandException;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import screen.panel.InfoPanel;
import screen.panel.InputPanel;
import screen.panel.TurtlePanel;

/**
 * 
 * @author Benjamin Hodgson
 *
 * A class that implements the Screen interface to generate the UserScreen displayed to the user
 * to animate inputted commands and change some program properties.
 */
public class UserScreen implements Screen {

    private Parent ROOT;
    private TurtlePanel TURTLE_PANEL;
    private Controller PROGRAM_CONTROLLER;
    private final FileIO FILE_READER;
    private List<String> INPUT_HISTORY;
    private List<String> OUTPUT_HISTORY;
    private List<SingleTurtle> allTurtles;

    public UserScreen(Controller programController, FileIO fileReader) {
	FILE_READER = fileReader;
	PROGRAM_CONTROLLER = programController;
	INPUT_HISTORY = new ArrayList<String>();
	OUTPUT_HISTORY = new ArrayList<String>();	
    }


    @Override
    public void makeRoot() {
	BorderPane rootPane = new BorderPane();
	rootPane.setId("userScreenRoot");
	rootPane.setBottom(new InputPanel(this, FILE_READER).getPanel());
	rootPane.setRight(new InfoPanel( rootPane, this, FILE_READER).getPanel());
	TURTLE_PANEL = new TurtlePanel(rootPane, this, FILE_READER);//, rootPane
	rootPane.setCenter(TURTLE_PANEL.getPanel());
	allTurtles = PROGRAM_CONTROLLER.getAllTurtles();
	ROOT = rootPane;
    }

    @Override
    public Parent getRoot() {
	if (ROOT == null) {
	    makeRoot();
	}
	return ROOT;
    }

    /**
     * Receives @param command and @param output from the user and stores them in 
     * the List<String> objects INPUT_HISTORY and OUTPUT_HISTORY.
     * 
     * @param command: String representing the user inputted command to save in history
     * @param output: String representing the output generated from the user inputted command
     */
    public void addCommand(String command, String output) {
	INPUT_HISTORY.add(command);
	OUTPUT_HISTORY.add(output);
    }

    /**
     * @return Iterator<String>: an iterator to iterate over the items in the INPUT_HISTORY
     */
    public Iterator<String> commandHistory() {
	List<String> retList = new ArrayList<String>(INPUT_HISTORY);
	return retList.iterator();
    }

    /**
     * @return Iterator<String>: an iterator to iterate over the items in the OUTPUT_HISTORY
     */
    public Iterator<String> outputHistory() {
	List<String> retList = new ArrayList<String>(OUTPUT_HISTORY);
	return retList.iterator();
    }

    /**
     * Creates a pop-up error message at the bottom of the UserScreen to describe a 
     * 'minor' error related to user input.
     * 
     * @param errorMessage: The message describing the error to display to the user
     */
    public void displayErrorMessage(String errorMessage) {
	TURTLE_PANEL.displayErrorMessage(errorMessage);
    }
    
    public void commandRunFromHistory(String command) {
	try {
	    Double commandVal = PROGRAM_CONTROLLER.parseInput(command);
	    addCommand(command, commandVal.toString());
	} catch (TurtleNotFoundException | BadFormatException | UnidentifiedCommandException
		| MissingInformationException e) {
		 e.printStackTrace();
		displayErrorMessage(e.getMessage());
	}
	
    }

//    /**
//     * Changes the image displayed on the screen to represent the Turtle
//     * 
//     * @param selected: The selected image to change the turtle display to
//     * @throws MissingInformationException 
//     * @throws UnidentifiedCommandException 
//     * @throws BadFormatException 
//     * @throws TurtleNotFoundException 
//     */
//    public void changeTurtleImage(String selected)  {
//	TURTLE_PANEL.changeTurtlesImages(selected);
//    }

    @Override
    public void changeBackgroundColor(String color) {
	String colorCode = FILE_READER.getColorHexfromName(color);
	TURTLE_PANEL.changeBackgroundColor(colorCode);
    }

    public void changeBackgroundColorHex(String hex) {
	TURTLE_PANEL.changeBackgroundColor(hex);
    }

    @Override
    public void changeRightPanel(Parent panelRoot) {
	// TODO Auto-generated method stub
    }

    /**
     * Removes the error pop-up from the screen
     */
    public void clearErrorDisplay() {
	TURTLE_PANEL.removeErrorButton();
    }


    public void applyPreferences(String selected) {
	Map<String, String> preferences = FILE_READER.getWorkspacePreferences(selected);
	TURTLE_PANEL.changeBackgroundColor(preferences.get("backgroundColor"));
	String penColor = preferences.get("penColor");
	penColor = penColor.substring(1, penColor.length());
	System.out.println(penColor);
	PROGRAM_CONTROLLER.changePenColorHex(Integer.parseInt(penColor,16));
	FILE_READER.bundleUpdateToNewLanguage(preferences.get("language"));
    }
    
    public void checkForNewTurtle() {
	List<SingleTurtle> newTurtles = PROGRAM_CONTROLLER.getAllTurtles();
	System.out.println("allTurtleSize: " + allTurtles.size());
	System.out.println("newTurtleSize: " + newTurtles.size());

	for(SingleTurtle newT : newTurtles) {
	    double id = newT.getID();
	    if(containsElementWithID(id, allTurtles) == false) {
		ImageView turtleImage = PROGRAM_CONTROLLER.getTurtleWithIDImageView(id);
		Group penLines = PROGRAM_CONTROLLER.getTurtleWithIDPenLines(id);
		TURTLE_PANEL.attachTurtleObjects(turtleImage, penLines);
	    }
	}
	allTurtles = newTurtles;
    }
    
    private boolean containsElementWithID(double id, List<SingleTurtle> theList) {
	for(SingleTurtle t : theList) {
	    if (t.getID() == id) {
		return true;
	    }
	}
	return false;
    }
    
    public Map<String, Double> getVariables(){
	return PROGRAM_CONTROLLER.getVariables();
    }
    
    public Map<String, String> getUserDefined(){
	return PROGRAM_CONTROLLER.getUserDefined();
    }
    
    public void throwErrorScreen(String message) {
	PROGRAM_CONTROLLER.loadErrorScreen(message);
    }
    
    public void makeNewTurtleCommand(String id, ImageView turtleImage, String penColor, Group penLines) {
	PROGRAM_CONTROLLER.makeNewTurtleCommand(id, turtleImage, penColor, penLines);
    }
    
    public double sendCommandToParse(String inputText) throws TurtleNotFoundException, BadFormatException, UnidentifiedCommandException, MissingInformationException {
	 return PROGRAM_CONTROLLER.parseInput(inputText);
    }
    
    public List<SingleTurtle> getAllTurtles() {
	return allTurtles;
    }


}
