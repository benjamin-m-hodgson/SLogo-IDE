CompSci 308: Cell Society Analysis  
===================  
  

API Review  
=======  

by: ASA48, DPB20, AS734, BMH43, YM101  
  

### Part 1  

* What about your API/design is intended to be flexible?  
    * We attempted to design the external API to limit the number/complexity of available public methods. This design attempts to make the API easy to use and understand by other members of the project working on separate functionality issues.  
* How is your API/design encapsulating your implementation decisions?  
    * Interfaces and abstract classes to standardize methods but leave implementation flexible. 
* What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)? 
    * If there is an error related to reading a file upon program initialization an error screen will be displayed containing some text describing the error that forces the user to quit the program. If an error from an invalid command is thrown a small error alert will appear in the bottom corner of the turtle panel. 
* Why do you think your API/design is good (also define what your measure of good is)? 
    * Internally we have a fleshed out idea how the classes and various user interface displays will interface with each other. We envision using JavaFX controls (i.e. buttons, text fields, comboboxes) to determine the flow of program method/class execution. At this point we define good by our level of certainty. We feel fairly certain how the internal components will work together and have a good idea how these will be implemented. 
  
### Part 2

*  How do you think Design Patterns are currently represented in the design or could be used to help improve the design?
    *  A design pattern that is currently represented in the design is the Mediator pattern. The controller class will be a concrete mediator which will help centralize the control flow of the program. Our design is similar to the model view controller pattern. 
*  What feature/design problem are you most excited to work on?
    * We are most excited to work on adding support for multiple languages. This includes creating and reading from different .properties files to change the language of the text displayed.  
*  What feature/design problem are you most worried about working on?
    * Also worried about implementing the language support. The largest challenge is determining what default language to display before the user selects a language from the drop down menu. It also seems challenging to populate the drop down menu with valid language options from the present .property files. 
*  Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).
    1. Change background color: user clicks a settings button to change some visualization settings. This button brings up a new panel on the screen with various settings, one of which is a drop down menu with various color options. If the user selects a valid color option, an apply button becomes active. If the user clicks this button the new background color is applied.
    2. Change language: User clicks the settings button in the info panel. The panel becomes the settings panel. The new language is selected from a drop down menu and the language of the GUI is changed.
    3. Change turtle image: user clicks a settings button to change some visualization settings. This button brings up a new panel on the screen with various settings, one of which is a drop down menu with various turtle images. If the user selects a valid image, an apply button becomes active. If the user clicks this button the new turtle image is applied. The image is stored in the actual turtle object and this object will have a public method to set a new image.
    4. Enter a command: User types in a command to the command line. When the run button is hit the command is executed if valid and a warning shown if it is not.
    5. Change pen color: User clicks the settings button in the info panel. The settings panel replaces the info panel and the desired pen color is selected from a drop down menu. The pen color is then changed.