/**
 *Has all the storage of weather data, aka the HashMaps and have a different one for each location.
 * Parses trough the xml file to get the right coordinates for a given location name
 * @author Pontus Eriksson Jirbratt, ponjir-7
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class Model
{
   public static HashMap<String, String> weatherTimeAndTempSkellefte = new HashMap<>();
   public static HashMap<String, String> weatherTimeAndTempStockholm = new HashMap<>();
   public static HashMap<String, String> weatherTimeAndTempKage = new HashMap<>();
   public static String longitude;
   public static String latitude;
   public static String locationName;

   /**Takes the selected location as argument and parses out the coordinates from location.xml for each location.
    *
    * @param selectedLocation the selected location, example Stockholm
    * @return null
    */
   //
   public static String parse(String selectedLocation) {
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
         Document doc = builder.parse("Laboration2/src/location.xml");

         NodeList koordinatesList = doc.getElementsByTagName("location");
         NodeList locationList = doc.getElementsByTagName("locality");

         for (int i = 0; i < koordinatesList.getLength(); i++) {
            locationName = ((Element) locationList.item(i)).getAttribute("name");
            if (locationName.equals(selectedLocation)) {

               longitude = ((Element) koordinatesList.item(i)).getAttribute("longitude");
               latitude = ((Element) koordinatesList.item(i)).getAttribute("latitude");
               //System.out.println("lon " + longitude + " lat "  + latitude);
            }
         }

      } catch (ParserConfigurationException | SAXException | IOException e) {
         e.printStackTrace();
      }
      return null;
   }
}
