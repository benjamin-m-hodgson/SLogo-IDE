package screen;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Ben Hodgson
 *
 * A class that implements the Screen interface to generate an ErrorScreen to display to the 
 * user. The screen contains text describing the error.
 */
public class ErrorScreen implements Screen {
    private Parent ROOT;
    private String ERROR_MESSAGE;
    
    public ErrorScreen(String errorMessage) {
	ERROR_MESSAGE = errorMessage;
    }

    @Override
    public void makeRoot() {
	Label errorLabel = errorLabel(ERROR_MESSAGE);
	VBox rootBox = new VBox(errorLabel);
	rootBox.setId("errorScreenRoot");
	ROOT = rootBox;
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
    
    /**
     * Creates a Label to display to the user on the Error Screen that contains the Error
     * Message indicating the cause of the error.
     * 
     * @param errorMessage: The String defining the Error Message to display to the user
     * @return Label: A JavaFX Label object displaying the Error Message
     */
    private Label errorLabel(String errorMessage) {
	Label errorLabel = new Label(errorMessage);
	errorLabel.setId("errorLabel");
	return errorLabel;
    }

}
