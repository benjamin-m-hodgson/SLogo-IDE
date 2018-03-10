# SLOGO README
Susie Choi, Andrew Arnold, Ben Hodgson, Sarah Bland
## Timeline
We began the project with the planning stage on February 15 and began actually coding on February 17. We finished the project on March 9. Estimated hours (per person) spent on the project:

- **Planning/whiteboarding together**: 10 hours
- **Writing code for new features separately**: 25 hours
- **Writing code collaboratively**: 25 hours
- **Debugging**: 15 hours
- **Refactoring**: 10 hours

## Roles
For the first coding sprint, roles were divided as: 
- **Susie** created the CommandTreeBuilder and CommandNode object, TextFieldParser, CommandMaker, RegexMatcher and the encapsulated ExceptionFactory, support for If/IfElse, support for Variables and UserDefinedCommands
- **Sarah** created the CommandTreeReader, the Turtle and Query commands, methods to support calculations in Turtle, and support for loops.
- **Andrew** made the basic info, variable, user-command, history, settings and help panels, along with the turtle panel and error notification in turtle panel.
- **Ben** made the Userscreen, Startscreen and Errorscreen, along with the input panel and CSS reading.

For the second coding sprint, roles were divided as:
- **Susie** implemented display commands (setting/getting attributes such as background, pen color, pen size, turtle shape, user-defined colors), adjusted frontend implementation so that display settings would be parsed as backend Commands, and created the PropertiesReader/PropertiesWriter classes to allow for the definition and usage of new colors by users 
- **Sarah** refactored Turtle class to be an abstract class supporting single and multiple turtles, created the TurtleHolder class to protect the collection of active/all turtles, set up new commands to enable multiple and single turtles, and edited existing commands to support multiple turtles.
- **Andrew** Implemented commands running from the history, command panel and variable panel. Also implemented multiple workspaces and the setting and loading of preferences. Refactored out of controller the property reading and writing methods.
- **Ben** Integrated the pallete of colors and images. Created the GUI for moving the turtle through the front end. Created the GUI for editing pen properties, clicking on turtles, toggling who was active and seeing current state of turtle. 

## Outside Resources
We used StackOverflow (to learn about writing out to Properties files mid-program, etc.) as well as class resources for information about lambdas.
## Files to start project
Driver is the class used to start the project. Running the main from that class will launch the IDE.
## Testing 
The files included in data/examples were used to test the project. No error in inputting commands should cause the program to crash - all errors will be displayed to the user on a designated part of the screen, above the text inpul panel. 

## Files Required
In addition to standard Properties files for the various commands in all languages, Properties files for ColorPalette, ColorPaletteNames, Number of Arguments for Commands, Number of Brackets for Control Flow commands, Settings, all of the various Colors, Error prompts, and Instruction prompts are necessary. Turtle image files are necessary as well as a CSS file for styling of the GUI. Saved UserCommands/Variables/Settings are written into and read from Properties files as well.

## Using the program
The use can interact with the program in two main ways. The first is through the input bar at the bottom of the user screen. This bar allows one to enter in commands and execute them by clicking the "Run" button. The second way to interact with the program is graphically. Users can click on the turtles and manually move and turn them around the screen while changing any of their specific pen or color settings. Finally, users can execute commands previously run from the history panel or run user-defined commands from the command panel. If you run into any trouble, please visit the help panel for a full list of accepted commands.

## Assumptions
It was assumed that concatenated commands (ie fd rt id ) would be executed entirely by one turtle, then the next (i.e. one turtle would rotate its id then move forward that distance, then the next would do so for its id, etc). This also means that for commands like fd random, each turtle will be given a different random distance to go through. It was also assumed that the user would only want to use double IDs for the turtles.

## Bugs/Crashes

The CommandTreeBuilder has a few peculiarities that cause some commands to not have all of their arguments added, likely to the iterative (rather than recursive) nature of tree-building. This is a a bug which could have been more thoroughly fixed with enough time. The prominent bug causing issue related to the nesting/concatenation of multi-argument commands as arguments to two-argument commands. A complete list of all test cases, including reasons why the problematic cases fail, can be found at [this link](https://docs.google.com/spreadsheets/d/13CutPr9Hvu3YDioJ-buQ2fmV1yYrtN6x5PQ93h0GpMQ/edit?usp=sharing). 

## Extra Features
No extra features beyond those mentioned in the specifications were implemented.

## Impressions of the Project
The back-end was definitely a nightmare to complete and debug, but it was immensely satisfying to see actual pictures drawn - having the wealth of predefined test cases was super helpful!

The front end was very satisfying to make. Although the initial design was simple, by the end it was a challenge to keep all of the panels clean and the dependencies clear.
