package screen;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import mediator.Controller;
import screen.panel.InfoPanel;
import screen.panel.InputPanel;
import screen.panel.TurtlePanel;

public class UserScreen implements Screen {
    
    private Parent ROOT;
    private Controller PROGRAM_CONTROLLER;

    public UserScreen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    @Override
    public void makeRoot() {
	BorderPane rootPane = new BorderPane();
	rootPane.setRight(new InfoPanel(PROGRAM_CONTROLLER).getPanel());
	rootPane.setBottom(new InputPanel(PROGRAM_CONTROLLER).getPanel());
	rootPane.setCenter(new TurtlePanel(PROGRAM_CONTROLLER).getPanel());
	ROOT = rootPane;
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
    

}
