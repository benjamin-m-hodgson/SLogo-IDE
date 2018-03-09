package screen.panel;

import java.util.Iterator;

import interpreter.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import screen.UserScreen;

public class HistoryPanel extends SpecificPanel {
    private final double FRAMES_PER_SECOND = 2;
    private final long MILLISECOND_DELAY = Math.round(1000 / FRAMES_PER_SECOND);
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private BorderPane PANE;
    private VBox HISTORY_BOX; 
    private UserScreen USER_SCREEN;
    
    public HistoryPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
	PROGRAM_CONTROLLER = programController;
	PANE = pane;
	USER_SCREEN = userScreen;
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> setHistory(SECOND_DELAY));
	Timeline animation = new Timeline();
	animation.setCycleCount(Animation.INDEFINITE);
	animation.getKeyFrames().add(frame);
	animation.play();
    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton(PROGRAM_CONTROLLER);
	ScrollPane historyPane = new ScrollPane();
	historyPane.setId("settingsField");
	HISTORY_BOX = new VBox();
	historyPane.setContent(HISTORY_BOX);
	VBox panelRoot = new VBox(historyPane, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(historyPane, Priority.ALWAYS);
	PANEL = panelRoot;
	setHistory(SECOND_DELAY);
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
	HISTORY_BOX.getChildren().clear();
	Iterator<String> commandHistory = USER_SCREEN.commandHistory();
	Iterator<String> outputHistory = USER_SCREEN.outputHistory();
	int currentRun = 1; 
	while (commandHistory.hasNext() && outputHistory.hasNext()) {
	    String command = commandHistory.next();
	    String output = outputHistory.next();
	    String commandNumber = Integer.toString(currentRun);
	    Label numberLabel = new Label(commandNumber);
	    numberLabel.setId("numberLabel");
	    numberLabel.setDisable(true);
	    Label commandLabel = new Label(command);
	    // override click event
	    commandLabel.setOnMouseClicked((arg0)-> getPane()
		    .setRight(verboseCommand(command,
			    PROGRAM_CONTROLLER.resourceDisplayText("RunPrompt") 
			    + " " + commandNumber, output)));
	    commandLabel.setId("historyLabel");
	    HBox numberedCommand = new HBox(numberLabel, commandLabel);
	    HISTORY_BOX.getChildren().add(numberedCommand);
	    currentRun++;
	}
    }
    private VBox verboseCommand(String command, String commandNumberHeading, String output) {
	Button commandButton = new Button(commandNumberHeading);
	commandButton.setId("commandButton");
	commandButton.setOnMouseClicked((arg0)-> USER_SCREEN.commandRunFromHistory(command));
	Button backButton = new Button(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
	backButton.setId("backButton");
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(PANEL));
	ScrollPane commandInfoPane = new ScrollPane();
	commandInfoPane.setId("historyField");
	TextArea commandInfoArea = new TextArea();
	commandInfoPane.setContent(commandInfoArea);
	commandInfoArea.setId("historyField");
	commandInfoArea.setText(command);
	commandInfoArea.setEditable(false);
	TextArea consoleInfoArea = new TextArea();
	consoleInfoArea.setId("historyField");
	consoleInfoArea.setText(output);
	consoleInfoArea.setEditable(false);
	VBox panelRoot = new VBox(commandButton, commandInfoArea, consoleInfoArea, backButton);
	panelRoot.setId("infoPanel");
	VBox.setVgrow(commandInfoArea, Priority.ALWAYS);
	return panelRoot;
    }
}
