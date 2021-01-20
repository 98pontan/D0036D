import Grid.GUI;
import Grid.Grid;
import Grid.Server;
import java.awt.*;
import java.io.IOException;

/**
 * @author Pontus Eriksson Jirbratt
 */
public class Controller
{
   public Controller() throws IOException
   {

      Grid grid = new Grid(201, 201, Color.WHITE, Color.ORANGE);
      new GUI(grid);
      new Server(grid);
      //grid.firstLocation(grid.playerStartPosition(8, 8, Color.BLACK));

   }





}
