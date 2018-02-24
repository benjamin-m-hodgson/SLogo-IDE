package screen.panel;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import mediator.Controller;

public class InfoPanel implements Panel {

    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private final double DEFAULT_WIDTH = 600;
    private final double DEFAULT_HEIGHT = 600;
    private final String[] buttonLabels = {"Settings", "Variables", "History"};
    
    public InfoPanel(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makePanel() {
    	Button testButton = makeButton("Settings", "test");
    	Button testButton2 = makeButton("Information", "test");

	VBox panelRoot = new VBox(10, testButton, testButton2);
	panelRoot.setId("infoPanel");
	panelRoot.setMaxHeight(DEFAULT_HEIGHT);
	panelRoot.setMinHeight(DEFAULT_HEIGHT);
	panelRoot.setMaxWidth(DEFAULT_WIDTH);
	panelRoot.setMinWidth(DEFAULT_WIDTH);
	panelRoot.setAlignment(Pos.CENTER);
	PANEL = panelRoot;
    }
    

    
    /**
     * 
     * @return Button: Button to start the program
     */
    private Button makeButton(String buttonLabel, String boxId) {
	Button startButton = new Button(buttonLabel);
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
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }
    
}
