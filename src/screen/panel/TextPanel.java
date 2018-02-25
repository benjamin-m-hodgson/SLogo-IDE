package screen.panel;

import interpreter.BadFormatException;
import interpreter.Controller;
import interpreter.MissingInformationException;
import interpreter.TurtleNotFoundException;
import interpreter.UnidentifiedCommandException;
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
    
    public void run() {
	String inputText = INPUT_AREA.getText().replaceAll("\n", 
		System.getProperty("line.separator"));
	CONSOLE_AREA.setText(inputText);
	try {
	    PROGRAM_CONTROLLER.parseInput(inputText);
	} 
	catch (TurtleNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	catch (BadFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	catch (UnidentifiedCommandException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	catch (MissingInformationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (NullPointerException e) {
	    // TODO handle null exception. What to return to parse? ""?
	    System.out.println("NULL");
	}
	
    }
    
    private TextArea makeInputArea() {
	TextArea inputArea = new TextArea();
	inputArea.setId("inputField");
	inputArea.setPromptText(PROGRAM_CONTROLLER.resourceDisplayText("InputPrompt"));
	return inputArea;
    }
    
    private TextArea makeConsoleArea() {
	TextArea consoleArea = new TextArea();
	consoleArea.setId("consoleField");
	consoleArea.setPromptText(PROGRAM_CONTROLLER.resourceDisplayText("ConsolePrompt"));
	consoleArea.setEditable(false);
	return consoleArea;
    }

}
