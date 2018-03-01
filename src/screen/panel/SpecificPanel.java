package screen.panel;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import screen.UserScreen;
import interpreter.Controller;

/**
 * The abstract class, which implements panel, for the specific sub-panels in the right sidebar. 
 * @author Andrew Arnold
 *
 */
public abstract class SpecificPanel implements Panel {
	
	protected abstract BorderPane getPane();
	protected abstract Controller getController();
	protected abstract UserScreen getUserScreen();

	
	/**
	 * A generic make back button class which is used by every panel which implements this class
	 * @param PROGRAM_CONTROLLER
	 * @return a back button which links to info panel
	 */
	protected Button makeBackButton(Controller PROGRAM_CONTROLLER) {
		Button backButton = new Button(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
		backButton.setId("backButton");
		// handle click event
		backButton.setOnMouseClicked((arg0)-> getPane().setRight(new InfoPanel(getController(), getPane(), getUserScreen()).getPanel()));
		return backButton;
	}

}
