package screen.panel;

import javafx.scene.Parent;

public class CommandPanel implements Panel {
    
    private Parent PANEL;
    
    public CommandPanel() {
	
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
