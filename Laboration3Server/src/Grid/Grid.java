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
   boolean messagePlayerPlaced = false;
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
            gridList[i][j] = new GridSquares(i + x, j + y, boardColor);
            y += 3;

         }
         y = 0;
         x += 3;
      }
   }

   public boolean placePlayer(int i)
   {
      final int CENTERPOSITIONINDEX = 0;

      switch (i) {
         //Right
         case 0:
            if (currentLocation[CENTERPOSITIONINDEX].posX + 3 == gridList.length)
               return false;
            break;

         //Down
         case 1:
            if (currentLocation[CENTERPOSITIONINDEX].posY + 3 == gridList.length)
               return false;
            break;

         //Left
         case 2:
            if (currentLocation[CENTERPOSITIONINDEX].posX - 2 == 0)
               return false;
            break;

         //Up
         case 3:
            if (currentLocation[CENTERPOSITIONINDEX].posY - 2 == 0)
               return false;
            break;
      }
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
      final int CENTERPOSITIONINDEX = 0;

      placed = true;
      for (int k = 1; k < currentLocation.length; k++){
         gridList[currentLocation[k].posX][currentLocation[k].posY].color = boardColor;
      }
      System.out.println("HEJ");
      switch (i) {
         //Right
         case 0:
            currentLocation = playerStartPosition(currentLocation[CENTERPOSITIONINDEX].posX + 1, currentLocation[CENTERPOSITIONINDEX].posY);
            setChanged();
            notifyObservers();
            break;

         //Down
         case 1:
            currentLocation = playerStartPosition(currentLocation[CENTERPOSITIONINDEX].posX, currentLocation[CENTERPOSITIONINDEX].posY + 1);
            setChanged();
            notifyObservers();
            break;

         //Left
         case 2:
            currentLocation = playerStartPosition(currentLocation[CENTERPOSITIONINDEX].posX - 1, currentLocation[CENTERPOSITIONINDEX].posY);
            setChanged();
            notifyObservers();
            break;

         //Up
         case 3:
            currentLocation = playerStartPosition(currentLocation[CENTERPOSITIONINDEX].posX, currentLocation[CENTERPOSITIONINDEX].posY - 1);
            setChanged();
            notifyObservers();
            break;
      }
   }
   public void placePlayerCommunication(int x, int y, Color c)
   {
      if (messagePlayerPlaced) {
         System.out.println("clean board");
         for (int k = 1; k < currentLocation.length; k++) {

            gridList[currentLocation[k].posX][currentLocation[k].posY].color = boardColor;
         }
      }
      System.out.println("placePlayerCom");
      playerColor = c;
      firstLocation(playerStartPosition(x, y));
      messagePlayerPlaced = true;
   }
   /**
    * Sets up the start position for the player
    * @param middlePositionX
    * @param middlePositionY

    * @return
    */

   public GridSquares[] playerStartPosition(int middlePositionX, int middlePositionY)
   {
      GridSquares[] gridSquares;
      gridSquares = new GridSquares[9];

      for (int i = 0; i < gridSquares.length; i++) {
         switch (i) {
            case 0:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY, playerColor);
               break;

            case 1:
               gridSquares[i] = new GridSquares(middlePositionX - 1, middlePositionY, playerColor);
               break;

            case 2:
               gridSquares[i] = new GridSquares(middlePositionX - 2, middlePositionY, playerColor);
               break;

            case 3:
               gridSquares[i] = new GridSquares(middlePositionX + 1, middlePositionY, playerColor);
               break;

            case 4:
               gridSquares[i] = new GridSquares(middlePositionX + 2, middlePositionY, playerColor);
               break;

            case 5:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY - 1, playerColor);
               break;

            case 6:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY - 2, playerColor);
               break;

            case 7:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY + 1, playerColor);
               break;

            case 8:
               gridSquares[i] = new GridSquares(middlePositionX, middlePositionY + 2, playerColor);
               break;
         }
      }
      return gridSquares;
   }
}
