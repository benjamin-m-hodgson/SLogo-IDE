package screen.panel;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import mediator.Controller;

public class InputPanel implements Panel {
    
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    
    public InputPanel(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makePanel() {
	HBox panelRoot = new HBox();
	panelRoot.setId("inputPanel");
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
