package screen.panel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import screen.UserScreen;

import java.util.Map;
import java.util.Map.Entry;

import interpreter.Controller;

/**
 * 
 * @author Ben Hodgson
 *
 * A class that extends the SpecificPanel super class to generate a panel containing information
 * about the currently available variables in the program.
 */
public class VariablesPanel extends SpecificPanel {
    private final double FRAMES_PER_SECOND = 2;
    private final long MILLISECOND_DELAY = Math.round(1000 / FRAMES_PER_SECOND);
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private BorderPane PANE;
    private VBox VARIABLE_BOX;
    private UserScreen USER_SCREEN;


    public VariablesPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
	PROGRAM_CONTROLLER = programController;
	PANE = pane;
	USER_SCREEN = userScreen;
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> populateVariableBox(SECOND_DELAY));
	Timeline animation = new Timeline();
	animation.setCycleCount(Timeline.INDEFINITE);
	animation.getKeyFrames().add(frame);
	animation.play();
    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton(PROGRAM_CONTROLLER);
	ScrollPane scroll = new ScrollPane();
	scroll.setId("settingsField");
	VARIABLE_BOX = new VBox();
	scroll.setContent(VARIABLE_BOX);
	VBox panelRoot = new VBox(scroll, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(scroll, Priority.ALWAYS);
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

    /**
     * Populates the child Nodes in VARIABLE_BOX to represent the variables available in the 
     * program and their associated values.
     * 
     * @param elapsedTime: time since last animation update
     */
    private void populateVariableBox(double elapsedTime) {
	VARIABLE_BOX.getChildren().clear();
	Map<String, Double> programVariables = PROGRAM_CONTROLLER.getVariables();
	for (Entry<String, Double> variable : programVariables.entrySet()) {
	    String variableName = variable.getKey();
	    String variableValue = variable.getValue().toString();
	    Label nameLabel = new Label(variableName);
	    nameLabel.setId("variableLabel");
	    Label valueLabel = new Label(variableValue);
	    valueLabel.setId("variableLabel");
	    HBox infoRow = new HBox(nameLabel, valueLabel);
	    infoRow.setAlignment(Pos.CENTER);
	    VARIABLE_BOX.getChildren().add(infoRow);
	}
    }

}
