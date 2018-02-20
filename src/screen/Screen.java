package screen;

import javafx.scene.Parent;

public interface Screen {

    /**
     * If property ROOT is null, calls makeRoot() to generate the root. 
     * 
     * @return ROOT: The Parent node to be used in the Scene object. 
     */
    public Parent getRoot();
    
    /**
     * Creates the root node to be displayed on the Screen
     */
    public abstract void makeRoot();
    
    /**
     * Changes the background color of the screen
     * 
     * @param color: The desired color to change the Background color to
     */
    public abstract void changeBackgroundColor(String color);
    
}
