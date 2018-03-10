package screen.panel;

import java.util.Map;
import java.util.Map.Entry;

import interpreter.FileIO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
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
    private BorderPane PANE;
    private final FileIO FILE_READER;
    private VBox VARIABLE_BOX;
    private UserScreen USER_SCREEN;


    public VariablesPanel( BorderPane pane, UserScreen userScreen, FileIO fileReader) {
	PANE = pane;
	USER_SCREEN = userScreen;
	FILE_READER = fileReader;
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> populateVariableBox(SECOND_DELAY));
	Timeline animation = new Timeline();
	animation.setCycleCount(Animation.INDEFINITE);
	animation.getKeyFrames().add(frame);
	animation.play();
    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton(FILE_READER);
	ScrollPane scroll = new ScrollPane();
	scroll.setId("settingsField");
	VARIABLE_BOX = new VBox();
	scroll.setContent(VARIABLE_BOX);
	VBox panelRoot = new VBox(scroll, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(scroll, Priority.ALWAYS);
	PANEL = panelRoot;
	populateVariableBox(SECOND_DELAY);
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
	Map<String, Double> programVariables = USER_SCREEN.getVariables();
	for (Entry<String, Double> variable : programVariables.entrySet()) {
	    String variableName = variable.getKey();
	    String variableValue = variable.getValue().toString();
	    Label nameLabel = new Label(variableName);
	    nameLabel.setId("variableNameLabel");
	    Label valueLabel = new Label(variableValue);
	    valueLabel.setId("variableLabel");
	    valueLabel.setOnMouseClicked((arg0)->getPane().setRight(verboseVariableDisplay(variableName, variableValue)));
	    HBox infoRow = new HBox(nameLabel, valueLabel);
	    infoRow.setAlignment(Pos.CENTER);
	    VARIABLE_BOX.getChildren().add(infoRow);
	}
    }

    private VBox verboseVariableDisplay(String varName, String varValue) {
	Button backButton = new Button(FILE_READER.resourceDisplayText("backButton"));
	backButton.setId("backButton");
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(PANEL));
	Label nameLabel = new Label(varName);
	nameLabel.setId("variableNameLabel");
	TextArea valueDisplay = new TextArea();
	valueDisplay.setId("inputField");
	valueDisplay.setText(varValue);
	valueDisplay.setEditable(true);
	Button setVariable = new Button(FILE_READER.resourceDisplayText("saveVariableButton"));
	setVariable.setId("saveVariableButton");
	setVariable.setOnMouseClicked((arg0)-> USER_SCREEN.commandRunFromHistory("set " + varName + " " + valueDisplay.getText()));
	VBox panelRoot = new VBox(nameLabel,valueDisplay,setVariable, backButton);
	panelRoot.setId("infoPanel");
	return panelRoot;
    }

    @Override
    protected BorderPane getPane() {
	return PANE;
    }
}
