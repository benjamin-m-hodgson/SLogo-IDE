# Design Document

## Introduction

## Design Overview

## User Interface

## API Details 

### Backend Internal
**Methods**
* protected Turtle(String name, ImageView image, Group trails) 
    * void setTurtleImage(ImageView image)
    * void hideTurtle()
    * void showTurtle()
    * void setPenColor(Color color) 
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
* public MakeNewTurtleCommand(String name, ImageView turtleImage, Color penColor, Group trails)

**Justification**
* The UserScreen will call the Controller's parseInput method when userInput has been received (i.e. upon user "return"ing text input or clicking a button), thus triggering the Controller's command execution process. 
* The only Command that will be accessible to the UserScreen is the MakeNewTurtleCommand. This is because the UserScreen will add each new Turtle's ImageView and Group of pen lines to its root. This is important because it eliminates the need for the UserScreen to have to "ask" the Controller what updates have been made to the Model. Changes to the back-end Turtle objects will be changes to the same objects that are attached to the UserScreen's root upon Turtle creation. 
* The TextFieldParser will throw Exceptions in the case of ill-formatted user commands or arguments. The UserScreen will catch these Exceptions, and display an error-specific notification message to the user on a designated part of the user screen.

### Frontend Internal
**Methods**

**Justification**

### Frontend External
**Methods**

**Justification**

## API Example Code

## Design Considerations

## Team Responsibilities

