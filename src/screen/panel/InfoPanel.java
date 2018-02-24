package screen.panel;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import mediator.Controller;

public class InfoPanel implements Panel {

    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    
    public InfoPanel(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makePanel() {
	VBox panelRoot = new VBox();
	panelRoot.setId("infoPanel");
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
