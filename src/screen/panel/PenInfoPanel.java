package screen.panel;

import java.util.Map;

import interpreter.FileIO;
import interpreter.SingleTurtle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import screen.UserScreen;

public class PenInfoPanel extends SpecificPanel {
    // TODO: put in settings .properties file
    private final double FRAMES_PER_SECOND = 2;
    private final long MILLISECOND_DELAY = Math.round(1000 / FRAMES_PER_SECOND);
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final int VISIBLE_ROW_COUNT = 5;
    public static final String DEFAULT_SHAPE_COMMAND = "SetShape";
    public static final String DEFAULT_PENCOLORCHANGE_COMMAND = "SetPenColor";
    public static final String DEFAULT_PENSIZE_COMMAND = "SetPenSize";
    private int WIDTH_MIN = 0;
    private int WIDTH_MAX = 10;
    private final FileIO FILE_READER;
    private UserScreen USER_SCREEN;
    private String TURTLE_ID;
    private SingleTurtle TURTLE;
    private BorderPane PANE;
    private Label PEN_DOWN;
    private Label COLOR;

    public PenInfoPanel(BorderPane pane, UserScreen userScreen, String id, FileIO fileReader) {
	FILE_READER = fileReader;
	PANE = pane;
	USER_SCREEN = userScreen;
	TURTLE_ID = id;
	TURTLE = USER_SCREEN.getAllTurtles().get(Integer.parseInt(TURTLE_ID) - 1);
	WIDTH_MIN = Integer.parseInt(FILE_READER.resourceSettingsText("turtleInfoPanelMinWidth"));
	WIDTH_MAX = Integer.parseInt(FILE_READER.resourceSettingsText("turtleInfoPanelMaxWidth"));
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> checkChange(SECOND_DELAY));
	Timeline animation = new Timeline();
	animation.setCycleCount(Animation.INDEFINITE);
	animation.getKeyFrames().add(frame);
	animation.play();
    }

    @Override
    protected BorderPane getPane() {
	return PANE;
    }

    @Override
    protected UserScreen getUserScreen() {
	return USER_SCREEN;
    }

    @Override
    public void makePanel() {
	Button backButton = makePenBackButton("backButton");
	VBox penOptions = makePenOptions();
	ComboBox<Object> penColorChooser = makePenColorChooser("penColorChooser");
	VBox penInfoPanel = new VBox(penOptions, penColorChooser, backButton);
	penInfoPanel.setId("infoPanel");
	PANEL = penInfoPanel;	
    }

    private Button makePenBackButton(String itemId) {
	Button backButton = new Button(FILE_READER.resourceDisplayText(itemId));
	backButton.setId(itemId);
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(new TurtleInfoPanel(PANE, USER_SCREEN, TURTLE_ID, 
			FILE_READER).getPanel()));
	return backButton;
    }

    private VBox makePenOptions() {
	Label penDownLabel = new Label(FILE_READER.resourceDisplayText("penDown"));
	penDownLabel.setId("variableNameLabel");
	PEN_DOWN = new Label(Boolean.toString(TURTLE.getPenVisibility()));
	PEN_DOWN.setId("variableNameLabel");
	HBox penDown = new HBox(penDownLabel, PEN_DOWN);
	penDown.setAlignment(Pos.CENTER);
	Label penWidthLabel = new Label(FILE_READER.resourceDisplayText("penWidth"));
	penWidthLabel.setId("variableNameLabel");
	TextField penWidthField = widthField(PANE, WIDTH_MIN, WIDTH_MAX, 
		Double.toString(TURTLE.getPenWidth()));
	HBox penWidth = new HBox(penWidthLabel, penWidthField);
	penWidth.setAlignment(Pos.CENTER);
	Label penColorLabel = new Label(FILE_READER.resourceDisplayText("penColor"));
	penColorLabel.setId("variableNameLabel");
	COLOR = new Label(TURTLE.getPenColor());
	COLOR.setId("variableNameLabel");
	HBox penColor = new HBox(penColorLabel, COLOR);
	penColor.setAlignment(Pos.CENTER);
	VBox penOptions = new VBox(penDown, penWidth, penColor);
	penOptions.setId("moveBox");
	return penOptions;
    }

    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makePenColorChooser(String itemID) {
	String selectionPrompt = FILE_READER.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip penTip = new Tooltip();
	penTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(penTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	Map<String, String> colorPaletteNames = FILE_READER.getColors();
	for (String key : colorPaletteNames.keySet()) {
	    simulationChoices.add(key+". "+colorPaletteNames.get(key));
	}
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId(itemID);
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener(( arg0, arg1, arg2) -> {
	    String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
	    if (!selected.equals(selectionPrompt)) {
		String selectedColorIdx = (selected.split(". "))[0];
		FILE_READER.parseSettingInput(DEFAULT_PENCOLORCHANGE_COMMAND+" "+selectedColorIdx);
		// TODO: add to history
		getPane().setRight(new PenInfoPanel(PANE, USER_SCREEN, 
			TURTLE_ID, FILE_READER).getPanel());
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
     * Creates a text field that takes integer only input to set the width amount for the 
     * turtle pen
     * 
     * @param min: the minimum allowable input value
     * @param max: the maximum allowable input value
     * @return movementField: a text field that allows the user to input an integer amount
     * to specify the width of the pen
     */
    private TextField widthField(Parent root, int min, int max, String currentValue) {
	TextField numberTextField = new TextField();
	numberTextField.setId("widthField");
	String promptText = FILE_READER.resourceDisplayText("penWidth");
	numberTextField.setPromptText(promptText);
	numberTextField.setText(currentValue);
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
			    FILE_READER.parseSettingInput(DEFAULT_PENSIZE_COMMAND+" "+Integer.toString(sizeVal));
			}
			else {
			    numberTextField.setText(currentValue);
			}
		    }
		    catch(Exception e) {
			numberTextField.setText(currentValue);
		    }
		    root.requestFocus();
		}
	    }
	});
	return numberTextField;
    }
    
    private void checkChange(double elapsedTime) {
	if (!PEN_DOWN.getText().equals(Boolean.toString(TURTLE.getPenVisibility()))) {
	    getPane().setRight(new PenInfoPanel(PANE, USER_SCREEN, 
			TURTLE_ID, FILE_READER).getPanel());
	}
    }
}
