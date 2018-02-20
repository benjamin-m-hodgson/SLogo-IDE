package screen;

import javafx.scene.layout.BorderPane;
import mediator.Controller;
import screen.panel.InfoPanel;
import screen.panel.InputPanel;
import screen.panel.TurtlePanel;

public class UserScreen extends Screen {

    public UserScreen(Controller programController) {
	super(programController);
    }

    @Override
    protected void makeRoot() {
	BorderPane rootPane = new BorderPane();
	rootPane.setRight(new InfoPanel().getPanel());
	rootPane.setBottom(new InputPanel().getPanel());
	rootPane.setCenter(new TurtlePanel().getPanel());
	ROOT = rootPane;
    }
    

}
