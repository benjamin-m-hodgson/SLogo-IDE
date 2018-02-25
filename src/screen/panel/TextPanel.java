package screen.panel;

import interpreter.Controller;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TextPanel implements Panel {
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private TextArea INPUT_AREA;
    private TextArea CONSOLE_AREA;
    
    public TextPanel(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }
    
    @Override
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }

    @Override
    public void makePanel() {
	INPUT_AREA = makeInputArea();
	CONSOLE_AREA = makeConsoleArea();
	HBox centerInputArea = new HBox(INPUT_AREA, CONSOLE_AREA);
	VBox textPanel = new VBox(centerInputArea);
	textPanel.setId("centerTextPanel");
	PANEL = textPanel;
    }
    
    public void clearInputArea() {
	INPUT_AREA.clear();
	CONSOLE_AREA.clear();
    }
    
    private TextArea makeInputArea() {
	TextArea inputArea = new TextArea();
	inputArea.setId("inputField");
	inputArea.setPromptText(PROGRAM_CONTROLLER.resourceDisplayText("InputPrompt"));
	return inputArea;
    }
    
    private TextArea makeConsoleArea() {
	TextArea inputArea = new TextArea();
	inputArea.setId("consoleField");
	return inputArea;
    }

}
