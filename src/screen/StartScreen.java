package screen;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import mediator.Controller;

public class StartScreen extends Screen {
    
    private final double DEFAULT_WIDTH = 600;
    private final double DEFAULT_HEIGHT = 600;
    
    private Button START;

    public StartScreen(Controller programController) {
	super(programController);
    }

    @Override
    protected void makeRoot() {
	START = makeStartButton();
	VBox rootBox = new VBox(10, START);
	rootBox.setMaxHeight(DEFAULT_HEIGHT);
	rootBox.setMinHeight(DEFAULT_HEIGHT);
	rootBox.setMaxWidth(DEFAULT_WIDTH);
	rootBox.setMinWidth(DEFAULT_WIDTH);
	// TODO: format with CSS
	rootBox.setAlignment(Pos.CENTER);
	ROOT = rootBox;
    }
    
    /**
     * 
     * @return Button: Button to start the program
     */
    private Button makeStartButton() {
	Button startButton = new Button("Start");
	// TODO: format with CSS
	startButton.setAlignment(Pos.CENTER);
	// handle click event
	startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		PROGRAM_CONTROLLER.loadUserScreen();  
	    }
	});
	return startButton;
    }

}
