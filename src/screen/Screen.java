package screen;

import javafx.scene.Parent;
import mediator.Controller;

public abstract class Screen {

    protected Parent ROOT;
    protected Controller PROGRAM_CONTROLLER;

    public Screen(Controller programController) {
	PROGRAM_CONTROLLER = programController;
    }

    /**
     * If property ROOT is null, calls makeRoot() to generate the root. 
     * 
     * @return ROOT: The Parent node to be used in the Scene object. 
     */
    public Parent getRoot() {
	if (ROOT == null) {
	    makeRoot();
	}
	return ROOT;
    }
    
    /**
     * Creates the root node to be displayed on the Screen
     */
    protected abstract void makeRoot();
}
