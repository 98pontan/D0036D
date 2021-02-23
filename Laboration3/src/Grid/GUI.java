package Grid;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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
   int displaySizeY = 900;
   Draw[][] gs;

   /**
    * Takes in a grid of gridSquares
    * Inisilazes a 2D array with the same size as the grid array from Grid.
    * For each Grid element in the array a Draw object is created.
    *
    * @param grid
    */
   public GUI(Grid grid)
   {
      this.gr = grid;
      gs = new Draw[gr.rows][gr.columns];

      //loops trough all the elements in the array and creates a Draw object for each square
      for (int i = 0; i < gr.rows; i++) {
         for (int j = 0; j < gr.columns; j++) {
            gs[i][j] = new Draw(gr.gridList[i][j]);
         }
      }

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
         addKeyListener(new Listener());
         setPreferredSize(new Dimension(20 + x, 20 + y));
         setFocusable(true);
         //JPanel panel = new JPanel();


      }

      @Override
      public void paintComponent(Graphics g)
      {
         super.paintComponents(g);

         for (int i = 0; i < gr.rows; i++) {
            for (int j = 0; j < gr.columns; j++) {
               //  System.out.println(i*j);
               if (gr.placed) {
                  for (int k = 0; k < gr.currentLocation.length; k++) {
                     gs[gr.currentLocation[k].posX][gr.currentLocation[k].posY].gridSquares.color = gr.playerColor;
                  }
                  gr.placed = false;
               }
               gs[i][j].drawSquares(g);
            }// end for j
         }// end for i
      } // end paintComponent

      private class Listener implements KeyListener
      {
         public void keyPressed(KeyEvent arg0)
         {
            int key = arg0.getKeyCode();

            //Right
            if (key == KeyEvent.VK_RIGHT && gr.placePlayer(0)) {
               try {
                  gr.movePlayer(0);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            //Down
            if (key == KeyEvent.VK_DOWN && gr.placePlayer(1)) {
               try {
                  gr.movePlayer(1);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            //Left
            if (key == KeyEvent.VK_LEFT && gr.placePlayer(2)) {
               try {
                  gr.movePlayer(2);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

            //Up
            if (key == KeyEvent.VK_UP && gr.placePlayer(3)) {
               try {
                  gr.movePlayer(3);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }

         @Override
         public void keyTyped(KeyEvent e)
         {

         }

         @Override
         public void keyReleased(KeyEvent e)
         {

         }
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
         g.setColor(gridSquares.color);
         g.fillRect(gridSquares.posX, gridSquares.posY, gridSquares.dx, gridSquares.dy);
      }
   }

}