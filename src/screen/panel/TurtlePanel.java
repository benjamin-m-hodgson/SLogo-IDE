package screen.panel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import interpreter.Controller;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class TurtlePanel implements Panel {
	// TODO: put in setting.properties file
	private final double DEFAULT_TURTLE_SIZE = 20;
	private final String DEFAULT_TURTLE = "TurtleDefault.png";
	private BorderPane PANEL;
	private Controller PROGRAM_CONTROLLER;
	private List<ImageView> TURTLE_LIST;
	private ScrollPane SCROLL_PANE;


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
	
	public void displayErrorMessage(String error) {
		Button errorButton = new Button(error);
		errorButton.setId("errorButton");
		HBox holder = new HBox(errorButton);
		holder.setAlignment(Pos.CENTER);
		HBox.setHgrow(errorButton, Priority.ALWAYS);
		errorButton.setOnMouseClicked((arg0)-> PANEL.getChildren().remove(holder));
		errorButton.setMaxWidth(PANEL.widthProperty().get());
		errorButton.setMinWidth(PANEL.widthProperty().get());
		PANEL.setBottom(holder);
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
			turtleView.translateXProperty().bind(Bindings.divide(scrollPane.widthProperty(), 2));
			turtleView.translateYProperty().bind(Bindings.divide(scrollPane.heightProperty(), 2));
			panel.getChildren().add(turtleView);
			// TODO: possibly add turtles to list ?
			PROGRAM_CONTROLLER.makeNewTurtleCommand("Turtle", turtleView, Color.BLACK, new Group());
		}

		catch (Exception e) {
			// TODO: make custom exception super class with sub classes for specifications
			//String specification = "%nFailed to find language files";
			System.out.println("FAILED TO LOAD TURTLE IMG");
		}
	}
	public void changeBackgroundColor(String colorCode) {
		SCROLL_PANE.setStyle("-fx-background-color:" + colorCode + ";");
	}
    
    
}
