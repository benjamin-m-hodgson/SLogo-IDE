
# Cell Society API Critique 

Susie Choi, Andrew Arnold, Ben Hodgson, Sarah Bland

## Simulation External API
* all methods in `public class Grid`

## Simulation Internal API
* all methods in `public abstract class Cell`
    * subclasses: GameOfLifeCell, FireCell, RockPaperScissorsCell, WatorCell, SegregationCell
    * should be included, should be public except setMyRow/setMyColumn, which should not be included in the API because there should be no need to move physical Cells in this implementation

## Configuration External API 
* all methods in `public class SplashScreen` 
    * all methods should be included, should be public
* all methods in `public class OptionsScreen`
    * all methods should be included, should be public

## Configuration Internal API
* all methods in `public class RandomizedInitConfig` ?

## Visualization External API
* all methods in `public class Sliders` ?

## Visualization Internal API
* all methods in `public class Graphing`
* all methods in `public class Triangle`
* all methods in `public class RandomizedInitConfig`
* all methods in `public class SimulationView` 
* all methods in `public class XMLSaver` 

## Should not be part of API 
* everything public in Main should not be public 