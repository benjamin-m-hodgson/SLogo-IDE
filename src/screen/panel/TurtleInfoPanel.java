package screen.panel;

import interpreter.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import screen.UserScreen;

/**
 * 
 * @author Benjamin Hodgson
 * 
 * Class to create a side panel to display information about a specific turtle
 */
public class TurtleInfoPanel extends SpecificPanel {
    // TODO: put in settings .properties file
    private final int MOVEMENT_MIN = 0;
    private final int MOVEMENT_MAX = Integer.MAX_VALUE;
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private BorderPane USER_PANE;
    private UserScreen USER_SCREEN;
    private String TURTLE_ID;
    
    public TurtleInfoPanel(Controller programController, BorderPane pane, UserScreen userScreen, 
	    String id) {
	PROGRAM_CONTROLLER = programController;
	USER_PANE = pane;
	USER_SCREEN = userScreen;
	TURTLE_ID = id;
    }
    
    @Override
    public void makePanel() {
	VBox turtleInfoPanel = new VBox();
	populateInfoBox(turtleInfoPanel);
	turtleInfoPanel.setId("infoPanel");
	PANEL = turtleInfoPanel;
    }
    
    @Override
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }
    
    private void populateInfoBox(VBox turtleInfoPanel) {
	Button backButton = makeBackButton(PROGRAM_CONTROLLER);
	Button turtleIdButton = new Button(TURTLE_ID);
	turtleIdButton.setId("commandButton");
	turtleIdButton.setDisable(true);
	VBox movementButtons = drawMovementButtons(turtleInfoPanel);
	turtleInfoPanel.getChildren().addAll(turtleIdButton, movementButtons, backButton);
    }
    
    /**
     * An area of the turtle info panel that allows the user to move the turtle directionally
     * around the screen
     * 
     * @return A VBox containing buttons to move the turtle and a text field to specify by how much
     */
    private VBox drawMovementButtons(VBox turtleInfoPanel) {
	TextField movementField = movementField(turtleInfoPanel, MOVEMENT_MIN, MOVEMENT_MAX);
	Button upButton = new Button();
	upButton.setId("upButton");
	upButton.setOnMouseClicked((arg0) -> moveUp(movementField.getText()));
	Button downButton = new Button();
	downButton.setId("downButton");
	downButton.setOnMouseClicked((arg0) -> moveDown(movementField.getText()));
	Button leftButton = new Button();
	leftButton.setId("leftButton");
	leftButton.setOnMouseClicked((arg0) -> moveLeft(movementField.getText()));
	Button rightButton = new Button();
	rightButton.setId("rightButton");
	rightButton.setOnMouseClicked((arg0) -> moveRight(movementField.getText()));
	HBox movementRow = new HBox(leftButton, movementField, rightButton);
	movementRow.setId("moveBox");
	VBox movementButtons = new VBox(upButton, movementRow, downButton);
	movementButtons.setId("moveBox");
	return movementButtons;
    }
    
    /**
     * Creates a text field that takes integer only input to set the movement amount for the 
     * turtle movement buttons
     * 
     * @param min: the minimum allowable input value
     * @param max: the maximum allowable input value
     * @return movementField: a text field that allows the user to input an integer amount
     * to specify how much to move the turtle
     */
    private TextField movementField(Parent root, int min, int max) {
	TextField numberTextField = new TextField();
	numberTextField.setId("numberTextField");
	String promptText = PROGRAM_CONTROLLER.resourceDisplayText("MovePrompt");
	numberTextField.setPromptText(promptText);
	numberTextField.setTooltip(new Tooltip(promptText));
	// clear when the mouse clicks on the text field
	numberTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		numberTextField.clear();
	    }
	});
	numberTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent key) {
		if (key.getCode() == KeyCode.ENTER) {
		    // check input to make sure the value is within bounds
		    try {
			int sizeVal = Integer.parseInt(numberTextField.getText());
			if (sizeVal >= min && sizeVal <= max) {	
			    numberTextField.setText(Integer.toString(sizeVal));
			}
			else {
			    numberTextField.clear();
			}

		    }
		    catch(Exception e) {
			numberTextField.clear();
		    }
		    root.requestFocus();
		}
	    }
	});
	return numberTextField;
    }
    
    private void moveUp(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Forward " + amount;
		//PROGRAM_CONTROLLER.parseInput("Ask [ " + TURTLE_ID + " ] [ " + command + " ]");
		PROGRAM_CONTROLLER.parseInput(command);
		USER_SCREEN.addCommand(command, value);
	    }
	    catch(Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }
    
    private void moveDown(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Backward " + amount;
		//PROGRAM_CONTROLLER.parseInput("Ask [ " + TURTLE_ID + " ] [ " + command + " ]");
		PROGRAM_CONTROLLER.parseInput(command);
		USER_SCREEN.addCommand(command, value);
	    }
	    catch(Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }
    
    private void moveLeft(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Left " + amount;
		//PROGRAM_CONTROLLER.parseInput("Ask [ " + TURTLE_ID + " ] [ " + command + " ]");
		PROGRAM_CONTROLLER.parseInput(command);
		USER_SCREEN.addCommand(command, value);
	    }
	    catch(Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }
    
    private void moveRight(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Right " + amount;
		//PROGRAM_CONTROLLER.parseInput("Ask [ " + TURTLE_ID + " ] [ " + command + " ]");
		PROGRAM_CONTROLLER.parseInput(command);
		USER_SCREEN.addCommand(command, value);
	    }
	    catch(Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }

    @Override
    protected BorderPane getPane() {
	return USER_PANE;
    }

    @Override
    protected Controller getController() {
	// TODO Auto-generated method stub
	return PROGRAM_CONTROLLER;
    }

    @Override
    protected UserScreen getUserScreen() {
	// TODO Auto-generated method stub
	return USER_SCREEN;
    }

}
