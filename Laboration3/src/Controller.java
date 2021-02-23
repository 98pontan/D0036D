import Grid.Grid;
import Grid.GUI;
import Grid.Client;

import java.awt.*;
import java.io.IOException;

/**
 * @author Pontus Eriksson Jirbratt
 */
public class Controller
{
   public Controller() throws IOException
   {
      Grid grid = new Grid(201, 201, Color.WHITE, Color.BLACK);
      new GUI(grid);
      new Client("8", "8", "1");
      grid.firstLocation(grid.playerStartPosition(8, 8, Color.BLACK));
   }
}
