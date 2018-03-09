package screen.panel;

import java.util.ArrayList;
import java.util.List;

import interpreter.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import screen.UserScreen;

/**
 * A class which implements panel and is the default/navigation panel for the right side bar. This panel allows 
 * movement to the specific panels
 * @author Andrew Arnold
 *
 */
public class InfoPanel extends Panel {

	private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private BorderPane PANE;
	private UserScreen USER_SCREEN;

	private final String[] BUTTON_IDS = {"settingsButton", "variablesButton", "historyButton", "commandsButton", "helpButton"};

	public InfoPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
		PROGRAM_CONTROLLER = programController;
		PANE = pane;
		USER_SCREEN = userScreen;
	}

	@Override
	public void makePanel() {
		List<Button> buttons = makeButtons();
		VBox panelRoot = new VBox();
		panelRoot.getChildren().addAll(buttons);
		panelRoot.setId("infoPanel");
		panelRoot.setAlignment(Pos.CENTER);
		PANEL = panelRoot;
	}

	private List<Button> makeButtons() {
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0; i < BUTTON_IDS.length; i++) {
			buttons.add(makeButton(BUTTON_IDS[i]));
		}
		setLinks(buttons);
		return buttons;
	}

	//CHANGE THIS METHOD TO A BETTER ALTERNATIVE
	private void setLinks(List<Button> buttons) {
		buttons.get(0).setOnMouseClicked((arg0) ->PANE.setRight(new SettingsPanel(PROGRAM_CONTROLLER, PANE, USER_SCREEN).getPanel()));
		buttons.get(1).setOnMouseClicked((arg0) ->PANE.setRight(new VariablesPanel(PROGRAM_CONTROLLER, PANE, USER_SCREEN).getPanel()));
		buttons.get(2).setOnMouseClicked((arg0) ->PANE.setRight(new HistoryPanel(PROGRAM_CONTROLLER, PANE, USER_SCREEN).getPanel()));
		buttons.get(3).setOnMouseClicked((arg0) ->PANE.setRight(new CommandPanel(PROGRAM_CONTROLLER, PANE, USER_SCREEN).getPanel()));
		buttons.get(4).setOnMouseClicked((arg0) ->PANE.setRight(new HelpPanel(PROGRAM_CONTROLLER, PANE, USER_SCREEN).getPanel()));
	}

	private Button makeButton(String buttonId) {
	    	System.out.println(buttonId);
		Button button = new Button(PROGRAM_CONTROLLER.resourceDisplayText(buttonId));
		button.setId(buttonId);
		return button;
	}


}
