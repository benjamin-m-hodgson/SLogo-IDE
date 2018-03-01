package screen.panel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import interpreter.Controller;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class TurtlePanel implements Panel {
	// TODO: put in setting.properties file
	private final double DEFAULT_TURTLE_SIZE = 20;
	private final String DEFAULT_TURTLE = "TurtleDefault.png";
	private BorderPane PANEL;
	private ScrollPane SCROLL_PANE;
	private Controller PROGRAM_CONTROLLER;
	private String DEFAULT_COLOR_HEXCODE = "2d3436";
	private HBox ErrorHolder;
	private List<ImageView> TURTLE_LIST;

	public TurtlePanel(Controller programController) {
		PROGRAM_CONTROLLER = programController;
		TURTLE_LIST = new ArrayList<ImageView>();
	}

	@Override
	public void makePanel() {
		BorderPane layoutPane = new BorderPane();
		Pane panel = new Pane();

		ScrollPane scroll = new ScrollPane(panel);
		layoutPane.setCenter(scroll);

		SCROLL_PANE = scroll;
		scroll.setId("turtlePanel");
		createTurtle(panel, scroll);

		PANEL = layoutPane;
	}

	@Override
	public Parent getPanel() {
		if (PANEL == null) {
			makePanel();
		}
		return PANEL;
	}

	private void createTurtle(Pane panel, ScrollPane scrollPane) {
		String currentDir = System.getProperty("user.dir");
		try {
			File turtleFile = new File(currentDir + File.separator + "turtleimages" 
					+ File.separator + DEFAULT_TURTLE);
			Image turtleImage = new Image(turtleFile.toURI().toURL().toExternalForm());
			ImageView turtleView = new ImageView(turtleImage);
			turtleView.setId("turtleView");
			turtleView.setFitHeight(DEFAULT_TURTLE_SIZE);
			turtleView.setFitWidth(DEFAULT_TURTLE_SIZE);
			// center the turtle on the screen
			turtleView.translateXProperty().bind(Bindings.divide(scrollPane.widthProperty(), 2));
			turtleView.translateYProperty().bind(Bindings.divide(scrollPane.heightProperty(), 2));
			turtleView.setX(-DEFAULT_TURTLE_SIZE/2);
			turtleView.setY(-DEFAULT_TURTLE_SIZE/2);
			panel.getChildren().add(turtleView);
			Group penLines = new Group();
			penLines.translateXProperty().bind(Bindings.divide(scrollPane.widthProperty(), 2));
			penLines.translateYProperty().bind(Bindings.divide(scrollPane.heightProperty(), 2));
			panel.getChildren().add(penLines);
			// TODO: possibly add turtles to list ?
			PROGRAM_CONTROLLER.makeNewTurtleCommand("Turtle", turtleView,DEFAULT_COLOR_HEXCODE , penLines);
		}
		catch (Exception e) {
			// TODO: make custom exception super class with sub classes for specifications
			//String specification = "%nFailed to find language files";
			System.out.println("FAILED TO LOAD TURTLE IMG");
		}
	}

	public void displayErrorMessage(String error) {
		Button errorButton = new Button(error);
		errorButton.setId("errorButton");
		ErrorHolder = new HBox(errorButton);
		ErrorHolder.setAlignment(Pos.CENTER);
		HBox.setHgrow(errorButton, Priority.ALWAYS);
		errorButton.setOnMouseClicked((arg0)-> PANEL.getChildren().remove(ErrorHolder));
		errorButton.setMaxWidth(PANEL.widthProperty().get());
		errorButton.setMinWidth(PANEL.widthProperty().get());
		PANEL.setBottom(ErrorHolder);
	}

	public void changeBackgroundColor(String colorCode) {
		SCROLL_PANE.setStyle("-fx-background-color:" + colorCode + ";");
	}
	
	public void removeErrorButton() {
		PANEL.getChildren().remove(ErrorHolder);
	}
}
