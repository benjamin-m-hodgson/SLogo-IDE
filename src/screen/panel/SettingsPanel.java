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
import interpreter.Controller;
import javafx.scene.control.ScrollPane;


public class SettingsPanel extends SpecificPanel  {

	private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private BorderPane PANE;


	private final int DEFAULT_BUTTON_SPACEING = 10;

	public SettingsPanel(Controller programController, BorderPane pane) {
		PROGRAM_CONTROLLER = programController;
		PANE = pane;
	}

	@Override
	public void makePanel() {
		Button back = makeBackButton();
		VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACEING, back );
		panelRoot.setId("infoPanel");
		panelRoot.setAlignment(Pos.BASELINE_CENTER);
		PANEL = panelRoot;

	}



//	/**
//	 * 
//	 * @return dropDownMenu: a drop down menu that lets the user choose the
//	 * language for the simulation
//	 */
//	private ComboBox<Object> languageChooser() {
//		String defaultPrompt = "Choose Background color: ";
//		ComboBox<Object> dropDownMenu = makeComboBox(defaultPrompt);
//		ObservableList<Object> simulationChoices = 
//				FXCollections.observableArrayList(defaultPrompt);
//		simulationChoices.addAll(PROGRAM_CONTROLLER.getLanguages());
//		dropDownMenu.setItems(simulationChoices);
//		dropDownMenu.setId("languageChooser");
//		dropDownMenu.getSelectionModel().selectedIndexProperty()
//		.addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> arg0, 
//					Number arg1, Number arg2) {
//				String selected = (String) simulationChoices.get((Integer) arg2);
//				if (!selected.equals(defaultPrompt)) {
//					APPLY.setDisable(false);
//					LANGUAGE = selected;
//				} 
//				else {
//					APPLY.setDisable(true);
//					START.setDisable(true);
//				}
//			}
//		});
//		return dropDownMenu;
//	}

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

}
