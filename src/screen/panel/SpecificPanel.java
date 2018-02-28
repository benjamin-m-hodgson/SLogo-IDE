package screen.panel;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import screen.UserScreen;
import interpreter.Controller;

public abstract class SpecificPanel implements Panel {
	
	protected abstract BorderPane getPane();
	protected abstract Controller getController();
	protected abstract UserScreen getUserScreen();

	
	protected Button makeBackButton(Controller PROGRAM_CONTROLLER) {
		Button backButton = new Button(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
		backButton.setId("backButton");
		// handle click event
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				getPane().setRight(new InfoPanel(getController(), getPane(), getUserScreen()).getPanel());
			}
		});
		return backButton;
	}

}
