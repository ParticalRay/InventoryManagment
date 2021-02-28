
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.inventory;

/**
 * FXML Controller class
 *
 * @author Jonathon Makepeace
 */
public class AddPartMenuController implements Initializable {

    Stage stage;
    Parent scene;
      @FXML
    private Label switchingLabel;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField inventoryText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField switchingText;

    @FXML
    private TextField minText;

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;

    private Button saveButton;

    private Button cancelText;

    @FXML
    private Text errorText;
    @FXML
    private ToggleGroup locationGroup;
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    

    /**
     * Changing text to machine id
     * @param event radio button
     */
    @FXML
    private void onActionInHouse(ActionEvent event) {
        switchingLabel.setText("Machine ID");
    }

    /**
     * Changing text to Company name
     * @param event radio button
     */
    @FXML
    private void onActionOutsourced(ActionEvent event) {
        switchingLabel.setText("Company Name");
    }

    /**
     * Take in all given fields to create an InHouse or Outsourced
     *  object based on whats given. 
     * @param event button clicked to save object
     * @throws IOException Exception clause in case something is 
     *  entered wrong or values do not match intended use. 
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try{
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/MainMenu.fxml"));
                loader.load();
                MainMenuController MMCController = loader.getController();
                int id = MMCController.getID()+3;
                String name = nameText.getText();
                
                if(name.matches("-?(0|[1-9]\\d*)")){ //To prevent an integer from being accepted in name
                    throw new Exception();
                }
                
                int stock = Integer.parseInt(inventoryText.getText());
                double price = Double.parseDouble(priceText.getText());
                int min = Integer.parseInt(minText.getText());
                int max = Integer.parseInt(maxText.getText());
                
                
                if(min>max || min>stock || stock>max){
                    Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                    warningAlert.setTitle("Warning Dialog");
                    warningAlert.setContentText("Min must be less than stock. Stock must be less than max.");
                    warningAlert.showAndWait();

                }else{

                      if(inHouseButton.isSelected()){

                        int inHouseID = Integer.parseInt(switchingText.getText()); 
                        Part newPart = new InHouse(id, name, price,
                                   stock, min, max,
                                    inHouseID);
                        inventory.addPart(newPart);
                        }else{
                        String company = switchingText.getText();
                        if(company.matches("-?(0|[1-9]\\d*)")){ //To prevent an integer from being accepted in name
                            throw new Exception();
                        }
                        Part newPart = new Outsourced(id,name,price,
                                            stock, min, max, company);
                        inventory.addPart(newPart);
                    }

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            

            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each field");
            errorText.setText("Name must be a string\n"
                    + "Inventory must be an Integer\n"
                    + "Price must be a double\n"
                    + "Min must be an Integer\n"
                    + "Max must be an Integer\n"
                    + "Machine ID must be an Integer\n"
                    + "Company name must be a string");
            alert.showAndWait();
        }
    }

    /**
     * Cancel creation and return to main menu
     * @param event button clicked
     * @throws IOException Change in views
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    
}
