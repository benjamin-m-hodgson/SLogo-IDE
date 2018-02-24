package screen.panel;

import interpreter.Controller;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;

public class TurtlePanel implements Panel {

    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    
    public TurtlePanel(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }
    
    @Override
    public void makePanel() {
	ScrollPane panelRoot = new ScrollPane();
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
}
