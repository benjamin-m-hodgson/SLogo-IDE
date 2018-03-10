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
 * Creates the panel containing information about the user defined commands available in the 
 * program environment.
 */
public class CommandPanel extends SpecificPanel {
    private final double FRAMES_PER_SECOND = 2;
    private final long MILLISECOND_DELAY = Math.round(1000 / FRAMES_PER_SECOND);
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private BorderPane PANE;
    private FileIO FILE_READER;
    private VBox COMMAND_BOX;
    private UserScreen USER_SCREEN;


    public CommandPanel( BorderPane pane, UserScreen userScreen, FileIO fileReader) {
	PANE = pane;
	USER_SCREEN = userScreen;
	FILE_READER = fileReader;
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> populateCommandBox(SECOND_DELAY));
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
	COMMAND_BOX = new VBox();
	scroll.setContent(COMMAND_BOX);
	VBox panelRoot = new VBox(scroll, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(scroll, Priority.ALWAYS);
	PANEL = panelRoot;	
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
    private void populateCommandBox(double elapsedTime) {
	COMMAND_BOX.getChildren().clear();
	Map<String, String> programCommands = USER_SCREEN.getUserDefined();
	for (Entry<String, String> command : programCommands.entrySet()) {
	    //   System.out.println("anything?");
	    String commandName = command.getKey();
	    String commandValue = command.getValue();
	    Label nameLabel = new Label(commandName);
	    nameLabel.setId("variableLabel");
	    Label valueLabel = new Label(commandValue);
	    valueLabel.setId("commandLabel");
	    valueLabel.setOnMouseClicked((arg0)-> getPane()
		    .setRight(commandInformation(commandName, commandValue)));
	    HBox infoRow = new HBox(nameLabel, valueLabel);
	    infoRow.setAlignment(Pos.CENTER);
	    COMMAND_BOX.getChildren().add(infoRow);
	}
    }

    /**
     * Takes a user defined command and its user defined value and displays this information
     * in a new panel that aims to enhance readability.  
     * 
     * @param commandName: The user defined name for the command
     * @param commandValue: The user defined 
     * @return VBox: The root of the command informational panel
     */
    private VBox commandInformation(String commandName, String commandValue) {
	Button commandButton = new Button(commandName);
	commandButton.setId("commandButton");
	commandButton.setDisable(true);
	Button backButton = new Button(FILE_READER.resourceDisplayText("backButton"));
	backButton.setId("backButton");
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(PANEL));
	ScrollPane commandInfoPane = new ScrollPane();
	commandInfoPane.setId("settingsField");
	TextArea commandInfoArea = new TextArea();
	commandInfoArea.setId("settingsField");
	commandInfoPane.setContent(commandInfoArea);
	commandInfoArea.setText(commandValue);
	Button runFunction = new Button(FILE_READER.resourceDisplayText("runUserCommButton"));
	runFunction.setId("runUserCommButton");
	runFunction.setDisable(true);
	TextArea parameterInput = new TextArea();
	parameterInput.setId("parametersField"); 
	parameterInput.setPromptText(FILE_READER.resourceDisplayText("parameters"));
	parameterInput.setEditable(true);
	parameterInput.setOnKeyTyped((arg0) -> runFunction.setDisable(false));
	runFunction.setOnMouseClicked((arg0) ->{ 
	    USER_SCREEN.commandRunFromHistory(commandName + " " + parameterInput.getText());
	    USER_SCREEN.checkForNewTurtle();
	});



	VBox panelRoot = new VBox(commandButton, commandInfoArea,parameterInput, runFunction,backButton);
	panelRoot.setId("infoPanel");
	VBox.setVgrow(commandInfoArea, Priority.ALWAYS);
	return panelRoot;
    }

    @Override
    protected BorderPane getPane() {
	return PANE;
    }

}
