package screen.panel;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mediator.Controller;

public class HistoryPanel extends SpecificPanel {

	private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private BorderPane PANE;

	private final int DEFAULT_BUTTON_SPACEING = 10;


	public HistoryPanel(Controller programController, BorderPane pane) {
		PROGRAM_CONTROLLER = programController;
		PANE = pane;
	}

	@Override
	public void makePanel() {
		Button backButton = makeBackButton();
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

}
