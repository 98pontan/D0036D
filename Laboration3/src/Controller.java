import Grid.Grid;
import Grid.GUI;

import java.awt.*;

/**
 * @author Pontus Eriksson Jirbratt
 */
public class Controller
{
   public Controller()
   {
      Grid grid = new Grid(201, 201, Color.WHITE, Color.BLACK);
      new GUI(grid);
      grid.firstLocation(grid.playerStartPosition(8, 8, Color.BLACK));
   }





}
