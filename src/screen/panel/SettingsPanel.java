package screen.panel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interpreter.BadFormatException;
import interpreter.Controller;
import interpreter.MissingInformationException;
import interpreter.TurtleNotFoundException;
import interpreter.UnidentifiedCommandException;
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

	public static final int VISIBLE_ROW_COUNT = 5;
	public static final String COLOR_FOLDER = "colors";
	public static final String PREFERENCES_FOLDER = "workspacePreferences";
	public static final String TURTLE_IMAGE_FOLDER = "turtleimages";
	public static final String DEFAULT_BGCOLORCHANGE_COMMAND = "SetBackground";
	public static final String DEFAULT_PENCOLORCHANGE_COMMAND = "SetPenColor";
	private  Parent PANEL;
	private final Controller PROGRAM_CONTROLLER;
	private final BorderPane PANE;
	private  Button BACK;
	private Button NEW_WORKSPACE;
	private Button SAVE_PREFERENCES;
	private ComboBox<Object> LANGUAGE_CHOOSER;
	private ComboBox<Object> BACKGROUND_COLOR_CHOOSER;
	private ComboBox<Object> PEN_COLOR_CHOOSER;
	private ComboBox<Object> TURTLE_IMAGE_CHOOSER;
	private ComboBox<Object> PREFERENCES_CHOOSER;
	private List<String> colorsUntranslated;
	private List<String> colorsTranslated;
	private UserScreen USER_SCREEN;
	private final int DEFAULT_BUTTON_SPACEING = 10;
	private final String[] DROPDOWN_IDS = {"languageSettingsChooser", "backgroundColorChooser", "penColorChooser", "turtleImageChooser", "preferencesChooser"};
	private final String[] BUTTON_IDS = {"newworkspaceButton", "saveprefButton"};

	public SettingsPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
		PROGRAM_CONTROLLER = programController;
		PANE = pane;
		USER_SCREEN = userScreen;
		//		String codeTest = "#2d3436";
		//		codeTest = codeTest.substring(1, codeTest.length());
		//		int hexConvert = Integer.parseInt(codeTest,16);
		//		System.out.println(Integer.toHexString(hexConvert));
	}


	@Override
	public void makePanel() {
		BACK = makeBackButton(PROGRAM_CONTROLLER);
		NEW_WORKSPACE =  makeNewWorkspaceButton(BUTTON_IDS[0]);
		LANGUAGE_CHOOSER = makeLanguageChooser(DROPDOWN_IDS[0]);
		BACKGROUND_COLOR_CHOOSER = makeBackgroundColorChooser(DROPDOWN_IDS[1]);
		PEN_COLOR_CHOOSER = makePenColorChooser(DROPDOWN_IDS[2]);
		TURTLE_IMAGE_CHOOSER= makeTurtleImageChooser(DROPDOWN_IDS[3]);
		HBox prefHolder = makeWorkspacePrefChooser(DROPDOWN_IDS[4],BUTTON_IDS[1]);
		VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACEING, LANGUAGE_CHOOSER,BACKGROUND_COLOR_CHOOSER,PEN_COLOR_CHOOSER,TURTLE_IMAGE_CHOOSER,prefHolder,NEW_WORKSPACE,BACK);
		panelRoot.setId("infoPanel");
		panelRoot.setAlignment(Pos.BASELINE_CENTER);
		PANEL = panelRoot;
	}


	/**
	 * 
	 * @return dropDownMenu: a drop down menu that lets the user choose the
	 * language for the simulation
	 */
	private ComboBox<Object> makeLanguageChooser(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		Tooltip languageTip = new Tooltip();
		languageTip.setText(selectionPrompt);
		dropDownMenu.setTooltip(languageTip);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		simulationChoices.addAll(PROGRAM_CONTROLLER.getLanguages());
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(( arg0, arg1,  arg2) ->{
			String selected = (String) simulationChoices.get((Integer) arg2);
			if (!selected.equals(selectionPrompt)) {
				PROGRAM_CONTROLLER.changeLanguage(selected);
				updatePrompt();
			}
		});
		return dropDownMenu;
	}

	/**
	 * 
	 * @return dropDownMenu: a drop down menu that lets the user choose the
	 * background color for the simulation
	 */
	private ComboBox<Object> makeBackgroundColorChooser(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		Tooltip backgroundTip = new Tooltip();
		backgroundTip.setText(selectionPrompt);
		dropDownMenu.setTooltip(backgroundTip);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		colorsUntranslated = PROGRAM_CONTROLLER.getFileNames(COLOR_FOLDER);
		colorsTranslated = PROGRAM_CONTROLLER.translateColors(colorsUntranslated);
		Map<String, String> colorPaletteNames = PROGRAM_CONTROLLER.getColors();
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
				try {
					PROGRAM_CONTROLLER.parseSettingInput(DEFAULT_BGCOLORCHANGE_COMMAND+" "+selectedColorIdx);
				} catch (TurtleNotFoundException | BadFormatException | UnidentifiedCommandException
						| MissingInformationException e) {
					PROGRAM_CONTROLLER.loadErrorScreen(e.getMessage());
				} 
			}
		});
		return dropDownMenu;
	}

	/**
	 * 
	 * @return dropDownMenu: a drop down menu that lets the user choose the
	 * language for the simulation
	 */
	private ComboBox<Object> makePenColorChooser(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		Tooltip penTip = new Tooltip();
		penTip.setText(selectionPrompt);
		dropDownMenu.setTooltip(penTip);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
//		colorsUntranslated = PROGRAM_CONTROLLER.getFileNames(COLOR_FOLDER);
//		colorsTranslated = PROGRAM_CONTROLLER.translateColors(colorsUntranslated);
//		simulationChoices.addAll(colorsTranslated);
		Map<String, String> colorPaletteNames = PROGRAM_CONTROLLER.getColors();
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
				try {
					PROGRAM_CONTROLLER.parseSettingInput(DEFAULT_PENCOLORCHANGE_COMMAND+" "+selectedColorIdx);
				} catch (TurtleNotFoundException | BadFormatException | UnidentifiedCommandException
						| MissingInformationException e) {
					PROGRAM_CONTROLLER.loadErrorScreen(e.getMessage());
				} 
			}
		});
		return dropDownMenu;
	}

	private HBox makeWorkspacePrefChooser(String dropId, String buttonId) {
		PREFERENCES_CHOOSER = makeWorkspacePrefDropDown(dropId);
		SAVE_PREFERENCES = new Button(PROGRAM_CONTROLLER.resourceDisplayText(buttonId));
		SAVE_PREFERENCES.setId(buttonId);
		HBox holder = new HBox(PREFERENCES_CHOOSER,SAVE_PREFERENCES);


		return holder;

	}

	/**
	 * 
	 * @return dropDownMenu: a drop down menu that lets the user choose the
	 * language for the simulation
	 */
	private ComboBox<Object> makeWorkspacePrefDropDown(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		List<String> preferenceOptions = PROGRAM_CONTROLLER.getFileNames(PREFERENCES_FOLDER);
		simulationChoices.addAll(preferenceOptions);
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(( arg0, arg1, arg2) ->{
			String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
			if (!selected.equals(selectionPrompt)) {
				USER_SCREEN.applyPreferences(selected);
				updatePrompt();
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
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		Tooltip turtleTip = new Tooltip();
		turtleTip.setText(selectionPrompt);
		dropDownMenu.setTooltip(turtleTip);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		Map<String, String> turtleShapesMap = PROGRAM_CONTROLLER.getShapes();
		for (String idx : turtleShapesMap.keySet()) {
			simulationChoices.add(idx+". "+turtleShapesMap.get(idx));
		}
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener((arg0,arg1, arg2)-> {
			String selected = (String) simulationChoices.get((Integer) arg2);
			if (!selected.equals(selectionPrompt)) {
				try {
					USER_SCREEN.changeTurtleImage(selected);
				} catch (TurtleNotFoundException | BadFormatException | UnidentifiedCommandException
						| MissingInformationException e) {
					PROGRAM_CONTROLLER.loadErrorScreen(e.getMessage());
				}

			}
		});
		return dropDownMenu;
	}  

	private Button makeNewWorkspaceButton(String itemId) {
		Button workspaceButton = new Button(PROGRAM_CONTROLLER.resourceDisplayText(itemId));
		workspaceButton.setId(itemId);
		Tooltip workspaceTip = new Tooltip();
		workspaceTip.setText(PROGRAM_CONTROLLER.resourceDisplayText(itemId));
		workspaceButton.setTooltip(workspaceTip);
		workspaceButton.setOnAction(click ->{Driver d = new Driver();try {
			d.start(new Stage());
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Failed to attach workspace button action");
			a.showAndWait();
		}});
		return workspaceButton;
	}

	/**
	 * Updates the text displayed to the user to match the current language
	 */
	private void updatePrompt() {
		BACK.setText(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
		LANGUAGE_CHOOSER = makeLanguageChooser(DROPDOWN_IDS[0]);
		NEW_WORKSPACE =  makeNewWorkspaceButton(BUTTON_IDS[0]);
		BACKGROUND_COLOR_CHOOSER = makeBackgroundColorChooser(DROPDOWN_IDS[1]);
		PEN_COLOR_CHOOSER = makePenColorChooser(DROPDOWN_IDS[2]);
		TURTLE_IMAGE_CHOOSER= makeTurtleImageChooser(DROPDOWN_IDS[3]);
		HBox prefHolder = makeWorkspacePrefChooser(DROPDOWN_IDS[4], BUTTON_IDS[1]);
		((VBox)PANEL).getChildren().setAll(LANGUAGE_CHOOSER,BACKGROUND_COLOR_CHOOSER,PEN_COLOR_CHOOSER,TURTLE_IMAGE_CHOOSER,prefHolder,NEW_WORKSPACE,BACK);
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
	public Parent getPanel() {
		if (PANEL == null) {
			makePanel();
		}
		return PANEL;
	}

	@Override
	protected BorderPane getPane() {
		// TODO Auto-generated method stub
		return PANE;
	}

	@Override
	protected Controller getController() {
		// TODO Auto-generated method stub
		return PROGRAM_CONTROLLER;
	}

	@Override
	protected UserScreen getUserScreen() {
		return USER_SCREEN;
	}
}
