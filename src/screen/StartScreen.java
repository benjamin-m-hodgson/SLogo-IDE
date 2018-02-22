package screen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import mediator.Controller;

public class StartScreen implements Screen {
    private final int VISIBLE_ROW_COUNT = 5;
    private final String DEFAULT_SELECTION_PROMPT = "Select a language";
    private final String DEFAULT_APPLY_PROMPT = "Apply";
    private final String DEFAULT_START_PROMPT = "Start";
    private Parent ROOT;
    private Controller PROGRAM_CONTROLLER;
    private Button START;
    private Button APPLY;

    public StartScreen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makeRoot() {
	START = makeStartButton();
	VBox rootBox = new VBox(10, START);
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
	Button startButton = new Button(DEFAULT_START_PROMPT);
	// TODO: format with CSS
	startButton.setAlignment(Pos.CENTER);
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
	Button startButton = new Button(DEFAULT_APPLY_PROMPT);
	// TODO: format with CSS
	startButton.setAlignment(Pos.CENTER);
	// handle click event
	startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		// TODO: change display prompts on start screen to reflect applying language
	    }
	});
	startButton.setDisable(true);
	return startButton;
    }
    
    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> languageChooser() {
	String defaultPrompt;
	try {
	    defaultPrompt = PROGRAM_CONTROLLER.resourceDisplayText("LanguageSelection");
	}
	catch (Exception e) {
	    defaultPrompt = DEFAULT_SELECTION_PROMPT;
	}
	final String defaultString = defaultPrompt;
	ComboBox<Object> dropDownMenu = makeComboBox(defaultPrompt);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(defaultPrompt);
	//simulationChoices.addAll(getLanguages());
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId("languageChooser");
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener(new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> arg0, 
		    Number arg1, Number arg2) {
		String selected = (String) simulationChoices.get((Integer) arg2);
		if (!selected.equals(defaultString)) {
		    APPLY.setDisable(false);
		} 
		else {
		    APPLY.setDisable(true);
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
}
