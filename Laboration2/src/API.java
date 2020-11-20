/**
 *Asks for and parses an xml file from yr.no
 * @author Pontus Eriksson Jirbratt, ponjir-7
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class API extends Controller {
    /**
     * Gets an xml string from yr.no containg weather information from the selected location.
     * Calls parse to parse the xml string to get the time and temperature.
     * @param lat latitude, comes from an xml file that is parsed in Model
     * @param lon longitue, comes from an xml file that is parsed in Model
     * @return the HashMap of the current location
     */

   public static HashMap connection(double lat, double lon) {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("https://api.met.no/weatherapi/locationforecast/2.0/classic?lat=" + lat + "4&lon=" + lon))
              .build();
      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenApply(API::parse)
              .join();
      if (flag == 1) {
         return weatherTimeAndTempKage;
      } else if (flag == 2) {
         return weatherTimeAndTempStockholm;
      } else {
         return weatherTimeAndTempSkellefte;
      }
   }

    /**
     *Parses the xml string from yr.no with the use of Document builderFactory
     * loops 24 times due to 24 hours is a day
     * puts in the temperature and time in a hashmap depening whitch on the location
     * @param responseBody xml string with weather inforamtion
     * @return null
     */
   public static String parse(String responseBody) {
      DocumentBuilderFactory factory = new DocumentBuilderFactory() {
         @Override
         public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
            return null;
         }

         @Override
         public void setAttribute(String name, Object value) throws IllegalArgumentException {

         }

         @Override
         public Object getAttribute(String name) throws IllegalArgumentException {
            return null;
         }

         @Override
         public void setFeature(String name, boolean value) throws ParserConfigurationException {

         }

         @Override
         public boolean getFeature(String name) throws ParserConfigurationException {
            return false;
         }
      }.newInstance();
      try {
         DocumentBuilder builder = factory.newDocumentBuilder();
         InputSource is = new InputSource(new StringReader(responseBody));
         Document doc = builder.parse(is);
         NodeList weatherList = doc.getElementsByTagName("temperature");
         System.out.println("IM PARSING");
         for (int i = 0; i < 24; i++) {

            String time = ((Element) (((Element) weatherList.item(i)).getParentNode().getParentNode())).getAttribute("from");

            Node currentTempNode = weatherList.item(i);
            Element temperature = (Element) currentTempNode;
            String temperatureValue = temperature.getAttribute("value");
            NodeList temperatureList = temperature.getChildNodes();

            if (flag == 1) {
               weatherTimeAndTempKage.put(time, temperatureValue);
               System.out.println("Updated data Kage");
            } else if (flag == 2) {
               weatherTimeAndTempStockholm.put(time, temperatureValue);
               System.out.println("Updated data Stockholm");
            } else if (flag == 3) {
               weatherTimeAndTempSkellefte.put(time, temperatureValue);
               System.out.println("Updated data Skellefte");
            }
         }
      } catch (ParserConfigurationException | SAXException | IOException e) {
         e.printStackTrace();
      }
      return null;
   }
}
