import Grid.Grid;
import Grid.GridSquares;
import Grid.GUI;

import java.awt.*;

/**
 * @author Pontus Eriksson Jirbratt
 */
public class Controller
{
   public Controller(){
      Grid grid = new Grid(201, 201);
      new GUI(grid);
      grid.firstLocation(playerStartPosition(8, 8, Color.BLACK));
   }

   public GridSquares[] playerStartPosition(int middlePositionX, int middlePositionY, Color playerColor)
   {
      GridSquares[] gridSquares;
      gridSquares = new GridSquares[9];


      for (int i = 0; i < gridSquares.length; i++)
      {
         switch (i){
            case 0:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY, playerColor);

               break;

            case 1:
               gridSquares[i] = new GridSquares(middlePositionX-4, middlePositionY, playerColor);
               break;
            case 2:
               gridSquares[i] = new GridSquares(middlePositionX-8, middlePositionY, playerColor);
               break;
            case 3:
               gridSquares[i] = new GridSquares(middlePositionX+4, middlePositionY, playerColor);
                  break;
            case 4:
               gridSquares[i] = new GridSquares(middlePositionX+8, middlePositionY, playerColor);
               break;

            case 5:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY-4, playerColor);
               break;
            case 6:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY-8, playerColor);
               break;
            case 7:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY+4, playerColor);
               break;
            case 8:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY+8, playerColor);
               break;
         }
      }
      return gridSquares;
   }

}
