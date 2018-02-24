package screen;


import interpreter.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartScreen implements Screen {
    private final int VISIBLE_ROW_COUNT = 5;
    private String SELECTION_PROMPT = "Select a language";
    private String APPLY_PROMPT = "Apply";
    private String START_PROMPT = "Start";
    private Parent ROOT;
    private Controller PROGRAM_CONTROLLER;
    private Button START;
    private Button APPLY;
    private String LANGUAGE;

    public StartScreen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makeRoot() {
	START = makeStartButton();
	APPLY = makeApplyButton();
	ComboBox<Object> languageSelector = languageChooser();
	HBox centerBox = new HBox(languageSelector, APPLY);
	centerBox.setId("centerBox");
	VBox rootBox = new VBox(START, centerBox);
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
     * 
     * @return Button: Button to start the program
     */
    private Button makeStartButton() {
	Button startButton = new Button(START_PROMPT);
	startButton.setId("startButton");
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
     * 
     * @return Button: Button to apply a language change
     */
    private Button makeApplyButton() {
	Button applyButton = new Button(APPLY_PROMPT);
	applyButton.setId("applyButton");
	// handle click event
	applyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		PROGRAM_CONTROLLER.changeLanguage(LANGUAGE);
		START.setDisable(false);
	    }
	});
	applyButton.setDisable(true);
	return applyButton;
    }
    
    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> languageChooser() {
	String defaultPrompt = SELECTION_PROMPT;
	ComboBox<Object> dropDownMenu = makeComboBox(defaultPrompt);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(defaultPrompt);
	simulationChoices.addAll(PROGRAM_CONTROLLER.getLanguages());
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId("languageChooser");
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener(new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> arg0, 
		    Number arg1, Number arg2) {
		String selected = (String) simulationChoices.get((Integer) arg2);
		if (!selected.equals(defaultPrompt)) {
		    APPLY.setDisable(false);
		    LANGUAGE = selected;
		} 
		else {
		    APPLY.setDisable(true);
		    START.setDisable(true);
		}
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
     * Updates the text displayed to the user to match the current language
     */
    private void updatePrompt() {
	APPLY_PROMPT = PROGRAM_CONTROLLER.resourceDisplayText("ApplyPrompt");
	START_PROMPT = PROGRAM_CONTROLLER.resourceDisplayText("StartPrompt");
	SELECTION_PROMPT = PROGRAM_CONTROLLER.resourceDisplayText("LanguageSelection");
    }
}
