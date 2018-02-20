package screen.panel;

import javafx.scene.Parent;

public class SettingsPanel implements Panel {
    
    private Parent PANEL;
    
    public SettingsPanel() {
	
    }

    @Override
    public void makePanel() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }

}
