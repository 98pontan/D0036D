/**
 * Its the graphical user interface, adds all the buttons, textboxes and so on.
 * Takes the users input and sends them to the controller to be handeld.
 * Updates are handeld here
 * @author Pontus Eriksson Jirbratt, ponjir-7
 */

import javax.swing.*;
import java.awt.event.ActionEvent;


public class GUI
{
   private static final String[] TIME = {"01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
           "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
           "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
           "23:00", "00:00"}; 

   public static String selectedTime;
   public static String temperature;

   public static JTextField updateTextField;

   private static JLabel weatherLocationTitle;
   private static JLabel weatherLocation;
   private static JLabel weatherTempTitle;
   private static JLabel weatherTemp;

   private static JButton updateButton;
   private static JButton locationSkellefte;
   private static JButton locationStockholm;
   private static JButton locationKage;

   private static JScrollBar chooseTime;

   private static JTextField givenTime = new JTextField(); //Shows the time the user selected
   private static JTextField selectionTitle = new JTextField("Choose Time"); //Title for the button that users use to selec time
   private static JComboBox comboBox = new JComboBox(); //The combobox that users schrool through to select time
   private static int count = 0; // a counter for adding the diffrent times into the combobox

    /**Initiates the GUI and adds text to function as titles for location and temperature, also adds scrollbar
     * and update button with a textfield above the button to take input.
     */
   public static void initiateGUI() {
      JPanel panel = new JPanel();
      JFrame frame = new JFrame();

      setLocation(panel);
      dropDownButton(panel);

      frame.setSize(400, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.add(panel);

      panel.setLayout(null);


      weatherLocationTitle = new JLabel("Weather in");
      weatherLocationTitle.setBounds(10, 20, 80, 25);
      panel.add(weatherLocationTitle);

      weatherLocation = new JLabel("");
      weatherLocation.setBounds(80, 20, 80, 25);
      panel.add(weatherLocation);

      weatherTempTitle = new JLabel("Temperature");
      weatherTempTitle.setBounds(10, 60, 80, 25);
      panel.add(weatherTempTitle);

      weatherTemp = new JLabel(temperature);
      weatherTemp.setText("");
      weatherTemp.setBounds(90, 60, 80, 25);
      panel.add(weatherTemp);

      chooseTime = new JScrollBar();
      chooseTime.setBounds(200, 150, 80, 25);

      updateTextField = new JTextField("Min until update");
      updateTextField.setBounds(10, 180, 115, 25);
      panel.add(updateTextField);

      updateButton = new JButton(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Controller.updateHashMaps();
         }
      });
      updateButton.setText("Update");
      updateButton.setBounds(25, 210, 80, 25);
      panel.add(updateButton);
   }

    /**Adds the different buttons for locations and calls Controller to start the process of getting the
     * right temperature displayed.
     * @param panel adds the location buttons
     */
   // The function
   public static void setLocation(JPanel panel) {
      locationKage = new JButton(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            weatherLocation.setText("Kage");
            temperature = Controller.getTemp(Controller.weatherTimeAndTempKage);
            weatherTemp.setText(temperature);
         }
      });
      locationKage.setText("Kage");
      locationKage.setBounds(260, 100, 120, 25);
      panel.add(locationKage);

      locationStockholm = new JButton(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            weatherLocation.setText("Stockholm");
            temperature = Controller.getTemp(Controller.weatherTimeAndTempStockholm);
            weatherTemp.setText(temperature);
         }
      });
      locationStockholm.setText("Stockholm");
      locationStockholm.setBounds(260, 140, 120, 25);
      panel.add(locationStockholm);


      locationSkellefte = new JButton(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            weatherLocation.setText("Skellefteå");
            temperature = Controller.getTemp(Controller.weatherTimeAndTempSkellefte);
            weatherTemp.setText(temperature);

         }
      });
      locationSkellefte.setText("Skellefteå");
      locationSkellefte.setBounds(260, 180, 120, 25);
      panel.add(locationSkellefte);
   }

   /**Handels the scrool and adds all the different times into it as well as when the user has selected a time
    * @param panel added combobox, selectionTitle and givenTime
    */
   private static void dropDownButton(JPanel panel) {

      for (int i = 0; i < TIME.length; i++)
         comboBox.addItem(TIME[count++]);
      selectionTitle.setEditable(false);

      comboBox.addActionListener(new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            selectedTime = (String) ((JComboBox) e.getSource()).getSelectedItem();
            givenTime.setText("You Selected : " + selectedTime);
         }
      });

      comboBox.setBounds(130, 200, 120, 25);
      selectionTitle.setBounds(130, 170, 120, 25);
      givenTime.setBounds(130, 140, 120, 25);
      panel.add(comboBox);
      panel.add(selectionTitle);
      panel.add(givenTime);

   }

   /**
    * getter for selectedTime
    * @return the users selected time
    */
   public static String getSelectedTime() {
      return selectedTime;
   }

}

