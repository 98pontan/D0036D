package Grid;
import java.io.IOException;
import java.net.*;


public class Client
{
   private String posX;
   private String posY;
   private String color;

   public Client(String posX, String posY, String color) throws IOException
   {
      this.posX = posX;
      this.posY = posY;
      this.color = color;

      sendMessage(posX, posY, color);

   }
   public void sendMessage(String posX, String posY, String color)throws IOException{
      DatagramSocket ds = new DatagramSocket();
      String str = posX + ":" + posY + ":" + color;
      InetAddress ip = InetAddress.getByName("127.0.0.1");

      DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(), ip, 3000);
      ds.send(dp);

      ds.close();
   }
}
