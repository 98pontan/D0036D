package Grid;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.*;


public class Server
{
   

   public Server(Grid grid) throws IOException
   {
      DatagramSocket ds;
      byte[] buf;
      DatagramPacket dp;
      String recivedMessage;
      Boolean reciving;

      reciving = true;
      ds = new DatagramSocket(3000);
      buf = new byte[1024];

      while (reciving){
         dp = new DatagramPacket(buf, 1024);
         try {
            ds.receive(dp);
            recivedMessage = new String(dp.getData(), 0, dp.getLength());
            decodeCommunication(recivedMessage, grid);
            System.out.println(recivedMessage);
         }catch (IOException e){
            e.printStackTrace();
         }

      }
      ds.close();
   }

   public void decodeCommunication(String message, Grid grid)
   {
      int posX = 0;
      int posY = 0;
      int colorNum = 0;
      String[] splitMessage;

      try {
         splitMessage = message.split(":");
         posX = Integer.parseInt(splitMessage[0]);
         posY = Integer.parseInt(splitMessage[1]);
         colorNum = Integer.parseInt(splitMessage[2]);

      } catch (Exception e){
         System.out.println("ERROR");
      }
      if (posX <= 1 || posX >= 199 || posY <= 1 || posY >= 199)
         return;

     grid.placePlayerCommunication(posX, posY, colorNum);
   }
}
