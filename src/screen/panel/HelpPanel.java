package screen.panel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import interpreter.FileIO;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import screen.UserScreen;

/**
 * 
 * @author Ben Hodgson and Andrew Arnold
 *
 * A class that extends the SpecificPanel to create the a panel that displays information
 * about the commands and their uses to the user
 */
public class HelpPanel extends SpecificPanel  {

    private final FileIO FILE_READER;
    private VBox HELP_BOX;
    private BorderPane PANE;
    private UserScreen USER_SCREEN;

    public HelpPanel( BorderPane pane, UserScreen userScreen, FileIO fileReader) {
	FILE_READER = fileReader;
	PANE = pane;
	USER_SCREEN = userScreen;

    }

    @Override
    public void makePanel() {
	Button backButton = makeBackButton(FILE_READER);
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
    protected UserScreen getUserScreen() {
	return USER_SCREEN;
    }

    /**
     * Retrieves the command files from the build path and uses this to populate a 
     * pane containing buttons for each command.
     */
    private void populateHelp() {
	String currentDir = System.getProperty("user.dir");
	try {
	    File file = new File(currentDir + File.separator + "reference" + File.separator 
		    + FILE_READER.resourceDisplayText("Name"));
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
			USER_SCREEN.throwErrorScreen(FILE_READER
				.resourceErrorText("CommandFileError"));
		    }
		});
		HELP_BOX.getChildren().add(commandButton);
	    }
	}
	catch (Exception e) {
	    USER_SCREEN.throwErrorScreen(FILE_READER
		    .resourceErrorText("CommandFileError"));
	}
    }

    /**
     * Takes a command and it's corresponding informational file and generates an 
     * informational pane explaining the commands details as described in the file. 
     * 
     * @param commandFile: The file containing the information pertaining to the given command 
     * @param command: The given command to be described/explained in the informational panel
     * @return VBox: The root of the command informational panel
     * @throws FileNotFoundException: An exception thrown if the file can't be found in the path
     */
    private VBox commandInformation(File commandFile, String command) throws FileNotFoundException {
	Button commandButton = new Button(command);
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
	populateInfoBox(commandFile, commandInfoArea);
	VBox panelRoot = new VBox(commandButton, commandInfoArea, backButton);
	panelRoot.setId("infoPanel");
	VBox.setVgrow(commandInfoArea, Priority.ALWAYS);
	return panelRoot;
    }

    /**
     * Loops through the text in the provided commandFile argument and places this text in
     * @param infoBox so it can be attached to the pane root and displayed to the user.
     * 
     * @param commandFile: The file containing the information pertaining to the given command
     * @param infoBox: The TextArea where the information about the command is stored
     * @throws FileNotFoundException: An exception thrown if the file can't be found in the path
     */
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
    @Override
    protected BorderPane getPane() {
	return PANE;
    }
}
