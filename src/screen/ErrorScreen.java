package screen;

import javafx.scene.layout.VBox;
import mediator.Controller;

public class ErrorScreen extends Screen {
    
    private double DEFAULT_WIDTH = 600;
    private double DEFAULT_HEIGHT = 600;
    private String ERROR_MESSAGE;
    
    public ErrorScreen(Controller programController, String errorMessage) {
	super(programController);
	ERROR_MESSAGE = errorMessage;
    }

    @Override
    protected void makeRoot() {
	VBox root = new VBox();
	
    }

}
