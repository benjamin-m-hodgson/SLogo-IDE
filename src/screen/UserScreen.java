package screen;

import interpreter.Controller;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import screen.panel.InfoPanel;
import screen.panel.InputPanel;
import screen.panel.TurtlePanel;

public class UserScreen implements Screen {

	private Parent ROOT;
	private TurtlePanel TURTLE_PANEL;
	private Controller PROGRAM_CONTROLLER;

	public UserScreen(Controller programController) {
		PROGRAM_CONTROLLER = programController;
	}

	@Override
	public void makeRoot() {
		BorderPane rootPane = new BorderPane();
		rootPane.setId("userScreenRoot");
		rootPane.setBottom(new InputPanel(PROGRAM_CONTROLLER, this).getPanel());
		rootPane.setRight(new InfoPanel(PROGRAM_CONTROLLER, rootPane, this).getPanel());
		TURTLE_PANEL = new TurtlePanel(PROGRAM_CONTROLLER);
		rootPane.setCenter(TURTLE_PANEL.getPanel());
		ROOT = rootPane;
	}

	@Override
	public Parent getRoot() {
		if (ROOT == null) {
			makeRoot();
		}
		return ROOT;
	}

	@Override
	public void changeBackgroundColor(String color) {
		String colorCode = PROGRAM_CONTROLLER.changeBackgroundColor(color);
		TURTLE_PANEL.changeBackgroundColor(colorCode);
	}
	
	public void displayErrorMessage(String errorMessage) {
		TURTLE_PANEL.displayErrorMessage(errorMessage);
	}

	@Override
	public void changeRightPanel(Parent panelRoot) {
		// TODO Auto-generated method stub
	}
}
