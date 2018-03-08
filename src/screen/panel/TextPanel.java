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
import screen.UserScreen;

public class TextPanel extends Panel {
    private Parent PANEL;
    private final Controller PROGRAM_CONTROLLER;
    private final UserScreen USER_SCREEN;
    private TextArea INPUT_AREA;
    private TextArea CONSOLE_AREA;

    public TextPanel(Controller programController, UserScreen userScreen) {
	PROGRAM_CONTROLLER = programController;
	USER_SCREEN = userScreen;
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
	try {
	    // if there is no input, do nothing
	    if (INPUT_AREA.getText().isEmpty()) {
		return;
	    }
	    String inputText = INPUT_AREA.getText().replaceAll("\n", 
		    System.getProperty("line.separator"));
	    System.out.println(inputText);
	    Double consoleVal = PROGRAM_CONTROLLER.parseInput(inputText);
	    String outputText = consoleVal.toString();
	    CONSOLE_AREA.setText(outputText);
	    USER_SCREEN.addCommand(inputText, outputText);
	} 
	catch (TurtleNotFoundException e) {
	    e.printStackTrace();
	    clearInputArea();
	    USER_SCREEN.displayErrorMessage(e.getMessage());
	} 
	catch (BadFormatException e) {
	    e.printStackTrace();
	    clearInputArea();
	    USER_SCREEN.displayErrorMessage(e.getMessage());

	} 
	catch (UnidentifiedCommandException e) {
	    e.printStackTrace();
	    clearInputArea();
	    USER_SCREEN.displayErrorMessage(e.getMessage());

	} 
	catch (MissingInformationException e) {
	    e.printStackTrace();
	    clearInputArea();
	    USER_SCREEN.displayErrorMessage(e.getMessage());

	}
	catch (Exception e) {
	    e.printStackTrace();
	    clearInputArea();
	    USER_SCREEN.displayErrorMessage(PROGRAM_CONTROLLER.resourceErrorText("GeneralError"));
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
