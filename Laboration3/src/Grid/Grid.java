package Grid;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * 1. Creates an 2D array of GridSquares
 * 2. Inizalises the array with positions and colors for the objects.
 * @author Pontus Eriksson Jirbratt
 */
public class Grid extends Observable
{
   public GridSquares[][] gridList;
   private GridSquares startLocation;
   public GridSquares currentLocation;
   int columns;
   int rows;
   private GridSquares square;

   public Grid(int columns, int rows){
      this.columns = columns;
      this.rows = rows;

      int x = 0;
      int y = 0;

      gridList = new GridSquares[rows][columns];
      for (int i = 0; i < rows; i++){
         for (int j = 0; j < columns; j++){
            gridList[i][j] = new GridSquares(i+x, j+y, Color.BLUE);
            y += 3;
            System.out.println(gridList[i][j].posX);
         }
         y = 0;
         x +=3;
      }
   }

   public boolean placePlayer(GridSquares[] player){
      return true;
   }
   public void firstLocation(GridSquares g) {
      startLocation = g;
      currentLocation = startLocation;
   }
   public void colorChanger(int i)
   {

      switch (i)
      {
         //WHITE
         case 0:

            break;

         //BLUE
         case 1:

            break;

         //CYAN
         case 2:
           // g.color = Color.CYAN;
            break;
      }
   }
}
