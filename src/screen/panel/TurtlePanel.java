package screen.panel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import interpreter.Controller;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TurtlePanel implements Panel {
	// TODO: put in setting.properties file
	private final double DEFAULT_TURTLE_SIZE = 20;
	private final String DEFAULT_TURTLE = "TurtleDefault.png";
	private Parent PANEL;
	private Controller PROGRAM_CONTROLLER;
	private List<ImageView> TURTLE_LIST;

	public TurtlePanel(Controller programController) {
		PROGRAM_CONTROLLER = programController;
		TURTLE_LIST = new ArrayList<ImageView>();
	}

	@Override
	public void makePanel() {
		Pane panel = new Pane();
		ScrollPane panelRoot = new ScrollPane(panel); 
		panelRoot.setId("turtlePanel");
		createTurtle(panel, panelRoot);
		PANEL = panelRoot;
	}

	@Override
	public Parent getPanel() {
		if (PANEL == null) {
			makePanel();
		}
		return PANEL;
	}

	private void createTurtle(Pane panel, ScrollPane panelRoot) {
		String currentDir = System.getProperty("user.dir");
		try {
			File turtleFile = new File(currentDir + File.separator + "turtleimages" 
					+ File.separator + DEFAULT_TURTLE);
			Image turtleImage = new Image(turtleFile.toURI().toURL().toExternalForm());
			ImageView turtleView = new ImageView(turtleImage);
			turtleView.setId("turtleView");
			turtleView.setFitHeight(DEFAULT_TURTLE_SIZE);
			turtleView.setFitWidth(DEFAULT_TURTLE_SIZE);
			turtleView.translateXProperty().bind(Bindings.divide(panelRoot.widthProperty(), 2));
			turtleView.translateYProperty().bind(Bindings.divide(panelRoot.heightProperty(), 2));
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
}
