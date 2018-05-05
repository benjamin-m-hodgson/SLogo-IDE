# Slogo Addition
Sarah Bland, NetID: scb49

## Estimation

I believe it will take around two hours to complete this new feature.
I should need to update five or six classes. I will definitely need to make a new Command class, and I may need to add a new set of turtles that get passed around among the parsers/backend classes (the new set of "turtles" will be stand-ins for the stamps). I may also need to adjust one class in the Front-end in order to make sure the stamps get added to the Scene properly after the  Command is parsed (this is what we did for new turtles, and I believe a similar approach will work).

## Review
It ended up taking me around 2 hours and 15 minutes to complete the feature, from initial commit to end commit (and I took a few breaks), so time-wise my prediction was accurate. I needed to change 13 files (significantly more than I initially estimated), for the following reasons:
- Languages properties file - to add the new command to the recognized command
- NumArgsForCommands properties file - to allow the parser to know that the commands took 0 arguments
- Created a new StampCommand class to fulfill both commands' functionality
- PropertiesFactory - adding "if" statements for the new command types (since we did not use reflection)
- Turtle abstract class - needed new stamping functionality (getStamps, stamp, and removeStamps)
	- MultipleTurtles for the same reason
		- TurtleHolder for the same reason
	- SingleTurtle for the same reason
- CommandTreeBuilder - needed to add the command Strings to DEFAULT_DOUBLE_SUBSTITUTES (see analysis)
- CommandMaker - needed to add getStamps() method to send to frontend
	- TextFieldParser for the same reason (wraps CommandMaker method)
	- Controller for the same reason (wraps CommandMaker method)
- UserScreen - needed to add call to attach stamps to screen/instance variable to keep track of current stamps on screen
- TurtlePanel - needed to add functionality to add stamps to screen

I did not get the code exactly right on the first try - I would say that I spent about 30 minutes bug fixing. Most of these bugs were silly errors (like stamping all turtles instead of active turtles), but some stemmed from confusion/lack of documentation in the frontend classes (specifically, I didn't know what `setUpImageView()` in TurtlePanel did, so I did not initially know that it was necessary and that it hard-coded in the default turtle image.

## Analysis
This exercise actually revealed that our design/documentation were not as difficult to work with as I thought coming out of the project (though perhaps I just remember the code better than I thought I did). I only had to add a couple of methods to only two classes in the frontend, which I think demonstrated how the encapsulation of the parsing made the design very flexible (I didn't have to change much in the frontend to get a new backend feature). Additionally, another good thing I noticed was that the refactoring I did towards the end of the project to create the TurtleHolder object really did make the design more flexible - instead of creating a whole new list of turtles and having to pass it around throughout the entire backend, I was able to just embed the new functionality within the TurtleHolder, which saved me a lot of trouble in trying to touch pretty much every method in the backend.

However, the exercise definitely also reinforced that we had room for improvement. The number of files I had to change reveals a lot of dependencies in our code that could have been avoided or simplified by better using the Command design pattern (treating, for example, TextFieldParser as an action encapsulated in a class (parsing the text) rather than overflowing it with getters/setters). Additionally, where to look in the code to make these changes was not intuitive at all - I couldn't remember where in the hierarchy of our parsing the actual activeTurtles object was held, so it was difficult to pinpoint where to make my changes. Similarly, it was unclear in which Screen class the actual checking for new turtles occurred (initially, I assumed it would be in the TurtlePanel, and later found it was in UserScreen). These difficulties would definitely hinder someone who had never looked at our code before in implementing a new feature. Finally, the biggest flaw I saw was the fact that we had a hard-coded list of "default double substitutes" at the top of our CommandTreeBuilder class. Because I was familiar with the code, I knew to look for it, but someone who had never worked on the project before would have absolutely no way to know what a "double substitute" was or that they needed to add it to that list. Documentation certainly could have helped with this, or we could have eliminated the list altogether by searching in the NumArgs properties file for 0s (adding to the NumArgs file seems much more intuitive to me than adding to the top of a random, unrelated class).