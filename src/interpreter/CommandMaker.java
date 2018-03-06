//
//public interface CommandMaker {
//	/**
//	 * Creates a Queue of Command objects given a Queue of user-inputted Strings
//	 */
//	public Command parseCommand(String stringCommand);
//	
//	/**
//	 * Handle parsing individual text String commands
//	 */
//	public void PARSING_BLACK_BOX();
//
//}

package interpreter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/** 
 * @author Susie Choi
 */

class CommandMaker {

	public static final String DEFAULT_FILEPATH = "interpreter/";
	public static final ResourceBundle DEFAULT_LANGUAGE = ResourceBundle.getBundle("interpreter/English");
	public static final String DEFAULT_NUM_ARGS_FILE = "NumArgsForCommands";
	public static final String DEFAULT_COMMAND_IDENTIFIER = "Command"; //TODO allow this to be client-specified
	public static final String[] DEFAULT_CONTROLFLOW_IDENTIFIERS = {"Repeat", "DoTimes", "For"};

	private HashMap<String, Double> myVariables; 
	private HashMap<String, String> myUserDefCommands; 
	private HashMap<String, Integer> myUserCommandsNumArgs; 
	private ArrayList<Turtle> myActiveTurtles; 
	private ArrayList<Turtle> myTurtles;
	private ResourceBundle myLanguage; 
	private CommandTreeBuilder myCommandTreeBuilder; 
	private IntegerProperty myBackColor; 
	private BooleanProperty myBackColorChangeHeard; 

	protected CommandMaker() {
		this(DEFAULT_LANGUAGE, DEFAULT_FILEPATH+DEFAULT_NUM_ARGS_FILE);
	}

	protected CommandMaker(ResourceBundle languageBundle, String numArgsFileName) {
		myTurtles = new ArrayList<Turtle>(); 
		myActiveTurtles = new ArrayList<Turtle>();
		myLanguage = languageBundle;
		myVariables = new HashMap<String, Double>(); 
		myUserDefCommands = new HashMap<String, String>(); 
		myUserCommandsNumArgs = new HashMap<String, Integer>(); 
		myCommandTreeBuilder = new CommandTreeBuilder(numArgsFileName, myVariables, myUserDefCommands, myUserCommandsNumArgs); 
		myBackColor = new SimpleIntegerProperty(0);
		myBackColorChangeHeard = new SimpleBooleanProperty(false);
		setUpBackColorListener();
	}

	protected void setUpBackColorListener() {
		myCommandTreeBuilder.getBackColor().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number t1, Number t2) {
				myBackColor = myCommandTreeBuilder.getBackColor();
				myBackColorChangeHeard.set(true);
			}
		});
	}
	
	protected double parseValidTextArray(String turtleID, String[] userInput, String[] typesOfInput) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		return parseValidTextArray(turtleID, userInput, typesOfInput, DEFAULT_COMMAND_IDENTIFIER);
	}

	private double parseValidTextArray(String turtleID, String[] userInput, String[] typesOfInput, String commandIdentifier) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
		Turtle identifiedTurtle = null;
		if (myTurtles.size() > 0) {
			identifiedTurtle = myTurtles.get(0); 
		}
		boolean turtleIdentified = false; 
		try {
			double turtleIDDouble = Double.parseDouble(turtleID);
			for (Turtle turtle : myTurtles) {
				if (turtle.getID()==(turtleIDDouble)) {
					identifiedTurtle = turtle; 
					turtleIdentified = true; 
				}
			}
		}
		catch(NumberFormatException e) {
			//TODO: do I need to have something here?
		}
		String[] commandTypes = new String[userInput.length];
		int startIdx = 0; 
		if (turtleIdentified) {
			startIdx = 1; 
		}
		for (int idx = startIdx; idx < userInput.length; idx++) {
			if (typesOfInput[idx].equals(commandIdentifier)) {
				try{
					commandTypes[idx] = getCommandType(userInput[idx]);
				} 
				catch (Exception e) {
					commandTypes[idx] = "";
				}
			}
			else {
				commandTypes[idx] = "";
			}
		}

		String[] userInputArrayToPass = userInput;
		String[] commandTypesToPass = commandTypes; 

		if (turtleIdentified) {
			userInputArrayToPass = Arrays.copyOfRange(userInputArrayToPass, 1, userInputArrayToPass.length); 
		}
		
		for (int i = 0; i < userInputArrayToPass.length; i++) {
			if (commandTypesToPass[i].length() > 0) {
				userInputArrayToPass[i] = commandTypesToPass[i];
			}
		}
 		//		makeListForBuilder(myListForBuilder, userInput, commandTypes, 1, DEFAULT_CONTROLFLOW_IDENTIFIERS);
		return myCommandTreeBuilder.buildAndExecute(myTurtles, myActiveTurtles, userInputArrayToPass, true); 
	}

	//	private void makeListForBuilder(ArrayList<String> currCommandList, String[] inputArray, String[] commandTypes, int numTimesToAdd, String[] controlFlowIdentifiers) {
	//		for (int i = 0; i < inputArray.length; i++) {
	//			if (Arrays.asList(controlFlowIdentifiers).contains(commandTypes[i])) {
	//
	//			}
	//		}
	//	}

//	// TODO if node where children are if statement and if body?
//	// shouldn't build body tree unless if statement evaluates to true 
//	private ArrayList<String> parseIf(int ifIdx, Turtle turtle, String[] userInput, String[] commandTypes, String[] allTypes) throws BadFormatException, UnidentifiedCommandException, MissingInformationException {
//		ArrayList<String> ifExpression = new ArrayList<String>(); 
//		int i = ifIdx+1; 
//		while (! userInput[i].equals(DEFAULT_IFEXPR_END)) {
//			ifExpression.add(userInput[i]);
//			i++; 
//		}
//		// TODO COPY RANGE OF COMMANDTYPES, USERINPUTARRAY, TYPESARRAY
//		String[] ifExprArray = ifExpression.toArray(new String[ifExpression.size()]);
//		double ifExprEval = myCommandTreeBuilder.buildAndExecute(turtle, ifExprArray, commandTypes, allTypes);
//		if (ifExprEval > 0) {
//			ArrayList<String> ifExprBody = new ArrayList<String>(); 
//			while (! userInput[i].equals(DEFAULT_IFBODY_END)) {
//				ifExprBody.add(userInput[i]);
//				i++;
//			}
//		}
//	} 
	
	private String getCommandType(String text) throws BadFormatException, MissingInformationException, UnidentifiedCommandException {
		RegexMatcher regexMatcher = new RegexMatcher(myLanguage);
		String commandType = regexMatcher.findMatchingKey(text);
		return commandType;
	}

	protected void changeLanguage(ResourceBundle languageBundle) {
		myLanguage = languageBundle; 
	}

	protected Map<String, Double> getVariables() {
		return Collections.unmodifiableMap(myVariables);
	}
	protected List<Turtle> getAllTurtles(){
		return Collections.unmodifiableList(myTurtles);
	}
	protected List<Turtle> getActiveTurtles(){
		return Collections.unmodifiableList(myActiveTurtles);
	}

	protected void addNewTurtle(String ID, ImageView turtleImage, String penColor, Group penLines) {
		//System.out.println(name);
		double id = Double.parseDouble(ID);
		Turtle newTurtle = new Turtle(id, turtleImage, penLines, penColor);
		myTurtles.add(newTurtle);
		myActiveTurtles.add(newTurtle);
	}
	protected Map<String, String> getUserDefined() {
		return myUserDefCommands;
	}
	
	protected IntegerProperty getBackColor()  {
		return myBackColor;
	}
	
	protected BooleanProperty getBackColorChangeHeard() {
		return myBackColorChangeHeard;
	}
	

}
