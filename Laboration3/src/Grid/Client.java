package Grid;
import java.io.IOException;
import java.net.*;

/**
 * Sends a datagramPacket with UDP with the position and color of the player to the server.
 * @author Pontus Eriksson Jirbratt
 */
public class Client
{
   public Client(String posX, String posY, String color) throws IOException
   {
      sendMessage(posX, posY, color);
   }
   public void sendMessage(String posX, String posY, String colorNum)throws IOException{
      DatagramSocket ds;
      String message;

      ds = new DatagramSocket();
      message = posX + ":" + posY + ":" + colorNum;
      InetAddress ip = InetAddress.getByName("127.0.0.1");

      DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, 3000);
      ds.send(dp);
      ds.close();
   }
}
