package screen;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import mediator.Controller;

public class UserScreen extends Screen {

    public UserScreen(Controller programController) {
	super(programController);
    }

    @Override
    protected void makeRoot() {
	BorderPane rootPane = new BorderPane();
	rootPane.setRight(new Label("infoPanel"));
	rootPane.setBottom(new Label("inputPanel"));
	rootPane.setCenter(new Label("turtlePanel"));
	ROOT = rootPane;
    }
    

}
