# Design Document

## Introduction
The purpose of this project is to create an integrated development environment to allow users to program in a simplified version of the Logo language. The project will emulate a Model-View-Controller design. A mediator/"Controller" will communicate information from the front- to the back-end to ensure that the details of the front- and back-end are relatively hidden from one another. The "Model" will consist of Turtles and their corresponding Pen/Trail objects, whose attributes are updated based on the user's text input that the Controller turns into actionable commands. The UserScreen will hold references to the objects that the Model holds, thus ensuring that changes to the Model manifest visually on the UserScreen.

## Design Overview
The general design of this program is divided between its backend and frontend components, which are mediated through the Controller class. This design was used to mitigate dependencies between frontend and backend, so that for example a new graphical interface could be created entirely from scratch but could be easily plugged into the same backend code. Additionally, this design hides many of the details of the backend (the list of Turtles, the Turtle objects themselves, and how commands are parsed and executed) entirely from the frontend, reducing the potential for frontend code to create errors in backend implementation. Both the main front-end class (UserScreen) and the Controller are instantiated in the Driver class, which also puts the UserScreen on the Stage and contains the launching method for the program.

On the front-end, the flow of information (from buttons and menus in various Panels like TurtlePanel and SettingsPanel classes) goes into the UserScreen, which then calls four main external backend methods (all within the Controller class): `parseInput(String userTextInput)`, `makeNewTurtleCommand(String name, ImageView turtleImage, Color penColor, Group penLines`, `getUserCommands()`, and `getVariables()`, with the latter two returning an UnmodifiableMap and an ImmutableList, respectively, so that the front-end can view for display but not change these values. Only a command to make a new turtle (a flexibility we anticipate needing to add to our program in future implementations) uses the `makeNewTurtleCommand()` method, while all other commands (sent in through the TextField or through buttons that the front-end automatically assigns text to) go through the `parseInput()` command. This maximizes flexibility to add new Command functionality without changing anything fundamental on the front-end, since all commands will be sent to the Controller through the same method, Strings.
	
On the back-end, once the controller receives the user input through the `parseInput()` method, it sends it to the TextFieldParser class through `parseText()` to be split up into individual commands (based on newline markers in the user text). Those individual commands are then sent in the form of a Queue to the CommandMaker, which we are treating as a parsing black box for now and which will create based on the String command Queue a Queue of appropriate objects which all implement the Command interface, meaning they have an `execute()` method. That Queue of Command objects will be sent via the Controller to the Executor class, which will call `.execute()` on each of the commands in the proper order and return to Controller the appropriate value. Within each `.execute()`, the Command object will retrieve and set the proper information within Turtle and Pen objects through methods like `setX()` and `penUp()`, then return the appropriate value. The Controller will then return the final such value to the UserScreen to be displayed to the user, and the entire process begins again with new user input.
## User Interface

## API Details 

### Backend Internal
**Methods**
* Turtle (public constructor)
    * void hideTurtle()
    * void showTurtle()
* protected Pen(Color color, int strokeWidth, boolean up, Group penLines)
    * void penUp()
    * void penDown()
    * void drawLine(double oldX, double oldY, double x, double y)
* abstract Command(String name)
    * MakeTurtleCommand(String name, List<Turtle> onScreenTurtles, double x, double y, double angle, Pen pen, ImageView image, boolean visible)
    * MoveTurtleCommand(String name, List<Turtle> onScreenTurtles, double x, double y)
        * note: this handles fd, bk
    * RotateTurtleCommand(String name, List<Turtle> onScreenTurtles, boolean absolute, double rotation) 
        * lt, rt, seth, towards
    * PenCommand(String name, List<Turtle> onScreenTurtles, Color color, int strokeWidth, boolean up)
    * VariableCommand(String varName, double varValue)
    * ErrorCommand(String ErrorMessage)
    * protected double executeCommand()
* protected Executor(Queue<Command> queue)
* protected TextFieldParser(String userInput)
    * protected Queue<Command> parseInput()
    * protected ImmutableList<String> getUserCommands()
    * protected UnmodifiableMap<String,Double> getCurrentVars()
* protected CommandMaker(Queue<String> stringCommandQueue)
    * protected Queue<Command> parseStringCommands()

**Justification**
* Using an interface for linking different kinds of Commands will allow the program to be flexible to adding new commands if desired. This will be based on the Command design pattern. Potential inheritance structures could be put in place for Variables or Pens to add new features (e.x. for Pens to make a dashed line rather than a solid line).
* The Turtle/Pen basic classes should be closed for modification, as well as the Executor class and TextFieldParser. Additional functionality will be achieved by creating new implementations of the Command and CommandMaker interfaces, or extending Pen/Turtle classes to new subclasses.
* Errors may be thrown by the TextFieldParser if a command is not recognized as either a known command, a user-defined command, or a variable. In this case, the Controller will catch this error and throw it back to the UserScreen to display to the user (via a popup window) without making a Command.

### Backend External
**Methods**
* from Controller
	* void parseInput(String userTextInput) 
* public MakeNewTurtleCommand(String name, ImageView turtleImage, Color penColor, Group pen)

**Justification**
* The UserScreen will call the Controller's parseInput method when userInput has been received (i.e. upon user clicking the run button), thus triggering the Controller's command execution process. 
* The only Command that will be accessible to the UserScreen is the MakeNewTurtleCommand. This is because the UserScreen will add each new Turtle's ImageView and Group of pen lines to its root. This is important because it eliminates the need for the UserScreen to have to "ask" the Controller what updates have been made to the Model. Changes to the back-end Turtle objects will be changes to the same objects that are attached to the UserScreen's root upon Turtle creation. 
* The TextFieldParser will throw Exceptions in the case of ill-formatted user commands or arguments. The UserScreen will catch these Exceptions, and display an error-specific notification message to the user on a designated part of the user screen.

### Frontend Internal
**Methods**

**Justification**

### Frontend External
**Methods**

**Justification**

## API Example Code
* "The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the command is added to the environment's history."
	* The ImageView and Lines (in a Group) associated the Turtle is attached to the root of the UserScreen. When the user presses the "Run" button on the UserScreen, the UserScreen will call Controller's `parseText(String userInput)` method, thereby notifying the Controller that commands (in this case, a single command) needs to be generated from the user text input. Controller's `parseText` method will wrap a call to TextFieldParser's `parseText` method, which calls CommandMaker's `parseStringCommands` method to generate a Queue of actionable Command objects. Controller can then get the Queue of Commands with TextFieldParser's `getCommandQueue()` method. In this case, the Queue of Commands will only have one command, a MoveTurtleCommand. The Queue with the MoveTurtleCommand will be passed into an Executer object that will loop through the entirety of the Queue (in this case, just one loop), and call the Command's `execute` method, which will add 50x(some pixel scale) to the position of the ImageView associated with the Turtle object in the direction the Turtle is facing, and also add a new Line to the Turtle object's Pen instance variable that stretches from the Turtle's old coordinate to its new coordinate. Because the Turtle and its Lines are already a part of the UserScreen's root, the update to the Turtle's position and the addition of a new Line will manifest without any need for the UserScreen class to call methods from the Controller. 

Additional Use Cases:

1. **The user runs the command hp (hide pen)**: The same flow of text-parsing and Queue-of-Command generation will ensue as in the use case above. In this case, however, the Queue of Commands will only have one command, a PenVisibilityCommand. When the Executer calls the PenVisibilityCommand's `execute()` method, the PenVisibilityCommand will invoke the corresponding Turtle's `hidePen()` method, which wraps a call to the Turtle's Pen's `hidePen()` method. This will flip a "myVisibility" boolean value, which will be checked before adding any additional Lines to the Group that characterizes the Pen/the Turtle's trails. 

2. **The user mistypes the 'fd' command as 'fw,' e.g. 'fw 50'**: The TextFieldParser's `parseText` command will throw an UnidentifiedCommandException. The UserScreen will catch this Exception, and match the key/property in its ErrorMessagesProperties file corresponding to that Exception, and display the String corresponding with that key/property on a part of the UserScreen designated for error notifications. 

3. **The user changes the image of a turtle**: The user selects an image name (i.e. Turtle, Fish, or Balloon) from a dropdown menu on the GUI. The `handleOnButtonPressed` command will call `myController.parseInput(String userInput)` with a String key (something like "changeturtleimage" + turtleName + filepath where turtleName is the name corresponding to this particular Turtle and filepath is the path to the new image file). The controller will pass this string to the TextFieldParser's `parseText(String string)` method, which will see that there is only one line of commands and create a Queue containing only the input String. That string will be parsed by the CommandMaker, which will create a `SetTurtleImageCommand` with an instance variable String corresponding to the filepath to the new image and an instance variable Turtle corresponding to the Turtle whose image needs to be changed.  The Queue of Commands (in this case just the one `SetTurtleImageCommand`) will be returned back to the Controller (most likely as part of a private helper method), which will make an Executor using that Queue of Commands. The Executor will call `.execute()` on the `SetTurtleImageCommand`, and that method will call `setTurtleImage(String filepath)` on the Turtle whose image it is changing. The Executor will return the proper double, which will then be returned by the `parseInput` method to the front end.

4. **The user runs the command XCOR**: The user inputs XCOR to the TextField and hits run, triggering the UserScreen to call `parseInput("XCOR")` on the Controller. The controller calls `parseText("XCOR")`, which sends "XCOR", as it is only one line, to the CommandMaker. The CommandMaker makes a GetXCoordinateCommand and adds it to the command Queue, which is then passed via the controller to the Executor. The Executor calls `.execute` on the single Command in the Queue, which uses the `.getX()` method from the Turtle Class to retrieve and return the necessary information. The Executor returns this value to the Controller, which returns it to the UserScreen to be displayed to the user.

5.

6.

7.

8.

## Design Considerations/Notable Design Decisions

* Delegating user error communication to back-end vs. front-end 
	* We considered handling user-input errors on the back-end (by creating ErrorCommand extensions of the Command superclass), whose `execute()` methods would populate a text field on the UserScreen designated for error notifications. We ultimately decided to have the back-end parser throw an Exception to indicate the command input error, which the UserScreen would then handle (see use case #2). This has the design downside of preventing all user actions from influencing the Model (i.e. the fact that the user mis-entered a command would not be incorporated into the back-end Model) per a strict interpretation of the MVC design pattern. However, the team agreed that the decision to display a certain message in response to a user error was more of a front-end decision in nature. 

* Passing instance of Controller to UserScreen, and vice versa; initializing Turtles on the front- vs. back-end
	* We discussed whether to pass the Controller into the UserScreen constructor and/or to pass the UserScreen into the Controller constructor. We decided to pass the Controller into the UserScreen constructor so that the UserScreen could trigger the text-to-command parsing process with the `parseText(String userInput)` command. We considered passing the UserScreen into the Controller constructor, which would be made accessible to the Model in conveying model updates; however, we decided against this because we thought it would too severely compromise the compartmentalization of the front- and back-ends. We eliminated the need to pass the UserScreen into the Controller by ensuring that the objects influenced by the Model (Turtle and its Line trails) would be attached to the UserScreen's root before being added to the Model. This ensures that updates to the Model automatically manifest visually on the UserScreen, without the need for a mediator between the back- and front-ends. 

## Team Responsibilities
### Backend
**Susie** will be responsible for the Executor, TextFieldParser, Turtle, and Pen classes, as well as half of the Command classes.
**Sarah** will be responsible for the Controller and CommandMaker (actual parsing of SLogo) classes as well as half of the Command classes.
