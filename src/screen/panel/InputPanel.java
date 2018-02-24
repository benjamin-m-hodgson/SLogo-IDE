package screen.panel;

import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    
    private VBox drawRunBox() {
	VBox runBox = new VBox();
	runBox.setId("runBox");
	Button runButton = drawRunButton();
	runBox.getChildren().add(runButton);
	return runBox;
    }
    
    private Button drawRunButton() {
	Button runButton = new Button();
	return runButton;
    }

}
