package screen;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import mediator.Controller;

public class StartScreen implements Screen {
    
    private Parent ROOT;
    private Controller PROGRAM_CONTROLLER;
    private Button START;

    public StartScreen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makeRoot() {
	START = makeStartButton();
	VBox rootBox = new VBox(10, START);
	rootBox.setId("startScreenRoot");
	// TODO: format with CSS
	rootBox.setAlignment(Pos.CENTER);
	ROOT = rootBox;
    }
    
    @Override
    public Parent getRoot() {
	if (ROOT == null) {
	    makeRoot();
	}
	return ROOT;
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

    @Override
    public void changeBackgroundColor(String color) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void changeRightPanel(Parent panelRoot) {
	// TODO Auto-generated method stub
	
    }
}
