## Estimation: before looking at the old code:

* How long do you think it will take you to complete this new feature?

1 hour.

*  How many files will you need to add or update? Why?

3 files. One to create the panel, the CSS file to style the panel, and possibly a class to handle loading image options and changing the image. 

## Review: after completing the feature:

* How long did it take you to complete this new feature?

1.5 hours.

* How many files did you need to add or update? Why?

I had to change 3 files. First, I had to change the below method from `protected` to `public` in the `SingleTurtle.java` class:

```java
public ImageView getImageView() {
	return myImage;
}
```

Then the I had to modify the `populateTurtlesList()` method to add images under the turtles names in the `TurtleListPanel.java` class:

```java
Button turtleImage = new Button();
ImageView turtleView = turtle.getImageView();
turtleView.setFitHeight(150);
turtleView.setFitWidth(135);
turtleImage.setGraphic(turtleView);
turtleImage.setOnMouseClicked((arg1)-> {
    getPane()
    .setRight(new TurtleImagePanel(PANE, USER_SCREEN, FILE_READER, 
		turtle).getPanel());
});
```
Now, clicking the _Turtles_ button in the _Settings_ panel displays a list of turtle names with their images under them for all of the turtles. When this image is clicked, the `TurtleImagePanel.java` panel is displayed. This new class shows all of the possible images for a turtle. If an image is pressed, just that _Turtle_ image that was clicked will be updated. 

* Did you get it completely right on the first try?

No, at first I didn't know how to show the change graphically on the screen. I was able to change the _SingleTurtle_ object's image, but this wasn't being communicated to the screen. Tracing through my old code, I was able to find the methods in the **FileReader** and the **UserScreen** that used back end commands to communicate this information:

```java
// handle click event
turtleButton.setOnMouseClicked((arg0)-> {
	TURTLE.setImage(turtleView); 
	FILE_READER.parseSettingInput(FILE_READER.resourceSettingsText("defaultShapeCommand")
	    +" "+entry.getKey());
	USER_SCREEN.updateCurrentState("turtleImage", entry.getKey());
});
```

## Analysis: what do you feel this exercise reveals about your project's design and documentation?

* Was it as good (or bad) as you remembered?

The design seemed as good and as bad as I remembered. At first it was frustrating because I wasn't sure how to communicate the image change to the screen, but once I found the method, called in the above code snippet, in the **UserScreen** it was fairly straightforward. 

* What could be improved?

The documentation on the methods in the **UserScreen** could be improved.

* What would it have been like if you were not familiar with the code at all?

If I was not familiar with the code at all it would have been confusing to understand what some of the methods in the **UserScreen** do. This would have made the solution to this extension much more complicated and could have led to code with duplicated functionality. 