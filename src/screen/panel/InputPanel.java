package screen.panel;

import javafx.scene.Parent;
import mediator.Controller;

public class InputPanel implements Panel {
    
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    
    public InputPanel(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makePanel() {
	
    }

    @Override
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }

}
