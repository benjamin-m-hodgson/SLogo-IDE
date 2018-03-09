package screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import interpreter.BadFormatException;
import interpreter.Controller;
import interpreter.MissingInformationException;
import interpreter.TurtleNotFoundException;
import interpreter.UnidentifiedCommandException;
import javafx.scene.Parent;
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
    private List<String> INPUT_HISTORY;
    private List<String> OUTPUT_HISTORY;
    private Map<String, String[]> workspacePreferences;

    public UserScreen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
	INPUT_HISTORY = new ArrayList<String>();
	OUTPUT_HISTORY = new ArrayList<String>();
	workspacePreferences = new HashMap<String, String[]>();
    }


    @Override
    public void makeRoot() {
	BorderPane rootPane = new BorderPane();
	rootPane.setId("userScreenRoot");
	rootPane.setBottom(new InputPanel(PROGRAM_CONTROLLER, this).getPanel());
	rootPane.setRight(new InfoPanel(PROGRAM_CONTROLLER, rootPane, this).getPanel());
	TURTLE_PANEL = new TurtlePanel(PROGRAM_CONTROLLER, rootPane);
	rootPane.setCenter(TURTLE_PANEL.getPanel());
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

    /**
     * Changes the image displayed on the screen to represent the Turtle
     * 
     * @param selected: The selected image to change the turtle display to
     */
    public void changeTurtleImage(String selected) {
	TURTLE_PANEL.changeTurtlesImages(selected);
    }

    @Override
    public void changeBackgroundColor(String color) {
	String colorCode = PROGRAM_CONTROLLER.changeBackgroundColor(color);
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
	Map<String, String> preferences = PROGRAM_CONTROLLER.getWorkspacePreferences(selected);
	TURTLE_PANEL.changeBackgroundColor(preferences.get("backgroundColor"));
	String penColor = preferences.get("penColor");
	penColor = penColor.substring(1, penColor.length());
	System.out.println(penColor);
	PROGRAM_CONTROLLER.changePenColorHex(Integer.parseInt(penColor,16));
	
	PROGRAM_CONTROLLER.changeLanguage(preferences.get("language"));

    }


}
