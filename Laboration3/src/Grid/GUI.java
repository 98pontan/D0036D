package Grid;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Pontus Eriksson Jirbratt
 */
public class GUI implements Observer
{
   private Grid gr;
   private Display d;
   int displaySizeX = 900;
   int displaySizeY = 700;
   GridSquares[][] gridSquares;
   Draw gs[][];

   /**
    * Takes in a grid of gridSquares
    * Inisilazes a 2D array with the same size as the grid array from Grid.
    * For each Grid element in the array a Draw object is created.
    * @param grid
    */
   public GUI(Grid grid)
   {
      this.gr = grid;
      gs = new Draw[gr.rows][gr.columns];

      //loops trough all the elements in the array
      for (int i = 0; i < gr.rows; i++) {
         for (int j = 0; j < gr.columns; j++) {
            //  System.out.println(i*j);
            gs[i][j] = new Draw(gr.gridList[i][j]);
         }
      }

      //System.out.println(Arrays.toString(gs));
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      d = new Display(displaySizeX, displaySizeY);

      frame.getContentPane().add(d);
      frame.pack();
      frame.setLocation(0, 0);
      frame.setVisible(true);

      gr.addObserver(this);
   }

   @Override
   public void update(Observable o, Object arg)
   {
      d.repaint();
   }

   private class Display extends JPanel
   {

      public Display(int x, int y)
      {

         setPreferredSize(new Dimension(20+x, 20+y));
         setFocusable(true);
      }

      @Override
      public void paintComponent(Graphics g)
      {
         super.paintComponents(g);
         for (int i = 0; i < gr.rows; i++) {
            for (int j = 0; j < gr.columns; j++) {
               //  System.out.println(i*j);
               gs[i][j].drawSquares(g);
            }
         }
         g.setColor(Color.GREEN);

      //   gr.currentLocation[0].color = Color.GREEN;


      }
   }

   public class Draw
   {
      private GridSquares gridSquares;

      public Draw(GridSquares gs)
      {
         gridSquares = gs;
      }

      void drawSquares(Graphics g)
      {
         //System.out.println(gridSquares.posX);
         g.setColor(gridSquares.color);
         g.fillRect(gridSquares.posX, gridSquares.posY, gridSquares.dx, gridSquares.dy);
      }
   }

}