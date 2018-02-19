# Design Document

## Introduction

## Design Overview

## User Interface

## API Details 

### Backend Internal
**Methods**
* protected Turtle(String name, ImageView image, Group trails) 
    * void hideTurtle()
    * void showTurtle()
    * void penUp()
    * void penDown()
* protected Trails(Color color, boolean penUp)
    * void addTrail(double xStart, double yStart, double xEnd, double yEnd)
    * void setColor(Color color) 
    * void penUp()
    * void penDown()
* abstract Command(String name)
    * MoveTurtleCommand(String name, List<Turtle> onScreenTurtles, double x, double y)
        * note: this handles fd, bk
    * RotateTurtleCommand(String name, List<Turtle> onScreenTurtles, double rotation, boolean absolute) 
        * lt, rt, seth, towards
    * PenCommand(String name, List<Turtle> onScreenTurtles, Color color, boolean up)
    * VariableCommand(String varName, double varValue)
    * protected double executeCommand()
* protected Executor(ViewUpdater viewUpdater, CommandQueue queue)
* protected TextFieldParser()
    * protected Queue<Command> getCommandQueue()
* protected CommandMaker()
    * protected Queue<Command> parseStringCommands(Queue<String> stringQueue)
    
**Justification**
* Using an interface for linking different kinds of Commands will allow the program to be flexible to adding new commands if desired. This will be based on the Command design pattern. A potential CommandMaker interface will allow different types of commands (i.e. text input vs. slider/button input) to be parsed correctly and go through the flow of the program correctly. Potential inheritance structures could be put in place for Variables or Pens to add new features (e.x. for Pens to make a dashed line rather than a solid line).
* The Turtle/Pen basic classes should be closed for modification, as well as the Executor class and TextFieldparser. Additional functionality will be achieved by creating new implementations of the Command and CommandMaker interfaces, or extending Pen/Turtle classes to new subclasses.
* Errors may be thrown by the parsers if a command is not recognized (but currently the plan is not to throw an error, but to create a Command that creates a popup to notify the user of the mistake). We may also use the Null Object design pattern to allow even commands with no known implementation to avoid breaking the program.

### Backend External
**Methods**
* from Controller
	* void parseInput(String userTextInput) 
* from Turtle
    * void setTurtleImage(ImageView image)
    * void setPenColor(Color color)
* public MakeNewTurtleCommand(String name, ImageView turtleImage, Color penColor, Group trails)

**Justification**
* The ViewUpdater will manifest changes to the model in the UserView by invoking get() methods of Turtle objects related to the Turtle and its corresponding Pen. 
* Details about the TextFieldParser, the CommandMaker, and all Command subclasses are hidden to the ViewUpdater. Turtle objects are the only backend objects which will be accessible to the ViewUpdater. 
* Errors will be made known to users in the case that a user mistypes a command or misformats its arguments. If this is the case, an ErrorComand (subclass of Command) will be created as part of the Queue<Command> output of CommandMaker. Executer will ensure that a String variable is set with the error notification that to be displayed to the user, and ViewUpdater's update method will populate UserView's designated error-notification textfield with the contents of this String variable. 

### Frontend Internal
**Methods**

**Justification**

### Frontend External
**Methods**

**Justification**

## API Example Code

## Design Considerations

## Team Responsibilities

