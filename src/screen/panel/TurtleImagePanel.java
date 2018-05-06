package screen.panel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import interpreter.FileIO;
import interpreter.SingleTurtle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import screen.UserScreen;

public class TurtleImagePanel extends SpecificPanel{

    private final FileIO FILE_READER;
    private VBox LIST_BOX;
    private BorderPane PANE;
    private UserScreen USER_SCREEN;
    private SingleTurtle TURTLE;
    private Map<SingleTurtle, ImageView> IMAGES;

    public TurtleImagePanel(BorderPane pane, UserScreen userScreen, FileIO fileReader, 
	    SingleTurtle turtle) {
	FILE_READER = fileReader;
	PANE = pane;
	USER_SCREEN = userScreen;
	TURTLE = turtle;
	IMAGES = new HashMap<SingleTurtle, ImageView>();
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
	ScrollPane turtleImagePane = new ScrollPane();
	turtleImagePane.setId("settingsField");
	LIST_BOX = new VBox();
	turtleImagePane.setContent(LIST_BOX);
	VBox panelRoot = new VBox(turtleImagePane, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(turtleImagePane, Priority.ALWAYS);
	populateTurtlesImages();
	PANEL = panelRoot;
    }

    private Button makeTurtlesBackButton(String itemId) {
	Button backButton = new Button(FILE_READER.resourceDisplayText(itemId));
	backButton.setId(itemId);
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(new TurtleListPanel(PANE, USER_SCREEN, FILE_READER).getPanel()));
	return backButton;
    }

    private void populateTurtlesImages() {
	VBox turtles = new VBox();
	try {
	    String currentDir = System.getProperty("user.dir");
	    for (Entry<String, String> entry : FILE_READER.getShapes().entrySet()) {
		File image = new File(currentDir + File.separator + "turtleimages"
			    + File.separator + entry.getValue() + ".png");
		Image turtleView = new Image(image.toURI().toString());
		ImageView graphicView = new ImageView(turtleView);
		graphicView.setFitHeight(40);
		graphicView.setFitWidth(40);
		Button turtleButton = new Button();
		turtleButton.setGraphic(graphicView);
		// handle click event
		turtleButton.setOnMouseClicked((arg0)-> {
		    TURTLE.setImage(turtleView); 
		    FILE_READER.parseSettingInput(FILE_READER.resourceSettingsText("defaultShapeCommand")
			    +" "+entry.getKey());
		    USER_SCREEN.updateCurrentState("turtleImage", entry.getKey());
		});
		turtles.getChildren().add(turtleButton);
	    }
	}
	catch (Exception e) {
	    USER_SCREEN.throwErrorScreen(FILE_READER
		    .resourceErrorText("ScreenErrorPrompt"));
	}
	LIST_BOX.getChildren().clear();
	LIST_BOX.getChildren().addAll(turtles);
    }
}
