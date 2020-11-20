import Grid.Grid;
import Grid.GridSquares;
import Grid.GUI;
/**
 * @author Pontus Eriksson Jirbratt
 */
public class Controller
{
   public Controller(){
      Grid grid = new Grid(201, 201);
      new GUI(grid);
      //grid.firstLocation();
   }
}
