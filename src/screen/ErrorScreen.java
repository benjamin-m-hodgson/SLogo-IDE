package screen;

import interpreter.Controller;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ErrorScreen implements Screen {
    
    private double DEFAULT_WIDTH = 600;
    private double DEFAULT_HEIGHT = 600;
    private Parent ROOT;
    private Controller PROGRAM_CONTROLLER;
    private String ERROR_MESSAGE;
    
    public ErrorScreen(Controller programController, String errorMessage) {
	PROGRAM_CONTROLLER = programController;
	ERROR_MESSAGE = errorMessage;
    }

    @Override
    public void makeRoot() {
	VBox root = new VBox();
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
	// TODO Auto-generated method stub
	
    }

    @Override
    public void changeRightPanel(Parent panelRoot) {
	// TODO Auto-generated method stub
	
    }

}
