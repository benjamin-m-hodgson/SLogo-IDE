package screen.panel;

import javafx.scene.Parent;

public interface Panel {
    
    /**
     * If property PANEL is null, calls makePanel() to generate the root. 
     * 
     * @return PANEL: The Parent node to be used in the Scene object. 
     */
    public Parent getPanel();
    
    /**
     * Creates the root panel to be displayed on the Screen
     */
    public abstract void makePanel();

}
