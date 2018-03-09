package screen.panel;

import interpreter.Controller;
import interpreter.FileIO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import screen.UserScreen;

public class InputPanel extends Panel {
    private final double FRAMES_PER_SECOND = 120;
    private final long MILLISECOND_DELAY = Math.round(1000 / FRAMES_PER_SECOND);
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private TextPanel INPUT_AREA;
    private FileIO FILE_READER;
    private final UserScreen USER_SCREEN;
    private Button RUN;
    private Button CLEAR;


    public InputPanel(UserScreen userScreen, FileIO fileReader) {
	FILE_READER = fileReader;
	USER_SCREEN = userScreen;
    }

    @Override
    public void makePanel() {
	INPUT_AREA = new TextPanel(USER_SCREEN, FILE_READER);
	VBox runBox = drawRunBox();
	Parent textPanel = drawTextPanel(INPUT_AREA);
	BorderPane panelRoot = new BorderPane();
	panelRoot.setRight(runBox);
	panelRoot.setCenter(textPanel);
	panelRoot.setId("inputPanel");
	PANEL = panelRoot;
	// attach "animation loop" to time line to play it
	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		e -> step(SECOND_DELAY));
	Timeline animation = new Timeline();
	animation.setCycleCount(Animation.INDEFINITE);
	animation.getKeyFrames().add(frame);
	animation.play();
    }

    private VBox drawRunBox() {
	RUN = drawRunButton();
	CLEAR = drawClearButton();
	VBox runBox = new VBox(RUN, CLEAR);
	runBox.setId("runBox");
	return runBox;
    }



    private Button drawRunButton() {
	Button runButton = new Button();
	// handle click event
	runButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		USER_SCREEN.clearErrorDisplay();
		INPUT_AREA.run();
	    }
	});
	runButton.setId("runButton");
	return runButton;
    }

    private Button drawClearButton() {
	Button clearButton = new Button();
	// handle click event
	clearButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent arg0) {
		INPUT_AREA.clearInputArea();
		USER_SCREEN.clearErrorDisplay();
	    }
	});
	clearButton.setId("clearButton");
	return clearButton;
    }

    private Parent drawTextPanel(TextPanel inputArea) {
	Parent textPanel = inputArea.getPanel();
	return textPanel;
    }

    /**
     * Change properties of objects to animate them. In this case, changes the text
     * displayed on the buttons @param RUN and @param CLEAR.  
     * 
     * @param elapsedTime: time since last animation update
     */
    private void step (double elapsedTime) {
	RUN.setText(FILE_READER.resourceDisplayText("RunPrompt"));
	CLEAR.setText(FILE_READER.resourceDisplayText("ClearPrompt"));
    }


}
