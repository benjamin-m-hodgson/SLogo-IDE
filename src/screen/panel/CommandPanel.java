package screen.panel;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import screen.UserScreen;
import interpreter.Controller;

public class CommandPanel extends SpecificPanel {
    
    private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private BorderPane PANE;
	private final int DEFAULT_BUTTON_SPACEING = 10;
    private UserScreen USER_SCREEN;

    
    public CommandPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
    	PROGRAM_CONTROLLER = programController;
		PANE = pane;
		USER_SCREEN = userScreen;
    }

    @Override
    public void makePanel() {
		Button backButton = makeBackButton(PROGRAM_CONTROLLER);
		ScrollPane scroll = new ScrollPane();
		VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACEING, scroll,backButton );
		panelRoot.setId("infoPanel");
		panelRoot.setAlignment(Pos.BASELINE_CENTER);
		PANEL = panelRoot;	
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
