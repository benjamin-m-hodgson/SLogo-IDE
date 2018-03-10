package screen.panel;

import java.io.FileNotFoundException;
import java.util.List;

import interpreter.FileIO;
import interpreter.SingleTurtle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import screen.UserScreen;

public class TurtleListPanel extends SpecificPanel{

    private final FileIO FILE_READER;
    private VBox LIST_BOX;
    private BorderPane PANE;
    private UserScreen USER_SCREEN;
    private List<SingleTurtle> TURTLES_ALL;
    private List<SingleTurtle> TURTLES_ACTIVE;

    public TurtleListPanel(BorderPane pane, UserScreen userScreen, FileIO fileReader) {
	FILE_READER = fileReader;
	PANE = pane;
	USER_SCREEN = userScreen;

    }

    @Override
    protected BorderPane getPane() {
	return PANE;
    }

    @Override
    protected UserScreen getUserScreen() {
	return USER_SCREEN;
    }

    @Override
    public void makePanel() {
	Button backButton = makeTurtlesBackButton("backButton");
	ScrollPane turtleListPane = new ScrollPane();
	turtleListPane.setId("settingsField");
	LIST_BOX = new VBox();
	turtleListPane.setContent(LIST_BOX);
	VBox panelRoot = new VBox(turtleListPane, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(turtleListPane, Priority.ALWAYS);
	populateTurtlesList();
	PANEL = panelRoot;
    }

    private Button makeTurtlesBackButton(String itemId) {
	Button backButton = new Button(FILE_READER.resourceDisplayText(itemId));
	backButton.setId(itemId);
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(new SettingsPanel(PANE, USER_SCREEN, FILE_READER).getPanel()));
	return backButton;
    }

    private void populateTurtlesList() {
	TURTLES_ACTIVE = USER_SCREEN.getActiveTurtles();
	TURTLES_ALL = USER_SCREEN.getAllTurtles();
	VBox activeTurtles = new VBox();
	VBox inactiveTurtles = new VBox();
	for (SingleTurtle turtle : TURTLES_ALL) {
	    try {
		Button turtleButton = new Button(FILE_READER.resourceDisplayText("TurtlePrompt")
			+ " " + Integer.toString((int) Math.rint(turtle.getID())));
		turtleButton.setId("commandButton");
		// handle click event
		turtleButton.setOnMouseClicked((arg0)-> {
		    getPane()
		    .setRight(new TurtleInfoPanel(PANE, USER_SCREEN, 
			    Double.toString(turtle.getID()), FILE_READER).getPanel());
		});
		if (TURTLES_ACTIVE.contains(turtle)) {
		    activeTurtles.getChildren().add(turtleButton);
		}
		else {
		    inactiveTurtles.getChildren().add(turtleButton);
		}
	    }
	    catch (Exception e) {
		USER_SCREEN.throwErrorScreen(FILE_READER
			.resourceErrorText("ScreenErrorPrompt"));
	    }
	}
	LIST_BOX.getChildren().clear();
	LIST_BOX.getChildren().addAll(activeTurtles, inactiveTurtles);
    }

    private void checkDifferences() {
	if (USER_SCREEN.getActiveTurtles().size() != TURTLES_ACTIVE.size()
		|| USER_SCREEN.getAllTurtles().size() != TURTLES_ALL.size()) {
	    populateTurtlesList();
	}
    }

}
