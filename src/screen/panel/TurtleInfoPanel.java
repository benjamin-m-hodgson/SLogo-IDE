package screen.panel;
import java.util.Map;

import interpreter.FileIO;
import interpreter.SingleTurtle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
 * @author Andrew Arnold
 * 
 * Class to create a side panel to display information about a specific turtle
 */
public class TurtleInfoPanel extends SpecificPanel {
    // TODO: put in settings .properties file
    private final int VISIBLE_ROW_COUNT;
    public  final String DEFAULT_SHAPE_COMMAND;
    public  final String DEFAULT_PENCOLORCHANGE_COMMAND;
    private final int MOVEMENT_MIN = 0;
    private final int MOVEMENT_MAX = Integer.MAX_VALUE;
    private final FileIO FILE_READER;
    private UserScreen USER_SCREEN;
    private String TURTLE_ID;
    private SingleTurtle TURTLE;
    private BorderPane PANE;
    private final String[] CURRENTSTATE_KEYS = {"turtleImage", "backgroundColor"};


    public TurtleInfoPanel(BorderPane pane, UserScreen userScreen, String id, FileIO fileReader) {
	FILE_READER = fileReader;
	PANE = pane;
	USER_SCREEN = userScreen;
	TURTLE_ID = id;
	TURTLE = USER_SCREEN.getAllTurtles().get(Integer.parseInt(TURTLE_ID) - 1);
	DEFAULT_SHAPE_COMMAND = FILE_READER.resourceSettingsText("defaultShapeCommand");
	DEFAULT_PENCOLORCHANGE_COMMAND = FILE_READER.resourceSettingsText("defaultPenColorChangeCommand");
	VISIBLE_ROW_COUNT = Integer.parseInt(FILE_READER.resourceSettingsText("turtleInfoPanelVisibleRowCount"));
    }

    @Override
    public void makePanel() {
	VBox turtleInfoPanel = new VBox();
	populateInfoBox(turtleInfoPanel);
	turtleInfoPanel.setId("infoPanel");
	PANEL = turtleInfoPanel;
    }  

    private void populateInfoBox(VBox turtleInfoPanel) {
	Button backButton = makeBackButton(FILE_READER);
	Button turtleIdButton = new Button(FILE_READER.resourceDisplayText("TurtlePrompt")
		+ " " + TURTLE_ID);
	turtleIdButton.setId("commandButton");
	turtleIdButton.setDisable(true);
	Button penOptions = makePenOptions();
	ComboBox<Object> shapeChooser = makeTurtleImageChooser("turtleImageChooser");
	VBox movementButtons = drawMovementButtons(turtleInfoPanel);
	turtleInfoPanel.getChildren().addAll(turtleIdButton, shapeChooser, 
		penOptions, movementButtons, backButton);
    }

    private Button makePenOptions() {
	Button penButton = new Button(FILE_READER.resourceDisplayText("penOptions"));
	penButton.setId("penOptionsButton");
	// override click event
	penButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(new PenInfoPanel(PANE, USER_SCREEN, TURTLE_ID, FILE_READER).getPanel()));
	return penButton;
    }

    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makeTurtleImageChooser(String itemID) {
	String selectionPrompt = FILE_READER.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip turtleTip = new Tooltip();
	turtleTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(turtleTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	Map<String, String> turtleShapesMap = FILE_READER.getShapes();
	for (String idx : turtleShapesMap.keySet()) {
	    simulationChoices.add(idx+". "+turtleShapesMap.get(idx));
	}
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId(itemID);
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener((arg0,arg1, arg2)-> {
	    String selected = (String) simulationChoices.get((Integer) arg2);
	    if (!selected.equals(selectionPrompt)) {
		String selectedShapeIdx = (selected.split(". "))[0];
		FILE_READER.parseSettingInput(DEFAULT_SHAPE_COMMAND+" "+selectedShapeIdx);
		USER_SCREEN.updateCurrentState(CURRENTSTATE_KEYS[0], selectedShapeIdx);
	    }
	});
	return dropDownMenu;
    }    

    /**
     * @param defaultChoice: String that represents the default value for this combo box
     * @return A ComboBox bearing the default choice
     */
    private ComboBox<Object> makeComboBox(String defaultChoice) {
	ComboBox<Object> dropDownMenu = new ComboBox<>();
	dropDownMenu.setVisibleRowCount(VISIBLE_ROW_COUNT);
	dropDownMenu.setValue(defaultChoice);
	return dropDownMenu;
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
	String promptText = FILE_READER.resourceDisplayText("MovePrompt");
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
		sendCommandAddHistory(command,value);
	    }
	    catch (Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }

    private void moveDown(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Backward " + amount;
		sendCommandAddHistory(command,value);
	    }
	    catch (Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }

    private void moveLeft(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Left " + amount;
		sendCommandAddHistory(command,value);
	    }
	    catch (Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }

    private void moveRight(String value) {
	if (!value.isEmpty()) {
	    try {
		int amount = Integer.parseInt(value);
		String command = "Right " + amount;
		sendCommandAddHistory(command,value);
	    }
	    catch (Exception e) {
		// do nothing, don't move the turtle
	    }
	}
    }

    private void sendCommandAddHistory(String command, String value) {
	try {
	    USER_SCREEN.sendCommandToParse(command);
	    USER_SCREEN.addCommand(command, value);
	}
	catch(Exception e) {
	    // do nothing, don't move the turtle
	}
    }


    @Override
    protected UserScreen getUserScreen() {
	// TODO Auto-generated method stub
	return USER_SCREEN;
    }

    @Override
    protected BorderPane getPane() {
	return PANE;
    }
}
