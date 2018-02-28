package screen.panel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import screen.UserScreen;

import java.util.Iterator;
import java.util.List;

import interpreter.Controller;

public class HistoryPanel extends SpecificPanel {
    private final double FRAMES_PER_SECOND = 120;
    private final long MILLISECOND_DELAY = Math.round(1000 / FRAMES_PER_SECOND);
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private BorderPane PANE;
    private TextArea HISTORY; 
    private UserScreen USER_SCREEN;
    private List<String> INPUT_HISTORY;
    private int INPUTS_RUN;


    public HistoryPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
	PROGRAM_CONTROLLER = programController;
	PANE = pane;
	USER_SCREEN = userScreen;
	INPUTS_RUN = 0;
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> setHistory(SECOND_DELAY));
	Timeline animation = new Timeline();
	animation.setCycleCount(Timeline.INDEFINITE);
	animation.getKeyFrames().add(frame);
	animation.play();
    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton(PROGRAM_CONTROLLER);
	HISTORY = new TextArea();
	HISTORY.setId("settingsField");
	HISTORY.setEditable(false);
	HISTORY.setPromptText(PROGRAM_CONTROLLER.resourceDisplayText("historyContent"));
	VBox panelRoot = new VBox(HISTORY,backButton );
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(HISTORY, Priority.ALWAYS);
	PANEL = panelRoot;
    }



    @Override
    public Parent getPanel() {
	if (PANEL == null) {
	    makePanel();
	}
	return PANEL;
    }

    @Override
    protected BorderPane getPane() {
	// TODO Auto-generated method stub
	return PANE;
    }

    @Override
    protected Controller getController() {
	// TODO Auto-generated method stub
	return PROGRAM_CONTROLLER;
    }

    @Override
    protected UserScreen getUserScreen() {
	return USER_SCREEN;
    }
    
    private void setHistory(double elapsedTime) {
	StringBuilder buildHistory = new StringBuilder();
	Iterator<String> commandHistory = USER_SCREEN.commandHistory();
	int currentRun = 1; 
	while (commandHistory.hasNext()) {
	    buildHistory.append("------" + System.lineSeparator()
	    		+ PROGRAM_CONTROLLER.resourceDisplayText("RunPrompt") 
		    	+ " " + Integer.toString(currentRun) + System.lineSeparator()
	    		+ "======" + System.lineSeparator());
	    String command = commandHistory.next() + System.lineSeparator();
	    buildHistory.append(command);
	    currentRun++;
	}
	HISTORY.setText(buildHistory.toString());
    }

}
