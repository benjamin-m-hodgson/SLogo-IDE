# API Changes

## Backend External

### Adjustments
* Map<String, String> getUserDefined() - Changed return value from List<String> to Map<String, String> to account for the names (keys) assigned to the command sequences (values) 

### Additions
* void loadSavedUserDefined() - Added to accomodate the new (2nd sprint) feature of saving user-defined commands via front-end mouse input
* void loadSavedVariables() - Added to accomodate the new (2nd sprint) feature of saving user-defined variables via front-end mouse input
* List<SingleTurtle> getAllTurtles() 
* List<SingleTurtle> getActiveTurtles()
* ImageView getTurtleWithIDImageView(double ID)
* Group getTurtleWithIDPenLines(double ID)
* void loadStartScreen()
* void loadUserScreen()
* void loadErrorScreen(String errorMessage)

## Frontend External 

### Adjustments
* void changeBackgroundColorHex(String hex) - Changed from 0 parameters to 1 parameter to account for the possible range of background colors (specified by hex) for the front-end user view

### Additions
* void displayErrorMessage(String errorMessage) - Used to facilitate the back-to-front-end communication of parsing issues, and thereby trigger the display of error messages on a designated part of the user screen
* In the added `FileIO.java` class:
  * public void saveUserDefined(Controller control) - Used to save user defined commands
  * public void saveVariables() - Used to save user defined variables
  * public Map<String, String> getColors() - Used to determine colors and their indices from program files
  * public Map<String, String> getShapes() - Used to determine turlte shapes from program files
  * public Map<String, String> getMapFromProperties(String filePath) 
  * public List<String> getLanguages()
  * public List<String> getFileNames(String folderName)
  * public String resourceDisplayText(String key) - Finds text to display to the user on the display from the language.properties files
  * public String palleteColorText(String key) - Finds palette information to display to the user
  * public String resourceErrorText(String key) - Decides which error messages to display
  * public String resourceSettingsText(String key) - Used for setting default program settings