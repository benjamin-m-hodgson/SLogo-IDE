# SLogo API Peer Review

Susie Choi (sc477) and Sarah Bland (scb49)
Worked with Summer Smith

## Part One

1.  What about your API/design is intended to be flexible?
* flexible for new kinds of command (as long as it can be represented as a string/double, we can add new Command classes to execute it)
* both of our designs are flexible for multiple Turtle objects (always pass in a Turtle to execute methods)
* Summer's design has each Command's execute method go into the array of arguments that the Parser creates to check whether a sufficient # of arguments exists for that Command to execute
    * This challenges our implementation idea of having a numArgsProperties file that must be added to whenever a new Command is added 
    
2.  How is your API/design encapsulating your implementation decisions?
* Details of the Turtle/Pen classes are encapsulated and only available to 
* The parsing process that leads to errors is on the backend, but the Exception-handling that leads to the display of an error message on the UserScreen is handled on the frontend

3.  What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
* characters recognized
* all valid commands
    * valid number of args
* currently throw errors back to front-end, which populates a designated area of the screen with information to user 
    * with complete implementation, potentially will try to "guess" what user meant by a certain mistyped command

4.  Why do you think your API/design is _good_ (also define what your measure of good is)?
* Functionality is well-encapsulated but allows for communication from front-end in the case that extension deems it necessary (through new recognized string commands/buttons)

## Part 2

1.  How do you think Design Patterns are currently represented in the design or could be used to help improve the design?
* MVC: Turtle/Pen as Model, UserSreen as View, Controller as Controller 
* Factory (to generate Commands) 
* Command: encapsulating execute behavior in Command objects, adding them to a Queue
* If we had had ChangeListeners on the UserScreen as opposed to having the UserScreen directly invoke the parseText method, that would have been the Mediator design pattern 

2.  What feature/design problem are you most excited to work on?
* the tree! because it's a data structure in action
3.  What feature/design problem are you most worried about working on?
* the tree! because it's a data structure in action and has lots of potential to be complicated/confusing
* Summer said the stack, which is her data structure for organizing commands 

4.  Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).
1. **The user runs the command hp (hide pen)**: The same flow of text-parsing and Queue-of-Command generation will ensue as in the use case above. In this case, however, the Queue of Commands will only have one command, a PenVisibilityCommand. When the Executer calls the PenVisibilityCommand's `execute()` method, the PenVisibilityCommand will invoke the corresponding Turtle's `hidePen()` method, which wraps a call to the Turtle's Pen's `hidePen()` method. This will flip a "myVisibility" boolean value, which will be checked before adding any additional Lines to the Group that characterizes the Pen/the Turtle's trails. 

2. **The user mistypes the 'fd' command as 'fw,' e.g. 'fw 50'**: The TextFieldParser's `parseText` command will throw an UnidentifiedCommandException. The UserScreen will catch this Exception, and match the key/property in its ErrorMessagesProperties file corresponding to that Exception, and display the String corresponding with that key/property on a part of the UserScreen designated for error notifications. 

3. **The user changes the image of a turtle**: The user selects an image name (i.e. Turtle, Fish, or Balloon) from a dropdown menu on the GUI. The `handleOnButtonPressed` command will call `myController.parseInput(String userInput)` with a String key (something like "changeturtleimage" + turtleName + filepath where turtleName is the name corresponding to this particular Turtle and filepath is the path to the new image file). The controller will pass this string to the TextFieldParser's `parseText(String string)` method, which will see that there is only one line of commands and create a Queue containing only the input String. That string will be parsed by the CommandMaker, which will create a `SetTurtleImageCommand` with an instance variable String corresponding to the filepath to the new image and an instance variable Turtle corresponding to the Turtle whose image needs to be changed.  The Queue of Commands (in this case just the one `SetTurtleImageCommand`) will be returned back to the Controller (most likely as part of a private helper method), which will make an Executor using that Queue of Commands. The Executor will call `.execute()` on the `SetTurtleImageCommand`, and that method will call `setTurtleImage(String filepath)` on the Turtle whose image it is changing. The Executor will return the proper double, which will then be returned by the `parseInput` method to the front end.

4. **The user runs the command XCOR**: The user inputs XCOR to the TextField and hits run, triggering the UserScreen to call `parseInput("XCOR")` on the Controller. The controller calls `parseText("XCOR")`, which sends "XCOR", as it is only one line, to the CommandMaker. The CommandMaker makes a GetXCoordinateCommand and adds it to the command Queue, which is then passed via the controller to the Executor. The Executor calls `.execute` on the single Command in the Queue, which uses the `.getX()` method from the Turtle Class to retrieve and return the necessary information. The Executor returns this value to the Controller, which returns it to the UserScreen to be displayed to the user.

5. **The user changes the background color**: The user selects the color option from a drop down menu in the settings panel of the GUI. The `handleOnButtonPressed` command will call an internal api method along the lines of `turtlePanel.changeBackgroundColor(Paint color)`. The turtle panel will then check the input and attempt to set its background color to that color. If unsuccessful, an error will be thrown and caught by the settings panel. The exception will be handled by removing the triggering option from the dropdown and creating a popup alert to inform the user of what occurred. 
