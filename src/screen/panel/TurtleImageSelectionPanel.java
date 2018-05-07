package screen.panel;

import java.util.List;
import interpreter.FileIO;
import interpreter.SingleTurtle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import screen.UserScreen;

public class TurtleImageSelectionPanel extends SpecificPanel {
    private BorderPane PANE;
    private UserScreen USER_SCREEN;
    private List<SingleTurtle> TURTLES_ALL;
    private final FileIO FILE_READER;
    private final double DEFAULT_TURTLE_SIZE;
    public static final String DEFAULT_TURTLESHAPES_FILE = "interpreter/TurtleShapes";


    public TurtleImageSelectionPanel(BorderPane pane, UserScreen userScreen, FileIO fileReader) {
	PANE = pane;
	USER_SCREEN = userScreen;
	FILE_READER= fileReader;
	DEFAULT_TURTLE_SIZE = Double.parseDouble(
		FILE_READER.resourceSettingsText("defaultTurtleSize"));
    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton("backButton");
	VBox turtleImageHolderFull = new VBox();
	ScrollPane turtleImageList = new ScrollPane(turtleImageHolderFull);
	turtleImageList.setId("settingsField");
	VBox panelRoot = new VBox(turtleImageList, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(turtleImageList, Priority.ALWAYS);
	fillWithTurtleImages(turtleImageHolderFull);
	PANEL = panelRoot;
    }
    
    /**
     * fills box will button for all active turtles
     * @param turtleImageHolderFull
     */
    public void fillWithTurtleImages(VBox turtleImageHolderFull) {
	TURTLES_ALL = USER_SCREEN.getAllTurtles();
	Button turtleImageButton;
	for(SingleTurtle turtle: TURTLES_ALL) {
	    turtleImageButton = new Button();
	    ImageView turtleImage = turtle.getImageView();
	    turtleImage.setFitHeight(DEFAULT_TURTLE_SIZE);
	    turtleImage.setFitWidth(DEFAULT_TURTLE_SIZE);
	    turtleImageButton.setGraphic(turtleImage);
	    turtleImageButton.setOnMouseClicked(arg0 ->{
		getPane()
		.setRight(new TurtleInfoPanel(PANE, USER_SCREEN, 
			    Integer.toString((int) Math.rint(turtle.getID())), 
			    FILE_READER).getPanel());
	    });
	    turtleImageHolderFull.getChildren().add(turtleImageButton);
	}
    }
    
   
    private Button makeBackButton(String buttonId) {
	Button backButton = new Button(FILE_READER.resourceDisplayText(buttonId));
	backButton.setId(buttonId);
	backButton.setOnMouseClicked(arg0 -> getPane().setRight(new InfoPanel(PANE,USER_SCREEN, FILE_READER).getPanel()));
	return backButton;
    }
    
    @Override
    protected BorderPane getPane() {
	return PANE;
    }

    @Override
    protected UserScreen getUserScreen() {
	return USER_SCREEN;
    }

}
