package screen.panel;

import javafx.scene.Parent;

public abstract class Panel {
    
    protected Parent PANEL;
    
    /**
     * If property PANEL is null, calls makePanel() to generate the root. 
     * 
     * @return PANEL: The Parent node to be used in the Scene object. 
     */
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }
    
    /**
     * Creates the root panel to be displayed on the Screen
     */
    protected abstract void makePanel();

}
