package screen.panel;

import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;



import interpreter.FileIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import screen.UserScreen;

/**
 * 
 * @author Benjamin Hodgson
 *
 * Class that creates a panel where the user can change display settings 
 * and save and load user preferences
 *
 */
public class PreferencePanel extends SpecificPanel {
    public  final String DEFAULT_BGCOLORCHANGE_COMMAND;
    public  final String PREFERENCES_FOLDER;
    private final int VISIBLE_ROW_COUNT;
    private final String[] DROPDOWN_IDS = {"backgroundColorChooser", "preferencesChooser"};
    private final String[] BUTTON_IDS = {"backButton", "saveprefButton"};
    private final String[] CURRENTSTATE_KEYS;
    private  Button BACK;
    private Button SAVE_PREFERENCES;
    private ComboBox<Object> BACKGROUND_COLOR_CHOOSER;
    private ComboBox<Object> PREFERENCES_CHOOSER;
    private TextArea PARAMETER_INPUT;
    private BorderPane PANE;
    private UserScreen USER_SCREEN;
    private final FileIO fileReader;

    public PreferencePanel(BorderPane pane, UserScreen userScreen, FileIO fileReaderIn, 
	    String[] currentStateKeys) {
	PANE = pane;
	USER_SCREEN = userScreen;
	fileReader = fileReaderIn;
	CURRENTSTATE_KEYS = currentStateKeys;
	DEFAULT_BGCOLORCHANGE_COMMAND = fileReader.resourceSettingsText("defaultBGColorChangeCommand");
	PREFERENCES_FOLDER = fileReader.resourceSettingsText("preferencesFolder");
	VISIBLE_ROW_COUNT = Integer.parseInt(fileReader.resourceSettingsText("preferencesPanelVisibleRowCount"));
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
	BACK = makeBackPrefButton(BUTTON_IDS[0]);
	BACKGROUND_COLOR_CHOOSER = makeBackgroundColorChooser(DROPDOWN_IDS[0]);
	PREFERENCES_CHOOSER = makeWorkspacePrefDropDown(DROPDOWN_IDS[1]);
	SAVE_PREFERENCES = makeSavePrefButton(BUTTON_IDS[1]);
	PARAMETER_INPUT = makePrefNamer(SAVE_PREFERENCES);
	VBox preferencePanel = new VBox(BACKGROUND_COLOR_CHOOSER,
		PREFERENCES_CHOOSER, PARAMETER_INPUT,SAVE_PREFERENCES, BACK);
	preferencePanel.setId("infoPanel");
	PANEL = preferencePanel;
    }
    
    private TextArea makePrefNamer(Button button) {
	TextArea parameterInput = new TextArea();
	parameterInput.setId("parametersField"); 
	parameterInput.setPromptText(fileReader.resourceDisplayText("fileNaming"));
	parameterInput.setEditable(true);
	parameterInput.setOnKeyTyped((arg0) -> button.setDisable(false));
	return parameterInput;
    }

    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * background color for the simulation
     */
    private ComboBox<Object> makeBackgroundColorChooser(String itemID) {
	String selectionPrompt = fileReader.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip backgroundTip = new Tooltip();
	backgroundTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(backgroundTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	Map<String, String> colorPaletteNames = fileReader.getColors();
	for (String key : colorPaletteNames.keySet()) {
	    simulationChoices.add(key+". "+colorPaletteNames.get(key));
	}
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId(itemID);
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener((arg0,arg1, arg2)->{
	    String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
	    if (!selected.equals(selectionPrompt)) {
		String selectedColorIdx = (selected.split(". "))[0];
		fileReader.parseSettingInput(DEFAULT_BGCOLORCHANGE_COMMAND+" "+selectedColorIdx);
		String colorCode = fileReader.palleteColorText(selectedColorIdx);
		USER_SCREEN.updateCurrentState(CURRENTSTATE_KEYS[1], colorCode);

	    }
	});
	return dropDownMenu;
    }


    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makeWorkspacePrefDropDown(String itemID) {
	String selectionPrompt = fileReader.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip workspaceTip = new Tooltip();
	workspaceTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(workspaceTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	List<String> preferenceOptions = fileReader.getFileNames(PREFERENCES_FOLDER);
	simulationChoices.addAll(preferenceOptions);
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId(itemID);
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener((arg0, arg1, arg2) -> {
	    String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
	    if (!selected.equals(selectionPrompt)) {
		USER_SCREEN.applyPreferences(selected);
		updatePrompt();
	    }
	});
	return dropDownMenu;
    }

    private Button makeSavePrefButton(String itemId) {
	Button saveButton = makeButton(itemId);
	saveButton.setDisable(true);
	saveButton.setOnMouseClicked((arg0)->{
	    System.out.println("hit");
	    Map<String, String> currentState = USER_SCREEN.getCurrentState();
	    try {
		String currentDir = System.getProperty("user.dir");
		File dir = new File(currentDir + File.separator + "workspacePreferences");
		File newPref=new File(dir,PARAMETER_INPUT.getText()+".properties");
		if(!newPref.exists()){
		    newPref.createNewFile();
		}
		FileWriter fw = new FileWriter(newPref);
		BufferedWriter out = new BufferedWriter(fw);
		for(String key: currentState.keySet()) {
		    out.write(key + " = " + currentState.get(key));
		    out.newLine();
		}
		out.flush();
		out.close();
		updatePrompt();
	    } catch (IOException e) {
		USER_SCREEN.displayErrorMessage("Failed to save Properties");
	    }});
	return saveButton;
    }

    private Button makeBackPrefButton(String itemId) {
	Button backButton = new Button(fileReader.resourceDisplayText(itemId));
	backButton.setId(itemId);
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(new SettingsPanel(PANE, USER_SCREEN, fileReader).getPanel()));
	return backButton;
    }

    private Button makeButton(String itemId) {
	Button button = new Button(fileReader.resourceDisplayText(itemId));
	button.setId(itemId);
	Tooltip buttonTip = new Tooltip();
	buttonTip.setText(fileReader.resourceDisplayText(itemId));
	button.setTooltip(buttonTip);
	return button;
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
	BACK = makeBackPrefButton(BUTTON_IDS[0]);
	BACKGROUND_COLOR_CHOOSER = makeBackgroundColorChooser(DROPDOWN_IDS[1]);
	PREFERENCES_CHOOSER = makeWorkspacePrefDropDown(DROPDOWN_IDS[1]);
	SAVE_PREFERENCES = makeSavePrefButton(BUTTON_IDS[1]);
	PARAMETER_INPUT = makePrefNamer(SAVE_PREFERENCES);
	((VBox)PANEL).getChildren().setAll(BACKGROUND_COLOR_CHOOSER, 
		PREFERENCES_CHOOSER, PARAMETER_INPUT,SAVE_PREFERENCES, BACK);
    }

}
