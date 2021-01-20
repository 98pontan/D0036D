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
      int x = 0;
      int y = 0;

      Color c;
      String[] splitMessage;

      try {
         splitMessage = message.split(":");
         x = Integer.parseInt(splitMessage[0]);
         y = Integer.parseInt(splitMessage[1]);
      } catch (Exception e){
         System.out.println("ERROR");
      }
      if (x <= 1 || x >= 199 || y <= 1 || y >= 199)
         return;
     grid.placePlayerCommunication(x, y, Color.BLUE);
   }
}
