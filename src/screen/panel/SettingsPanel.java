package screen.panel;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import screen.UserScreen;
import interpreter.Controller;
import javafx.scene.control.ScrollPane;


public class SettingsPanel extends SpecificPanel  {

	private final int VISIBLE_ROW_COUNT = 5;
	private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private BorderPane PANE;
	private Button BACK;
	private UserScreen USER_SCREEN;



	private final int DEFAULT_BUTTON_SPACEING = 10;
	private final String[] DROPDOWN_IDS = {"languageChooser", "backgroundColorChooser", "penColorChooser", "turtleImageChooser"};

	public SettingsPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
		PROGRAM_CONTROLLER = programController;
		PANE = pane;
		USER_SCREEN = userScreen;

	}

	@Override
	public void makePanel() {
		BACK = makeBackButton(PROGRAM_CONTROLLER);
		ComboBox<Object> languageChooser = makeLanguageChooser(DROPDOWN_IDS[0]);
		ComboBox<Object> backgroundColorChooser = makeBackgroundColorChooser(DROPDOWN_IDS[1]);
		ComboBox<Object> penColorChooser = makePenColorChooser(DROPDOWN_IDS[2]);
		ComboBox<Object> turtleImageChooser= makeTurtleImageChooser(DROPDOWN_IDS[3]);

		VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACEING, languageChooser,backgroundColorChooser,penColorChooser,turtleImageChooser,BACK);
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
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, 
					Number arg1, Number arg2) {
				String selected = (String) simulationChoices.get((Integer) arg2);
				if (!selected.equals(selectionPrompt)) {
					PROGRAM_CONTROLLER.changeLanguage(selected);
					updatePrompt();
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
	private ComboBox<Object> makeBackgroundColorChooser(String itemID) {
		String selectionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemID);
		ComboBox<Object> dropDownMenu = makeComboBox(selectionPrompt);
		//dropDownMenu.setTooltip(SELECTION_TIP);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		simulationChoices.addAll(PROGRAM_CONTROLLER.getColors());
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, 
					Number arg1, Number arg2) {
				String selected = (String) simulationChoices.get((Integer) arg2);
				if (!selected.equals(selectionPrompt)) {
					USER_SCREEN.changeBackgroundColor(selected);
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
		//dropDownMenu.setTooltip(SELECTION_TIP);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selectionPrompt);
		simulationChoices.addAll(PROGRAM_CONTROLLER.getColors());
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, 
					Number arg1, Number arg2) {
				String selected = (String) simulationChoices.get((Integer) arg2);
				if (!selected.equals(selectionPrompt)) {
					//controller.changePenColor() //something like this
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
		//simulationChoices.addAll(PROGRAM_CONTROLLER.getColors());
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemID);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, 
					Number arg1, Number arg2) {
				String selected = (String) simulationChoices.get((Integer) arg2);
				if (!selected.equals(selectionPrompt)) {
					//controller.changePenColor() //something like this
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
	private ComboBox<Object> makeDropDown(String itemId) {
		String selctionPrompt = PROGRAM_CONTROLLER.resourceDisplayText(itemId);
		ComboBox<Object> dropDownMenu = makeComboBox(selctionPrompt);
		//dropDownMenu.setTooltip(SELECTION_TIP);
		ObservableList<Object> simulationChoices = 
				FXCollections.observableArrayList(selctionPrompt);
		simulationChoices.addAll(PROGRAM_CONTROLLER.getLanguages());
		dropDownMenu.setItems(simulationChoices);
		dropDownMenu.setId(itemId);
		dropDownMenu.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, 
					Number arg1, Number arg2) {
				String selected = (String) simulationChoices.get((Integer) arg2);
				PROGRAM_CONTROLLER.changeLanguage(selected);
				updatePrompt();
			}
		});
		return dropDownMenu;
	}

	/**
	 * Updates the text displayed to the user to match the current language
	 */
	private void updatePrompt() {
		BACK.setText(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
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
