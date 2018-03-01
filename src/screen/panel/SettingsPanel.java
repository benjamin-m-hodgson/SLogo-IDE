package screen.panel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import screen.UserScreen;
import java.util.List;

import interpreter.BadFormatException;
import interpreter.Controller;
import interpreter.MissingInformationException;
import interpreter.TurtleNotFoundException;
import interpreter.UnidentifiedCommandException;
import javafx.scene.control.Tooltip;


/**
 * A class which extends Specific Panel and allows the user to dynamically change the settings. 
 * @author Andrew Arnold
 *
 */
public class SettingsPanel extends SpecificPanel  {

	private final int VISIBLE_ROW_COUNT = 5;
	private final String COLOR_FOLDER = "colors";
	private final String TURTLE_IMAGE_FOLDER = "turtleimages";

	private  Parent PANEL;
	private final Controller PROGRAM_CONTROLLER;
	private final BorderPane PANE;
	private  Button BACK;
	private ComboBox<Object> LANGUAGE_CHOOSER;
	private ComboBox<Object> BACKGROUND_COLOR_CHOOSER;
	private ComboBox<Object> PEN_COLOR_CHOOSER;
	private ComboBox<Object> TURTLE_IMAGE_CHOOSER;
	private List<String> colorsUntranslated;
	private List<String> colorsTranslated;

	private UserScreen USER_SCREEN;

	private final int DEFAULT_BUTTON_SPACEING = 10;
	private final String[] DROPDOWN_IDS = {"languageSettingsChooser", "backgroundColorChooser", "penColorChooser", "turtleImageChooser"};

	public SettingsPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
		PROGRAM_CONTROLLER = programController;
		PANE = pane;
		USER_SCREEN = userScreen;
	}

	@Override
	public void makePanel() {
		BACK = makeBackButton(PROGRAM_CONTROLLER);
		LANGUAGE_CHOOSER = makeLanguageChooser(DROPDOWN_IDS[0]);
		BACKGROUND_COLOR_CHOOSER = makeBackgroundColorChooser(DROPDOWN_IDS[1]);
		PEN_COLOR_CHOOSER = makePenColorChooser(DROPDOWN_IDS[2]);
		TURTLE_IMAGE_CHOOSER= makeTurtleImageChooser(DROPDOWN_IDS[3]);
		VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACEING, LANGUAGE_CHOOSER,BACKGROUND_COLOR_CHOOSER,PEN_COLOR_CHOOSER,TURTLE_IMAGE_CHOOSER,BACK);
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
		//dropDownMenu.setTooltip(SELECTION_TIP);
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
	 * language for the simulation
	 */
	private ComboBox<Object> makeBackgroundColorChooser(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		colorsUntranslated = PROGRAM_CONTROLLER.getFileNames(COLOR_FOLDER);
		colorsTranslated = PROGRAM_CONTROLLER.translateColors(colorsUntranslated);
		simulationChoices.addAll(colorsTranslated);
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener((arg0,arg1, arg2)->{
			String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
			if (!selected.equals(selectionPrompt)) {
				USER_SCREEN.changeBackgroundColor(colorsUntranslated.get(colorsTranslated.indexOf(selected)));
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
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		colorsUntranslated = PROGRAM_CONTROLLER.getFileNames(COLOR_FOLDER);
		colorsTranslated = PROGRAM_CONTROLLER.translateColors(colorsUntranslated);
		simulationChoices.addAll(colorsTranslated);
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(( arg0, arg1, arg2) ->{
			String selected = (String) dropDownMenu.getItems().get((Integer) arg2);
			if (!selected.equals(selectionPrompt)) {
				try {
				    PROGRAM_CONTROLLER.changePenColor(colorsUntranslated.get(colorsTranslated.indexOf(selected)));
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
	private ComboBox<Object> makeTurtleImageChooser(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		//dropDownMenu.setTooltip(SELECTION_TIP);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		simulationChoices.addAll(PROGRAM_CONTROLLER.getFileNames(TURTLE_IMAGE_FOLDER));
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener((arg0,arg1, arg2)-> {
			String selected = (String) simulationChoices.get((Integer) arg2);
			if (!selected.equals(selectionPrompt)) {
				USER_SCREEN.changeTurtleImage(selected); //something like this
			}
		});
		return dropDownMenu;
	}

	/**
	 * Updates the text displayed to the user to match the current language
	 */
	private void updatePrompt() {
		BACK.setText(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
		LANGUAGE_CHOOSER = makeLanguageChooser(DROPDOWN_IDS[0]);
		BACKGROUND_COLOR_CHOOSER = makeBackgroundColorChooser(DROPDOWN_IDS[1]);
		PEN_COLOR_CHOOSER = makePenColorChooser(DROPDOWN_IDS[2]);
		TURTLE_IMAGE_CHOOSER= makeTurtleImageChooser(DROPDOWN_IDS[3]);
		((VBox)PANEL).getChildren().setAll(LANGUAGE_CHOOSER,BACKGROUND_COLOR_CHOOSER,PEN_COLOR_CHOOSER,TURTLE_IMAGE_CHOOSER,BACK);
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