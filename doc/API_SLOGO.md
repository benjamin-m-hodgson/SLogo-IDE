# API SLOGO

Susie Choi, Andrew Arnold, Ben Hodgson, Sarah Bland

## Architecture Design 

1.  When does parsing need to take place and what does it need to start properly?
* Parsing takes place after the model (mediator between the FE and BE) identifies that user input has been entered. It will then "get" that input and pass it to the Parsing class. 

2.  What is the result of parsing and who receives it?
* The parser will create a command object in the backend which will execute the inputed command.

3.  When are errors detected and how are they reported?
* The parser will detect errors after they are passed to it from the model which is the external backend API. Upon detection the parser will create a error command object which will, through the API in model2(to be renamed) to the front end.

4.  What do commands know, when do they know it, and how do they get it?
* Commands know the amount that the user wants to change by (e.g. rotation, distance movement), and the current state (e.g. position, x/y positions) of the turtle. 
5.  How is the GUI updated after a command has completed execution?
* Model2 class (to be renamed later) runs a series of updating methods (like draw() to draw a line, updateTurtleLocation(), updateTurtleImage()) in the Front-end based on the newly updated back-end information.
![](https://i.imgur.com/YLJSSPt.jpg)




Represents our current ideas about the overall design architecture.

## Use Cases

1. The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
* Model listens for this user input, then sends the strings passed in to the Parsing class. The parser creates a Command that changes the Turtle's properties (the x and y position). When Model2 (to be renamed later) calls its update methods, it updates the front-end visualization based on the now-updated information from the backend (the new location of the turtle) and executes its draw() function to create the line between where the turtle was to where the turle is now.
2. The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
* The "Model" identifies that user input has been received, gets the input, and passes it to the Command Parser. The Command Parser identifies that the command is invalid, and creates an instantiation of the Exception superclass, which it gives to Model 2 (to be renamed) to display on a specified TextView on the front end.  
3. The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).
* The "Model" identifies that user input has been recieved, and creates a Command Queue. The user input is then passed to a "Text Field Parser" which uses "Command Maker" to add the commands into the Command Queue. The Queue then gets called to execute all the Commands it contains.  The Commands execute, using model 2(to be renamed) to update the front end. The final command executed by the Command Queue is a defualt in all Command Queues and uses the Model 2(to be renamed) API to let the front end know that Model is availible to take new input.
4. The user changes the color of the environment's background.
* This will be implemented entirely in the Visualization portion of the program.  Pressing the button will trigger the ImageView stored as a root in one of the visualization classes to be changed to one with the correct color.