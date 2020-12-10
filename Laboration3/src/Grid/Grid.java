package Grid;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * 1. Creates an 2D array of GridSquares
 * 2. Inizalises the array with positions and colors for the objects.
 *
 * @author Pontus Eriksson Jirbratt
 */
public class Grid extends Observable
{
   public Color playerColor;
   public Color boardColor;
   public GridSquares[][] gridList;
   private GridSquares[] startLocation;
   public GridSquares[] currentLocation;
   int columns;
   int rows;
   boolean placed;
   private GridSquares square;

   public Grid(int columns, int rows, Color boardColor, Color playerColor)
   {
      this.playerColor = playerColor;
      this.columns = columns;
      this.rows = rows;
      this.boardColor = boardColor;

      int x = 0;
      int y = 0;

      gridList = new GridSquares[rows][columns];
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < columns; j++) {
            gridList[i][j] = new GridSquares(i + x, j + y, boardColor, i, j);
            y += 3;
            // System.out.println(x);
            // System.out.println(gridList[i][j].posX);
         }
         y = 0;
         x += 3;
      }
   }

   public boolean placePlayer(GridSquares[] player)
   {
      return true;
   }

   /**
    * sets the first position for the player and saves the first position
    * @param gridSquares a list of gridsquares contains the coordinates for the first position
    */
   public void firstLocation(GridSquares[] gridSquares)
   {
      startLocation = gridSquares;
      currentLocation = startLocation;
      placed = true;
      setChanged();
      notifyObservers();
   }

   public void movePlayer(int i)
   {
      int centerpositionIndex = 0;
      placed = true;
      for (int k = 1; k < currentLocation.length; k++){
         gridList[currentLocation[k].posX][currentLocation[k].posY].color = boardColor;
      }

      switch (i) {
         //Right
         case 0:
            currentLocation = playerStartPosition(currentLocation[centerpositionIndex].posX + 1, currentLocation[centerpositionIndex].posY, playerColor);
            setChanged();
            notifyObservers();
            break;

         //Down
         case 1:
            currentLocation = playerStartPosition(currentLocation[centerpositionIndex].posX, currentLocation[centerpositionIndex].posY + 1, playerColor);
            setChanged();
            notifyObservers();
            break;

         //Left
         case 2:
            currentLocation = playerStartPosition(currentLocation[centerpositionIndex].posX - 1, currentLocation[centerpositionIndex].posY, playerColor);
            setChanged();
            notifyObservers();
            break;

         //Up
         case 3:
            currentLocation = playerStartPosition(currentLocation[centerpositionIndex].posX, currentLocation[centerpositionIndex].posY - 1, playerColor);
            setChanged();
            notifyObservers();
            break;
      }
   }

   /**
    * Sets up the start position for the player
    * end with i and i is temporary
    * @param middlePositionX
    * @param middlePositionY
    * @param playerColor
    * @return
    */

   public GridSquares[] playerStartPosition(int middlePositionX, int middlePositionY, Color playerColor)
   {
      GridSquares[] gridSquares;
      gridSquares = new GridSquares[9];

      for (int i = 0; i < gridSquares.length; i++) {
         switch (i) {
            case 0:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY, playerColor, i, i);
               break;

            case 1:
               gridSquares[i] = new GridSquares(middlePositionX - 1, middlePositionY, playerColor, i, i);
               break;

            case 2:
               gridSquares[i] = new GridSquares(middlePositionX - 2, middlePositionY, playerColor, i, i);
               break;

            case 3:
               gridSquares[i] = new GridSquares(middlePositionX + 1, middlePositionY, playerColor, i, i);
               break;

            case 4:
               gridSquares[i] = new GridSquares(middlePositionX + 2, middlePositionY, playerColor, i, i);
               break;

            case 5:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY - 1, playerColor, i, i);
               break;

            case 6:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY - 2, playerColor, i, i);
               break;

            case 7:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY + 1, playerColor, i, i);
               break;

            case 8:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY + 2, playerColor, i, i);
               break;
         }
      }
      return gridSquares;
   }
}
