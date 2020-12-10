package Grid;

import java.awt.*;
/**
 * This is a square object that will be put on the grid.
 * The square has a location, posX, posY as well as a sixe dx, dy and a color.
 * @author Pontus Eriksson Jirbratt
 */
public class GridSquares
{
   public Color color;
   public int posX;
   public int posY;
   public int indexX;
   public int indexY;
   public int dx = 3;
   public int dy = 3;


   public GridSquares(int posX, int posY, Color color, int indexX, int indexY){
      this.posX = posX;
      this.posY = posY;
      this.color = color;
      this.indexX = indexX;
      this.indexY = indexY;

   }
}
