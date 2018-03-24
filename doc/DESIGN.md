# Slogo Design

Susie Choi, Andrew Arnold, Ben Hodgson, Sarah Bland

## High-level Design Goals

Overall, we decided to make our main design goal limiting dependencies between the frontend and backend such that one could be swapped out without directly affecting the other.


Besides this, our main specific design goals were to protect against direct access to turtles by the front end (and hide implementation like buttons from the back end) and to make our design flexible enough to easily deal with multiple turtles. We also wanted to make adding multiple languages as easy and immersive as possible. 

## Adding new features
- to add a **new basic command**:
	- must add to each of the Properties files in the languages folder (i.e. add `LogTen = log10||lg10 ` to English.properties)
	- add to NumArgsForCommands properties file with number of arguments command takes (i.e. add `LogTen = 1` )
	- add an if statement to the CommandFactory ( `else if commandName.equals("LogTen")) { return new LogTenCommand ( commandArgs.get(0), myVariables, activeTurtles);}`
	- create a Command subclass that implements the given operation
~~~~
	class LogTenCommand extends Command		{
private Command argCommand;
private Map<String, Double> myVariables;
protected PowerCommand(Command arg, Map<String, Double> variables, Turtle turtles) {
argCommand = arg;
myVariables = variables;
setActiveTurtles(turtles);
}

@Override
protected double execute() throws UnidentifiedCommandException {
double ARGUMENT = getCommandValue(argCommand, myVariables, getActiveTurtles().toSingleTurtle());
getActiveTurtles().executeSequentially(myTurtle -\> {
try {
getCommandValue(argCommand, myVariables, myTurtle);
}
catch(UnidentifiedCommandException e) {
throw new UnidentifiedCommandError("Improper # arguments");
}
});
return Math.log10(ARGUMENT);
}

}


~~~~
- special cases of adding commands:
	- if command takes no arguments, add to list of `DEFAULT_DOUBLE_SUBSTITUTES` in CommandTreeBuilder
	- if command requires brackets, add to NumBracketsControlFlow how many total of one kind of bracket (open or close) entire syntax includes
		- unfortunately, in our current implementation, this kind of command must be parsed separately in the CommandTreeBuilder
- to add a new **component to front end**
	- add the JavaFx object (Button, Pane, etc.) to the proper Panel
	- add the styling for the object to the CSS file
	- set up on a particular action to either ask for information from the backend (via the methods discussed above) or to send a String command through the parser
- to implement **specifications not previously completed**
	- clicking to activate/deactivate turtles would only require the front-end to retrieve the immutable list of active turtles and issue a Tell command containing all of the existing active turtles' ids and either adding or removing the id of the turtle whose status is being toggled
	- adding the additional language (Urdu) would simply be a matter of adding the Urdu.properties file to the languages folder - no coding required! Our group did not see the post on Piazza so we did not add this very simple feature.
	- implementing unlimited parameters would also not be conceptually difficult but would just require some additional parsing work (similar to the control flow parsing): in CommandMaker, a Parenthesis command would need to be created and inserted before the first instruction (replacing the open parentheses), the number of arguments the first command in the parentheses takes (retrieved from the NumArgsForCommands file) would need to be inserted next, and all of the info in the parentheses would need to be bracketed.
		- in our current implementation, in CommandTreeBuilder a separate parsing method would be necessary
		- within the Parenthesis command, the initial command would be inserted at indices spread apart by NumArgs, and from there a CommandTree could be built and executed
			- since error-checking for number of arguments already occurs in CommandTreeReader, this would be taken care of


## Major Design Choices
1. One major design decision we spent many hours discussing was where in the hierarchy to place the list of variables and how to handle changing them. We initially wanted to house them only in the TextFieldParser and replace them with values before they even came to the CommandTreeBuilder. The advantage of this was that it was simplest to understand and avoided having variables as global or pseudo-global variables. However, this implementation failed in the case that a variable was made and then used within the same run, so we decided to go with a design that waited until the tree was executed to find and replace the variables. However, because of our linear design, this meant that we had to keep a single map of variables in the CommandTreeBuilder and pass it all the way down the hierarchy. One option was to keep the list in the CommandFactory, but we could not implement that because Commands, being below CommandFactory in the hierarchy, could not access the CommandFactory, so a separate parsing would need to be done in the CommandFactory, which would violate the Single Purpose Principle. I still prefer this method, however, because passing the variable maps (as well as many other parameters - at some points we were passing 6 or 7 parameters to every constructor) for the sole purpose of passing them down obscures dependencies and results in duplicated code.
2. Another design decision we struggled with was the decision of how to execute commands in their proper order. Initially, we had in our design an "Executor" class that would take a Queue of concrete Commands, then execute them in the proper order. The advantage of this was to separate the creation of the Commands from their execution and to render more flexiblity - we envisioned that the Executor would be able to handle control flow and loops by simply executing multiple times or deciding whether or not to execute. This would have helped reduce bloating in the (currently massive) CommandTreeBuilder class. However, the disadvantage would be that there would be one more level in the downwards hierarchy that would need to be traversed with any kind of information or design change. By reading, compressing, and executing the CommandTree in the same class (CommandTreeReader), the separation between the top and bottom of the hierarchy is reduced, even though flexibility is lost because the tree must be built completely and executed completely (no stopping at any point to check for variables, or control flow, or any number of things). Ultimately, I agree with our decision because I have seen the negative ramifications of our linear design and think mitigating it was a good idea. Additionally, because we were able to implement the lion's share of the control flow functionality through standard Commands (and could have done even more with more refactoring time), that negative was mitigated.
3. We considered having abstract classes for “regular” Commands and query commands. We decided not to do this because, in CommandTreeBuilder, it would be necessary to write an “if” statement to check which type of Command something is before continuing in the tree-building process (i.e. adding children/arguments to that CommandNode). However, query/0-argument commands have access to, but do not need, the `getValueOfVar` method in the Command abstract superclass. Alternatively, we could have used one Command interface with a required `execute` method, and subclasses implementing that interface such as ArgCommand and NoArgCommand. 


## Assumptions/Decisions
It was assumed that the Tell[] command would only make a turtle for each number inside the brackets (i.e. tell [ 200 ] makes one turtle rather than 200). It was also assumed that concatenated commands affecting turtles are completed in their entirety for each turtle (i.e. fd random will make turtles move different distances).