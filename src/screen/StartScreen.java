package screen;


import interpreter.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Benjamin Hodgson
 * 
 * A class that implements the Screen super class to create a Start screen to display at 
 * application launch. Displays a panel to the user that has them choose an initial language
 * before launching the program and creating the UserScreen.
 */
public class StartScreen implements Screen {
    private final int VISIBLE_ROW_COUNT = 5;
    private String SELECTION_PROMPT;
    private Parent ROOT;
    private Controller PROGRAM_CONTROLLER;
    private Button START;
    private String LANGUAGE;
    private Tooltip START_TIP;
    private Tooltip SELECTION_TIP;

    public StartScreen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
	SELECTION_PROMPT = PROGRAM_CONTROLLER.resourceDisplayText("languageChooser");
    }

    @Override
    public void makeRoot() {
	makeTips();
	// make control objects, must be done after making their tips
	START = makeStartButton();
	ComboBox<Object> languageChooser = makeLanguageChooser();
	// write to object tips, must be done after control objects created
	writeTipText();
	VBox rootBox = new VBox(START, languageChooser);
	rootBox.setId("startScreenRoot");
	ROOT = rootBox;
    }

    @Override
    public Parent getRoot() {
	if (ROOT == null) {
	    makeRoot();
	}
	return ROOT;
    }

    @Override
    public void changeBackgroundColor(String color) {
	// method does nothing, can't change start screen background color
    }

    @Override
    public void changeRightPanel(Parent panelRoot) {
	// method does nothing, start screen has no right panel
    }

    /**
     * Starts the program by creating and displaying the UserScreen upon button click
     * 
     * @return Button: Button to start the program
     */
    private Button makeStartButton() {
	Button startButton = new Button(PROGRAM_CONTROLLER.resourceDisplayText("StartPrompt"));
	startButton.setId("startButton");
	startButton.setTooltip(START_TIP);
	// handle click event
	startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		PROGRAM_CONTROLLER.loadUserScreen();  
	    }
	});
	startButton.setDisable(true);
	return startButton;
    }

    /**
     * Creates a drop down menu with language options for changing the language used 
     * in the program.
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makeLanguageChooser() {
	ComboBox<Object> dropDownMenu = makeComboBox(SELECTION_PROMPT);
	dropDownMenu.setTooltip(SELECTION_TIP);
	dropDownMenu.setItems(simulationChoices());
	dropDownMenu.setId("languageChooser");
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener((arg0, arg1,  arg2) -> {
	    String selected = (String) simulationChoices().get((Integer) arg2);
	    if (!selected.equals(SELECTION_PROMPT)) { 
		START.setDisable(false);
		LANGUAGE = selected;
		PROGRAM_CONTROLLER.changeLanguage(LANGUAGE);
		updatePrompt();
		dropDownMenu.setItems(simulationChoices());
	    } 
	    else {
		START.setDisable(true);
	    }
	});
	return dropDownMenu;
    }
    
    /**
     * @return ObservableList<Object>: a list of selections to place in the combo box
     */
    private ObservableList<Object> simulationChoices() {
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(SELECTION_PROMPT);
	simulationChoices.addAll(PROGRAM_CONTROLLER.getLanguages());
	return simulationChoices;
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
     * Creates the necessary Tooltips and sets their text styling
     */
    private void makeTips() {
	START_TIP = new Tooltip();
	SELECTION_TIP = new Tooltip();
    }

    /**
     * Writes the appropriate text on each Tooltip
     */
    private void writeTipText() {
	START_TIP.setText(START.getText());
	SELECTION_TIP.setText(SELECTION_PROMPT);
    }

    /**
     * Updates the text displayed to the user to match the current language
     */
    private void updatePrompt() {
	START.setText(PROGRAM_CONTROLLER.resourceDisplayText("StartPrompt"));
	SELECTION_PROMPT = PROGRAM_CONTROLLER.resourceDisplayText("languageChooser");
	writeTipText();
    }
}
