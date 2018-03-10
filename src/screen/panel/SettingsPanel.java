package screen.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import interpreter.BadFormatException;
import interpreter.FileIO;
import interpreter.MissingInformationException;
import interpreter.TurtleNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screen.UserScreen;
import StartUp.Driver;



/**
 * A class which extends Specific Panel and allows the user to dynamically change the settings. 
 * @author Andrew Arnold
 *
 */
public class SettingsPanel extends SpecificPanel  {

    private final int VISIBLE_ROW_COUNT = 5;
    public static final String PREFERENCES_FOLDER = "workspacePreferences";
    public static final String DEFAULT_BGCOLORCHANGE_COMMAND = "SetBackground";
    public static final String DEFAULT_PENCOLORCHANGE_COMMAND = "SetPenColor";
    public static final String DEFAULT_SHAPE_COMMAND = "SetShape";
    private  Button BACK;
    private BorderPane PANE;
    private Button NEW_WORKSPACE;
    private Button PREFERENCES;
    private ComboBox<Object> LANGUAGE_CHOOSER;
    private ComboBox<Object> PEN_COLOR_CHOOSER;
    private ComboBox<Object> TURTLE_IMAGE_CHOOSER;
    private UserScreen USER_SCREEN;
    private final FileIO fileReader;
    private final int DEFAULT_BUTTON_SPACING = 10;
    private final String[] DROPDOWN_IDS = {"languageSettingsChooser", "penColorChooser", "turtleImageChooser"};
    private final String[] CURRENTSTATE_KEYS = {"language", "turtleImage", "backgroundColor"};
    private final String[] BUTTON_IDS = {"newworkspaceButton", "preferencesButton"};

    public SettingsPanel(BorderPane pane, UserScreen userScreen, FileIO fileReaderIn) {
	PANE = pane;
	USER_SCREEN = userScreen;
	fileReader = fileReaderIn;
    }


    @Override
    public void makePanel() {
	BACK = makeBackButton(fileReader);
	NEW_WORKSPACE =  makeNewWorkspaceButton(BUTTON_IDS[0]);
	LANGUAGE_CHOOSER = makeLanguageChooser(DROPDOWN_IDS[0]);
	PEN_COLOR_CHOOSER = makePenColorChooser(DROPDOWN_IDS[1]);
	TURTLE_IMAGE_CHOOSER= makeTurtleImageChooser(DROPDOWN_IDS[2]);
	PREFERENCES = makePreferenceButton(BUTTON_IDS[1]);
	VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACING, LANGUAGE_CHOOSER, 
		PEN_COLOR_CHOOSER, TURTLE_IMAGE_CHOOSER, PREFERENCES, NEW_WORKSPACE, BACK);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	PANEL = panelRoot;
    }

    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makePenColorChooser(String itemID) {
	String selectionPrompt = fileReader.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip penTip = new Tooltip();
	penTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(penTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	Map<String, String> colorPaletteNames = fileReader.getColors();
	for (String key : colorPaletteNames.keySet()) {
	    simulationChoices.add(key+". "+colorPaletteNames.get(key));
	}
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId(itemID);
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener(( arg0, arg1, arg2) ->{
	    String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
	    if (!selected.equals(selectionPrompt)) {
		String selectedColorIdx = (selected.split(". "))[0];
		fileReader.parseSettingInput(DEFAULT_PENCOLORCHANGE_COMMAND+" "+selectedColorIdx);
	    }
	});
	return dropDownMenu;
    }

    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makeLanguageChooser(String itemID) {
	String selectionPrompt = fileReader.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip languageTip = new Tooltip();
	languageTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(languageTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	simulationChoices.addAll(fileReader.getLanguages());
	dropDownMenu.setItems(simulationChoices);
	dropDownMenu.setId(itemID);
	dropDownMenu.getSelectionModel().selectedIndexProperty()
	.addListener(( arg0, arg1,  arg2) ->{
	    String selected = (String) simulationChoices.get((Integer) arg2);
	    if (!selected.equals(selectionPrompt)) {
		fileReader.bundleUpdateToNewLanguage(selected);
		updatePrompt();
		USER_SCREEN.updateCurrentState(CURRENTSTATE_KEYS[0], selected);
	    }
	});
	return dropDownMenu;
    }

    /**
     * 
     * @return dropDownMenu: a drop down menu that lets the user choose the
     * language for the simulation
     */
    private ComboBox<Object> makeTurtleImageChooser(String itemID) {
	String selectionPrompt = fileReader.resourceDisplayText(itemID);
	ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
	Tooltip turtleTip = new Tooltip();
	turtleTip.setText(selectionPrompt);
	dropDownMenu.setTooltip(turtleTip);
	ObservableList<Object> simulationChoices = 
		FXCollections.observableArrayList(selectionPrompt);
	Map<String, String> turtleShapesMap = fileReader.getShapes();
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
		fileReader.parseSettingInput(DEFAULT_SHAPE_COMMAND+" "+selectedShapeIdx);
		USER_SCREEN.updateCurrentState(CURRENTSTATE_KEYS[1], selectedShapeIdx);
	    }
	});
	return dropDownMenu;
    }  

    private Button makeNewWorkspaceButton(String itemId) {
	Button workspaceButton = makeButton(itemId);
	workspaceButton.setOnAction(click ->{Driver d = new Driver();try {
	    d.start(new Stage());
	} catch (Exception e) {
	    Alert a = new Alert(AlertType.ERROR);
	    a.setContentText("Failed to attach workspace button action");
	    a.showAndWait();
	}});
	return workspaceButton;
    }

    private Button makePreferenceButton(String itemId) {
	Button preferenceButton = makeButton(itemId);
	// override click event
	preferenceButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(new PreferencePanel(PANE, USER_SCREEN, fileReader,CURRENTSTATE_KEYS).getPanel()));
	return preferenceButton;
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
     * Updates the text displayed to the user to match the current language
     */
    private void updatePrompt() {
	BACK.setText(fileReader.resourceDisplayText("backButton"));
	LANGUAGE_CHOOSER = makeLanguageChooser(DROPDOWN_IDS[0]);
	NEW_WORKSPACE =  makeNewWorkspaceButton(BUTTON_IDS[0]);
	PREFERENCES = makePreferenceButton(BUTTON_IDS[1]);
	PEN_COLOR_CHOOSER = makePenColorChooser(DROPDOWN_IDS[1]);
	TURTLE_IMAGE_CHOOSER= makeTurtleImageChooser(DROPDOWN_IDS[2]);
	((VBox)PANEL).getChildren().setAll(LANGUAGE_CHOOSER, PEN_COLOR_CHOOSER, 
		TURTLE_IMAGE_CHOOSER, PREFERENCES, NEW_WORKSPACE, BACK);
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

    @Override
    protected UserScreen getUserScreen() {
	return USER_SCREEN;
    }


    @Override
    protected BorderPane getPane() {
	return PANE;
    }
}
