package screen.panel;

import interpreter.Controller;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;


public class TurtlePanel implements Panel {

	private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private ScrollPane SCROLL_PANE;

	public TurtlePanel(Controller programController) {
		PROGRAM_CONTROLLER = programController;
	}

	@Override
	public void makePanel() {
		ScrollPane panelRoot = new ScrollPane();
		SCROLL_PANE = panelRoot;
		panelRoot.setId("turtlePanel");
		PANEL = panelRoot;
	}

	@Override
	public Parent getPanel() {
		if (PANEL == null) {
			makePanel();
		}
		return PANEL;
	}

	public void changeBackgroundColor(String colorCode) {
		SCROLL_PANE.setStyle("-fx-background-color:" + colorCode + ";");
	}

}
