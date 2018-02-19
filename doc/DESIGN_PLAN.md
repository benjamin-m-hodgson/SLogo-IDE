# Design Document

## Introduction
The purpose of this project is to create an integrated development environment to allow users to program in a simplified version of the Logo language. The project will emulate a Model-View-Controller design. A mediator/"Controller" will communicate information from the front- to the back-end to ensure that the details of the front- and back-end are relatively hidden from one another. The "Model" will consist of Turtles and their corresponding Pen/Trail objects, whose attributes are updated based on the user's text input that the Controller turns into actionable commands. The UserScreen will hold references to the objects that the Model holds, thus ensuring that changes to the Model manifest visually on the UserScreen.

## Design Overview

## User Interface

## API Details 

### Backend Internal
**Methods**
* protected Turtle(String name, ImageView image, Group pen) 
    * void setTurtleImage(ImageView image)
    * void hideTurtle()
    * void showTurtle()
    * void setPenColor(Color color) 
    * void showPen()
    * void hidePen()
    * void clearPen()
* protected Pen(Color color, boolean penUp)
    * void addLine(double xStart, double yStart, double xEnd, double yEnd)
    * void setColor(Color color) 
    * void showPen()
    * void hidePen()
* protected TextFieldParser()
    * protected Queue<Command> getCommandQueue()
* protected CommandMaker()
    * protected Queue<Command> parseStringCommands(Queue<String> stringQueue)
* abstract Command(String name)
    * MoveTurtleCommand(String name, List<Turtle> onScreenTurtles, double x, double y)
        * note: this handles fd, bk, setxy, home
    * RotateTurtleCommand(String name, List<Turtle> onScreenTurtles, double rotation, boolean absolute) 
        * lt, rt, seth, toward
    * TurtleVisibilityCommand(String name, List<Turtle> onScreenTurtles, boolean visible)
    * TurtleImageCommand(String name, List<Turtle> onScreenTurtles, ImageView newTurtleImage)
    * PenVisibilityCommand(String name, List<Turtle> onScreenTurtles, boolean visible)
    * PenColorCommand(String name, List<Turtle> onScreenTurtles, Color color)
    * ClearCommand(List<Turtle> onScreenTurtles) 
    * VariableCommand(String varName, double varValue)
    * protected double executeCommand()
* protected Executor(CommandQueue queue)
    
    
**Justification**
* Using an interface for linking different kinds of Commands will allow the program to be flexible to adding new commands if desired. This will be based on the Command design pattern. A potential CommandMaker interface will allow different types of commands (i.e. text input vs. slider/button input) to be parsed correctly and go through the flow of the program correctly. Potential inheritance structures could be put in place for Variables or Pens to add new features (e.g. for Pens to make a dashed line rather than a solid line).
* The Turtle/Pen basic classes should be closed for modification, as well as the Executor class and TextFieldparser. Additional functionality will be achieved by creating new implementations of the Command and CommandMaker interfaces, or extending Pen/Turtle classes to new subclasses.
* Errors may be thrown by the parsers if a command is not recognized (but currently the plan is not to throw an error, but to create a Command that creates a popup to notify the user of the mistake). We may also use the Null Object design pattern to allow even commands with no known implementation to avoid breaking the program.

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

1. The user runs the command hp (hide pen). 

* The same flow of text-parsing and Queue-of-Command generation will ensue as in the use case above. In this case, however, the Queue of Commands will only have one command, a PenVisibilityCommand. When the Executer calls the PenVisibilityCommand's `execute()` method, the PenVisibilityCommand will invoke the corresponding Turtle's `hidePen()` method, which wraps a call to the Turtle's Pen's `hidePen()` method. This will flip a "myVisibility" boolean value, which will be checked before adding any additional Lines to the Group that characterizes the Pen/the Turtle's trails. 

2. The user mistypes the 'fd' command as 'fw,' e.g. 'fw 50' 

* The TextFieldParser's `parseText` command will throw an UnidentifiedCommandException. The UserScreen will catch this Exception, and match the key/property in its ErrorMessagesProperties file corresponding to that Exception, and display the String corresponding with that key/property on a part of the UserScreen designated for error notifications. 

3.

4.

5.

6.

7.

8.

## Design Considerations/Notable Design Decisions

* Delegating user error communication to back-end vs. front-end 
	* We considered handling user-input errors on the back-end (by creating ErrorCommand extensions of the Command superclass), whose `execute()` methods would populate a text field on the UserScreen designated for error notifications. We ultimately decided to have the back-end parser throw an Exception to indicate the command input error, which the UserScreen would then handle (see use case #2). This has the design downside of preventing all user actions from influencing the Model (i.e. the fact that the user mis-entered a command would not be incorporated into the back-end Model) per a strict interpretation of the MVC design pattern. However, the team agreed that the decision to display a certain message in response to a user error was more of a front-end decision in nature. 

* Passing instance of Controller to UserScreen, and vice versa

* Initializing Turtles in the front-end vs. back-end 

* ViewUpdater vs. ensuring Turtles/Lines are already a part of UserScreen's root

## Team Responsibilities

