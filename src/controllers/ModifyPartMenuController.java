/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.inventory;

/**
 * FXML Controller class
 *
 * @author jonat
 */
public class ModifyPartMenuController implements Initializable {

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
    private TextField swtichingText;

    @FXML
    private TextField minText;

    @FXML
    private RadioButton inHouseButton;

    @FXML
    private RadioButton outsourcedButton;

    @FXML
    private Text errorText;

    @FXML
    private ToggleGroup switchingGroup;

    private Part originalPart;

    /**
     * Get the current part from the previous screen
     *  then load the values in the bars to be modified
     * @param p part
     */
    public void setInPart(Part p) {
        idText.setText(String.valueOf(p.getId()));
        nameText.setText(String.valueOf(p.getName()));
        inventoryText.setText(String.valueOf(p.getStock()));
        priceText.setText(String.valueOf(p.getPrice()));
        maxText.setText(String.valueOf(p.getMax()));
        minText.setText(String.valueOf(p.getMin()));
        if (p instanceof InHouse) {
            swtichingText.setText(String.valueOf(((InHouse) p).getMachineId()));
            inHouseButton.setSelected(true);
            switchingLabel.setText("Machine ID");
        } else{
            swtichingText.setText(String.valueOf(((Outsourced) p).getCompanyName()));
            outsourcedButton.setSelected(true);
            switchingLabel.setText("Company Name");
        }

        originalPart = p;

    }

    /**
     * Get the saved part in case the user cancels
     *  or exits before saving
     * @return original part
     */
    public Part getSavedPart() {
        return originalPart;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }

    @FXML
    private void onActionInHouse(ActionEvent event) {
        switchingLabel.setText("Machine ID");
    }

    @FXML
    private void onActionOutsourced(ActionEvent event) {
        switchingLabel.setText("Company Name");
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException, Exception {
        try {
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                int id = Integer.parseInt(idText.getText());
                String name = nameText.getText();
                if(name.matches("-?(0|[1-9]\\d*)")){ //To prevent an integer from being accepted in name
                    throw new Exception();
                }
                int stock = Integer.parseInt(inventoryText.getText());
                double price = Double.parseDouble(priceText.getText());
                int min = Integer.parseInt(minText.getText());
                int max = Integer.parseInt(maxText.getText());
                String text = swtichingText.getText();
                Part newPart;

                
                if (min > max || min > stock || stock > max) {
                    Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                    warningAlert.setTitle("Warning Dialog");
                    warningAlert.setContentText("Min must be less than stock. Stock must be less than max.");
                    warningAlert.showAndWait();

                } else {
                    if (inHouseButton.isSelected()) {

                        int inHouseID = Integer.parseInt(text);
                        newPart = new InHouse(id, name, price,
                                stock, min, max,
                                inHouseID);

                        inventory.addPart(newPart);

                    } else {

                        newPart = new Outsourced(id, name, price,
                                stock, min, max, text);
                        inventory.addPart(newPart);
                    }
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid values for all fields");
            errorText.setText("Name must be a string\n"
                    + "Inventory must be an Integer\n"
                    + "Price must be a double\n"
                    + "Min must be an Integer\n"
                    + "Max must be an Integer"
                    + "Machine ID must be an Integer\n"
                    + "Company name must be a string");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {

        inventory.addPart(getSavedPart());
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
