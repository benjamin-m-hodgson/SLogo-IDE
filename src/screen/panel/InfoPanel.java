package screen.panel;

import java.util.ArrayList;
import java.util.List;

import interpreter.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class InfoPanel implements Panel {

    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private BorderPane PANE;

	private final int DEFAULT_BUTTON_SPACEING = 10;

    private final String[] BUTTON_IDS = {"settingsButton", "variablesButton", "historyButton", "commandsButton", "helpButton"};
    
    public InfoPanel(Controller programController, BorderPane pane) {
	PROGRAM_CONTROLLER = programController;
	PANE = pane;
    }

    @Override
    public void makePanel() {
    	List<Button> buttons = makeButtons();
	VBox panelRoot = new VBox(DEFAULT_BUTTON_SPACEING);
	panelRoot.getChildren().addAll(buttons);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.CENTER);
	PANEL = panelRoot;
    }
    
    private List<Button> makeButtons()
    {
    		List<Button> buttons = new ArrayList<Button>();
    		for(int i = 0; i < BUTTON_IDS.length; i++) {
    			buttons.add(makeButton(i));
    		}
    		setLinks(buttons);
    		return buttons;
    }
    
    //CHANGE THIS METHOD TO A BETTER ALTERNATIVE
    private void setLinks(List<Button> buttons) {
    		buttons.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent arg0) {
    		    	PANE.setRight(new SettingsPanel(PROGRAM_CONTROLLER, PANE).getPanel());
    		    }
    		});
    		buttons.get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent arg0) {
    		    	PANE.setRight(new VariablesPanel(PROGRAM_CONTROLLER, PANE).getPanel());
    		    }
    		});
    		buttons.get(2).setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent arg0) {
    		    	PANE.setRight(new HistoryPanel(PROGRAM_CONTROLLER, PANE).getPanel());
    		    }
    		});
    		buttons.get(3).setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent arg0) {
    		    	PANE.setRight(new CommandPanel(PROGRAM_CONTROLLER, PANE).getPanel());
    		    }
    		});
    		buttons.get(4).setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent arg0) {
    		    	PANE.setRight(new HelpPanel(PROGRAM_CONTROLLER, PANE).getPanel());
    		    }
    		});
    }
    
    
    private Button makeButton(int buttonNum)
    {
    		Button button = new Button(PROGRAM_CONTROLLER.resourceDisplayText(BUTTON_IDS[buttonNum]));
    		button.setId(BUTTON_IDS[buttonNum]);
    		return button;
    }
    

    @Override
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }
    
}
