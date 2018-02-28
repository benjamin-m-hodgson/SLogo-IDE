package screen.panel;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import screen.UserScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import interpreter.Controller;

public class HelpPanel extends SpecificPanel  {

    private Parent PANEL;
    private Controller PROGRAM_CONTROLLER;
    private BorderPane PANE;
    private VBox HELP_BOX;
    private UserScreen USER_SCREEN;



    public HelpPanel(Controller programController, BorderPane pane, UserScreen userScreen) {
	PROGRAM_CONTROLLER = programController;
	PANE = pane;
	USER_SCREEN = userScreen;

    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton(PROGRAM_CONTROLLER);
	ScrollPane helpPane = new ScrollPane();
	helpPane.setId("settingsField");
	HELP_BOX = new VBox();
	helpPane.setContent(HELP_BOX);
	VBox panelRoot = new VBox(helpPane, backButton);
	panelRoot.setId("infoPanel");
	panelRoot.setAlignment(Pos.BASELINE_CENTER);
	VBox.setVgrow(helpPane, Priority.ALWAYS);
	populateHelp();
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

    private void populateHelp() {
	System.out.println("Getting help");
	String currentDir = System.getProperty("user.dir");
	try {
	    //TODO get current language from controller
	    File file = new File(currentDir + File.separator + "reference" + File.separator 
		    + "English");
	    File[] helpFiles = file.listFiles();
	    for (File helpFile : helpFiles) {
		String commandName = helpFile.getName();
		String[] commandSplit = commandName.split("\\.");
		String command = commandSplit[0];
		Button commandButton = new Button(command);
		commandButton.setId("commandButton");
		// handle click event
		commandButton.setOnMouseClicked((arg0)-> {
		    try {
			getPane()
				.setRight(commandInformation(helpFile, command));
		    } catch (FileNotFoundException e) {
			// TODO: make custom exception super class with sub classes for specifications
			//String specification = "%nFailed to find color files";
			//loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
		    }
		});
		HELP_BOX.getChildren().add(commandButton);

	    }
	    System.out.println("Done");
	}
	catch (Exception e) {
	    // TODO: make custom exception super class with sub classes for specifications
	    //String specification = "%nFailed to find color files";
	    //loadErrorScreen(resourceErrorText(FILE_ERROR_KEY));
	}
    }

    private VBox commandInformation(File commandFile, String command) throws FileNotFoundException {
	Button commandButton = new Button(command);
	commandButton.setId("commandButton");
	commandButton.setDisable(true);
	Button backButton = new Button(PROGRAM_CONTROLLER.resourceDisplayText("backButton"));
	backButton.setId("backButton");
	// override click event
	backButton.setOnMouseClicked((arg0)-> getPane()
		.setRight(PANEL));
	ScrollPane commandInfoPane = new ScrollPane();
	commandInfoPane.setId("settingsField");
	TextArea commandInfoArea = new TextArea();
	commandInfoArea.setId("settingsField");
	commandInfoPane.setContent(commandInfoArea);
	populateInfoBox(commandFile, commandInfoArea);
	VBox panelRoot = new VBox(commandButton, commandInfoArea, backButton);
	panelRoot.setId("infoPanel");
	VBox.setVgrow(commandInfoArea, Priority.ALWAYS);
	return panelRoot;
    }
    
    private void populateInfoBox(File commandFile, TextArea infoBox) throws FileNotFoundException {
	Scanner in = new Scanner(commandFile);
	StringBuilder commandInfoBuilder = new StringBuilder();
	while (in.hasNextLine()) {
	    String line = in.nextLine();
	    commandInfoBuilder.append(line);
	    commandInfoBuilder.append(System.lineSeparator());
	}
	infoBox.setText(commandInfoBuilder.toString());
	infoBox.setEditable(false);
	in.close();
    }
}
