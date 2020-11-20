/**
 * Initializes the weather application and gets the specified temperature for a given hour
 * in the HashMap.
 *
 * @author Pontus Eriksson Jirbratt, ponjir-7
 */

import java.util.HashMap;
import java.util.Map;

public class Controller extends Model {
   public static int flag;

   /**
    * Iniziates the hashmaps for each location by using model to get the location
    * and then calling API to fill the HashMap.
    */
   public static void iniziateHashMaps() {

      flag = 1;
      parse("Kage");
      double lat = Double.parseDouble(latitude);
      double lon = Double.parseDouble(longitude);
      API.connection(lat, lon); //Kage

      flag = 2;
      parse("Stockholm");
      lat = Double.parseDouble(latitude);
      lon = Double.parseDouble(longitude);
      API.connection(lat, lon); //Stockholm

      flag = 3;
      parse("Skelleftea");
      lat = Double.parseDouble(latitude);
      lon = Double.parseDouble(longitude);
      API.connection(lat, lon); //Skellefte
   }

   /**
    * Takes in the hashmap as argument and loops trough it to match the input time from the user
    * to the temperature of that time.
    * @param weatherTimeAndTemp HashMap containing temperature and time
    * @return temperature for the selected time as a string
    */

   public static String getTemp(HashMap<String, String> weatherTimeAndTemp) {
      String selectedTime = GUI.getSelectedTime();
      selectedTime = selectedTime + ":00Z";
      String[] keyList;
      System.out.println(selectedTime);

      int j = 1;

      for (Map.Entry<String, String> entry : weatherTimeAndTemp.entrySet()) {
         String key = entry.getKey();
         keyList = key.split("T");
         String value = entry.getValue();
         System.out.println("Tha Key " + key + " Tha Value " + value);
         if (keyList[1].equals(selectedTime)) {
            System.out.println("The right value" + value);
            return value;
         }
      }
      return "";
   }

   /**
    * Updates the hasmaps after x minutes selected from the user
    */

   public static void updateHashMaps(){
      String updateTime = GUI.updateTextField.getText();
      int updateTimeI = Integer.parseInt(updateTime);
      new java.util.Timer().schedule(
              new java.util.TimerTask() {
                 @Override
                 public void run() {
                    Controller.iniziateHashMaps();
                 }
              },
              updateTimeI * 60000);
   }
}
